package com.appa.snoop.presentation.ui.product

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.appa.snoop.presentation.navigation.Router
import com.appa.snoop.presentation.ui.category.utils.convertNaverUrl
import com.appa.snoop.presentation.ui.product.component.AlarmSnackBar
import com.appa.snoop.presentation.ui.product.component.ButtonView
import com.appa.snoop.presentation.ui.product.component.BuyTimingView
import com.appa.snoop.presentation.ui.product.component.PriceGraph
import com.appa.snoop.presentation.ui.product.component.ProductDetailView
import com.appa.snoop.presentation.ui.product.component.RecommendListView
import com.appa.snoop.presentation.ui.product.component.RegistAlertDialog
import com.appa.snoop.presentation.ui.product.component.graph.DataPoint
import com.appa.snoop.presentation.ui.product.data.Period
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.PriceUtil
import com.appa.snoop.presentation.util.effects.ProductLaunchedEffect
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "[김진영] ProductDetailScreen"

@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    showSnackBar: (String) -> Unit,
    productViewModel: ProductViewModel = hiltViewModel()
) {
    val productCode = navController.currentBackStackEntry?.arguments?.getString("productCode")
    val product by productViewModel.productState.collectAsState()
    val timing by productViewModel.timingState.collectAsState()
    val recommendProduct by productViewModel.recommendProductState.collectAsState()
    val productGraph by productViewModel.productGraphState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val snackState = remember { SnackbarHostState() }
    var selectChip by remember { mutableStateOf(Period.WEEK.label) }
    var graphData by remember {
        mutableStateOf(
            listOf(
                DataPoint(0f, 1000f)
            )
        )
    }
    var alertPrice by remember { mutableStateOf(0) }
    var alarmChecked by remember { mutableStateOf(false) }
    ProductLaunchedEffect(navController = navController)
    LaunchedEffect(productCode) {
        Log.d(TAG, "ProductDetailScreen: $productCode")
        if (productCode != null) {
            productViewModel.getProductDetail(productCode)
            productViewModel.getProductTiming(productCode)
            productViewModel.getRecommendProduct(productCode)
            productViewModel.getProductGraph(productCode, Period.WEEK.label)
        }
    }

    LaunchedEffect(selectChip) {
        if (productCode != null) {
            productViewModel.getProductGraph(productCode, selectChip)
        }
    }

    LaunchedEffect(productGraph) {
        graphData = productGraph.mapIndexed { index, graphItem ->
            DataPoint(index.toFloat(), graphItem.price.toFloat())
        }
        Log.d(TAG, "ProductDetailScreen: $graphData")
    }

    LaunchedEffect(product) {
        alarmChecked = product.alertYn
        Log.d(TAG, "ProductDetailScreen: $alarmChecked, ${product.alertYn}")
    }

    // 다이얼로그
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog && productCode != null) {
        RegistAlertDialog(visible = showDialog,
            focusManager = focusManager,
            onValueChanged = { price ->
                alertPrice = PriceUtil.parseFormattedPrice(price)
            },
            onWishboxRequest = {
                productViewModel.registWishProduct(productCode, 0)
                showSnackBar("찜목록에 등록하였습니다!")
                showDialog = !showDialog
            },
            onAlertRequest = {
                alarmChecked = !alarmChecked
                productViewModel.registWishProduct(productCode, alertPrice)
                coroutineScope.launch {
                    snackState.showSnackbar(
                        ""
                    )
                }
                showDialog = !showDialog
            },
            onDismissRequest = {
                showDialog = !showDialog
            }
        )
    }

    // 알람 클릭
    var alarmClicked by remember { mutableStateOf(false) }
    LaunchedEffect(alarmClicked) {

        Log.d(TAG, "alarmChecked 확인 : $alarmChecked")

        // 알람 클릭이 되었을때 SnackBar Show
        if (alarmClicked && !alarmChecked) {
            showDialog = true
            alarmClicked = false
//                        coroutineScope.launch {
//                            showDialog = true
//                            val job = coroutineScope.launch {
//                                snackState.showSnackbar(
//                                    "",
//                                    duration = SnackbarDuration.Indefinite
//                                )
//                            }
//                            delay(1500L)
//                            job.cancel()
//                        }
        } else if(alarmClicked && alarmChecked) {
            // 알림이 체크 되어있는 상태에서 알림 클릭
            alarmClicked = false
            alarmChecked = false
            coroutineScope.launch {
                if (productCode != null) {
                    Log.d(TAG, "ProductDetailScreen: 알림 해제!!")
                    productViewModel.registWishProduct(productCode, 0)
                }
                showSnackBar("지정가 알림이 취소되었습니다!")
            }
        }
    }


    // TODO(Domain model 가져오면 추후에 교체)
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = WhiteColor)
            .padding(start = 16.sdp, end = 16.sdp, bottom = 8.sdp),
    ) {
        Column(
            modifier = modifier
                .weight(1f)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.sdp)
        ) {
            ProductDetailView(
                Modifier,
                product,
                onSharedClicked = { url ->

                }
            )
            BuyTimingView(modifier = Modifier, timing = timing.timing)
            if (productGraph.isNotEmpty()) {
                PriceGraph(modifier = Modifier,
                    lines = listOf(graphData),
                    productGraph = productGraph,
                    selectChipLabel = selectChip,
                    selectChips = {
                        selectChip = it.label
                    })
            }

            RecommendListView(modifier = Modifier, recommendProduct, onItemClicked = { code ->
                val route = Router.CATEGORY_PRODUCT_ROUTER_NAME.replace(
                    "{productCode}",
                    code
                )
                navController.navigate(route)
            })
        }
        AlarmSnackBar(
            hostState = snackState,
            price = PriceUtil.formatPrice(alertPrice.toString()),
            percent = PriceUtil.calculateDiscountPercentage(product.price, alertPrice).toString()
        )
        Spacer(modifier = Modifier.height(16.sdp))
        ButtonView(
            alarmChecked = alarmChecked,
            onBuyClicked = {
                val url =
                    if (product.provider == "네이버")
                        convertNaverUrl(
                            product.productLink
                        )
                    else
                        product.productLink

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            },
            onAlarmClicked = {
                if (snackState.currentSnackbarData == null) {
                    alarmClicked = true
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewProductDetailScreen() {
//    ProductDetailScreen(navController = rememberNavController())
}