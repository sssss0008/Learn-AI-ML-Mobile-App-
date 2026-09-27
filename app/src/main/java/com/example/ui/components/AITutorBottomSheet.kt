package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AITutorBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var queryText by remember { mutableStateOf("") }
    var currentAnswer by remember {
        mutableStateOf(
            "Hello! I am your AI ML Academy Tutor. Ask me any question about Python, Mathematics, Neural Networks, Computer Vision, Transformers, LLMs, RAG, or MLOps!"
        )
    }
    var codeExample by remember { mutableStateOf("") }

    val presetQuestions = listOf(
        "Explain Self-Attention simply",
        "Why use ReLU instead of Sigmoid?",
        "How does RAG stop hallucinations?",
        "What is Cosine Similarity in vector search?",
        "Difference between Overfitting and Underfitting",
        "Explain Gradient Descent intuition"
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier.testTag("ai_tutor_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .navigationBarsPadding()
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(VioletSecondary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = VioletSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "AI Coding & Concept Tutor",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Instant First-Principles Explanations",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close Tutor")
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Presets row
            Text(
                text = "Quick Inquiries:",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(6.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(presetQuestions) { q ->
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
                        modifier = Modifier
                            .clickable {
                                queryText = q
                                answerQuestion(q) { ans, code ->
                                    currentAnswer = ans
                                    codeExample = code
                                }
                            }
                            .testTag("tutor_preset_$q")
                    ) {
                        Text(
                            text = q,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Query Input
            OutlinedTextField(
                value = queryText,
                onValueChange = { queryText = it },
                placeholder = { Text("Ask anything (e.g. 'What is LoRA?')") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("tutor_query_input"),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (queryText.isNotBlank()) {
                                answerQuestion(queryText) { ans, code ->
                                    currentAnswer = ans
                                    codeExample = code
                                }
                            }
                        },
                        modifier = Modifier.testTag("tutor_send_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send Question",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tutor Response Card
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    item {
                        Text(
                            text = "TUTOR EXPLANATION",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = currentAnswer,
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 22.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    if (codeExample.isNotBlank()) {
                        item {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "CODE DEMONSTRATION:",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = EmeraldSuccess
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Surface(
                                color = TerminalBg,
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = codeExample,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 12.sp,
                                    color = TerminalGreen,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun answerQuestion(query: String, onResult: (String, String) -> Unit) {
    val q = query.lowercase()
    when {
        q.contains("attention") -> {
            onResult(
                "Self-Attention allows every word in a sequence to calculate how relevant every other word is. It creates three vectors for each word: Query (what I'm looking for), Key (what I offer), and Value (my content). The dot product of Q and K divided by sqrt(d_k) passed through Softmax determines the attention weights applied to V!",
                """# Scaled Dot-Product Attention:
scores = (Q @ K.T) / np.sqrt(d_k)
weights = softmax(scores, axis=-1)
output = weights @ V"""
            )
        }
        q.contains("relu") || q.contains("sigmoid") -> {
            onResult(
                "Sigmoid compresses outputs between 0 and 1. When inputs are very high or low, its derivative approaches zero, causing the 'Vanishing Gradient' problem in deep layers. ReLU (Rectified Linear Unit) is max(0, x), whose derivative is 1 for all positive values—allowing gradients to backpropagate cleanly through 100+ layers with 6x faster compute!",
                """def relu(x):
    return np.maximum(0, x) # Gradient is 1 if x > 0 else 0"""
            )
        }
        q.contains("rag") || q.contains("hallucinat") -> {
            onResult(
                "LLMs hallucinate because they are probabilistic next-token predictors based on static training weights. RAG (Retrieval-Augmented Generation) connects the LLM to an external vector database. When a user asks a question, we first search for the top 3-5 factual document chunks via cosine similarity and inject them into the prompt. The LLM then acts as a synthesizer rather than a memorizer.",
                """# RAG Pattern:
chunks = vector_store.similarity_search(query, k=3)
context = "\n".join(chunks)
prompt = f"Using ONLY this context:\n{context}\n\nAnswer: {query}""""
            )
        }
        q.contains("cosine") || q.contains("vector") -> {
            onResult(
                "Cosine similarity measures the angle between two vectors rather than their length (magnitude). Two parallel vectors have similarity 1.0 (identical semantic meaning), orthogonal vectors have 0.0 (unrelated), and opposite vectors have -1.0. This makes it ideal for comparing text embeddings regardless of document word count!",
                """# Cosine Similarity Formula:
cos_sim = np.dot(u, v) / (np.linalg.norm(u) * np.linalg.norm(v))"""
            )
        }
        q.contains("overfitting") || q.contains("bias") -> {
            onResult(
                "Overfitting (High Variance) means your model memorized the training noise and fails on new test data (Train error low, Test error high). Underfitting (High Bias) means your model is too simplistic to capture the real relationship (both errors high). Solve overfitting with L1/L2 regularization, dropout, cross-validation, and more data!",
                """# Regularization penalty:
Loss_total = MSE_loss + lambda * sum(w**2) # L2 Ridge"""
            )
        }
        q.contains("gradient") || q.contains("descent") -> {
            onResult(
                "Gradient Descent is the universal optimization engine of machine learning. The gradient vector ∇L tells you which direction makes loss increase fastest. By taking a small step in the opposite direction (w := w - lr * ∇L), we iteratively descend toward the minimum error valley.",
                """for step in range(epochs):
    grads = compute_gradient(X, y, weights)
    weights -= learning_rate * grads"""
            )
        }
        else -> {
            onResult(
                "In machine learning, \"$query\" involves mapping features through parameterized functions, evaluating predictions with a loss metric, and updating weights using gradient optimization. Explore the Labs tab to visualize this concept interactively in real time!",
                """# Model formulation
y_pred = model.forward(features)
loss = loss_function(y_pred, ground_truth)
loss.backward()"""
            )
        }
    }
}
