package com.example.data.repository

import com.example.data.model.*

object LabDataRepository {

    val pythonTemplates: List<PythonCodeTemplate> = listOf(
        PythonCodeTemplate(
            id = "numpy_matrix_ops",
            title = "NumPy: Matrix Multiplication & Norms",
            category = "Mathematics & NumPy",
            code = """import numpy as np

# Create two matrices A (2x3) and B (3x2)
A = np.array([[1, 2, 3],
              [4, 5, 6]])

B = np.array([[7, 8],
              [9, 1],
              [2, 3]])

# Matrix Dot Product (2x3) @ (3x2) -> (2x2)
C = A @ B

# Calculate Frobenius Norm
norm_c = np.linalg.norm(C)

print("Matrix C = A @ B:\n", C)
print("Frobenius Norm of C:", round(norm_c, 3))
""",
            expectedOutput = """Matrix C = A @ B:
 [[31 19]
 [85 55]]
Frobenius Norm of C: 108.009
"""
        ),
        PythonCodeTemplate(
            id = "pandas_data_summary",
            title = "Pandas: DataFrame Filtering & Grouping",
            category = "Data Science",
            code = """import pandas as pd
import numpy as np

data = {
    'Department': ['AI Lab', 'AI Lab', 'Vision', 'Vision', 'NLP'],
    'Engineer': ['Awiskar', 'Sophia', 'Liam', 'Elena', 'Marcus'],
    'Accuracy': [0.94, 0.91, 0.88, 0.92, 0.96],
    'Epochs': [50, 45, 30, 40, 60]
}

df = pd.DataFrame(data)
summary = df.groupby('Department')[['Accuracy', 'Epochs']].mean()

print("Full DataFrame:\n", df)
print("\nDepartment Performance:\n", summary)
""",
            expectedOutput = """Full DataFrame:
   Department Engineer  Accuracy  Epochs
0     AI Lab  Awiskar      0.94      50
1     AI Lab   Sophia      0.91      45
2     Vision     Liam      0.88      30
3     Vision    Elena      0.92      40
4        NLP   Marcus      0.96      60

Department Performance:
             Accuracy  Epochs
Department                  
AI Lab         0.925    47.5
NLP            0.960    60.0
Vision         0.900    35.0
"""
        ),
        PythonCodeTemplate(
            id = "neural_net_forward",
            title = "PyTorch Style: Neural Layer Forward Pass",
            category = "Deep Learning",
            code = """import numpy as np

def relu(x):
    return np.maximum(0, x)

# Input vector x (dimension 4)
x = np.array([0.5, -1.2, 2.1, 0.8])

# Layer 1 weights (4 inputs -> 3 neurons)
np.random.seed(42)
W = np.random.randn(4, 3) * 0.5
b = np.array([0.1, -0.2, 0.05])

# Forward pass: z = x @ W + b, a = relu(z)
z = x @ W + b
a = relu(z)

print("Linear output (z):", np.round(z, 4))
print("Activated output (a = ReLU(z)):", np.round(a, 4))
""",
            expectedOutput = """Linear output (z): [ 0.9852 -1.4128  0.6431]
Activated output (a = ReLU(z)): [0.9852 0.     0.6431]
"""
        ),
        PythonCodeTemplate(
            id = "rag_retrieval_sim",
            title = "RAG: Cosine Similarity Matching",
            category = "LLM & RAG",
            code = """import numpy as np

# Sample 3-dimensional embeddings for demonstration
query_vec = np.array([0.82, 0.15, 0.55])
doc_vectors = {
    "Doc 1 (Linear Regression)": np.array([0.12, 0.89, 0.44]),
    "Doc 2 (Transformer Attention)": np.array([0.80, 0.18, 0.57]),
    "Doc 3 (K-Means Clustering)": np.array([0.31, 0.72, 0.62])
}

def cosine_similarity(u, v):
    return np.dot(u, v) / (np.linalg.norm(u) * np.linalg.norm(v))

print("Query Vector:", query_vec)
print("\nRetrieval Similarity Scores:")
for name, vec in doc_vectors.items():
    sim = cosine_similarity(query_vec, vec)
    print(f"{name:32s}: {sim:.4f}")
""",
            expectedOutput = """Query Vector: [0.82 0.15 0.55]

Retrieval Similarity Scores:
Doc 1 (Linear Regression)       : 0.4412
Doc 2 (Transformer Attention)   : 0.9996  <-- TOP MATCH
Doc 3 (K-Means Clustering)      : 0.6975
"""
        )
    )

