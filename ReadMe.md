
[//]: # (Do Not Push IN main for now!!)
=======
# 🗣️ SPEEK Scripting Engine

> **Simple Plain English Execution Kit** — a custom scripting language interpreter built from scratch in Java.

SPEEK lets you write and run programs in plain English. No semicolons, no brackets, no boilerplate — just readable code that actually executes.

```
let score be 85
if score is greater than 50 then
say "Pass"

repeat 3 times
say "hello"
```

```
Pass
hello
hello
hello
```

---

## 📋 Table of Contents

- [About the Project](#about-the-project)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running a Program](#running-a-program)
- [Project Structure](#project-structure)
- [Language Syntax](#language-syntax)
- [How It Works](#how-it-works)
- [Contributing](#contributing)
- [Team](#team)

---

## About the Project

SPEEK is a fully working interpreter for a small made-up programming language. You write code in `.speek` files, run the interpreter, and see real output on screen.

Built as a group project for **Advanced Object-Oriented Programming in Java** at Sitare University. The entire interpreter is written in pure Java with no external libraries.

**What it supports:**
- Variable assignment and arithmetic expressions
- String output
- Conditional blocks (`if ... then`)
- Fixed-count loops (`repeat N times`)
- Correct operator precedence — `*` before `+`, just like standard maths

**What makes it interesting:**
- Built around a real 3-stage pipeline: Tokenizer → Parser → Evaluator
- Every stage is designed using SOLID principles and OOP patterns
- Reads the phrase `"is greater than"` as a single comparison token

---

## Getting Started

### Prerequisites

You need Java JDK 11 or later installed.

```bash
# Check if you already have it
java -version
javac -version
```

If not installed:
- **Ubuntu / Debian** — `sudo apt install default-jdk`
- **macOS** — `brew install openjdk`
- **Windows** — download from [adoptium.net](https://adoptium.net)

### Installation

```bash
# 1. Clone the repository
git clone https://github.com/bocchi277/Speek-Scripting-Engine.git

# 2. Navigate into the project
cd Speek-Scripting-Engine

# 3. Compile
mkdir -p out
javac -d out \
  src/tokenizer/*.java \
  src/parser/*.java \
  src/parser/nodes/*.java \
  src/evaluator/*.java \
  src/evaluator/instructions/*.java \
  src/interpreter/*.java \
  Main.java
```

### Running a Program

Create a file called `hello.speek`:

```
let name be "World"
say "Hello"
say name
```

Run it:

```bash
java -cp out Main hello.speek
```

Output:
```
Hello
World
```

**Debug mode** — prints the token stream, instruction list, and every variable change:

```bash
java -cp out Main hello.speek --debug
```

---

## Project Structure

```
speek-interpreter/
│
├── src/
│   ├── tokenizer/              # Stage 1 — Lexical Analysis
│   │   ├── Token.java          # Immutable token value object
│   │   ├── TokenType.java      # Enum of all token types
│   │   └── Tokenizer.java      # Converts source text → token list
│   │
│   ├── parser/                 # Stage 2 — Syntax Analysis
│   │   ├── Parser.java         # Converts token list → instruction list
│   │   ├── Expression.java     # Expression interface
│   │   └── nodes/              # AST node classes
│   │       ├── NumberNode.java
│   │       ├── StringNode.java
│   │       ├── VariableNode.java
│   │       └── BinaryOpNode.java
│   │
│   ├── evaluator/              # Stage 3 — Execution
│   │   ├── Environment.java    # Variable store (Map<String, Object>)
│   │   ├── Instruction.java    # Instruction interface
│   │   └── instructions/       # Concrete instruction classes
│   │       ├── AssignInstruction.java
│   │       ├── PrintInstruction.java
│   │       ├── IfInstruction.java
│   │       └── RepeatInstruction.java
│   │
│   └── interpreter/
│       └── Interpreter.java    # Wires all 3 stages together
│
├── test/                       # Sample .speek programs
├── Main.java                   # CLI entry point
└── README.md
```

---

## Language Syntax

### Assign a variable
```
let x be 10
let name be "Sitare"
let result be x + y * 2
```

### Print to screen
```
say x
say "hello world"
```

### Conditional
```
if score is greater than 50 then
say "Pass"
```

### Loop
```
repeat 4 times
say "hello"
```

### Operators

| Operator | Description |
|----------|-------------|
| `+`  `-`  `*`  `/` | Arithmetic |
| `is greater than` | Greater-than comparison (plain English) |
| `>` | Greater than (symbol shorthand) |
| `<` | Less than |
| `==` | Equality check |

Operator precedence follows standard maths — `*` and `/` always before `+` and `-`.

---

## How It Works

The interpreter runs in three stages:

```
Source text
    │
    ▼  Tokenizer
List<Token>        — "let", "x", "be", "10" ...
    │
    ▼  Parser
List<Instruction>  — AssignInstruction, PrintInstruction ...
    │
    ▼  Evaluator
Output             — results printed to screen
```

**Tokenizer** reads the source character by character and produces a flat list of labelled tokens. It also handles the three-word phrase `is greater than` and collapses it into a single token.

**Parser** reads the token list using recursive-descent parsing and builds an Abstract Syntax Tree (AST). Operator precedence is handled automatically by the method call chain — `parseExpression()` → `parseTerm()` → `parsePrimary()`.

**Evaluator** walks the instruction list, evaluates each expression tree bottom-up, and produces the final output. All variable values are stored in a shared `Environment` map.

---

## Contributing

This project uses a branch-per-section workflow.

| Branch | Purpose |
|--------|---------|
| `main` | Stable, working, merged code only |
| `noobDevs` | Integration — `Main.java` and `Interpreter.java` |
| `section/tokenizer` | Tokenizer stage |
| `section/parser` | Parser stage |
| `section/evaluator` | Evaluator stage |

```bash
# Work on your section
git checkout section/tokenizer

# Push your changes
git push origin section/tokenizer

# Open a pull request into main when ready
```

Do not push directly to `main`. Open a pull request and get it reviewed by another team member first.

---

## Team

| Member | Section |
|--------|--------|
| `Ekta Kumari` | `tokenizer` |
| ``Aditya` | `parser` |
| `Sourabh Bisht` | `evaluator` |


---

*Sitare University · Advanced OOP in Java · Group Project*

