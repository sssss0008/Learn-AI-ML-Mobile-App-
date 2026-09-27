package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolboxScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedToolTab by remember { mutableStateOf(0) } // 0: ML Metric Calculator, 1: Matrix Calculator

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Developer Toolbox", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TabRow(
                selectedTabIndex = selectedToolTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = CyanPrimary
            ) {
                Tab(
                    selected = selectedToolTab == 0,
                    onClick = { selectedToolTab = 0 },
                    text = { Text("ML Metric Calculator") }
                )
                Tab(
                    selected = selectedToolTab == 1,
                    onClick = { selectedToolTab = 1 },
                    text = { Text("Matrix Calculator") }
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                if (selectedToolTab == 0) {
                    MLMetricCalculatorView()
                } else {
                    MatrixCalculatorView()
                }
            }
        }
    }
}

@Composable
private fun MLMetricCalculatorView() {
    var tp by remember { mutableStateOf(85f) }
    var fp by remember { mutableStateOf(15f) }
    var fn by remember { mutableStateOf(10f) }
    var tn by remember { mutableStateOf(90f) }

    val total = (tp + tn + fp + fn).coerceAtLeast(1f)
    val accuracy = (tp + tn) / total
    val precision = tp / (tp + fp).coerceAtLeast(1f)
    val recall = tp / (tp + fn).coerceAtLeast(1f)
    val f1 = (2f * precision * recall) / (precision + recall).coerceAtLeast(0.0001f)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            Text(
                text = "Confusion Matrix & Evaluation Metrics",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Adjust True Positives (TP), False Positives (FP), False Negatives (FN), and True Negatives (TN) to inspect formulas.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Confusion Matrix 2x2 Grid
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(text = "CONFUSION MATRIX 2x2", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = CyanPrimary)

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        MatrixCell("True Positive (TP)", "${tp.toInt()}", EmeraldSuccess, Modifier.weight(1f))
                        MatrixCell("False Positive (FP)", "${fp.toInt()}", RoseError, Modifier.weight(1f))
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        MatrixCell("False Negative (FN)", "${fn.toInt()}", AmberWarning, Modifier.weight(1f))
                        MatrixCell("True Negative (TN)", "${tn.toInt()}", CyanPrimary, Modifier.weight(1f))
                    }
                }
            }
        }

        // Live Computed Metrics
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MetricScoreCard("Accuracy", String.format("%.3f", accuracy), "(TP+TN)/Total", CyanPrimary, Modifier.weight(1f))
                MetricScoreCard("Precision", String.format("%.3f", precision), "TP/(TP+FP)", VioletSecondary, Modifier.weight(1f))
            }
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MetricScoreCard("Recall", String.format("%.3f", recall), "TP/(TP+FN)", AmberWarning, Modifier.weight(1f))
                MetricScoreCard("F1-Score", String.format("%.3f", f1), "Harmonic Mean", EmeraldSuccess, Modifier.weight(1f))
            }
        }

        // Sliders
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "ADJUST VALUES", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.primary)
                    Text(text = "True Positives (TP): ${tp.toInt()}", style = MaterialTheme.typography.labelSmall)
                    Slider(value = tp, onValueChange = { tp = it }, valueRange = 0f..100f)

                    Text(text = "False Positives (FP): ${fp.toInt()}", style = MaterialTheme.typography.labelSmall)
                    Slider(value = fp, onValueChange = { fp = it }, valueRange = 0f..100f)

                    Text(text = "False Negatives (FN): ${fn.toInt()}", style = MaterialTheme.typography.labelSmall)
                    Slider(value = fn, onValueChange = { fn = it }, valueRange = 0f..100f)

                    Text(text = "True Negatives (TN): ${tn.toInt()}", style = MaterialTheme.typography.labelSmall)
                    Slider(value = tn, onValueChange = { tn = it }, valueRange = 0f..100f)
                }
            }
        }
    }
}

@Composable
private fun MatrixCell(label: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = color.copy(alpha = 0.12f),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.35f)),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = label, style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = value, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = color)
        }
    }
}

@Composable
private fun MetricScoreCard(title: String, score: String, formula: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = score, style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), color = color)
            Text(text = formula, style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun MatrixCalculatorView() {
    var a11 by remember { mutableStateOf(1f) }
    var a12 by remember { mutableStateOf(2f) }
    var a21 by remember { mutableStateOf(3f) }
    var a22 by remember { mutableStateOf(4f) }

    var b11 by remember { mutableStateOf(5f) }
    var b12 by remember { mutableStateOf(6f) }
    var b21 by remember { mutableStateOf(7f) }
    var b22 by remember { mutableStateOf(8f) }

    // Matrix Multiplication C = A @ B
    val c11 = a11 * b11 + a12 * b21
    val c12 = a11 * b12 + a12 * b22
    val c21 = a21 * b11 + a22 * b21
    val c22 = a21 * b12 + a22 * b22

    // Determinant of A
    val detA = a11 * a22 - a12 * a21

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            Text(
                text = "Matrix Multiplication & Transformations",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Matrix multiplication C = A × B underpins linear layers in deep learning.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Visual Equation Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = TerminalBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyanPrimary.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(text = "MATRIX A × MATRIX B = MATRIX C", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = TerminalGreen)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MatrixDisplay("A", listOf(listOf(a11.toInt(), a12.toInt()), listOf(a21.toInt(), a22.toInt())), CyanPrimary)
                        Text(text = " × ", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        MatrixDisplay("B", listOf(listOf(b11.toInt(), b12.toInt()), listOf(b21.toInt(), b22.toInt())), VioletSecondary)
                        Text(text = " = ", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        MatrixDisplay("C", listOf(listOf(c11.toInt(), c12.toInt()), listOf(c21.toInt(), c22.toInt())), EmeraldSuccess)
                    }

                    Divider(color = Color(0xFF1E293B))

                    Text(
                        text = "det(A) = (${a11.toInt()} × ${a22.toInt()}) - (${a12.toInt()} × ${a21.toInt()}) = ${detA.toInt()} " +
                                if (detA == 0f) "(Singular: Not Invertible)" else "(Invertible)",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun MatrixDisplay(label: String, values: List<List<Int>>, color: Color) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFF0F172A),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            values.forEach { row ->
                Text(
                    text = "[ ${row[0]}   ${row[1]} ]",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    color = color,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
