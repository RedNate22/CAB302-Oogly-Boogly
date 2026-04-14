# Technical Requirements

## 1. Overview
High-level overview of the project purpose is outlined in the project [README.md](https://github.com/RedNate22/CAB302-Oogly-Boogly/blob/main/README.md).

### 1.1 Gameplay Loop

#### 1.1.1 Pet Stats
- **Happiness** – Measures the pet’s emotional state. Increases through interactions via items (e.g., brush, catnip, toys etc.), continously solving problems, and gradually decreases over **real-world time** to encourage regular engagement. Additionally, Happiness decreases when Fullness is low.
- **Fullness** – Tracks how well the pet has been fed. Feeding increases Fullness, which also restores Energy proportionally. Depletes over **real-world time** as it does with Happiness, and cannot exceed its maximum value, preventing repeated feeding for infinite Energy gains.
- **Energy** – Represents how active the pet is. Depletes when the player spends it on bonus challenges. Restores automatically proportionally to current Fullness state or via specific items.

#### 1.1.2 Cat Level System
- Users earn **XP** by completing math problems.
- Level progression unlocks:
  - Higher problem difficulty (e.g., Level 2 problems require cat to be at or above Level 2)  
  - Access to new shop items and variations
  - Scaling rewards and bonus multipliers

#### 1.1.3 Solving Math Problems
- Completing problems rewards **items + currency**.
- Rewards scale with difficulty and may include bonus multipliers:
  - **AI Usage Bonus** – Users can use the AI up to 3 times before the bonus is fully lost; each use reduces the bonus multiplier (e.g., using it 3 times results in no “No AI Used” bonus).
  - **Difficulty Bonus** – Higher-level problems provide higher rewards.
  - **Pet Stats Bonus** – Happiness and Fullness influence XP and item gains.
- Items earned or bought can **restore Energy, increase Happiness, or satisfy Fullness**.

#### 1.1.4 Bonus Challenges
- Users can spend **Energy** to attempt higher-difficulty problems (**(x) levels above current player level**).  
- Rewards are scaled to difficulty and include extra incentive.

#### 1.1.5 Shop
- Users use currency earned from math problems to buy items or sell (TBD) for more currency.
- Items vary in effect (stronger items cost more).
- Some cosmetic items can be bought; they have no effect on the cat's needs or gameplay but encourage progression.
- Some items are **locked behind player level**, further encouraging progression.

### 1.2 Scope
**In Scope**
- Virtual pet interaction system
	- Core actions: Feeding, playing, petting (via item use)
	- Dynamic pet behaviour system driven by internal need states (e.g. energy, hunger, happiness), affecting animations and interactions
- Math problem generation and validation
- User feedback and progression
- AI Clippy-style helper
    - [Socratic Method](https://en.wikipedia.org/wiki/Socratic_method) teaching (guides towards answers, but never explicitly gives it)

**Out of Scope**
- Advanced mathematics beyond defined difficulty levels
- Additional subjects (e.g. Science, History, English/Languages etc.)
- Multiplayer or social features
- Additional external integrations (e.g., browser support)
- Android/IOS Support

---

## 2. System Description
### 2.1 High Level Architecture
**Overview**  
The system uses a layered architecture. Core components include:

1. **Presentation Layer**
    - **UI**: Renders the interface and handles user input.
    - **FXML & CSS**: Define layout and styling.
    - UI communicates bidirectionally with Controllers.

3. **Controller Layer**
    - **Controller (.java)**: Handles user events, triggers pet actions, and manages the AI Chatbot window.
    - Controllers communicate bidirectionally with both the UI and Business Logic.
    - Controllers also communicate bidirectionally with the AI Chatbot Module.

4. **Business Logic Layer**
    - Implements game rules, math challenges, reward calculations, and interaction logic.
    - Interfaces with the Pet State Machine, AI Chatbot Module, and Database.
    - Can also query external APIs for math problems or datasets if integrated.

5. **Pet State Machine**
    - Tracks the cat’s internal states (Happiness, Energy, Fullness, Level, etc.).
    - Updates behaviour deterministically based on user interactions and time-based decay.

6. **AI Chatbot Module**
    - Optional, user-invoked guidance using the [Socratic method](https://en.wikipedia.org/wiki/Socratic_method).  
    - Generates hints/questions rather than direct answers.
    - Queries Business Logic Layer for current problem context or progress.
    - Interacts bidirectionally with Controllers for display and user input.

7. **Data Access Layer**
	- Handles persistent storage of user accounts, pet state, items, and other application data.

8. **Database**
    - Stores all persistent data, including user progress, pet attributes, and inventory.

### 2.2 Component Interaction
	- User actions -> UI <-> Controllers -> Business Logic -> Pet State Machine updates state.
	- Business Logic provides context -> AI Chatbot <-> Controllers -> UI updates.
	- Business Logic -> Database Layer -> Database for all persistence.
	- Optional API requests (e.g., math problem dataset) are queried by Business Logic or AI Chatbot.

### 2.3 Component Diagram

![Architecture Diagram](Diagrams/Desktop%20Pet%20Architecture%20Diagram.png)

Created by [Colin](https://github.com/Ka-319)

### 2.4 State Machine Diagram (Pet Stats)

![State Machine Diagram](Diagrams/MathCat_StateMachine.png)

Created by [Nathan](https://github.com/RedNate22)

### 2.5 Wireframe of Project (Screens + Flow)

![Medium Wireframe Diagram](Diagrams/CAB302_Project-MediumWireframe-withInteractions.png)

Created by [Leonora](https://github.com/smolbebby)

### 2.6 Technology Stack
- Language: Java21 (Amazon Corretto 21)
- Frameworks: JavaFX
- Build Tool: Maven
- Database: SQLite
- Unit Testing: [JUnit 5](https://docs.junit.org/5.10.5/user-guide/)
- Other Dependencies:
	- JavaFX Libraries:
 		- [FormsFX](https://github.com/dlsc-software-consulting-gmbh/FormsFX/)
   		- [FXGL](https://github.com/AlmasB/FXGL)
     	- [Ikonli](https://kordamp.org/ikonli/)

### 2.7 Deployment Environment
- OS: Windows, MacOS
- CI/CD: Github Actions

---

## 3. Functional Requirements
### 3.1 User Authentication
- Create Account: User needs to be able to register their details (username, email, password) to create an account.
- Login: User needs to be able to use registered username or email with password (key pair) to login to the application.
- Logout: User needs to be able to log out so progress is protected on shared devices.
- Account Storage: User needs to have in-game progress saved to account for continuous access.

### 3.2 User Interface
- Pet Display: User needs to be able to clearly view virtual pet on the main screen.
- Pet Attribute: User needs to be able to clearly view virtual pet attributes.
- Layout: User needs to be able to access different areas of the application to interact with virtual pet.
- Navigation: User needs to be able to use buttons and other appropriate functions to access separate areas of the application.
- Navigation Continued: User needs to be able to use buttons and other appropriate functions to interact with the virtual pet.
- Shop Display  User needs to be able to view available items, their costs, effects.
- Input Validation Registration fields must validate format such as valid email, minimum password length.
- Incorrect Details: User should be told when login credentials are incorrect.

### 3.3 User Experience
- Level Progression: Clear indicator for current level and progress towards the next
- Level up Notification User should receive a notification when pets level up.

### 3.4 Math Learning System
- Progress Recording: User's math performance should be tracked to adapt difficulty or provide suggestions.
- Rewards: User should earn rewards or points for completing math exercises, which can affect the virtual pet’s happiness or growth.

### 3.5 Data Persistence
- Data Saving  Every interation with the virtual pet is recorded and saved to prevent data loss.
- Inventory Persistence The user's item inventory and currency balance must be saved and restored between sessions.

---

## 4. Non-functional Requirements
### 4.1 User Authentication
- Account Security: User details need to be stored securely to prevent unauthorised access.
- Duplicate Registration: The system must prevent registration with a username or email that is already associated with an existing account.
- Password Requirements: Passwords must meet a minimum complexity requirement 

### 4.2 User Interface
- Accessibility: Buttons should be clearly labelled for accessibility and ease-of-use.
- Layout: Layout should be organised and easily readable to prevent confusion and difficulty navigating.

### 4.3 User Experience
- Feedback: User should receive immediate feedback on pet interactions.
- Performance The application should run smoothly without noticeable lag.
- Accessibility Buttons should be clearly labelled for accessibility and ease of use.
- UI Consistency components such as fonts, colors, button styles should remain consistent across all screens.

### 4.4 Math Learning System
- Adaptability The learning system should adjust difficulty based on user performance.
- Answer Accuracy The answer validation system must correctly evaluate all expected problem types without error.
- Problem Variety The system should generate varied problem types at each difficulty level to prevent repetitive or predictable question patterns.
- Answer Validation User's submitted answer must be evaluated and marked correct or incorrect with immediate feedback.

### 4.5 Data Persistence
- Reliability All game state and user progress must be saved after every meaningful interaction with no data loss on normal exit.
- Integrity The database must maintain consistent state at all times; partial writes must not corrupt saved data.
- Consistency Inventory, currency, pet stats, and level progress must all remain in sync

---

## 5. Maintainability
### 5.1 Code Standards
- **Separation of Concerns**
  - **FXML**: Define UI structure only. Avoid in-line styling unless absolutely necessary.
  - **CSS**: Define styling with classes/IDs to maintain consistency.
  - **Controller (.java)**: Handle UI behaviour, event handling, and interaction with the model/data.
  - **Other .java files**: Contain main application logic, APIs, and database interactions.
  - **Assets**: Images, icons, and other resources organised in dedicated folders with sub-folders.

- **Security, Validation, and Error Handling**
  - Sanitise user input to prevent invalid data or security issues.
  - Implement proper exception handling with `try/catch`. Use `throws` only when passing responsibility is intentional.
  - Provide fallback behaviour (e.g., return to main menu) on failures.

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
  - Organise code logically for readability and maintainability.
      - Separate logical blocks with empty lines

### 5.2 Documentation requirements
**Code Documentation**
- Use JavaDoc comments for all public classes and methods.
    - Include `@param`, `@return`, and `@throws` where applicable.
- Use inline comments to explain:
    - Complex logic
    - Non-obvious decisions or edge cases
- Avoid redundant comments that restate obvious code behaviour.
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
