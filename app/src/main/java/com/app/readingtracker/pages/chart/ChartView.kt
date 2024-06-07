package com.app.readingtracker.pages.chart

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import com.app.readingtracker.core.DataStoreManager
import com.app.readingtracker.core.UiState
import com.app.readingtracker.ui.theme.kPadding
import com.jaikeerthick.composable_graphs.composables.pie.PieChart
import com.jaikeerthick.composable_graphs.composables.pie.model.PieData
import com.jaikeerthick.composable_graphs.composables.pie.style.PieChartStyle
import com.jaikeerthick.composable_graphs.composables.pie.style.PieChartVisibility
import kotlinx.coroutines.flow.firstOrNull

class ChartView: Screen {
    @SuppressLint("StateFlowValueCalledInComposition")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = viewModel<ChartViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val context = LocalContext.current
        val dashboardData by viewModel.dashboardData.collectAsState()

        LaunchedEffect(Unit) {
            val token = DataStoreManager.read(context,"refresh").firstOrNull()
            if(token != null) {
                viewModel.getChart(token)
            }
        }
        return Scaffold (
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets(0.dp),
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    ),
                    title = { Text("Chart", textAlign = TextAlign.Center) },
                    actions = {
                        IconButton(
                            onClick = {

                            },
                            content = {
                                Icon(Icons.Filled.Refresh, contentDescription = null)
                            }
                        )
                    }
                )
            },
            content = {
                when(uiState) {
                    UiState.LOADING -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                            contentAlignment = Alignment.Center,
                            content = { CircularProgressIndicator() }
                        )
                    }
                    UiState.ERROR -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                                .padding(horizontal = 8.dp),
                            contentAlignment = Alignment.Center,
                            content = { Text("An error occurred. Please try again. ${viewModel.errorMessage.value}") }
                        )
                    }
                    UiState.SUCCESS -> {
                        Column (
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                                .padding(
                                    top = kPadding,
                                    end = kPadding * 2,
                                    start = kPadding * 2,
                                )
                                .verticalScroll(rememberScrollState()),
                            content = {
                                Text("Overview", style = MaterialTheme.typography.titleLarge)
                                Spacer(modifier = Modifier.height(kPadding / 2 ))
                                ListChart(list = dashboardData)
                            }
                        )
                    }
                }
            },
        )
    }
}

@Composable
fun ListChart(list: List<DashboardModel>) {
    Column {
        list.forEach { book ->
            ChartCompose(book)
        }
    }
}


@Composable
fun ChartCompose(data: DashboardModel) {
    val pieChartData = listOf(
        PieData(value = data.chart[0].value, label = data.chart[0].name, color = Color(0xFF6200EE)),
        PieData(value = data.chart[1].value, label = data.chart[1].name, color = Color(0xFF26A69A)),
        PieData(value = data.chart[2].value, label = data.chart[2].name, color = Color(0xFFFFC107)),
    )
    return Card (
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth().padding(kPadding),
        colors = CardDefaults.cardColors( containerColor = MaterialTheme.colorScheme.background),
        content = {
            Column(
                modifier = Modifier.fillMaxSize().padding(kPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Text(
                        text = data.year.toString(),
                        modifier = Modifier.fillMaxWidth().padding(kPadding),
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(kPadding))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        content = {
                            Spacer(modifier = Modifier.weight(1f))
                            PieChart(
                                modifier = Modifier.size(250.dp),
                                data = pieChartData,
                                style = PieChartStyle(
                                    visibility = PieChartVisibility(
                                        isLabelVisible = true,
                                        isPercentageVisible = true
                                    ),
                                ),
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    )
                    Spacer(modifier = Modifier.height(kPadding))
                    Row (
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            LegendChart(data.chart[0].name, Color(0xFF6200EE))
                            Spacer(modifier = Modifier.width(kPadding * 2))
                            LegendChart(data.chart[1].name, Color(0xFF26A69A))
                            Spacer(modifier = Modifier.width(kPadding * 2))
                            LegendChart(data.chart[2].name, Color(0xFFFFC107))
                        }
                    )
                }
            )
        }
    )
}

@Composable
fun LegendChart(label: String, color: Color) {
    return Row (
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Box(modifier = Modifier
                .clip(CircleShape)
                .background(color)
                .size(12.dp))
            Spacer(modifier = Modifier.width(kPadding))
            Text(text = label)
        }
    )
}
