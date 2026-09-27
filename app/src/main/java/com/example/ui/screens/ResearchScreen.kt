package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GlossaryTerm
import com.example.data.model.ResearchPaper
import com.example.data.repository.LabDataRepository
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResearchScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Landmark Papers, 1: AI Glossary
    var glossarySearchQuery by remember { mutableStateOf("") }
    var selectedPaper by remember { mutableStateOf<ResearchPaper?>(null) }

    val filteredGlossary = remember(glossarySearchQuery) {
        if (glossarySearchQuery.isBlank()) {
            LabDataRepository.glossaryTerms
        } else {
            LabDataRepository.glossaryTerms.filter {
                it.term.contains(glossarySearchQuery, ignoreCase = true) ||
                it.category.contains(glossarySearchQuery, ignoreCase = true) ||
                it.simpleDefinition.contains(glossarySearchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Research & Glossary", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) },
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
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = CyanPrimary
            ) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Landmark Papers") })
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Searchable Glossary") })
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                if (selectedTab == 0) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        contentPadding = PaddingValues(bottom = 80.dp)
                    ) {
                        item {
                            Text(
                                text = "Seminal AI Research Papers",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Deep technical breakdowns of the landmark publications that shaped modern artificial intelligence.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        items(LabDataRepository.researchPapers) { paper ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedPaper = paper },
                                shape = RoundedCornerShape(14.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text(text = paper.category.uppercase(), style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = CyanPrimary)
                                        Text(text = "${paper.year}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    Text(text = paper.title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface)
                                    Text(text = paper.authors, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(text = "Breakthrough: ${paper.breakthrough}", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = EmeraldSuccess)
                                }
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 80.dp)
                    ) {
                        item {
                            OutlinedTextField(
                                value = glossarySearchQuery,
                                onValueChange = { glossarySearchQuery = it },
                                placeholder = { Text("Search term (e.g. 'Attention', 'RAG', 'LoRA')...") },
                                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true
                            )
                        }

                        items(filteredGlossary) { item ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text(text = item.term, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface)
                                        Text(text = item.category, style = MaterialTheme.typography.labelSmall, color = CyanPrimary)
                                    }
                                    Text(text = "Simple: ${item.simpleDefinition}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                                    Text(text = "Technical: ${item.technicalDefinition}", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)

                                    if (item.formulaOrCode.isNotBlank()) {
                                        Surface(
                                            color = TerminalBg,
                                            shape = RoundedCornerShape(6.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = item.formulaOrCode,
                                                fontFamily = FontFamily.Monospace,
                                                fontSize = 11.sp,
                                                color = TerminalGreen,
                                                modifier = Modifier.padding(8.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    selectedPaper?.let { paper ->
        AlertDialog(
            onDismissRequest = { selectedPaper = null },
            confirmButton = {
                Button(onClick = { selectedPaper = null }, colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary)) {
                    Text("Close", color = Color(0xFF00363F))
                }
            },
            title = { Text(text = paper.title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) },
            text = {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Text(text = "Authors & Year:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = CyanPrimary)
                        Text(text = "${paper.authors} (${paper.year})", style = MaterialTheme.typography.bodySmall)
                    }
                    item {
                        Text(text = "Abstract:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = VioletSecondary)
                        Text(text = paper.abstractText, style = MaterialTheme.typography.bodySmall)
                    }
                    item {
                        Text(text = "Core Architecture:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = EmeraldSuccess)
                        Text(text = paper.keyArchitecture, style = MaterialTheme.typography.bodySmall)
                    }
                    item {
                        Text(text = "Practical Applications:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = AmberWarning)
                        Text(text = paper.practicalApplication, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        )
    }
}
