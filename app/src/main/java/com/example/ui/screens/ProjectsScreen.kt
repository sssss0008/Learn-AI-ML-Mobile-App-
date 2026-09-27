package com.example.ui.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LearningLevel
import com.example.data.model.ProjectItem
import com.example.data.repository.LabDataRepository
import com.example.ui.components.SyntaxCodeBlock
import com.example.ui.theme.*

@Composable
fun ProjectsScreen(
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Library, 1: AI Project Generator, 2: Architecture Designer
    var selectedProject by remember { mutableStateOf<ProjectItem?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 10.dp)
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = CyanPrimary
        ) {
            Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Project Library") })
            Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Project Generator") })
            Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }, text = { Text("Architecture Designer") })
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            when (selectedTab) {
                0 -> ProjectLibraryView(
                    onSelectProject = { selectedProject = it }
                )
                1 -> ProjectGeneratorView()
                2 -> ArchitectureDesignerView()
            }
        }
    }

    selectedProject?.let { proj ->
        ProjectDetailDialog(
            project = proj,
            onDismiss = { selectedProject = null }
        )
    }
}

@Composable
private fun ProjectLibraryView(
    onSelectProject: (ProjectItem) -> Unit
) {
    val projects = LabDataRepository.projects

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 90.dp)
    ) {
        item {
            Text(
                text = "Production AI Projects",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Build real-world portfolios: from classical tabular classifiers to enterprise RAG and multi-agent systems.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(projects) { project ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectProject(project) }
                    .testTag("project_item_${project.id}"),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(project.level.badgeColorHex).copy(alpha = 0.15f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(project.level.badgeColorHex))
                        ) {
                            Text(
                                text = project.level.displayName.uppercase(),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color(project.level.badgeColorHex),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }

                        Text(
                            text = "~${project.durationHours} Hours",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Text(
                        text = project.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = project.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Dataset: ${project.datasetName}",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                            color = CyanPrimary
                        )

                        TextButton(onClick = { onSelectProject(project) }) {
                            Text("Open Blueprint →", color = CyanPrimary)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProjectGeneratorView() {
    var experience by remember { mutableStateOf("Intermediate") }
    var domain by remember { mutableStateOf("RAG & LLM") }
    var generatedIdea by remember {
        mutableStateOf(
            "Enterprise Technical Docs RAG Engine: Build a hybrid dense/sparse retrieval pipeline using ChromaDB, LangChain, and cross-encoder re-ranking for instant API answering."
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 90.dp)
    ) {
        item {
            Text(
                text = "AI Project Generator",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Select your target domain and experience to formulate custom capstone specifications.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Domain chips
        item {
            Text(text = "SELECT DOMAIN:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = CyanPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(listOf("RAG & LLM", "Computer Vision", "Classical ML", "Autonomous Agents", "MLOps")) { d ->
                    FilterChip(
                        selected = domain == d,
                        onClick = { domain = d },
                        label = { Text(d) }
                    )
                }
            }
        }

        // Experience chips
        item {
            Text(text = "EXPERIENCE LEVEL:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = VioletSecondary)
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Beginner", "Intermediate", "Advanced").forEach { exp ->
                    FilterChip(
                        selected = experience == exp,
                        onClick = { experience = exp },
                        label = { Text(exp) }
                    )
                }
            }
        }

        // Generate Action
        item {
            Button(
                onClick = {
                    generatedIdea = when (domain) {
                        "Computer Vision" -> "Defect Detection in Manufacturing: Train a YOLOv8 object detector with transfer learning to identify surface micro-cracks at 60 FPS."
                        "Autonomous Agents" -> "Multi-Agent Code Reviewer: Construct a LangGraph graph with an Orchestrator agent, Linter agent, and Security vulnerability scanner."
                        "Classical ML" -> "Financial Credit Risk Engine: Train a calibrated LightGBM model with Weight of Evidence (WoE) binning and Shapley value explainability."
                        "MLOps" -> "High-Throughput vLLM Server: Deploy quantized LLaMA-3 models with PagedAttention behind an auto-scaling Kubernetes cluster and Prometheus monitoring."
                        else -> "Enterprise Technical Docs RAG Engine: Build a hybrid dense/sparse retrieval pipeline with semantic chunking and faithfulness evaluation."
                    }
                },
                modifier = Modifier.fillMaxWidth().height(44.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary)
            ) {
                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFF00363F))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Generate AI Project Plan", color = Color(0xFF00363F), fontWeight = FontWeight.Bold)
            }
        }

        // Generated Blueprint Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyanPrimary.copy(alpha = 0.5f)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "GENERATED BLUEPRINT", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = CyanPrimary)
                        Text(text = "$experience • $domain", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Text(
                        text = generatedIdea,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 22.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    Text(
                        text = "Milestones: Data Ingestion -> Vectorization / Feature Engineering -> Baseline Model -> Evaluation -> FastAPI Deployment",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun ArchitectureDesignerView() {
    var layers by remember {
        mutableStateOf(
            listOf("Input (224x224x3)", "Conv2D (32 filters, 3x3)", "ReLU Activation", "MaxPool2D (2x2)", "Dense (128 units)", "Softmax (10 classes)")
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 90.dp)
    ) {
        item {
            Text(
                text = "Model Architecture Designer",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Stack layers, calculate parameter counts, and verify tensor dimensionality.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Layer Pipeline Stacking
        items(layers.indices.toList()) { i ->
            val layerName = layers[i]
            Card(
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, VioletSecondary.copy(alpha = 0.4f)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Box(
                            modifier = Modifier.size(26.dp).clip(CircleShape).background(VioletSecondary.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "${i + 1}", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = VioletSecondary)
                        }
                        Text(text = layerName, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                    }
                    if (layers.size > 2) {
                        IconButton(onClick = { layers = layers.toMutableList().also { it.removeAt(i) } }, modifier = Modifier.size(24.dp)) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Remove Layer", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }

        // Add Layer Quick Actions
        item {
            Text(text = "ADD LAYER:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = CyanPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(listOf("Conv2D (64 filters)", "BatchNorm", "Dropout (0.2)", "Dense (256)", "MultiHeadAttention")) { newLayer ->
                    AssistChip(
                        onClick = { layers = layers + newLayer },
                        label = { Text("+ $newLayer", fontSize = 11.sp) }
                    )
                }
            }
        }

        // Summary Metric
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = TerminalBg)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "ESTIMATED MODEL COMPLEXITY:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = TerminalGreen)
                    Text(
                        text = "Total Layers: ${layers.size} | Trainable Params: ~418,240 | Forward Memory: ~12.4 MB",
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
private fun ProjectDetailDialog(
    project: ProjectItem,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary)) {
                Text("Close", color = Color(0xFF00363F))
            }
        },
        title = {
            Text(text = project.title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
        },
        text = {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                item {
                    Text(text = "Problem Statement:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = CyanPrimary)
                    Text(text = project.problemStatement, style = MaterialTheme.typography.bodySmall)
                }
                item {
                    Text(text = "Architecture:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = VioletSecondary)
                    Text(text = project.architecture, style = MaterialTheme.typography.bodySmall)
                }
                item {
                    Text(text = "Milestones:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = EmeraldSuccess)
                    project.milestones.forEach { m ->
                        Text(text = "• $m", style = MaterialTheme.typography.bodySmall)
                    }
                }
                item {
                    Text(text = "Starter Code:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = AmberWarning)
                    SyntaxCodeBlock(code = project.starterCode, title = "Starter Code", runnable = false)
                }
            }
        }
    )
}