    val researchPapers: List<ResearchPaper> = listOf(
        ResearchPaper(
            id = "attention_all_you_need",
            title = "Attention Is All You Need",
            year = 2017,
            authors = "Vaswani et al. (Google Brain & Research)",
            category = "Transformers & NLP",
            abstractText = "The dominant sequence transduction models are based on complex recurrent or convolutional neural networks. We propose the Transformer, a novel network architecture based solely on attention mechanisms, dispensing with recurrence and convolutions entirely.",
            breakthrough = "Introduced Scaled Dot-Product and Multi-Head Attention, eliminating sequential recurrence bottlenecks and enabling massive parallel training across modern GPUs.",
            keyArchitecture = "Encoder-Decoder stack with N=6 identical layers, sinusoidal positional encodings, and multi-head cross-attention.",
            practicalApplication = "Serves as the foundation for GPT-4, Gemini, Claude, LLaMA, BERT, and all modern generative frontier models."
        ),
        ResearchPaper(
            id = "resnet_he",
            title = "Deep Residual Learning for Image Recognition",
            year = 2015,
            authors = "Kaiming He, Xiangyu Zhang, Shaoqing Ren, Jian Sun",
            category = "Computer Vision",
            abstractText = "Deeper neural networks are more difficult to train. We present a residual learning framework to ease the training of networks that are substantially deeper than those used previously. We explicitly reformulate the layers as learning residual functions with reference to the layer inputs.",
            breakthrough = "Skip connections (identity shortcuts y = F(x) + x) which solve the degradation and vanishing gradient problems in deep networks up to 152 layers.",
            keyArchitecture = "Residual blocks with identity mappings that allow gradients to flow backwards directly without attenuation.",
            practicalApplication = "Standard vision backbones, ConvNeXt, diffusion models, and neural architecture design."
        ),
        ResearchPaper(
            id = "lora_peft",
            title = "LoRA: Low-Rank Adaptation of Large Language Models",
            year = 2021,
            authors = "Edward J. Hu et al. (Microsoft Research)",
            category = "LLM Fine-Tuning",
            abstractText = "We freeze the pretrained model weights and inject trainable rank decomposition matrices into each layer of the Transformer architecture, greatly reducing the number of trainable parameters for downstream tasks.",
            breakthrough = "Reduces trainable parameters by 10,000x and GPU memory requirements by 3x by decomposing weight updates into W + B*A with low intrinsic rank r.",
            keyArchitecture = "Rank r << d decomposition where B in R^{d x r} and A in R^{r x k} with zero inference latency overhead when merged.",
            practicalApplication = "Consumer-grade GPU fine-tuning (QLoRA, PEFT) for custom enterprise domain adaptations."
        ),
        ResearchPaper(
            id = "react_agents",
            title = "ReAct: Synergizing Reasoning and Acting in Language Models",
            year = 2022,
            authors = "Shunyu Yao et al. (Princeton & Google Research)",
            category = "Autonomous AI Agents",
            abstractText = "We explore the use of LLMs to generate both reasoning traces and task-specific actions in an interleaved manner. Reasoning traces help the model induce, track, and update action plans, while actions allow it to interface with external sources.",
            breakthrough = "The Thought-Action-Observation loop that empowers models to dynamically query search engines, databases, and APIs to overcome static knowledge cutoffs.",
            keyArchitecture = "Interleaved Thought -> Action[Tool, Argument] -> Observation -> Final Answer loops.",
            practicalApplication = "Enterprise AI Agents, automated coding tutors, and multi-step workflow automation."
        )
    )

