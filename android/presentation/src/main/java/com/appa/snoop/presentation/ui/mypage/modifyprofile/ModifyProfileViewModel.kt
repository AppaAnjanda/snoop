package com.appa.snoop.presentation.ui.mypage.modifyprofile

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.member.ChangedImage
import com.appa.snoop.domain.model.member.ChangedNickname
import com.appa.snoop.domain.model.member.Nickname
import com.appa.snoop.domain.usecase.member.UpdateMemberImageUseCase
import com.appa.snoop.domain.usecase.member.UpdateMemberNicknameUseCase
import com.appa.snoop.presentation.util.Converter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "[김진영] ModifyProfileViewModel"

@HiltViewModel
class ModifyProfileViewModel @Inject constructor(
    private val updateMemberNicknameUseCase: UpdateMemberNicknameUseCase,
    private val updateMemberImageUseCase: UpdateMemberImageUseCase
) : ViewModel() {

    private val _changedNicknameState = MutableStateFlow(ChangedNickname("", ""))
    val changedNicknameState: StateFlow<ChangedNickname> = _changedNicknameState.asStateFlow()

    private val _changedImageState = MutableStateFlow(ChangedImage(""))
    val changedImageState: StateFlow<ChangedImage> = _changedImageState.asStateFlow()

    fun changeNickname(nickname: Nickname) {
        viewModelScope.launch {
            val result = updateMemberNicknameUseCase.invoke(nickname = nickname)

            when (result) {
                is NetworkResult.Success -> {
                    _changedNicknameState.emit(result.data)
                    Log.d(TAG, "changeNickname: ${result.data}")
                }

                else -> {
                    Log.d(TAG, "getMemberInfo: 닉네임 변경 실패")
                }
            }
        }
    }

    fun changeImage(context: Context, uri: Uri) {
        val imagePath = Converter.getRealPathFromUri(context, uri)
        if (imagePath != null) {
            viewModelScope.launch {
                val result = updateMemberImageUseCase.invoke(file = imagePath)

                when (result) {
                    is NetworkResult.Success -> {
                        _changedImageState.emit(result.data)
                        Log.d(TAG, "changeImage: ${result.data}")
                    }

                    else -> {
                        Log.d(TAG, "changeImage: 이미지 변경 실패")
                    }
                }
            }
        } else {
            Log.d(TAG, "changeImage: 이미지 경로를 얻을 수 없습니다.")
        }
    }
}