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

AI Implementation (TBC):
- AI driven pet behavior?
- AI Clippy-style helper?
  - [Socratic Method](https://en.wikipedia.org/wiki/Socratic_method) teaching (guides towards answer, but never explicitly gives it)

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

2. **Business Logic Layer**
   - Implements game rules, math challenges, and interaction logic.
   - Interfaces with both AI modules.

3. **Pet State Machine (AI)**
   - Tracks pet state (health, happiness, fatigue, etc.).
   - Updates behavior based on user interactions.
   - Deterministic logic; part of core gameplay.

4. **AI Chatbot Module**
   - Optional, user-invoked guidance using the Socratic method.
   - Queries Business Logic Layer for current math problem or progress.
   - Generates hints/questions rather than direct answers.

5. **Data Access Layer**
   - Persists user accounts, pet state, and optional chatbot session history.

6. **Database**
   - Stores user progress, pet attributes, and other persistent data.

**Component Interaction**
- UI triggers actions → Business Logic → Pet State Machine updates pet state.
- Business Logic provides context to AI Chatbot when user requests help.
- AI Chatbot returns Socratic guidance → UI displays it.
- Data Access Layer persists pet state and user progress.

**Diagram (TBC)**
- Include a component diagram showing:
  - Boxes for: FXML & Controllers, Business Logic, Data Access, Database, AI Chatbot.
  - Arrows showing:
    - UI → Business Logic → Data Access → Database
    - UI ↔ AI Chatbot
    - AI Chatbot ↔ Business Logic

### 2.2 Technology Stack
- Language: Java21 (Amazon Corretto 21)
- Frameworks: JavaFX
- Build Tool: Maven
- Database: SQLite
- Other Dependencies (TBC):
  - Unit Testing

### 2.3 Deployment Environment
- OS: Windows, MacOS
- CI/CD: TBC

## 3. Maintainability
### 3.1 Code Standards
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
  - **Private Fields (Attributes)**: camelCase (e.g., `petHealth`)  
  - **Constants / Readonly Fields**: UPPERCASE, or UPPERCASE + underscores (e.g., `HUNGER`, `MAX_HUNGER`)  
  - **Events**: PascalCase with `EventHandler` suffix if applicable (e.g., `PetFedEventHandler`)  
  - **Variables / Parameters**: camelCase (e.g., `petAge`, `mathProblem`) 

- **Branching and Version Control**
  - Use separate branches for each feature or issue (e.g., `user-login`, `issue-6-broken-landing-page`).
  - Submit pull requests for merges.

- **Formatting and Readability**
  - Maintain consistent indentation and spacing.
  - Organize code logically for readability and maintainability.

#### Documentation requirements
**Code Documentation**
- Use XML comments for all public classes, methods, and properties.
  - Include `<summary>`, `<param>`, `<returns>` where applicable.
- Comment complex logic or algorithms inline for clarity.
- Keep comments up-to-date with code changes.

**Project Documentation**
- Maintain a **Technical Requirements Document** with epics, user stories, architecture diagrams, and non-functional requirements.
- Maintain a **README** with:
  - Project overview
  - Setup instructions
  - Core functionality summary
- Maintain a **Changelog** or version history for major updates and feature additions.

**Design & Architecture Documentation**
- Provide diagrams for:
  - Application architecture
  - Data model (classes, entities, relationships)
  - UI flow (XAML pages / navigation)
- Document APIs clearly with request/response formats, error codes, and example calls.

**Testing Documentation**
- Include unit test coverage reports.
- Maintain test plans for integration and end-to-end tests.
- Document known issues and edge cases found during testing.

**Maintenance**
- Keep all documentation in version control alongside the code.
- Assign responsibility for updating documentation when features are added or changed.

---

## 5. API Specifications

### 5.1 Endpoint: <Name>
- Method:
- URL:
- Description:

**Request**
```json
{}
