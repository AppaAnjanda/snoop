package com.appa.snoop.presentation.ui.chatting

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.model.chat.ChatItem
import com.appa.snoop.domain.usecase.chatting.GetPreChattingListUseCase
import com.appa.snoop.domain.usecase.register.EmailInputUseCase
import com.appa.snoop.domain.usecase.register.GetEmailUseCase
import com.appa.snoop.domain.usecase.register.GetLoginStatusUseCase
import com.appa.snoop.presentation.ui.category.CategoryViewModel
import com.appa.snoop.presentation.ui.category.utils.ProductKeywordPagingDataSource
import com.appa.snoop.presentation.ui.chatting.utils.ChatPagingDataSource
import com.appa.snoop.presentation.util.PriceUtil
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import javax.inject.Inject


private const val TAG = "[김희웅] ChattingViewModel"
@SuppressLint("MutableCollectionMutableState")
@HiltViewModel
class ChattingViewModel @Inject constructor(
    private val getLoginStatusUseCase: GetLoginStatusUseCase,
    private val getEmailUseCase: GetEmailUseCase,
    private val getPreChattingListUseCase: GetPreChattingListUseCase
) : ViewModel() {

    companion object {
        const val PAGE_SIZE = 50
    }

    var chatList = mutableStateOf(mutableListOf<ChatItem>())
        private set

    var chatRecieveState by mutableStateOf(0)
        private set
    fun recieveChat() {
        chatRecieveState++
    }

    var email by mutableStateOf("")
        private set
    suspend fun getEmailInfo() {
        email = getEmailUseCase.invoke()
    }

    /*
    -------------------------------------------------------------------------
                                STOMP 통신
    -------------------------------------------------------------------------
     */
    private var roomNumber = mutableStateOf(0)

    fun setRoomNumber(num: Int) {
        roomNumber.value = num
    }

    // 주소 선언 & 클라이언트 생성 부
    private val baseUrl = "ws://52.78.159.20:8080/ws" // baseUrl
    private val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, baseUrl)

    @SuppressLint("CheckResult")
    fun runStomp() {
        /**
         * 메시지 수신 (콜백)
         * 전달되면 해당 람다 표현식 내의 코드가 자동 실행
         */
        stompClient.topic("/sub/chat/room/${roomNumber.value}").subscribe { topicMessage ->
            Log.d(TAG, "메시지 받음!!" + topicMessage.payload)
            val jsonPayload = topicMessage.payload
            val jsonObject = JSONObject(jsonPayload)

            val roomidx = jsonObject.getInt("roomidx")
            val email = jsonObject.getString("email")
            val sender = jsonObject.getString("sender")
            val msg = jsonObject.getString("msg")
            val imageUrl = jsonObject.getString("imageUrl")
            val time = jsonObject.getString("time")

            Log.d(TAG, "runStomp: $sender / $msg")

            /**
             * 이 부분 중요!!
             * 다른 배열에 내용을 담은 후 새로운 메시지 내용을 그 곳에 추가
             * 그리고 새로운 내용까지 포함한 데이터를 postValue로 변경&추가 되었음을 알림
             * -> LiveData가 인식하고 동작
             */
            chatList.value.add(0,
                ChatItem(
                    roomidx = roomidx,
                    email = email,
                    sender = sender,
                    msg = msg,
                    imageUrl = imageUrl,
                    time = time
                ),
            )
            recieveChat()
        }

        stompClient.connect()

        stompClient.lifecycle().subscribe { lifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.d(TAG, "opened")
                }
                LifecycleEvent.Type.CLOSED -> {
                    Log.d(TAG, "closed")
                }
                LifecycleEvent.Type.ERROR -> {
                    Log.d(TAG, "error")
                    Log.i(TAG, lifecycleEvent.exception.toString())
                }
                else -> {
                    Log.d(TAG, lifecycleEvent.message)
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    fun sendStomp(msg: String) {
//        val sender = ApplicationClass.sharedPreferences.getString("userEmail")!!

        val data = JSONObject()
        data.put("roomidx", roomNumber.value)
        //TODO 바꿔야됨
        data.put("email", email)
        data.put("message", msg)

        // 메시지를 보낼 엔드포인트 URL
        val messageSendEndpoint = "/pub/chat/${roomNumber.value}"

        // stompClient.send() 함수로 메시지를 전송
        stompClient.send(messageSendEndpoint, data.toString()).subscribe(
            {
                // 성공시
                Log.d(TAG, "Message sent successfully")
            },
            { error ->
                // 실패시
                Log.d(TAG, error.toString())
            },
        )
    }

//    suspend fun sendTest(msg: String) {
//        val cur = ChatItem(4, "skdi550@nate.com", "희웅", msg, "", "오후 6:02")
//
//        val chatContent = _chatListState.value
//        chatContent.add(0, cur)
//
//        _chatListState.emit(chatContent)
//    }


    private var compositeDisposable: CompositeDisposable? = null

    private val mGson = GsonBuilder().create()


    @SuppressLint("CheckResult")
    fun connectStomp() {
        resetSubscriptions();

//        stompClient.topic("/sub/chat/4").subscribe() {topicMessage ->
//            Log.d(TAG, "message Recieve: ${topicMessage.payload}")
//        }

//        viewModelScope.launch {
//            // TODO 헤더 필요 유무?
//            val accessToken = getLoginStatusUseCase.invoke()
//            val headerList = arrayListOf<StompHeader>()
//            headerList.add(StompHeader("Authorization", "Bearer $accessToken"))
//            stompClient.connect(headerList)
//        }

        stompClient.lifecycle().subscribe { lifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.i("OPEND", "!!")
                }
                LifecycleEvent.Type.CLOSED -> {
                    resetSubscriptions();
                    Log.i("CLOSED", "!!")

                }
                LifecycleEvent.Type.ERROR -> {
                    Log.i("ERROR", "!!")
                    Log.e("CONNECT ERROR", lifecycleEvent.exception.toString())
                }
                else ->{
                    Log.i("ELSE", lifecycleEvent.message)
                }
            }
        }

        // TODO 통신 코드로 바꿔야함
//        val data = JSONObject()
//        data.put("roomidx", 4)
//        data.put("email", "skdi550@nate.com")
//        data.put("sender", "희웅")
//        data.put("msg", "test chat")
//        data.put("imageUrl", "")
//        data.put("time", "")

        val dispTopic: Disposable = stompClient.topic("/sub/chat/room/4")
            .subscribe({ topicMessage ->
//                Log.d(TAG, "Received " + topicMessage.getPayload())
                // TODO 대화 내용 List에 추가
                addItem(mGson.fromJson(topicMessage.getPayload(), ChatItem::class.java))
            }) { throwable -> Log.e(TAG, "Error on subscribe topic", throwable) }

        compositeDisposable!!.add(dispTopic)

        stompClient.connect()
//        stompClient.send("/pub/chat/4", data.toString()).subscribe()
    }

    fun disconnectStomp() {
        stompClient.disconnect()
    }

    private fun addItem(chatItem: ChatItem) {
//        mDataSet.add(chatItem.getEcho() + " - " + mTimeFormat.format(Date()))
        // TODO 리스트 안에 담는 코드
    }


    private fun resetSubscriptions() {
        if (compositeDisposable != null) {
            compositeDisposable!!.dispose()
        }
        compositeDisposable = CompositeDisposable()
    }

    // 채팅 내역 페이징
    val _pagingDataFlow = MutableStateFlow<PagingData<ChatItem>>(PagingData.empty())
    val pagingDataFlow = _pagingDataFlow.asStateFlow()

    fun getChattingListPaging(
        roomId: Int
    ): Flow<PagingData<ChatItem>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
            ChatPagingDataSource(
                getPreChattingListUseCase,
                roomId
            )
        }.flow.cachedIn(viewModelScope)
    }

    fun getChatList(roomId: Int) {
        viewModelScope.launch {
            getChattingListPaging(roomId).collectLatest { pagingData ->
                _pagingDataFlow.emit(pagingData)
            }
        }
    }
}