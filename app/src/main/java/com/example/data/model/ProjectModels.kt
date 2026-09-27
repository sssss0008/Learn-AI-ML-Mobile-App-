package com.example.data.model

data class ProjectItem(
    val id: String,
    val title: String,
    val level: LearningLevel,
    val category: String,
    val durationHours: Int,
    val description: String,
    val problemStatement: String,
    val architecture: String,
    val datasetName: String,
    val milestones: List<String>,
    val starterCode: String,
    val targetMetrics: Map<String, String>
)

data class ResearchPaper(
    val id: String,
    val title: String,
    val year: Int,
    val authors: String,
    val category: String,
    val abstractText: String,
    val breakthrough: String,
    val keyArchitecture: String,
    val practicalApplication: String
)

data class CreatorInfo(
    val name: String = "Awiskar Acharya",
    val title: String = "AI/ML Engineer & Systems Architect",
    val linkedInUrl: String = "https://www.linkedin.com/in/awiskaracharya/",
    val bio: String = "Passionate AI researcher, software engineer, and educator dedicated to demystifying artificial intelligence, machine learning, and deep neural architectures. Founder and lead creator of AI ML Academy, helping developers transform from curious learners into production-ready AI engineers.",
    val mission: String = "Empower developers worldwide to master AI from first principles—combining mathematics, hands-on experimentation, code, and production deployment.",
    val coreSpecialties: List<String> = listOf(
        "Deep Learning & Neural Networks",
        "Large Language Models & Transformers",
        "RAG & Autonomous AI Agents",
        "Computer Vision & Multimodal Systems",
        "MLOps & Scalable Model Inference",
        "Mathematical Foundations of AI"
    )
)
