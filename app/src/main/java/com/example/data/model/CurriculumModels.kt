package com.example.data.model

enum class LearningLevel(val displayName: String, val badgeColorHex: Long) {
    BEGINNER("Beginner", 0xFF10B981),
    INTERMEDIATE("Intermediate", 0xFF38BDF8),
    ADVANCED("Advanced", 0xFF818CF8),
    EXPERT("Expert", 0xFFA855F7)
}

data class QuizQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class Flashcard(
    val id: String,
    val question: String,
    val answer: String,
    val category: String,
    val level: LearningLevel
)

data class Lesson(
    val id: String,
    val domainId: String,
    val title: String,
    val durationMinutes: Int,
    val level: LearningLevel,
    val summary: String,
    val explainSimple: String,
    val explainStandard: String,
    val explainDeepDive: String,
    val codeSnippet: String,
    val visualType: String, // "neural_net", "regression", "matrix", "attention", "rag", "code", "chart"
    val keyTakeaways: List<String>,
    val quiz: List<QuizQuestion> = emptyList()
)

data class RoadmapDomain(
    val id: String,
    val title: String,
    val level: LearningLevel,
    val summary: String,
    val iconName: String,
    val estimatedHours: Int,
    val lessons: List<Lesson>
)

data class UserProgress(
    val currentLevel: LearningLevel = LearningLevel.BEGINNER,
    val xp: Int = 1450,
    val streakDays: Int = 14,
    val learningHours: Float = 36.5f,
    val modelsTrained: Int = 8,
    val challengesCompleted: Int = 19,
    val projectsCompleted: Int = 3,
    val completedLessonIds: Set<String> = setOf(
        "intro_ai_ml", "ml_vs_dl", "python_basics", "numpy_arrays", "matrix_mult", "linear_reg"
    ),
    val skillScores: Map<String, Float> = mapOf(
        "Python" to 0.88f,
        "Math & Stats" to 0.76f,
        "Classical ML" to 0.72f,
        "Deep Learning" to 0.58f,
        "LLMs & RAG" to 0.50f,
        "MLOps" to 0.38f
    )
)
