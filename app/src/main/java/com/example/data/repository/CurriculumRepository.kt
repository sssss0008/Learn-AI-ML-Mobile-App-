package com.example.data.repository

import com.example.data.model.*

object CurriculumRepository {

    val domains: List<RoadmapDomain> = listOf(
        RoadmapDomain(
            id = "ai_fundamentals",
            title = "1. AI & ML Fundamentals",
            level = LearningLevel.BEGINNER,
            summary = "Core concepts: AI vs ML vs Deep Learning, Supervised vs Unsupervised vs Reinforcement learning, parameters, loss functions.",
            iconName = "Psychology",
            estimatedHours = 12,
            lessons = listOf(
                Lesson(
                    id = "intro_ai_ml",
                    domainId = "ai_fundamentals",
                    title = "What is Artificial Intelligence & Machine Learning?",
                    durationMinutes = 20,
                    level = LearningLevel.BEGINNER,
                    summary = "Understand how AI mimics cognitive tasks, and how ML learns patterns from statistical data instead of hardcoded rules.",
                    explainSimple = "Traditional programming is like a cookbook recipe: you write every exact step. Machine Learning is like showing a child thousands of photos of cats until they learn to recognize cats on their own!",
                    explainStandard = "Machine Learning (ML) is a subset of AI that trains mathematical algorithms on existing datasets (features X, labels y) to discover an underlying function f(X) ≈ y that generalizes accurately to unseen data points.",
                    explainDeepDive = "Formally, given a dataset D = {(x_i, y_i)} drawn from unknown distribution P(X, Y), ML seeks an empirical risk minimizer f* in hypothesis class H that minimizes the expected loss: R(f) = E[L(f(X), Y)].",
                    codeSnippet = """
# Traditional vs ML mindset
def traditional_spam_filter(text):
    return "free money" in text.lower() # brittle rules

# Machine Learning formulation
from sklearn.linear_model import LogisticRegression
model = LogisticRegression()
model.fit(X_train_features, y_train_labels)
predictions = model.predict(X_test)
                    """.trimIndent(),
                    visualType = "chart",
                    keyTakeaways = listOf(
                        "AI is the umbrella concept of machines performing intelligent tasks.",
                        "ML algorithms build mathematical models based on sample data.",
                        "Supervised learning requires ground-truth labels; unsupervised learning discovers latent patterns."
                    ),
                    quiz = listOf(
                        QuizQuestion(
                            id = "q1_fund",
                            question = "What distinguishes Machine Learning from traditional rule-based software?",
                            options = listOf(
                                "ML uses larger RAM and faster processors",
                                "ML learns the decision function f(X) from data rather than hardcoded if/else rules",
                                "ML only works with text data",
                                "Traditional software cannot output numbers"
                            ),
                            correctIndex = 1,
                            explanation = "Machine learning derives the mapping function directly by optimizing loss over sample data, rather than requiring engineers to manually craft every condition."
                        )
                    )
                ),
                Lesson(
                    id = "ml_workflow_data",
                    domainId = "ai_fundamentals",
                    title = "The ML Lifecycle: Train, Val, Test Splits",
                    durationMinutes = 25,
                    level = LearningLevel.BEGINNER,
                    summary = "Why we never evaluate on training data, preventing data leakage, and balancing bias vs variance.",
                    explainSimple = "If you give students the exact exam questions during homework, they will memorize answers without understanding. The test split is the secret final exam!",
                    explainStandard = "The training set optimizes parameters (weights w, biases b). The validation set tunes hyperparameters (learning rate, depth). The test set provides unbiased final evaluation.",
                    explainDeepDive = "Data leakage occurs when target or test information inadvertently influences training representations, yielding overly optimistic empirical validation error that degrades sharply in production distributions.",
                    codeSnippet = """
from sklearn.model_selection import train_test_split

# 80% train+val, 20% test
X_temp, X_test, y_temp, y_test = train_test_split(
    X, y, test_size=0.20, random_state=42, stratify=y
)
# 70% train, 10% validation
X_train, X_val, y_train, y_val = train_test_split(
    X_temp, y_temp, test_size=0.125, random_state=42
)
print(f"Train: {len(X_train)}, Val: {len(X_val)}, Test: {len(X_test)}")
                    """.trimIndent(),
                    visualType = "regression",
                    keyTakeaways = listOf(
                        "Always isolate the test set prior to preprocessing or feature scaling.",
                        "Use stratified splits when classification classes are imbalanced.",
                        "Overfitting occurs when training loss is low but validation loss increases."
                    )
                )
            )
        ),
        RoadmapDomain(
            id = "python_ai",
            title = "2. Python for AI & Data Lab",
            level = LearningLevel.BEGINNER,
            summary = "Master vectorized computation with NumPy, tabular manipulation with Pandas, and idiomatic AI Python.",
            iconName = "Code",
            estimatedHours = 18,
            lessons = listOf(
                Lesson(
                    id = "numpy_vectorization",
                    domainId = "python_ai",
                    title = "NumPy: Vectorization & Broadcasting",
                    durationMinutes = 30,
                    level = LearningLevel.BEGINNER,
                    summary = "Why NumPy arrays are 50x faster than standard Python lists via contiguous C-memory layouts and SIMD execution.",
                    explainSimple = "Python lists are like single boxes with messy paper tags. NumPy arrays are like packed cargo containers aligned side-by-side in superfast memory!",
                    explainStandard = "NumPy ndarrays store homogeneous elements in contiguous blocks of memory. Vectorized operations compute element-wise calculations in optimized BLAS/LAPACK C routines without Python GIL overhead.",
                    explainDeepDive = "Broadcasting rules compare trailing array dimensions element-wise. Two dimensions are compatible when: 1) they are equal, or 2) one of them is 1. Memory is not replicated; strides are set to 0.",
                    codeSnippet = """
import numpy as np

# Vectorized dot product vs slow Python loop
a = np.random.randn(1000000)
b = np.random.randn(1000000)

# 100x faster: compiled C execution
dot_product = np.dot(a, b)

# Broadcasting example: Normalize (X - mu) / sigma
X = np.array([[10, 20], [30, 40], [50, 60]]) # Shape (3, 2)
mu = np.mean(X, axis=0)                       # Shape (2,) broadcasted to (3, 2)
sigma = np.std(X, axis=0)
X_norm = (X - mu) / sigma
print(X_norm.shape) # (3, 2)
                    """.trimIndent(),
                    visualType = "code",
                    keyTakeaways = listOf(
                        "Never use python 'for' loops across dataset samples; use vectorized NumPy broadcasting.",
                        "Matrix multiplication uses '@' or 'np.matmul(A, B)'.",
                        "Shapes must align: (M, K) @ (K, N) produces (M, N)."
                    )
                ),
                Lesson(
                    id = "pandas_dataframe_lab",
                    domainId = "python_ai",
                    title = "Pandas: DataFrames & Feature Cleaning",
                    durationMinutes = 35,
                    level = LearningLevel.BEGINNER,
                    summary = "Filter, group, impute missing values, handle categorical encodings, and transform features for ML pipelines.",
                    explainSimple = "Pandas turns raw messy CSV spreadsheets into flexible Python objects where you can filter, group, and calculate stats with one line of code.",
                    explainStandard = "A Pandas DataFrame is a 2-dimensional labeled data structure with columns of potentially different types, offering indexing, grouping, aggregation, and vectorized string/datetime transformations.",
                    explainDeepDive = "Memory footprint optimization is critical: convert object dtypes to categoricals or downcast int64 to int32/int16, saving up to 80% RAM during exploratory data analysis.",
                    codeSnippet = """
import pandas as pd

# Load and clean customer dataset
df = pd.read_csv('customers.csv')
# Impute missing income with median
df['income'] = df['income'].fillna(df['income'].median())

# One-hot encode categorical column
df = pd.get_dummies(df, columns=['subscription_tier'], drop_first=True)

# GroupBy aggregation
stats = df.groupby('churned')['total_spend'].agg(['mean', 'std', 'count'])
print(stats)
                    """.trimIndent(),
                    visualType = "chart",
                    keyTakeaways = listOf(
                        "Always inspect df.isnull().sum() and df.describe() first.",
                        "One-hot encode low cardinality categories; target-encode or embed high cardinality.",
                        "Prevent data leakage by computing imputation medians strictly on train split."
                    )
                )
            )
        ),
        RoadmapDomain(
            id = "math_stats",
            title = "3. Mathematics & Statistics for AI",
            level = LearningLevel.BEGINNER,
            summary = "Linear algebra, matrix transformations, calculus gradients, chain rule, and probability distributions.",
            iconName = "Calculate",
            estimatedHours = 20,
            lessons = listOf(
                Lesson(
                    id = "linear_algebra_matrices",
                    domainId = "math_stats",
                    title = "Linear Algebra: Matrices, Vectors & Dot Products",
                    durationMinutes = 35,
                    level = LearningLevel.BEGINNER,
                    summary = "How vectors represent embeddings, dot products measure similarity, and matrices represent neural layer weights.",
                    explainSimple = "Think of a vector as an arrow in space. The dot product tells you how much two arrows are pointing in the same direction!",
                    explainStandard = "The dot product u · v = ||u|| ||v|| cos(θ). In machine learning, cosine similarity is normalized dot product, fundamental to vector databases, recommendation systems, and attention heads.",
                    explainDeepDive = "A linear layer y = Wx + b geometrically applies a rotation and scaling (via matrix W) followed by a translation (via bias b) in Euclidean n-space.",
                    codeSnippet = """
import numpy as np

# Geometric dot product & Cosine Similarity
u = np.array([1.0, 2.0, 3.0])
v = np.array([2.0, 4.0, 6.0])

dot = np.dot(u, v)
cosine_sim = dot / (np.linalg.norm(u) * np.linalg.norm(v))
print(f"Cosine Similarity: {cosine_sim:.4f}") # 1.0000 (parallel)
                    """.trimIndent(),
                    visualType = "matrix",
                    keyTakeaways = listOf(
                        "Dot products are the fundamental operation of artificial neurons.",
                        "Matrix multiplication W @ x projects inputs from dimension D_in to D_out.",
                        "Eigenvalues and eigenvectors reveal the principal axes of data variance (PCA)."
                    )
                ),
                Lesson(
                    id = "gradient_descent_calculus",
                    domainId = "math_stats",
                    title = "Calculus: Derivatives, Gradients & Gradient Descent",
                    durationMinutes = 40,
                    level = LearningLevel.BEGINNER,
                    summary = "Understanding partial derivatives, the gradient vector ∇L, the chain rule, and iterative optimization.",
                    explainSimple = "Imagine you are blindfolded on a foggy mountain and want to reach the valley. You feel the ground with your foot to find the steepest downhill slope and take a step. That is Gradient Descent!",
                    explainStandard = "The gradient vector ∇L contains all partial derivatives of the loss function with respect to weights: [∂L/∂w1, ∂L/∂w2, ...]. Weights are updated in opposite direction: w := w - η * ∇L.",
                    explainDeepDive = "Under Lipschitz smoothness conditions, learning rate η ≤ 1/L guarantees monotonic convergence in convex optimization. Momentum adds an exponential moving average of past gradients to traverse saddle points.",
                    codeSnippet = """
# Interactive Gradient Descent step
# Loss: L(w) = (w - 3)^2, Gradient: dL/dw = 2 * (w - 3)
w = 10.0  # Initial weight far from optimum
lr = 0.1  # Learning rate

for step in range(25):
    grad = 2 * (w - 3.0)
    w = w - lr * grad
    loss = (w - 3.0)**2
    if step % 5 == 0:
        print(f"Step {step:02d} | w: {w:.4f} | Loss: {loss:.6f}")
                    """.trimIndent(),
                    visualType = "regression",
                    keyTakeaways = listOf(
                        "The gradient always points in the direction of steepest ascent; we subtract it for descent.",
                        "Learning rate η must be balanced: too high diverges, too low gets stuck.",
                        "Backpropagation is simply the recursive chain rule applied backward through computational graphs."
                    )
                )
            )
        ),
        RoadmapDomain(
            id = "classical_ml",
            title = "4. Classical Machine Learning",
            level = LearningLevel.INTERMEDIATE,
            summary = "Linear & Logistic Regression, Decision Trees, Random Forests, XGBoost, K-Means Clustering, and Model Evaluation.",
            iconName = "AutoGraph",
            estimatedHours = 24,
            lessons = listOf(
                Lesson(
                    id = "supervised_regression_trees",
                    domainId = "classical_ml",
                    title = "Supervised Learning: Decision Trees to Random Forests",
                    durationMinutes = 35,
                    level = LearningLevel.INTERMEDIATE,
                    summary = "Splitting criteria (Gini impurity vs Information Gain Entropy), bagging ensembles, and feature importances.",
                    explainSimple = "A decision tree is a game of 20 Questions. A Random Forest is an entire committee of 100 experts voting on the answer—reducing mistakes!",
                    explainStandard = "Decision Trees recursively partition feature space to maximize purity. Random Forests combine Bootstrap Aggregating (Bagging) with random feature subspace sampling, drastically reducing variance without increasing bias.",
                    explainDeepDive = "For tree node split s on feature j, criterion maximizes Information Gain: IG = H(parent) - [N_left/N * H(left) + N_right/N * H(right)], where H is Entropy or Gini Index.",
                    codeSnippet = """
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import classification_report

rf = RandomForestClassifier(n_estimators=100, max_depth=6, random_state=42)
rf.fit(X_train, y_train)

y_pred = rf.predict(X_test)
print(classification_report(y_test, y_pred))

# Inspect feature importances
for feat, imp in zip(feature_names, rf.feature_importances_):
    print(f"{feat:15s}: {imp:.3f}")
                    """.trimIndent(),
                    visualType = "chart",
                    keyTakeaways = listOf(
                        "Single trees easily overfit by growing deep branches; use max_depth or min_samples_leaf.",
                        "Random Forests reduce variance through ensemble voting.",
                        "Gradient boosting (XGBoost, LightGBM) trains trees sequentially to correct prior errors."
                    )
                )
            )
        ),
        RoadmapDomain(
            id = "deep_learning",
            title = "5. Deep Learning & Neural Networks",
            level = LearningLevel.INTERMEDIATE,
            summary = "Multi-Layer Perceptrons, activation functions (ReLU, GELU, Sigmoid), backpropagation, and Adam optimizer.",
            iconName = "Hub",
            estimatedHours = 28,
            lessons = listOf(
                Lesson(
                    id = "neural_network_architecture",
                    domainId = "deep_learning",
                    title = "Anatomy of a Neural Network: Forward & Backward Pass",
                    durationMinutes = 40,
                    level = LearningLevel.INTERMEDIATE,
                    summary = "Linear combinations z = Wx + b, non-linear activations a = σ(z), loss computation, and backpropagation flow.",
                    explainSimple = "Each artificial neuron takes input numbers, multiplies them by importance weights, adds a bias knob, and triggers a switch if the total is high enough!",
                    explainStandard = "Without non-linear activations, stacking multiple linear layers collapses into a single linear transformation W2(W1x) = W_combined * x. Activations allow networks to approximate any continuous function (Universal Approximation Theorem).",
                    explainDeepDive = "Backpropagation calculates ∂Loss/∂W_l = (∂Loss/∂z_l) * (a_{l-1})^T by caching forward activations and propagating delta error vectors backwards using the transposed weight matrices.",
                    codeSnippet = """
import numpy as np

def relu(z):
    return np.maximum(0, z)

def relu_deriv(z):
    return (z > 0).astype(float)

# Simple forward pass of 2-layer MLP
# Input x (1x3), Layer 1 W1 (3x4), Layer 2 W2 (4x1)
x = np.array([[0.5, -0.2, 0.9]])
W1 = np.random.randn(3, 4) * 0.1
b1 = np.zeros((1, 4))
W2 = np.random.randn(4, 1) * 0.1
b2 = np.zeros((1, 1))

# Forward Pass
z1 = np.dot(x, W1) + b1
a1 = relu(z1)
z2 = np.dot(a1, W2) + b2
y_hat = 1.0 / (1.0 + np.exp(-z2)) # Sigmoid output
print("Predicted probability:", y_hat)
                    """.trimIndent(),
                    visualType = "neural_net",
                    keyTakeaways = listOf(
                        "Activations introduce non-linearity, enabling deep representations.",
                        "ReLU mitigates vanishing gradients compared to Sigmoid for deep architectures.",
                        "The Adam optimizer combines momentum with adaptive per-parameter learning rate scaling."
                    )
                )
            )
        ),
        RoadmapDomain(
            id = "computer_vision",
            title = "6. Computer Vision & CNNs",
            level = LearningLevel.INTERMEDIATE,
            summary = "Convolutions, 2D kernels, pooling, feature maps, ResNet skip connections, and vision transformers.",
            iconName = "CenterFocusStrong",
            estimatedHours = 22,
            lessons = listOf(
                Lesson(
                    id = "cnn_kernels_feature_maps",
                    domainId = "computer_vision",
                    title = "Convolutional Neural Networks: Kernels & Feature Hierarchies",
                    durationMinutes = 35,
                    level = LearningLevel.INTERMEDIATE,
                    summary = "Why MLPs fail on high-res images (parameter explosion) and how 2D convolutions provide translation equivariance.",
                    explainSimple = "A convolution filter is like a tiny magnifying glass scanning across a photo, looking specifically for vertical edges, textures, or circles wherever they appear!",
                    explainStandard = "CNNs slide parameterized kernels (e.g. 3x3) over input tensor channels to produce feature maps. Early layers learn low-level primitives (edges, gradients); deeper layers compose faces and objects.",
                    explainDeepDive = "Output spatial dimensions follow: O = floor((W - K + 2P)/S) + 1, where W is input dimension, K kernel size, P padding, and S stride. Receptive field grows linearly with depth or exponentially with dilated convolutions.",
                    codeSnippet = """
# PyTorch Conv2D block example
import torch
import torch.nn as nn

class ConvBlock(nn.Module):
    def __init__(self, in_channels, out_channels):
        super().__init__()
        self.conv = nn.Conv2d(in_channels, out_channels, kernel_size=3, padding=1)
        self.bn = nn.BatchNorm2d(out_channels)
        self.relu = nn.ReLU(inplace=True)
        self.pool = nn.MaxPool2d(kernel_size=2, stride=2)

    def forward(self, x):
        return self.pool(self.relu(self.bn(self.conv(x))))
                    """.trimIndent(),
                    visualType = "chart",
                    keyTakeaways = listOf(
                        "Weight sharing in CNNs drastically cuts parameter counts compared to dense layers.",
                        "Pooling layers downsample feature spatial resolution while maintaining channel depth.",
                        "Residual skip connections (ResNet) allow gradients to backpropagate through 100+ layers without vanishing."
                    )
                )
            )
        ),
        RoadmapDomain(
            id = "nlp_embeddings",
            title = "7. NLP & Word Embeddings",
            level = LearningLevel.INTERMEDIATE,
            summary = "Text tokenization, BPE, Word2Vec semantic vectors, cosine similarity, and contextual representations.",
            iconName = "Translate",
            estimatedHours = 20,
            lessons = listOf(
                Lesson(
                    id = "word_embeddings_similarity",
                    domainId = "nlp_embeddings",
                    title = "Word Embeddings: Mapping Semantics to Geometry",
                    durationMinutes = 30,
                    level = LearningLevel.INTERMEDIATE,
                    summary = "Transforming discrete words into continuous dense vector spaces where semantic relationships become geometric vectors.",
                    explainSimple = "In vector space, words with similar meanings live in the same neighborhood. You can even do word math: King - Man + Woman = Queen!",
                    explainStandard = "Embeddings map tokens into R^d (e.g. d=768 or 1536). Unsupervised algorithms like Word2Vec (Skip-Gram/CBOW) train dense vectors using co-occurrence statistics across massive text corpora.",
                    explainDeepDive = "Skip-gram optimizes the log-probability of context words c given target word w: sum log P(c|w), using Negative Sampling to replace expensive full vocabulary softmax normalization.",
                    codeSnippet = """
# Conceptual Word Vector Arithmetic
import numpy as np

# Suppose 3D concept vectors for demonstration
king = np.array([0.9, 0.8, 0.1])
man = np.array([0.2, 0.8, 0.1])
woman = np.array([0.2, 0.2, 0.9])
queen_target = np.array([0.9, 0.2, 0.9])

calc_queen = king - man + woman
diff = np.linalg.norm(calc_queen - queen_target)
print("Vector distance to Queen:", diff) # 0.0 (exact match)
                    """.trimIndent(),
                    visualType = "matrix",
                    keyTakeaways = listOf(
                        "One-hot encoding is high-dimensional and orthogonal; embeddings are dense and semantic.",
                        "Subword tokenization (Byte-Pair Encoding, WordPiece) handles typos and out-of-vocabulary words.",
                        "Modern sentence embeddings (OpenAI, BGE) encapsulate entire documents into single 1024-dim vectors."
                    )
                )
            )
        ),
        RoadmapDomain(
            id = "transformers_attention",
            title = "8. Transformer Architecture & Attention",
            level = LearningLevel.ADVANCED,
            summary = "Self-attention mechanism, Query-Key-Value projection, Multi-Head Attention, and positional encodings.",
            iconName = "ElectricBolt",
            estimatedHours = 26,
            lessons = listOf(
                Lesson(
                    id = "scaled_dot_product_attention",
                    domainId = "transformers_attention",
                    title = "Demystifying Self-Attention: Q, K, and V",
                    durationMinutes = 45,
                    level = LearningLevel.ADVANCED,
                    summary = "How attention allows every token in a sequence to dynamically attend to every other token with O(N^2) context.",
                    explainSimple = "Think of YouTube search: Your query is what you want. The video tags are Keys. When Query matches a Key, you retrieve the Value (video content). Transformers do this for every single word!",
                    explainStandard = "Attention(Q, K, V) = softmax((Q K^T) / sqrt(d_k)) * V. Q (Query), K (Key), and V (Value) are linear projections of the input sequence. The scaling factor sqrt(d_k) prevents softmax saturation.",
                    explainDeepDive = "Multi-Head Attention projects Q, K, V into h distinct subspaces: MHA(Q, K, V) = Concat(head_1, ..., head_h) * W_O. This allows the model to simultaneously attend to syntactic agreements, coreferences, and long-range semantic dependencies.",
                    codeSnippet = """
import numpy as np

def scaled_dot_product_attention(Q, K, V):
    d_k = Q.shape[-1]
    # Step 1: Compute attention scores
    scores = np.matmul(Q, K.swapaxes(-1, -2)) / np.sqrt(d_k)
    # Step 2: Softmax along last axis
    exp_scores = np.exp(scores - np.max(scores, axis=-1, keepdims=True))
    weights = exp_scores / np.sum(exp_scores, axis=-1, keepdims=True)
    # Step 3: Weighted sum of values
    output = np.matmul(weights, V)
    return output, weights
                    """.trimIndent(),
                    visualType = "attention",
                    keyTakeaways = listOf(
                        "Self-attention removes recurrent sequential bottlenecks, enabling massive parallel GPU training.",
                        "Causal masking sets future attention scores to -infinity for autoregressive generation.",
                        "Positional encodings (RoPE, sinusoidal) inject sequence order into permutation-invariant attention."
                    )
                )
            )
        ),
        RoadmapDomain(
            id = "generative_llm",
            title = "9. Generative AI & LLM Systems",
            level = LearningLevel.ADVANCED,
            summary = "Autoregressive generation, temperature, top-p/top-k sampling, KV cache, pretraining vs fine-tuning.",
            iconName = "AutoAwesome",
            estimatedHours = 24,
            lessons = listOf(
                Lesson(
                    id = "llm_sampling_decoding",
                    domainId = "generative_llm",
                    title = "Inside LLM Generation: Decoding Strategies & Sampling",
                    durationMinutes = 35,
                    level = LearningLevel.ADVANCED,
                    summary = "How next-token probabilities are converted to responses using Greedy decoding, Temperature, Top-P, and Top-K.",
                    explainSimple = "An LLM doesn't write whole sentences at once; it plays predictive texting one token at a time! Temperature controls how adventurous or predictable its next word choices are.",
                    explainStandard = "Logits z from the final linear projection are converted to probabilities p_i = exp(z_i / T) / sum(exp(z_j / T)). Higher temperature T flattens the distribution (more creative); lower T sharpens mode peak (more deterministic).",
                    explainDeepDive = "Top-P (nucleus sampling) truncates the distribution to the smallest set of tokens whose cumulative probability exceeds threshold p. KV caching stores previously computed Key and Value matrices in GPU VRAM to avoid redundant O(N^2) calculations during token generation.",
                    codeSnippet = """
import numpy as np

def sample_with_temperature(logits, temperature=0.7, top_p=0.9):
    # Temperature scaling
    scaled_logits = logits / max(temperature, 1e-5)
    probs = np.exp(scaled_logits) / np.sum(np.exp(scaled_logits))

    # Top-P Nucleus Filtering
    sorted_indices = np.argsort(probs)[::-1]
    sorted_probs = probs[sorted_indices]
    cumulative_probs = np.cumsum(sorted_probs)

    # Filter tokens beyond top_p
    indices_to_remove = cumulative_probs > top_p
    indices_to_remove[0] = False # keep at least top-1
    sorted_probs[indices_to_remove] = 0.0
    sorted_probs /= np.sum(sorted_probs)

    # Sample next token
    choice = np.random.choice(sorted_indices, p=sorted_probs)
    return choice
                    """.trimIndent(),
                    visualType = "code",
                    keyTakeaways = listOf(
                        "Temperature = 0.0 gives deterministic greedy generation.",
                        "Context window length dictates maximum prompt + response token capacity.",
                        "KV Cache memory scales linearly with sequence length and batch size."
                    )
                )
            )
        ),
        RoadmapDomain(
            id = "rag_systems",
            title = "10. RAG: Retrieval-Augmented Generation",
            level = LearningLevel.ADVANCED,
            summary = "End-to-end RAG: document ingestion, chunking strategies, vector embeddings, vector databases, and re-ranking.",
            iconName = "MenuBook",
            estimatedHours = 25,
            lessons = listOf(
                Lesson(
                    id = "rag_pipeline_architecture",
                    domainId = "rag_systems",
                    title = "End-to-End RAG Architecture & Vector Retrieval",
                    durationMinutes = 40,
                    level = LearningLevel.ADVANCED,
                    summary = "Eliminate LLM hallucinations by retrieving factual external context and injecting it into prompt templates.",
                    explainSimple = "Instead of testing an open-book student from memory, you let them search a private library of updated documents and quote the exact paragraphs!",
                    explainStandard = "RAG indexes enterprise documents into vector representations stored in specialized databases (Chroma, Pinecone, Milvus). User queries are embedded, top-K nearest chunks are retrieved via Cosine similarity, and prepended to the LLM system prompt.",
                    explainDeepDive = "Advanced RAG utilizes Hybrid Search (Dense vector embeddings + Sparse BM25 keyword matching) combined with cross-encoder Re-ranking, query transformation (Hypothetical Document Embeddings - HyDE), and semantic chunking with overlapping context windows.",
                    codeSnippet = """
# Production RAG prompt assembly pattern
def build_rag_prompt(user_query, retrieved_chunks):
    context_str = "\n\n---\n\n".join([
        f"[Doc {i+1} | Source: {chunk['source']}]:\n{chunk['text']}"
        for i, chunk in enumerate(retrieved_chunks)
    ])

    system_instruction = (
        "You are an expert technical assistant. Answer the user question "
        "STRICTLY using the provided context documents. If the context does "
        "not contain the answer, reply that the information is unavailable. "
        "Never hallucinate facts."
    )

    return f"{system_instruction}\n\nContext:\n{context_str}\n\nQuestion: {user_query}\nAnswer:"
                    """.trimIndent(),
                    visualType = "rag",
                    keyTakeaways = listOf(
                        "RAG prevents hallucinations without requiring expensive model retraining.",
                        "Chunk size and overlap (e.g. 512 tokens with 50 token overlap) determine retrieval precision.",
                        "Always evaluate retrieval precision and generation faithfulness separately."
                    )
                )
            )
        ),
        RoadmapDomain(
            id = "ai_agents",
            title = "11. Autonomous AI Agents & Tool Calling",
            level = LearningLevel.ADVANCED,
            summary = "ReAct reasoning loop, function calling, stateful memory, multi-agent collaboration, and human-in-the-loop validation.",
            iconName = "SmartToy",
            estimatedHours = 28,
            lessons = listOf(
                Lesson(
                    id = "react_tool_calling_agents",
                    domainId = "ai_agents",
                    title = "Building Autonomous AI Agents with Tool Calling",
                    durationMinutes = 45,
                    level = LearningLevel.ADVANCED,
                    summary = "Empower LLMs with agency: Thought -> Action -> Observation loops to interact with external APIs and systems.",
                    explainSimple = "A regular chatbot can only talk. An AI Agent can actually act: it can check the weather API, calculate taxes in Python, write emails, and check flight bookings!",
                    explainStandard = "The ReAct (Reason + Act) paradigm prompts LLMs to interleave reasoning traces ('Thought') with concrete tool invocations ('Action: search_database(query)'). The environment returns results ('Observation'), until the agent reaches the final solution.",
                    explainDeepDive = "Stateful agent frameworks (LangGraph, AutoGen) represent workflows as directed cyclic graphs where nodes are LLM decision-makers or tool workers, and edges carry validated JSON schema payloads and guardrails.",
                    codeSnippet = """
# Autonomous Agent Loop
class WeatherTool:
    def execute(self, city):
        return f"Current temperature in {city}: 22°C, Sunny."

def agent_step(prompt, tools):
    # LLM outputs structured JSON tool call
    decision = {
        "thought": "I need live weather data for Tokyo.",
        "action": "weather_api",
        "params": {"city": "Tokyo"}
    }

    if decision["action"] == "weather_api":
        tool_result = tools["weather"].execute(decision["params"]["city"])
        return f"Based on live data: {tool_result}"
                    """.trimIndent(),
                    visualType = "chart",
                    keyTakeaways = listOf(
                        "Agents combine LLM reasoning with programmatic API execution.",
                        "Always implement tool sandboxing, timeout limits, and human approval for dangerous actions.",
                        "Maintain memory across multi-turn interactions using conversation buffers and summary summarizers."
                    )
                )
            )
        ),
        RoadmapDomain(
            id = "mlops_production",
            title = "12. MLOps & Production AI Deployment",
            level = LearningLevel.EXPERT,
            summary = "Model serving (FastAPI, Triton, vLLM), containerization, CI/CD, data drift monitoring, and AI safety guardrails.",
            iconName = "CloudSync",
            estimatedHours = 24,
            lessons = listOf(
                Lesson(
                    id = "model_serving_monitoring",
                    domainId = "mlops_production",
                    title = "Model Serving, Latency Optimization & Data Drift",
                    durationMinutes = 40,
                    level = LearningLevel.EXPERT,
                    summary = "Transitioning models from research notebooks to high-throughput production microservices with SLA guarantees.",
                    explainSimple = "Training a model is like inventing a great recipe. MLOps is building a worldwide restaurant franchise that serves millions of fresh meals every day without running out of ingredients!",
                    explainStandard = "Production deployment requires REST/gRPC microservices, batching, GPU quantization (AWQ, INT8, FP8), and continuous monitoring for covariate shift and concept drift using Kolmogorov-Smirnov statistical tests.",
                    explainDeepDive = "vLLM employs PagedAttention, partitioning KV-cache into virtual blocks to eliminate internal and external memory fragmentation, achieving 10x-24x higher throughput compared to standard HuggingFace pipelines.",
                    codeSnippet = """
# Production Model Inference Server using FastAPI
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel

app = FastAPI(title="Production AI Inference Service")

class PredictRequest(BaseModel):
    features: list[float]

@app.post("/v1/predict")
async def predict(req: PredictRequest):
    if len(req.features) != 10:
        raise HTTPException(status_code=400, detail="Invalid feature count")
    # Vectorized fast inference
    score = sum(f * 0.1 for f in req.features)
    return {"status": "success", "prediction": score, "latency_ms": 4.2}
                    """.trimIndent(),
                    visualType = "code",
                    keyTakeaways = listOf(
                        "Model serving requires low latency (P95/P99), high concurrency, and auto-scaling.",
                        "Quantization reduces memory footprint with negligible accuracy loss.",
                        "Continuously log inputs and monitor for data drift using baseline distributions."
                    )
                )
            )
        )
    )

    fun getDomain(domainId: String): RoadmapDomain? {
        return domains.find { it.id == domainId }
    }

    fun getLesson(lessonId: String): Lesson? {
        for (domain in domains) {
            val lesson = domain.lessons.find { it.id == lessonId }
            if (lesson != null) return lesson
        }
        return null
    }
}
