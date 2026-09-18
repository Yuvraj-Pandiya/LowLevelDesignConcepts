# SOLID Design Principles

SOLID is an acronym for five foundational object-oriented design principles introduced by **Robert C. Martin (Uncle Bob)** in the year 2000. These principles are designed to address common software problems:

1. **Maintainability** – Code is easier to refactor, update, and manage over time.
2. **Readability & Understandability** – Structure is intuitive and clean.
3. **Bug Reduction** – Minimizes unintended side-effects and regression bugs when making changes.

---

## Overview of the 5 Principles

| Letter | Principle | Core Idea |
|---|---|---|
| **S** | **Single Responsibility Principle (SRP)** | A class should have only one reason to change (one primary responsibility). |
| **O** | **Open / Closed Principle (OCP)** | Software entities should be open for extension, but closed for modification. |
| **L** | **Liskov Substitution Principle (LSP)** | Subtypes must be substitutable for their base types without breaking client expectations. |
| **I** | **Interface Segregation Principle (ISP)** | Clients should not be forced to depend upon interfaces they do not use (many specific interfaces > one general fat interface). |
| **D** | **Dependency Inversion Principle (DIP)** | High-level modules should not depend on low-level modules; both should depend on abstractions. |

---

## 1. Single Responsibility Principle (SRP)

> **Definition:** A class should have one, and only one, reason to change.

### Key Concept
A class should focus on doing one job well. When a class handles multiple responsibilities (e.g., business logic, database persistence, and presentation/printing), changes to one responsibility risk breaking or requiring re-testing of the others.

### Separation of Responsibilities
- **Business Logic:** Managing cart items and calculating total amounts (`SRPShoppingCart`).
- **Data Persistence:** Saving cart information into a database (`CartDBStorage`).
- **Presentation / Output:** Generating and formatting invoices (`CartInvoicePrinter`).

---

## 2. Open / Closed Principle (OCP)

> **Definition:** Software entities (classes, modules, functions) should be open for extension, but closed for modification.

### Key Concept
You should be able to introduce new functionality without altering existing, tested source code. This is primarily achieved through:
- **Abstraction & Interfaces**
- **Polymorphism**
- **Dependency Injection**

### Example
Instead of having a storage class full of `if/else` or `switch` statements for every database type, define a `DataPersistable` interface. New storage engines (`SqlStorage`, `MongoDbStorage`, `FileStorage`) can be added simply by implementing the interface without modifying the existing consumer classes.

---

## 3. Liskov Substitution Principle (LSP)

> **Definition:** If $S$ is a subtype of $T$, then objects of type $T$ may be replaced with objects of type $S$ without altering any of the desirable properties of the program (correctness, task performed, etc.).

Subclasses must extend the behavior of the parent class, not narrow it down or violate its expectations. A subclass must fulfill the contract promised by the base class.

### The 3 Formal Rules of LSP

#### 1. Signature Rule (Contravariant Input / Covariant Output)
Dictates how method arguments and return types can change when overriding:
- **Covariant Return Types:** A subclass method can return a more specific subtype than the parent method, but never a more general type.
- **Contravariant Parameter Types:** Subclass method parameters must accept at least as wide a range of types as the parent method.
- **Exception Rule:** An overriding method cannot throw new or broader checked exceptions than those declared by the parent class method.

#### 2. Method Rule (Preconditions & Postconditions)
Think of inheritance as a **Legal Contract**:
- **Preconditions (Client's Duty):** What the caller must satisfy before invoking a method.
  - *Rule:* A subclass can **weaken** preconditions (be more lenient/generous to the caller), but can **never strengthen** them.
  - *Example:* If the parent requires a credit score $> 700$, the child may accept $> 600$ (weaker), but cannot demand $> 750$ (stricter).
- **Postconditions (Class's Duty):** What the method guarantees upon completion.
  - *Rule:* A subclass can **strengthen** postconditions (deliver stricter or better guarantees), but can **never weaken** them (fail its promises).
  - *Example:* If the parent promises delivery within 5 days, the child delivering within 2 days strengthens the promise.

#### 3. Property Rule (Invariants & History Constraint)
- **Class Invariants:** Conditions and logical rules established by the base class must remain true and consistent across all subclasses.
- **History Constraint:** Subclasses cannot introduce state modifications or method behaviors that violate the evolutionary assumptions/state transitions established by the base class.

### Bank Account Example
- A `FixedDepositAccount` cannot allow arbitrary withdrawals without violating the contract.
- **Wrong Approach:** Having `BrokenAccount` with `withdraw()`, and `BrokenFixedDepositAccount` throwing `UnsupportedOperationException`.
- **LSP Solution:** Create a base `DepositAccount` (for deposits/balances) and a specialized `WithdrawableAccount extends DepositAccount` (for `SavingsAccount` and `CurrentAccount`).

---

## 4. Interface Segregation Principle (ISP)

> **Definition:** Clients should not be forced to depend upon interfaces they do not use.

### Key Concept
Prefer many small, client-specific interfaces over one large, "fat" general-purpose interface.

### LSP vs. ISP: Key Difference
- **LSP Violation:** The system fails or crashes at runtime because a subclass breaks a behavioral contract (e.g., throwing unexpected exceptions when calling an inherited method).
  - *Fix:* Correct the hierarchy, contracts, or subtyping behavior.
- **ISP Violation:** The system is rigid and tightly coupled because interfaces bundle unrelated responsibilities together, forcing implementations to write dummy/empty methods.
  - *Fix:* Break down fat interfaces into smaller, focused interfaces (e.g., separating `Shape2D` for area and `Shape3D` for area + volume).

---

## 5. Dependency Inversion Principle (DIP)

> **Definition:**
> 1. High-level modules should not depend on low-level modules. Both should depend on abstractions.
> 2. Abstractions should not depend on details. Details (concrete implementations) should depend on abstractions.

### Key Concept
DIP decouples high-level policy/business logic from low-level implementation details (such as specific database drivers, network protocols, or file systems).

> **Relationship between OCP and DIP:** If Open/Closed Principle (OCP) is the target architectural goal, Dependency Inversion (DIP) / Dependency Injection (DI) is the primary mechanism to achieve it.

---

## Common Conceptual Discussion

### Question:
> *In OOP, real-world objects have multiple attributes and complex behaviors. Why does SRP state that a class should only have one responsibility?*

### Answer:
Software modeling is not an attempt to replicate every real-world characteristic of an entity in a single class. In software design, an object is modeled strictly in the context of the domain and use case. 

Having a single class model everything (data, calculation, database persistence, UI rendering, network communication) creates an anti-pattern known as a **God Object**. SRP ensures that each distinct aspect/concern is separated into its own module, while still representing the conceptual domain entity cohesively.
