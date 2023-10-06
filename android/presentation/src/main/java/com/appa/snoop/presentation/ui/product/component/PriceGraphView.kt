package com.appa.snoop.presentation.ui.product.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.appa.snoop.domain.model.product.GraphItem
import com.appa.snoop.presentation.ui.product.component.graph.DataPoint
import com.appa.snoop.presentation.ui.product.component.graph.LineGraph
import com.appa.snoop.presentation.ui.product.component.graph.LinePlot
import com.appa.snoop.presentation.ui.product.data.Period
import com.appa.snoop.presentation.ui.theme.InvalidRedColor_60
import com.appa.snoop.presentation.ui.theme.LightGreyColor
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import com.appa.snoop.presentation.ui.theme.PrimaryColor_30
import com.appa.snoop.presentation.util.DateUtil
import com.appa.snoop.presentation.util.extensions.RoundRectangle
import com.appa.snoop.presentation.util.extensions.toPx
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import java.text.DecimalFormat


@Composable
internal fun PriceGraph(
    modifier: Modifier = Modifier,
    lines: List<List<DataPoint>>,
    productGraph: List<GraphItem>,
    selectChipLabel: String,
    selectChips: (Period) -> Unit,
) {
    val totalWidth = remember { mutableStateOf(0) }
    Column(
        modifier =
        Modifier.onGloballyPositioned {
            totalWidth.value = it.size.width
        }
    ) {
        val xOffset = remember { mutableStateOf(0f) }
        val cardWidth = remember { mutableStateOf(0) }
        val visibility = remember { mutableStateOf(false) }
        val points = remember { mutableStateOf(listOf<DataPoint>()) }
        val density = LocalDensity.current

        Box(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            Surface(modifier = Modifier.align(Alignment.TopEnd)) {
                ChipsSelectable(chips = Period.values().map { it.chip }) {
                    selectChips(Period.values()[it])
                }
            }
            Box(Modifier.height(30.sdp)) {
                if (visibility.value) {
                    Surface(
                        modifier = Modifier
                            .width(152.sdp)
                            .align(Alignment.BottomCenter)
                            .onGloballyPositioned {
                                cardWidth.value = it.size.width

                            }
                            .graphicsLayer(translationX = xOffset.value),
                        shape = RoundRectangle,
                        color = PrimaryColor_30
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(horizontal = 8.sdp, vertical = 8.sdp)
                        ) {
                            val value = points.value
                            if (value.isNotEmpty()) {
                                val time = productGraph[(value[0].x).toInt()].timestamp
                                Text(
                                    text = DateUtil.formatDate(time, selectChipLabel),
                                    style = TextStyle(
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 12.ssp,
                                        color = Color.Gray
                                    )
                                )
                                ScoreRow(value[0].y, Color.Gray)
                            }
                        }
                    }

                }
            }
        }

        val padding = 2.sdp
        LineGraph(
            plot = LinePlot(
                listOf(
                    LinePlot.Line(
                        dataPoints = lines[0],
                        connection = LinePlot.Connection(PrimaryColor),
                        intersection = LinePlot.Intersection(PrimaryColor),
                        highlight = LinePlot.Highlight { center ->
                            val color = PrimaryColor
                            drawCircle(color, 9.dp.toPx(), center, alpha = 0.3f)
                            drawCircle(color, 6.dp.toPx(), center)
                            drawCircle(Color.White, 3.dp.toPx(), center)
                        },
                    ),
                ),
                grid = LinePlot.Grid(LightGreyColor, steps = 3),
                selection = LinePlot.Selection(
                    highlight = LinePlot.Connection(
                        InvalidRedColor_60,
                        strokeWidth = 2.dp,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 20f))
                    ),
                ),
                yAxis = LinePlot.YAxis()
            ),
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = padding),
            onSelectionStart = { visibility.value = true },
            onSelectionEnd = { visibility.value = false }
        ) { x, pts ->
            val cWidth = cardWidth.value.toFloat()
            var xCenter = x + padding.toPx(density)
            xCenter = when {
                xCenter + cWidth / 2f > totalWidth.value -> totalWidth.value - cWidth
                xCenter - cWidth / 2f < 0f -> 0f
                else -> xCenter - cWidth / 2f
            }
            xOffset.value = xCenter
            points.value = pts
        }
    }
}

@Composable
private fun ScoreRow(value: Float, color: Color) {
    val formatted = DecimalFormat("#,###").format(value)
    Text(
        text = "${formatted}원",
        style = TextStyle(fontWeight = FontWeight.Normal, fontSize = 12.ssp),
        color = Color.DarkGray
    )

}

@Composable
fun SelectChip() {

}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun GraphComponentPreview() {
    PriceGraph(
        lines = listOf(DataPoints.dataPoints1),
        productGraph = listOf(GraphItem("", 0)),
        selectChipLabel = "week",
        selectChips = {},
    )
}

object DataPoints {
    val dataPoints1 = listOf(
        DataPoint(0f, 3500f),
        DataPoint(1f, 3800f),
        DataPoint(2f, 4000f),
        DataPoint(3f, 5000f),
        DataPoint(4f, 5000f),
        DataPoint(5f, 4900f),
        DataPoint(6f, 4700f),
        DataPoint(7f, 4500f),
        DataPoint(8f, 4000f),
        DataPoint(9f, 3400f),
        DataPoint(10f, 3700f),
        DataPoint(11f, 3600f),
        DataPoint(12f, 3700f),
        DataPoint(13f, 3700f),
    )

}