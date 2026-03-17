# Technical Requirements

## 1. Overview
### 1.1 Purpose
High-level overview of the project purpose is outlined in the project [README.md](https://github.com/RedNate22/CAB302-Oogly-Boogly/blob/main/README.md).

### 1.2 Scope
**In Scope**
- Virtual pet interaction system
- Math problem generation and validation
- Core actions: feeding, petting, playing
- User feedback and progression

AI Implementation:
- AI driven pet behavior
- AI Clippy-style helper
    - [Socratic Method](https://en.wikipedia.org/wiki/Socratic_method) teaching (guides towards answers, but never explicitly gives it)

**Out of Scope**
- Advanced mathematics beyond defined difficulty levels
- Additional subjects (e.g. Science, History, English/Languages etc.)
- Multiplayer or social features
- Additional external integrations

---

## 2. System Description
### 2.1 High Level Architecture
**Overview**  
The system uses a layered architecture. Core components include:

1. **Presentation Layer (UI)**
    - **FXML**: Defines layout.
    - **CSS**: Defines styling.
    - **Controller (.java)**: Handles events, triggers pet actions, opens AI Chatbot window.

3. **Business Logic Layer**
    - Implements game rules, math challenges, and interaction logic.
    - Interfaces with both AI modules.

4. **Pet State Machine (AI)**
    - Tracks pet state (health, happiness, fatigue, etc.).
    - Updates behavior based on user interactions.
    - Deterministic logic; part of core gameplay.

5. **AI Chatbot Module**
    - Optional, user-invoked guidance using the Socratic method.
    - Queries Business Logic Layer for current math problem or progress.
    - Generates hints/questions rather than direct answers.

6. **Data Access Layer**
    - Persists user accounts and pet state.

7. **Database**
    - Stores user progress, pet attributes, and other persistent data.

**Component Interaction**
    - UI triggers actions → Business Logic → Pet State Machine updates pet state.
    - Business Logic provides context to AI Chatbot when user requests help.
    - AI Chatbot returns Socratic guidance → UI displays it.
    - Data Access Layer persists pet state and user progress.

**Diagram**
- Include a component diagram showing:
	- Boxes for: FXML & Controllers, Business Logic, Data Access, Database, AI Chatbot.
    - Arrows showing:
        - UI -> Business Logic -> Data Access -> Database
        - UI <-> AI Chatbot
        - AI Chatbot <-> Business Logic

### 2.2 Technology Stack
- Language: Java21 (Amazon Corretto 21)
- Frameworks: JavaFX
- Build Tool: Maven
- Database: SQLite
- Other Dependencies:
	-

### 2.3 Deployment Environment
- OS: Windows, MacOS
- CI/CD:

---

## 3. Functional Requirements
### 3.1

---

## 4. Non-functional Requirements
### 4.1

### 4.2

---

## 5. Maintainability
### 5.1 Code Standards
- **Separation of Concerns**
  - **FXML**: Define UI structure only. Avoid in-line styling unless absolutely necessary.
  - **CSS**: Define styling with classes/IDs to maintain consistency.
  - **Controller (.java)**: Handle UI behavior, event handling, and interaction with the model/data.
  - **Other .java files**: Contain main application logic, APIs, and database interactions.
  - **Assets**: Images, icons, and other resources organized in dedicated folders with sub-folders.

- **Security, Validation, and Error Handling**
  - Sanitize user input to prevent invalid data or security issues.
  - Implement proper exception handling with `try/catch`. Use `throws` only when passing responsibility is intentional.
  - Provide fallback behavior (e.g., return to main menu) on failures.

- **Naming Conventions**
  - **Classes**: PascalCase (e.g., `VirtualPetController`)  
  - **Methods / Properties**: PascalCase (e.g., `FeedPet()`, `PetName`)
  - **Interfaces**: PascalCase with `I` prefix if applicable (e.g., `IQuestion`)
  - **Private Fields (Attributes)**: camelCase (e.g., `petHealth`)  
  - **Constants / Readonly Fields**: UPPERCASE, or UPPERCASE + underscores (e.g., `HUNGER`, `MAX_HUNGER`)  
  - **Events**: PascalCase with `EventHandler` suffix if applicable (e.g., `PetFedEventHandler`)  
  - **Variables / Parameters**: lowerCamelCase (e.g., `petAge`, `mathProblem`) 

- **Branching and Version Control**
  - Use separate branches for each feature or issue (e.g., `user-login`, `issue-6-broken-landing-page`).
  - Submit pull requests for merges.
  - Follow [conventional commit standards](https://www.conventionalcommits.org/en/v1.0.0/) with `type(optional scope): description` (e.g., `git commit -m "feat(auth): add login button to landing page"`)
      - (Optional) include detailed bodies (e.g, `git commit -m -m "feat(auth): add login button to landing page" "Add small login button to landing page header...etc."`)

- **Formatting and Readability**
  - Maintain consistent indentation and spacing.
  - Organize code logically for readability and maintainability.
      - Separate logical blocks with empty lines

### 5.2 Documentation requirements
**Code Documentation**
- Use JavaDoc comments for all public classes and methods.
    - Include `@param`, `@return`, and `@throws` where applicable.
- Use inline comments to explain:
    - Complex logic
    - Non-obvious decisions or edge cases
- Avoid redundant comments that restate obvious code behavior.
- Keep all comments accurate and update them alongside code changes.
- Maintain consistent formatting and style for all documentation.

**Project Documentation**
- Maintain a **Technical Requirements Document** with epics, user stories, architecture diagrams, and non-functional requirements.
- Maintain a **README** with:
    - Project overview
    - Setup instructions
    - Core functionality summary
- Maintain a **Changelog** or version history for major updates and feature additions.

---