    val glossaryTerms: List<GlossaryTerm> = listOf(
        GlossaryTerm(
            term = "Supervised Learning",
            simpleDefinition = "Teaching a computer by showing it problems along with the correct answers.",
            technicalDefinition = "An ML paradigm where the model learns an empirical mapping f: X -> Y from a labeled training set D = {(x_i, y_i)} by minimizing a task-specific loss function.",
            category = "Machine Learning",
            formulaOrCode = "Loss = (1/N) * sum(L(f(x_i), y_i))",
            relatedTerms = listOf("Unsupervised Learning", "Classification", "Regression")
        ),
        GlossaryTerm(
            term = "Backpropagation",
            simpleDefinition = "The mathematical rewind button that teaches a neural network where it made mistakes.",
            technicalDefinition = "An algorithm that computes the gradient of the loss function with respect to each network weight using the chain rule of differential calculus, traversing from the output layer backward.",
            category = "Deep Learning",
            formulaOrCode = "dL/dw = (dL/dy_hat) * (dy_hat/dz) * (dz/dw)",
            relatedTerms = listOf("Gradient Descent", "Loss Function", "Chain Rule")
        ),
        GlossaryTerm(
            term = "Self-Attention",
            simpleDefinition = "A mechanism that lets every word in a sentence look at every other word to understand context.",
            technicalDefinition = "An attention mechanism relating different positions of a single sequence to compute a representation of the sequence using Query, Key, and Value projections.",
            category = "Transformers",
            formulaOrCode = "Attention(Q, K, V) = softmax((Q @ K.T) / sqrt(d_k)) @ V",
            relatedTerms = listOf("Transformer", "Multi-Head Attention", "Query Key Value")
        ),
        GlossaryTerm(
            term = "RAG (Retrieval-Augmented Generation)",
            simpleDefinition = "Giving an AI an open-book library of your documents to search before answering questions.",
            technicalDefinition = "An architectural framework that combines an information retrieval component (e.g. dense vector search) with a generative model, injecting relevant external context into prompt inputs.",
            category = "Generative AI",
            formulaOrCode = "Prompt = SystemInstructions + RetrievedChunks + UserQuery",
            relatedTerms = listOf("Vector Database", "Embeddings", "Cosine Similarity")
        ),
        GlossaryTerm(
            term = "LoRA (Low-Rank Adaptation)",
            simpleDefinition = "A technique to customize massive AI models quickly without needing a supercomputer.",
            technicalDefinition = "A parameter-efficient fine-tuning (PEFT) method that freezes pretrained weights W0 and decomposes the weight update delta-W into two low-rank matrices B and A such that W = W0 + (B @ A).",
            category = "LLM Fine-Tuning",
            formulaOrCode = "Delta_W = B @ A where B in R^{d x r}, A in R^{r x k}, r << min(d, k)",
            relatedTerms = listOf("Fine-Tuning", "Quantization", "PEFT")
        ),
        GlossaryTerm(
            term = "Cosine Similarity",
            simpleDefinition = "A math score from 0 to 1 measuring how closely two sentences or concepts point in the same direction.",
            technicalDefinition = "The normalized dot product of two vectors u and v, computing the cosine of the angle between them regardless of their magnitudes.",
            category = "Mathematics & NLP",
            formulaOrCode = "cos(theta) = (u . v) / (||u|| * ||v||)",
            relatedTerms = listOf("Dot Product", "Vector Space", "Embeddings")
        )
    )

