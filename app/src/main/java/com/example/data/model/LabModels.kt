package com.example.data.model

data class Neuron(
    val id: String,
    val value: Float = 0.5f,
    val bias: Float = 0.1f
)

data class LayerConfig(
    val layerIndex: Int,
    val neuronCount: Int,
    val activation: String = "ReLU"
)

data class TrainingMetricPoint(
    val epoch: Int,
    val trainLoss: Float,
    val valLoss: Float,
    val accuracy: Float
)

data class PythonCodeTemplate(
    val id: String,
    val title: String,
    val category: String,
    val code: String,
    val expectedOutput: String
)

data class AttentionToken(
    val index: Int,
    val text: String,
    val weights: List<Float>
)

data class RagChunkItem(
    val id: Int,
    val text: String,
    val charRange: Pair<Int, Int>,
    val similarityScore: Float = 0.0f
)

data class AgentNode(
    val id: String,
    val title: String,
    val type: AgentNodeType,
    val detail: String,
    val status: String = "idle"
)

enum class AgentNodeType {
    TRIGGER,
    LLM_REASONING,
    TOOL_EXECUTION,
    DATABASE_QUERY,
    CONDITION,
    OUTPUT_RESPONSE
}

data class GlossaryTerm(
    val term: String,
    val simpleDefinition: String,
    val technicalDefinition: String,
    val category: String,
    val formulaOrCode: String = "",
    val relatedTerms: List<String> = emptyList()
)
