```
███████╗██████╗ ███████╗███████╗██╗  ██╗
██╔════╝██╔══██╗██╔════╝██╔════╝██║ ██╔╝
███████╗██████╔╝█████╗  █████╗  █████╔╝ 
╚════██║██╔═══╝ ██╔══╝  ██╔══╝  ██╔═██╗ 
███████║██║     ███████╗███████╗██║  ██╗
╚══════╝╚═╝     ╚══════╝╚══════╝╚═╝  ╚═╝
```
 
**Simple Plain English Execution Kit**
 
*A custom scripting language interpreter — built from scratch in Java.*
 
[![Java](https://imgs.search.brave.com/rhxy3SznYZa7d7ztHrI7yk4a6MBO0sN5Cv6iUG_D-lg/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9tZWRp/YS5pc3RvY2twaG90/by5jb20vaWQvMTA0/NjQ4NTE5MC92ZWN0/b3IvaGVsbG8td29y/ZC1jbG91ZC1pbi1k/aWZmZXJlbnQtbGFu/Z3VhZ2VzLmpwZz9z/PTYxMng2MTImdz0w/Jms9MjAmYz1sUVd3/YXhuczBSbEZMVUls/Q3lDOVljc0xRUVZv/dnlVeGZRT2FkcTRm/NFFRPQ)

---
 
> *Write code the way you think. No semicolons. No brackets. No boilerplate. Just words that execute.*
 
```plaintext
let score be 85
if score is greater than 50 then
    say "You passed!"
 
repeat 3 times
    say "SPEEK is alive"
```
 
```
▶  You passed!
   SPEEK is alive
   SPEEK is alive
   SPEEK is alive
```
 
---
 
## ◈ Table of Contents
 
- [What is SPEEK?](#-what-is-speek)
- [Getting Started](#-getting-started)
- [Language Syntax](#-language-syntax)
- [How It Works](#-how-it-works)
- [Project Structure](#-project-structure)
- [Contributing](#-contributing)
- [Team](#-team)
 
---
 
## ◈ What is SPEEK?
 
SPEEK is a fully working interpreter for a purpose-built scripting language. Write `.speek` files in natural English — run them like any program — see real output.
 
Built as a group project for **Advanced Object-Oriented Programming in Java** at Sitare University. Zero external libraries. Pure Java from the ground up.
 
### What it supports
 
| Feature | Description |
|---|---|
| `let x be ...` | Variable assignment with arithmetic |
| `say ...` | Print strings and variables to stdout |
| `if ... then` | Conditional blocks with indentation |
| `repeat N times` | Fixed-count loop blocks |
| `is greater than` | Natural-language comparison operators |
| `+ - * /` | Arithmetic with correct precedence |
 
### What makes it interesting
 
- **Real 3-stage pipeline** — Tokenizer → Parser → Evaluator, each stage fully decoupled
- **SOLID principles throughout** — every class has one job and does it well
- **Multi-word token recognition** — reads `is greater than` as a single comparison token
- **Operator precedence** — `*` and `/` always before `+` and `-`, just like real math
 
---
 
## ◈ Getting Started
 
### Prerequisites
 
You need **Java JDK 11 or later**.
 
```bash
# Check your current version
java -version
javac -version
```
Don't have Java? Click to install
 
| Platform | Command |
|---|---|
| Ubuntu / Debian | `sudo apt install default-jdk` |
| macOS | `brew install openjdk` |
| Windows | [Download from Adoptium](https://adoptium.net) |
 
### Installation
 
```bash
# 1 — Clone the repo
git clone https://github.com/bocchi277/Speek-Scripting-Engine.git
 
# 2 — Enter the project
cd Speek-Scripting-Engine
 
# 3 — Compile the engine
javac src/tokenizer/*.java \
      src/parser/*.java \
      src/parser/nodes/*.java \
      src/evaluator/*.java \
      src/evaluator/instructions/*.java \
      src/interpreter/*.java
```
 
### Run your first program
 
Create `tests/hello.speek`:
 
```plaintext
let name be "World"
say "Hello"
say name
```
 
Execute it:
 
```bash
java -cp src interpreter.Interpreter tests/hello.speek
```
 
Output:
 
```
Hello
World
```
 
---
 
## ◈ Language Syntax
 
### Assign a Variable
 
```plaintext
let x be 10
let name be "Sitare"
let result be x + y * 2
```
 
### Print to Screen
 
```plaintext
say x
say "hello world"
```
 
### Conditional Block
 
> ⚠ Indentation is required inside blocks.
 
```plaintext
if score > 50 then
    say "Pass"
```
 
### Loop Block
 
> ⚠ Indentation is required inside blocks.
 
```plaintext
repeat 4 times
    say "hello"
```
 
### Operators
 
| Operator | Type | Description |
|---|---|---|
| `+` `-` `*` `/` | Arithmetic | Standard math operations |
| `is greater than` | Comparison | Plain-English greater-than |
| `>` | Comparison | Symbol shorthand for greater-than |
| `<` | Comparison | Less than |
| `==` | Comparison | Equality check |
 
> **Precedence:** `*` and `/` always evaluate before `+` and `-`.
 
---
 
## ◈ How It Works
 
SPEEK processes your code through a clean **3-stage pipeline**:
 
```
┌─────────────────────────────────────────────────────────────────┐
│                        Source Code (.speek)                     │
└─────────────────────────────┬───────────────────────────────────┘
                              │
                    ┌─────────▼─────────┐
                    │    TOKENIZER      │  Stage 1 — Lexical Analysis
                    │                   │  Reads char-by-char
                    │  "let" "x" "be"   │  Produces labelled tokens
                    │  "10" INDENT ...  │  Handles INDENT / DEDENT
                    └─────────┬─────────┘
                              │  List<Token>
                    ┌─────────▼─────────┐
                    │      PARSER       │  Stage 2 — Syntax Analysis
                    │                   │  Recursive-descent parsing
                    │  AssignInstruct.  │  Builds AST nodes
                    │  PrintInstruct.   │  Enforces operator precedence
                    └─────────┬─────────┘
                              │  List<Instruction>
                    ┌─────────▼─────────┐
                    │    EVALUATOR      │  Stage 3 — Execution
                    │                   │  Walks the instruction list
                    │  Environment {}   │  Evaluates expressions bottom-up
                    │  → Output         │  Stores vars in shared memory map
                    └───────────────────┘
```
 
| Stage | Class | Responsibility |
|---|---|---|
| **Tokenizer** | `Tokenizer.java` | Converts raw source text into a flat token list |
| **Parser** | `Parser.java` | Transforms token list into an executable instruction tree |
| **Evaluator** | `Interpreter.java` | Walks the tree, resolves variables, produces output |
 
---
 
## ◈ Project Structure
 
```
speek-project/
│
├── tests/                          ← Sample .speek programs
│
└── src/
    │
    ├── tokenizer/                  ← Stage 1: Lexical Analysis
    │   ├── Token.java              · Immutable token value object
    │   ├── TokenType.java          · Enum of all recognised token types
    │   └── Tokenizer.java          · Source text → List<Token>
    │
    ├── parser/                     ← Stage 2: Syntax Analysis
    │   ├── Parser.java             · Token list → instruction list
    │   ├── Expression.java         · Expression node interface
    │   └── nodes/                  · AST node implementations
    │       ├── NumberNode.java
    │       ├── StringNode.java
    │       ├── VariableNode.java
    │       └── BinaryOpNode.java
    │
    ├── evaluator/                  ← Stage 3: Execution
    │   ├── Environment.java        · Variable store — Map<String, Object>
    │   ├── Instruction.java        · Instruction interface
    │   └── instructions/           · Concrete instruction types
    │       ├── AssignInstruction.java
    │       ├── PrintInstruction.java
    │       ├── IfInstruction.java
    │       └── RepeatInstruction.java
    │
    └── interpreter/
        └── Interpreter.java        · Wires all 3 stages; CLI entry point
```
 
---
 
## ◈ Contributing
 
This project uses a **branch-per-section** workflow to keep work isolated and reviewable.
 
### Branch Map
 
| Branch | Purpose |
|---|---|
| `main` | Stable, merged, production-ready code only |
| `noobdev` | Integration testing — merge sections here first |
| `section/tokenizer` | Tokenizer stage work |
| `section/parser` | Parser stage work |
| `section/evaluator` | Evaluator stage work |
 
### Workflow
 
```bash
# 1 — Work on your section
git checkout section/tokenizer
 
# 2 — Make your changes and commit
git add .
git commit -m "feat: handle INDENT tokens for nested blocks"
 
# 3 — Push your branch
git push origin section/tokenizer
 
# 4 — Open a Pull Request → noobdev
#     Get it reviewed before anything touches main
# Merge To Main
```
 
---
 
## ◈ Team
 
Built with ☕ and stubbornness by the SPEEK team at **Sitare University**  
Course: *Advanced Object-Oriented Programming in Java*
 
---
*"The best code reads like a sentence."*