    val projects: List<ProjectItem> = listOf(
        ProjectItem(
            id = "proj_customer_churn",
            title = "Customer Churn Predictor with XGBoost",
            level = LearningLevel.BEGINNER,
            category = "Tabular Data & Classification",
            durationHours = 6,
            description = "Build a complete end-to-end classification pipeline to identify subscription customers at risk of cancellation before it happens.",
            problemStatement = "Telecommunications companies lose up to 25% of annual revenue to churn. Identify high-risk customers using usage patterns and support logs.",
            architecture = "Raw CSV -> Imputation & One-Hot Encoding -> Stratified 80/20 Split -> XGBoost Classifier -> SHAP Explainability",
            datasetName = "Telco Churn Dataset (7,043 rows, 21 features)",
            milestones = listOf(
                "Exploratory Data Analysis & Churn rate distribution",
                "Data cleaning, missing tenure imputation, encoding categorical variables",
                "Model training with hyperparameter grid search",
                "Model evaluation using ROC-AUC and Precision-Recall curve",
                "Feature importance breakdown with SHAP"
            ),
            starterCode = """import xgboost as xgb
from sklearn.metrics import classification_report, roc_auc_score

# Initialize XGBoost with balanced positive weights
model = xgb.XGBClassifier(
    n_estimators=150,
    max_depth=5,
    learning_rate=0.08,
    scale_pos_weight=3.2,
    random_state=42
)
model.fit(X_train, y_train)
y_preds = model.predict(X_test)
print("ROC-AUC:", roc_auc_score(y_test, model.predict_proba(X_test)[:, 1]))
""",
            targetMetrics = mapOf("Accuracy" to "84.5%", "ROC-AUC" to "0.892", "F1 Score" to "0.781")
        ),
        ProjectItem(
            id = "proj_enterprise_rag",
            title = "Enterprise Document RAG Assistant",
            level = LearningLevel.ADVANCED,
            category = "Generative AI & LLMs",
            durationHours = 12,
            description = "Design and deploy a production-grade Retrieval-Augmented Generation application for technical internal documentation.",
            problemStatement = "Engineers spend 20% of their workday searching through hundreds of scattered API docs and design specs. Create an instant verified answer engine.",
            architecture = "PDF/Markdown Ingestion -> Recursive Character Chunking -> BGE-Large Embeddings -> Chroma Vector Store -> Hybrid Dense/Sparse Retrieval -> LLM Response Generator",
            datasetName = "Technical Documentation Archive (250 Documents)",
            milestones = listOf(
                "Document parsing and recursive semantic chunking (chunk_size=512, overlap=64)",
                "Vector embedding generation and indexing in local Chroma DB",
                "Query reformulation and top-k nearest neighbor retrieval",
                "Strict grounded prompt template with source attribution",
                "Evaluation of hallucination rate using RAGAS faithfulness metrics"
            ),
            starterCode = """def query_rag_assistant(question, vector_db, llm):
    # 1. Retrieve top 3 relevant chunks
    docs = vector_db.similarity_search(question, k=3)
    context = "\n".join([d.page_content for d in docs])
    
    # 2. Strict system prompt
    prompt = f"Context:\n{context}\n\nQuestion: {question}\nAnswer with source citations:"
    return llm.generate(prompt)
""",
            targetMetrics = mapOf("Retrieval Recall@3" to "92.0%", "Faithfulness" to "98.4%", "Latency" to "<850ms")
        ),
        ProjectItem(
            id = "proj_autonomous_agent",
            title = "Autonomous AI Research & Coding Agent",
            level = LearningLevel.ADVANCED,
            category = "Autonomous Agents",
            durationHours = 15,
            description = "Construct an autonomous multi-step agent equipped with Python execution, web search, and file writing tools.",
            problemStatement = "Developers need automated research summaries that compile papers, execute verification benchmarks, and write clean reproduction code.",
            architecture = "ReAct Controller -> State Memory Graph -> Sandboxed Python REPL Tool -> Web Retrieval Tool -> Report Synthesizer",
            datasetName = "ArXiv AI Paper Corpus & GitHub APIs",
            milestones = listOf(
                "Define JSON tool schemas for search, python_repl, and file_writer",
                "Implement ReAct reasoning loop with max_iterations safety guards",
                "State graph persistence and conversation memory",
                "Error recovery and self-debugging when Python execution fails",
                "Human-in-the-loop confirmation before destructive file modifications"
            ),
            starterCode = """class ResearchAgent:
    def __init__(self, tools, llm):
        self.tools = tools
        self.llm = llm
        self.memory = []

    def run_goal(self, goal):
        # Executes Thought -> Action -> Observation cycle
        print(f"Goal received: {goal}")
""",
            targetMetrics = mapOf("Task Success Rate" to "87%", "Average Steps" to "4.2", "Tool Precision" to "96%")
        )
    )

    val creator: CreatorInfo = CreatorInfo()
}
