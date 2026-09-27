package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PythonCodeTemplate
import com.example.data.repository.LabDataRepository
import com.example.ui.components.SyntaxCodeBlock
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.math.*

@Composable
fun LabsScreen(
    initialTab: Int = 0,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(initialTab) }

    val labTitles = listOf(
        "Neural Net",
        "Python & NumPy",
        "Regression & ML",
        "Attention",
        "LLM & Prompt",
        "RAG Chunking",
        "Agent Builder",
        "Training Sim"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 10.dp)
    ) {
        // Scrollable Lab Category Tabs
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            edgePadding = 16.dp,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = CyanPrimary,
            modifier = Modifier.fillMaxWidth()
        ) {
            labTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            fontSize = 12.sp
                        )
                    }
                )
            }
        }

        // Active Lab Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            when (selectedTab) {
                0 -> NeuralNetLab()
                1 -> PythonNumPyLab()
                2 -> RegressionMLLab()
                3 -> AttentionVisualizerLab()
                4 -> LLMPromptLab()
                5 -> RagChunkingLab()
                6 -> AgentWorkflowLab()
                7 -> ModelTrainingSimLab()
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 1. NEURAL NETWORK VISUALIZER
// ---------------------------------------------------------------------------
@Composable
fun NeuralNetLab() {
    var hiddenNeurons by remember { mutableStateOf(4) }
    var selectedActivation by remember { mutableStateOf("ReLU") }
    var inputX1 by remember { mutableStateOf(0.6f) }
    var inputX2 by remember { mutableStateOf(-0.4f) }
    var isForwardPassRunning by remember { mutableStateOf(false) }

    // Computed output
    val calculatedOutput = remember(hiddenNeurons, selectedActivation, inputX1, inputX2) {
        val sum = (inputX1 * 0.7f + inputX2 * -0.5f) * (hiddenNeurons / 3f)
        when (selectedActivation) {
            "Sigmoid" -> 1.0f / (1.0f + exp(-sum))
            "ReLU" -> max(0.0f, sum)
            "Tanh" -> tanh(sum.toDouble()).toFloat()
            else -> sum * (0.5f * (1.0f + tanh(sqrt(2.0 / Math.PI) * (sum + 0.044715f * sum.pow(3))))) // GELU approx
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 90.dp)
    ) {
        item {
            Text(
                text = "Interactive Neural Network Visualizer",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Adjust inputs, configure hidden neurons, switch activations, and watch the forward pass calculate in real time.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Canvas Architecture Rendering
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = TerminalBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyanPrimary.copy(alpha = 0.4f))
            ) {
                Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    val w = size.width
                    val h = size.height

                    val layerX = listOf(w * 0.15f, w * 0.50f, w * 0.85f)
                    val inputY = listOf(h * 0.35f, h * 0.65f)
                    val outputY = listOf(h * 0.50f)
                    val hiddenY = (0 until hiddenNeurons).map { i ->
                        h * (0.2f + 0.6f * (i.toFloat() / (hiddenNeurons - 1).coerceAtLeast(1)))
                    }

                    // Draw Synaptic connection lines
                    inputY.forEach { y1 ->
                        hiddenY.forEach { y2 ->
                            drawLine(
                                color = VioletSecondary.copy(alpha = 0.35f),
                                start = Offset(layerX[0], y1),
                                end = Offset(layerX[1], y2),
                                strokeWidth = 1.5f
                            )
                        }
                    }

                    hiddenY.forEach { y1 ->
                        outputY.forEach { y2 ->
                            drawLine(
                                color = CyanPrimary.copy(alpha = 0.45f),
                                start = Offset(layerX[1], y1),
                                end = Offset(layerX[2], y2),
                                strokeWidth = 2f
                            )
                        }
                    }

                    // Draw Input Neurons
                    inputY.forEachIndexed { i, y ->
                        drawCircle(color = CyanPrimary, radius = 14f, center = Offset(layerX[0], y))
                        drawCircle(color = Color.White, radius = 6f, center = Offset(layerX[0], y))
                    }

                    // Draw Hidden Neurons
                    hiddenY.forEach { y ->
                        drawCircle(color = VioletSecondary, radius = 13f, center = Offset(layerX[1], y))
                        drawCircle(color = Color.White, radius = 5f, center = Offset(layerX[1], y))
                    }

                    // Draw Output Neuron
                    outputY.forEach { y ->
                        drawCircle(color = EmeraldSuccess, radius = 16f, center = Offset(layerX[2], y))
                        drawCircle(color = Color.White, radius = 7f, center = Offset(layerX[2], y))
                    }
                }
            }
        }

        // Live Forward Pass Output Card
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "NETWORK OUTPUT: y_hat", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = CyanPrimary)
                        Text(text = "Formula: a = $selectedActivation(W2 · $selectedActivation(W1 · x + b1) + b2)", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = EmeraldSuccess.copy(alpha = 0.2f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldSuccess)
                    ) {
                        Text(
                            text = String.format("%.4f", calculatedOutput),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = EmeraldSuccess,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Controls
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(text = "ARCHITECTURE CONTROLS", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp), color = MaterialTheme.colorScheme.primary)

                    // Hidden Neurons Counter
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Hidden Neurons: $hiddenNeurons", style = MaterialTheme.typography.bodyMedium)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            FilledTonalButton(
                                onClick = { if (hiddenNeurons > 2) hiddenNeurons-- },
                                enabled = hiddenNeurons > 2
                            ) { Text("-") }
                            FilledTonalButton(
                                onClick = { if (hiddenNeurons < 6) hiddenNeurons++ },
                                enabled = hiddenNeurons < 6
                            ) { Text("+") }
                        }
                    }

                    // Activation Selector Chips
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = "Activation Function:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("ReLU", "Sigmoid", "Tanh", "GELU").forEach { act ->
                                FilterChip(
                                    selected = selectedActivation == act,
                                    onClick = { selectedActivation = act },
                                    label = { Text(act, fontSize = 11.sp) }
                                )
                            }
                        }
                    }

                    // Input Sliders
                    Column {
                        Text(text = "Input Feature x1: ${String.format("%.2f", inputX1)}", style = MaterialTheme.typography.labelSmall)
                        Slider(value = inputX1, onValueChange = { inputX1 = it }, valueRange = -1.0f..1.0f)
                    }
                    Column {
                        Text(text = "Input Feature x2: ${String.format("%.2f", inputX2)}", style = MaterialTheme.typography.labelSmall)
                        Slider(value = inputX2, onValueChange = { inputX2 = it }, valueRange = -1.0f..1.0f)
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 2. PYTHON & NUMPY/PANDAS LAB
// ---------------------------------------------------------------------------
@Composable
fun PythonNumPyLab() {
    var selectedTemplateIndex by remember { mutableStateOf(0) }
    val templates = LabDataRepository.pythonTemplates
    val currentTemplate = templates[selectedTemplateIndex]

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 90.dp)
    ) {
        item {
            Text(
                text = "Python & NumPy/Pandas Lab",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Vectorized matrix operations, DataFrame manipulation, and PyTorch tensors with instant execution.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Template Selector Tabs
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(templates.indices.toList()) { index ->
                    FilterChip(
                        selected = selectedTemplateIndex == index,
                        onClick = { selectedTemplateIndex = index },
                        label = { Text(templates[index].title, fontSize = 11.sp) }
                    )
                }
            }
        }

        // Code Editor Block
        item {
            SyntaxCodeBlock(
                code = currentTemplate.code,
                title = currentTemplate.title,
                runnable = true,
                simulatedOutput = currentTemplate.expectedOutput
            )
        }

        // Educational Insight
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "EFFICIENCY NOTE",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = CyanPrimary
                    )
                    Text(
                        text = "Notice how vectorized syntax avoids Python 'for' loops entirely. NumPy operations release the Global Interpreter Lock (GIL) and execute through SIMD instructions on your CPU/GPU cores.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 3. REGRESSION & MACHINE LEARNING LAB
// ---------------------------------------------------------------------------
@Composable
fun RegressionMLLab() {
    var slope by remember { mutableStateOf(1.8f) }
    var intercept by remember { mutableStateOf(2.0f) }
    var noiseLevel by remember { mutableStateOf(0.4f) }

    // Sample data points
    val points = listOf(
        Pair(1f, 3.8f), Pair(2f, 5.2f), Pair(3f, 7.9f),
        Pair(4f, 9.1f), Pair(5f, 11.4f), Pair(6f, 12.8f)
    )

    // Calculate MSE & MAE
    val (mse, mae, r2) = remember(slope, intercept) {
        var sumSqErr = 0.0
        var sumAbsErr = 0.0
        val yMean = points.map { it.second }.average()
        var totalSS = 0.0

        points.forEach { (x, y) ->
            val yPred = slope * x + intercept
            val err = y - yPred
            sumSqErr += err * err
            sumAbsErr += abs(err)
            totalSS += (y - yMean) * (y - yMean)
        }
        val computedMse = sumSqErr / points.size
        val computedMae = sumAbsErr / points.size
        val computedR2 = (1.0 - (sumSqErr / totalSS)).coerceIn(-1.0, 1.0)
        Triple(computedMse, computedMae, computedR2)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 90.dp)
    ) {
        item {
            Text(
                text = "Classical ML: Interactive Regression",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Fit the regression line y = w * x + b to minimize Mean Squared Error (MSE).",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Live 2D Scatter & Regression Plot
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = TerminalBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, VioletSecondary.copy(alpha = 0.4f))
            ) {
                Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    val w = size.width
                    val h = size.height

                    // Grid lines
                    for (i in 1..4) {
                        val y = h * (i / 5f)
                        drawLine(color = Color(0xFF1E293B), start = Offset(0f, y), end = Offset(w, y), strokeWidth = 1f)
                    }

                    // Plot Ground Truth Points
                    points.forEach { (x, y) ->
                        val px = (x / 7f) * w
                        val py = h - ((y / 16f) * h)
                        drawCircle(color = CyanPrimary, radius = 6f, center = Offset(px, py))
                    }

                    // Plot Regression Line
                    val xStart = 0f
                    val yStartPred = intercept
                    val xEnd = 7f
                    val yEndPred = slope * xEnd + intercept

                    val p1 = Offset(0f, h - ((yStartPred / 16f) * h))
                    val p2 = Offset(w, h - ((yEndPred / 16f) * h))

                    drawLine(
                        color = EmeraldSuccess,
                        start = p1,
                        end = p2,
                        strokeWidth = 3f
                    )
                }
            }
        }

        // Metrics Grid
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricCardItem("MSE Loss", String.format("%.3f", mse), "Mean Squared Error", CyanPrimary, Modifier.weight(1f))
                MetricCardItem("MAE", String.format("%.3f", mae), "Mean Absolute Error", VioletSecondary, Modifier.weight(1f))
                MetricCardItem("R² Score", String.format("%.2f", r2), "Variance Explained", EmeraldSuccess, Modifier.weight(1f))
            }
        }

        // Slider Controls
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(text = "PARAMETER TUNING", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.primary)
                    Column {
                        Text(text = "Slope Weight (w): ${String.format("%.2f", slope)}", style = MaterialTheme.typography.labelSmall)
                        Slider(value = slope, onValueChange = { slope = it }, valueRange = 0.5f..3.0f)
                    }
                    Column {
                        Text(text = "Bias Intercept (b): ${String.format("%.2f", intercept)}", style = MaterialTheme.typography.labelSmall)
                        Slider(value = intercept, onValueChange = { intercept = it }, valueRange = 0.0f..5.0f)
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricCardItem(title: String, value: String, subtitle: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = title, style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = value, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = color)
            Text(text = subtitle, style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

// ---------------------------------------------------------------------------
// 4. TRANSFORMERS & ATTENTION VISUALIZER
// ---------------------------------------------------------------------------
@Composable
fun AttentionVisualizerLab() {
    val tokens = listOf("The", "cat", "sat", "on", "the", "mat")
    var selectedTokenIndex by remember { mutableStateOf(1) } // "cat"

    // Attention weights matrix for "The cat sat on the mat"
    val attentionMatrix = listOf(
        listOf(0.40f, 0.35f, 0.10f, 0.05f, 0.05f, 0.05f), // The ->
        listOf(0.15f, 0.50f, 0.25f, 0.03f, 0.02f, 0.05f), // cat ->
        listOf(0.05f, 0.30f, 0.45f, 0.10f, 0.02f, 0.08f), // sat ->
        listOf(0.02f, 0.05f, 0.15f, 0.55f, 0.10f, 0.13f), // on ->
        listOf(0.05f, 0.05f, 0.05f, 0.10f, 0.45f, 0.30f), // the ->
        listOf(0.05f, 0.15f, 0.10f, 0.20f, 0.15f, 0.35f)  // mat ->
    )

    val currentWeights = attentionMatrix[selectedTokenIndex]

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 90.dp)
    ) {
        item {
            Text(
                text = "Transformers: Self-Attention Heatmap",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Tap a token in the sentence below to inspect which words its Query vector attends to.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Interactive Token Chips
        item {
            Text(text = "INPUT SENTENCE (TAP TOKEN TO ATTEND):", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = CyanPrimary)
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tokens.forEachIndexed { index, token ->
                    val isSelected = selectedTokenIndex == index
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) CyanPrimary else MaterialTheme.colorScheme.surfaceVariant,
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) CyanPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                        modifier = Modifier
                            .clickable { selectedTokenIndex = index }
                            .testTag("attention_token_$token")
                    ) {
                        Text(
                            text = token,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = if (isSelected) Color(0xFF00363F) else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }

        // Attention Breakdown for Selected Token
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "ATTENTION WEIGHTS FROM '${tokens[selectedTokenIndex].uppercase()}'",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = VioletSecondary
                    )

                    tokens.forEachIndexed { i, token ->
                        val weight = currentWeights[i]
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "$token", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold))
                                Text(text = "${(weight * 100).toInt()}%", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = CyanPrimary)
                            }
                            LinearProgressIndicator(
                                progress = { weight },
                                modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                                color = if (i == selectedTokenIndex) CyanPrimary else VioletSecondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Q, K, V Formula Explainer
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = TerminalBg)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "MATHEMATICAL FORMULA:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = TerminalGreen)
                    Text(
                        text = "Attention(Q, K, V) = Softmax((Q · K^T) / sqrt(d_k)) · V",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 5. LLM PLAYGROUND & PROMPT LAB
// ---------------------------------------------------------------------------
@Composable
fun LLMPromptLab() {
    var selectedModel by remember { mutableStateOf("Gemini 1.5 Pro") }
    var temperature by remember { mutableStateOf(0.7f) }
    var topP by remember { mutableStateOf(0.9f) }
    var promptInput by remember { mutableStateOf("Explain how backpropagation computes gradients in 2 sentences.") }
    var isGenerating by remember { mutableStateOf(false) }
    var generatedText by remember {
        mutableStateOf(
            "Backpropagation applies the chain rule of differential calculus recursively from the loss output backward to input weights. It computes partial derivatives (∂Loss/∂w) to tell each neuron exactly how to adjust its parameters to reduce error."
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 90.dp)
    ) {
        item {
            Text(
                text = "LLM Playground & Prompt Lab",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Experiment with temperature, nucleus top-p sampling, and structured prompt engineering.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Model selector chips
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Gemini 1.5 Pro", "Claude 3.5 Sonnet", "LLaMA-3 70B").forEach { model ->
                    FilterChip(
                        selected = selectedModel == model,
                        onClick = { selectedModel = model },
                        label = { Text(model, fontSize = 11.sp) }
                    )
                }
            }
        }

        // Sliders: Temperature & Top-P
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Temperature: ${String.format("%.2f", temperature)}", style = MaterialTheme.typography.labelSmall)
                        Text(
                            text = if (temperature < 0.3f) "Deterministic" else if (temperature > 1.0f) "Highly Creative" else "Balanced",
                            style = MaterialTheme.typography.labelSmall,
                            color = CyanPrimary
                        )
                    }
                    Slider(value = temperature, onValueChange = { temperature = it }, valueRange = 0.0f..1.5f)

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Top-P (Nucleus): ${String.format("%.2f", topP)}", style = MaterialTheme.typography.labelSmall)
                        Text(text = "Cumulative probability cutoff", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Slider(value = topP, onValueChange = { topP = it }, valueRange = 0.1f..1.0f)
                }
            }
        }

        // Prompt Input Field
        item {
            OutlinedTextField(
                value = promptInput,
                onValueChange = { promptInput = it },
                label = { Text("User Prompt") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                shape = RoundedCornerShape(12.dp)
            )
        }

        // Generate Action Button
        item {
            Button(
                onClick = {
                    isGenerating = true
                    generatedText = "Computing with Temperature = ${String.format("%.2f", temperature)} on $selectedModel:\n\n" +
                            "Backpropagation traverses the computational graph in reverse topological order. It multiplies local Jacobians with incoming adjoint vectors to compute exact parameter gradients in O(N) time."
                    isGenerating = false
                },
                modifier = Modifier.fillMaxWidth().height(44.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary)
            ) {
                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFF00363F))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "Generate Response", color = Color(0xFF00363F), fontWeight = FontWeight.Bold)
            }
        }

        // Response Display
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "MODEL RESPONSE ($selectedModel)", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = CyanPrimary)
                        Text(text = "~48 tokens • Latency 320ms", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Text(
                        text = generatedText,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 22.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 6. RAG & CHUNKING LAB
// ---------------------------------------------------------------------------
@Composable
fun RagChunkingLab() {
    var chunkSize by remember { mutableStateOf(80) }
    var chunkOverlap by remember { mutableStateOf(15) }
    var searchQuery by remember { mutableStateOf("How do transformers use attention?") }

    val rawText = "Transformers replace recurrent networks with self-attention mechanisms. Attention allows each word to attend to every other word dynamically. The Query, Key, and Value vectors map inputs into semantic subspaces. Scaled dot product prevents gradient saturation in deep architectures."

    val chunks = remember(chunkSize, chunkOverlap, rawText) {
        val result = mutableListOf<String>()
        var start = 0
        while (start < rawText.length) {
            val end = (start + chunkSize).coerceAtMost(rawText.length)
            result.add(rawText.substring(start, end))
            if (end == rawText.length) break
            start += (chunkSize - chunkOverlap).coerceAtLeast(1)
        }
        result
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 90.dp)
    ) {
        item {
            Text(
                text = "RAG & Document Chunking Lab",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Observe how chunk size and overlap determine retrieval boundaries and context packaging.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Sliders
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Chunk Size: $chunkSize characters", style = MaterialTheme.typography.labelSmall)
                    Slider(value = chunkSize.toFloat(), onValueChange = { chunkSize = it.toInt() }, valueRange = 40f..150f)

                    Text(text = "Chunk Overlap: $chunkOverlap characters", style = MaterialTheme.typography.labelSmall)
                    Slider(value = chunkOverlap.toFloat(), onValueChange = { chunkOverlap = it.toInt().coerceAtMost(chunkSize / 2) }, valueRange = 0f..40f)
                }
            }
        }

        // Resulting Chunks Display
        item {
            Text(text = "RESULTING CHUNKS (${chunks.size} CHUNKS CREATED):", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = EmeraldSuccess)
            Spacer(modifier = Modifier.height(4.dp))
        }

        items(chunks.indices.toList()) { i ->
            val chunk = chunks[i]
            val isTopMatch = i == 0 || chunk.contains("attention", ignoreCase = true)
            Card(
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, if (isTopMatch) EmeraldSuccess else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Chunk #$i", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.primary)
                        if (isTopMatch) {
                            Text(text = "Cosine Sim: 0.942 (Retrieved)", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = EmeraldSuccess)
                        }
                    }
                    Text(text = "\"$chunk\"", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 7. AI AGENT & WORKFLOW DESIGNER
// ---------------------------------------------------------------------------
@Composable
fun AgentWorkflowLab() {
    var agentStep by remember { mutableStateOf(0) }
    val steps = listOf(
        "1. User Trigger" to "User asks: 'Calculate quarterly revenue growth and summarize risks.'",
        "2. LLM Reasoning (ReAct Thought)" to "Thought: I need financial data from the SQL Database before writing a summary.",
        "3. Tool Action Invocation" to "Action: execute_sql_query('SELECT q1_rev, q2_rev FROM financials')",
        "4. Tool Observation" to "Observation: Returned Q1: $4.2M, Q2: $5.1M (+21.4% growth).",
        "5. Decision & Final Synthesis" to "Final Answer: Revenue grew 21.4% from $4.2M to $5.1M in Q2. Risk factors include supply chain delays."
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 90.dp)
    ) {
        item {
            Text(
                text = "Autonomous AI Agent Workflow",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Step through the ReAct (Reason + Act) loop: Thought -> Action -> Observation -> Final Output.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Stepper Controls
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { if (agentStep > 0) agentStep-- },
                    enabled = agentStep > 0
                ) { Text("Previous") }

                Text(text = "Step ${agentStep + 1} of ${steps.size}", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))

                Button(
                    onClick = { if (agentStep < steps.size - 1) agentStep++ },
                    enabled = agentStep < steps.size - 1,
                    colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary)
                ) { Text("Next Step", color = Color(0xFF00363F)) }
            }
        }

        // Pipeline Cards
        items(steps.indices.toList()) { index ->
            val (title, detail) = steps[index]
            val isActive = index <= agentStep
            val isCurrent = index == agentStep

            Card(
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isCurrent) CyanPrimary else if (isActive) EmeraldSuccess.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                ),
                colors = CardDefaults.cardColors(
                    containerColor = if (isCurrent) CyanPrimary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface
                )
            ) {
                Row(modifier = Modifier.padding(14.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(if (isActive) CyanPrimary else MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${index + 1}",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = if (isActive) Color(0xFF00363F) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = if (isCurrent) CyanPrimary else MaterialTheme.colorScheme.onSurface)
                        Text(text = detail, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 8. MODEL TRAINING SIMULATOR
// ---------------------------------------------------------------------------
@Composable
fun ModelTrainingSimLab() {
    var isTraining by remember { mutableStateOf(false) }
    var currentEpoch by remember { mutableStateOf(16) }
    val maxEpochs = 50
    var learningRate by remember { mutableStateOf(0.001f) }

    // Live Training Metrics
    val trainLoss = remember(currentEpoch, learningRate) {
        (0.85f * exp(-0.06f * currentEpoch) + 0.05f)
    }
    val valLoss = remember(currentEpoch, learningRate) {
        (0.88f * exp(-0.055f * currentEpoch) + 0.09f)
    }
    val accuracy = remember(currentEpoch) {
        (1.0f - (trainLoss * 0.7f)).coerceIn(0.40f, 0.98f)
    }

    LaunchedEffect(isTraining) {
        while (isTraining && currentEpoch < maxEpochs) {
            delay(400)
            currentEpoch++
        }
        if (currentEpoch >= maxEpochs) isTraining = false
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 90.dp)
    ) {
        item {
            Text(
                text = "Model Training Simulator",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Live epoch telemetry: observe loss convergence and validation divergence in real time.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Live Metrics HUD
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MetricCardItem("Epoch", "$currentEpoch / $maxEpochs", "Training Iteration", CyanPrimary, Modifier.weight(1f))
                MetricCardItem("Train Loss", String.format("%.4f", trainLoss), "Cross-Entropy", VioletSecondary, Modifier.weight(1f))
                MetricCardItem("Accuracy", "${(accuracy * 100).toInt()}%", "Validation Metric", EmeraldSuccess, Modifier.weight(1f))
            }
        }

        // Live Training Curve Canvas
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = TerminalBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyanPrimary.copy(alpha = 0.4f))
            ) {
                Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    val w = size.width
                    val h = size.height

                    // Grid
                    for (i in 1..3) {
                        val y = h * (i / 4f)
                        drawLine(color = Color(0xFF1E293B), start = Offset(0f, y), end = Offset(w, y), strokeWidth = 1f)
                    }

                    // Loss curve points
                    val pathTrain = Path()
                    val pathVal = Path()

                    val stepX = w / maxEpochs
                    for (ep in 1..currentEpoch) {
                        val tLoss = (0.85f * exp(-0.06f * ep) + 0.05f)
                        val vLoss = (0.88f * exp(-0.055f * ep) + 0.09f)

                        val px = ep * stepX
                        val pyTrain = (tLoss / 1.0f) * h
                        val pyVal = (vLoss / 1.0f) * h

                        if (ep == 1) {
                            pathTrain.moveTo(px, pyTrain)
                            pathVal.moveTo(px, pyVal)
                        } else {
                            pathTrain.lineTo(px, pyTrain)
                            pathVal.lineTo(px, pyVal)
                        }
                    }

                    drawPath(path = pathTrain, color = CyanPrimary, style = Stroke(width = 3f))
                    drawPath(path = pathVal, color = AmberWarning, style = Stroke(width = 3f))
                }
            }
        }

        // Controls
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { isTraining = !isTraining },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = if (isTraining) RoseError else CyanPrimary)
                        ) {
                            Icon(imageVector = if (isTraining) Icons.Default.Pause else Icons.Default.PlayArrow, contentDescription = null, tint = if (isTraining) Color.White else Color(0xFF00363F))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = if (isTraining) "Pause" else "Train Epochs", color = if (isTraining) Color.White else Color(0xFF00363F), fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = {
                                isTraining = false
                                currentEpoch = 1
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reset")
                        }
                    }
                }
            }
        }
    }
}
