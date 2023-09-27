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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.appa.snoop.presentation.ui.category.utils.convertNaverUrl
import com.appa.snoop.presentation.ui.product.component.AlarmSnackBar
import com.appa.snoop.presentation.ui.product.component.ButtonView
import com.appa.snoop.presentation.ui.product.component.BuyTimingView
import com.appa.snoop.presentation.ui.product.component.PriceGraph
import com.appa.snoop.presentation.ui.product.component.ProductDetailView
import com.appa.snoop.presentation.ui.product.component.RecommendListView
import com.appa.snoop.presentation.ui.product.component.graph.DataPoint
import com.appa.snoop.presentation.ui.product.data.Period
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.effects.ProductLaunchedEffect
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "[김진영] ProductDetailScreen"

@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    productViewModel: ProductViewModel = hiltViewModel()
) {
    val productCode = navController.currentBackStackEntry?.arguments?.getString("productCode")
    val product by productViewModel.productState.collectAsState()
    val timing by productViewModel.timingState.collectAsState()
    val recommendProduct by productViewModel.recommendProductState.collectAsState()
    val productGraph by productViewModel.productGraphState.collectAsState()
    var selectChip by remember { mutableStateOf(Period.WEEK.label) }
    var graphData by remember {
        mutableStateOf(
            listOf(
                DataPoint(0f, 3500f),
                DataPoint(1f, 3800f),
                DataPoint(2f, 4000f),
                DataPoint(3f, 5000f),
                DataPoint(4f, 5000f),
                DataPoint(5f, 4900f),
                DataPoint(6f, 4700f),
            )
        )
    }

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

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var alarmChecked by remember { mutableStateOf(false) }
    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    // TODO(Domain model 가져오면 추후에 교체)
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = WhiteColor)
            .padding(16.sdp),
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
            PriceGraph(modifier = Modifier,
                lines = listOf(graphData),
                productGraph = productGraph,
                selectChipLabel = selectChip,
                selectChips = {
                    selectChip = it.label
                })
            RecommendListView()
        }
        AlarmSnackBar(
            hostState = snackState,
            price = "2,640,000",
            percent = 10
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
                    alarmChecked = !alarmChecked
                    // 알람을 클릭이 되었을때 SnackBar Show
                    if (alarmChecked) {
                        coroutineScope.launch {
                            val job = coroutineScope.launch {
                                snackState.showSnackbar(
                                    "",
                                    duration = SnackbarDuration.Indefinite
                                )
                            }
                            delay(1500L)
                            job.cancel()
                        }
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewProductDetailScreen() {
    ProductDetailScreen(navController = rememberNavController())
}