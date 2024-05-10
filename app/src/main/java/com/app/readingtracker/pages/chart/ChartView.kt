package com.app.readingtracker.pages.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.app.readingtracker.ui.theme.kPadding
import com.jaikeerthick.composable_graphs.composables.pie.PieChart
import com.jaikeerthick.composable_graphs.composables.pie.model.PieData
import com.jaikeerthick.composable_graphs.composables.pie.style.PieChartStyle
import com.jaikeerthick.composable_graphs.composables.pie.style.PieChartVisibility

class ChartView: Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val pieChartData = listOf(
            PieData(value = 130F, label = "Want to Read", color = Color(0xFF6200EE)),
            PieData(value = 260F, label = "Current Reading", color = Color(0xFF26A69A)),
            PieData(value = 500F, label = "Read", color = Color(0xFFFFC107)),
        )
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
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Filled.Search, contentDescription = null)
                        }
                    }
                )
            },
            content = {
                Column (
                    modifier = Modifier.fillMaxSize().padding(it).padding(
                        top = kPadding,
                        end = kPadding * 2,
                        start = kPadding * 2,
                    ).verticalScroll(rememberScrollState()),
                ) {
                    Text("Overview", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(kPadding / 2 ))
                    ChartCompose(pieChartData, "2024")
                    Spacer(modifier = Modifier.height(kPadding / 2 ))
                    ChartCompose(pieChartData, "2023")
                    Spacer(modifier = Modifier.height(kPadding / 2 ))
                    ChartCompose(pieChartData, "2022")
                }
            },
        )
    }
}

@Composable
fun ChartCompose(pieChartData: List<PieData>, year: String) {
    return Card (
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth().padding(kPadding), // Add padding to the entire card
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        content = {
            Column(
                modifier = Modifier.fillMaxSize().padding(
                    kPadding
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = year,
                    modifier = Modifier.fillMaxWidth().padding(kPadding),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(kPadding))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
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
                Spacer(modifier = Modifier.height(kPadding))
                Row (
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    LegendChart("Want to Read", Color(0xFF6200EE))
                    Spacer(modifier = Modifier.width(kPadding * 2))
                    LegendChart("Current Reading", Color(0xFF26A69A))
                    Spacer(modifier = Modifier.width(kPadding * 2))
                    LegendChart("Read", Color(0xFFFFC107))
                }
            }
        }
    )
}

@Composable
fun LegendChart(label: String, color: Color) {
    return Row (
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.clip(CircleShape).background(color).size(12.dp))
        Spacer(modifier = Modifier.width(kPadding))
        Text(text = label)
    }
}
