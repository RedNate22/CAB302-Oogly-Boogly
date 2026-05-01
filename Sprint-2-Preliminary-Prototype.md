# Sprint 2 - Preliminary Prototype Video Walkthrough

---

## 1. Brief Introduction

MathCat is a Java/JavaFX virtual pet application built around the unit theme, "Living, Learning, Working with AI." Users care for a pixelated cat by solving math problems to earn items and XP. The cat has three stats (Happiness, Fullness, and Energy) that decay in real time and must be maintained through item use and regular play. An optional AI chatbot assists using the "I do, we do, you do" method, guiding users toward answers without giving them away.

The project was developed by team OoglyBoogly: Nathan, Alexander, Leonora, Colin, and Mohamed. Partway through Sprint 2 we lost one team member who had been assigned to the math problem system. As a result, we ran a scope review and re-planned the remaining sprints, which we'll cover in more detail in the planning section.

---

## 2. User Stories

We organised our work into epics, each containing related user stories.

[show User Stories board: `Oogly Boogly - User Stories` GitHub Project]

We have five core epics: User Authentication, User Interface, User Experience, Math Learning System, and Data Persistence. After our scope review, we added three further columns: Enhancements, Refactoring, and Out of Scope.

[scroll across the board columns, left to right]

For story sizing, we took a light-hearted approach and used a custom estimation scale based on the CHONK chart, a meme that grades cat body fat from "A Fine Boi" (smallest) up to "OH LAWD HE COMIN" (largest). These labels map to our story complexity estimates.

[show CHONK chart image, our estimation scale]

The acceptance criteria for each story are defined on the issue. For example:

[open one completed user story, e.g., #4 "As a user, I want to create an account so that I can save my pet's progress"]

[show acceptance criteria on that issue]

Completion status per epic at this milestone:

- **User Authentication** (#23): 5/5, done
- **Data Persistence** (#27): 3/4, 75%
- **Math Learning System** (#25): 2/7, 28%
- **User Interface** (#24): 1/5, 20%
- **User Experience** (#26): 0/3, planned for Sprint 3
- **Enhancements, Refactoring**: planned for Sprint 3
- **Out of Scope** (#51): items removed from scope

---

## 3. Planning Artefacts

[switch to `Oogly Boogly - Sprint Board` GitHub Project, Roadmap view]

Our roadmap shows two active sprints. Sprint 2 ran from April 15 to April 28 and contained 10 items. Sprint 3 runs from April 29 to May 12 and contains the bulk of the remaining work.

[scroll right on roadmap to show Sprint 3 stories]

When we lost our team member mid-Sprint 2, we ran an informal sprint retrospective and redesigned the scope. The changes are visible in the board:

[switch to Sprint Board kanban view]

- We added three new columns (Enhancements, Refactoring, and Out of Scope) to categorise work that was deprioritised or deferred
- The item shop and bonus challenge system were moved to Out of Scope
- The math question system was split from one large story into three smaller ones: basic question generation, randomisation, and difficulty weighting, so we could deliver the core functionality first without being blocked by the full feature

[show the Math Learning System epic (#25) with its sub-issues]

- The energy system was reworked: energy no longer gates whether the user can attempt problems; it only determines whether they receive a reward. This removes a dependency on the shop while keeping the daily reward cap meaningful.

---

## 4. Low-Fidelity UI Prototypes

We produced three design artefacts before writing code, all stored in the `Diagrams/` folder.

[open `Diagrams/` folder in file tree]

**Architecture Diagram**, created by Colin. Shows the five-layer architecture: Presentation, Controller, Business Logic, Pet State Machine, and Data Access.

[show `Diagrams/Desktop Pet Architecture Diagram.png`]

**State Machine Diagram**, created by Nathan. Shows how the cat's stats transition based on user actions, time decay, and hunger thresholds.

[show `Diagrams/MathCat_StateMachine.png`]

**Medium-Fidelity Wireframe**, created by Leonora. Shows all application screens and the navigation flow, from the initial screen through login, account creation, cat creation, home, and play screens.

[show `Diagrams/CAB302_Project-MediumWireframe-withInteractions.png`]

These artefacts shaped the code structure; the layered architecture diagram maps one-to-one with our package layout, and the wireframe was the reference used when building the FXML files.

---

## 5. Functional Prototype Demo

[launch the MathCat application]

Starting at the initial screen. We'll walk through the core workflow.

[click "Create Account"]

On the registration screen, the form validates all fields before submission. We'll cover that in the code section, but for now: try submitting with a weak password.

[attempt to register with a bad password, show the error message]

[register successfully with valid credentials]

After account creation the user goes straight to the cat creation screen. Name your cat and confirm.

[create a cat, name it and submit]

We're now on the home screen. The heading shows the cat's name loaded from the database. The three stat bars (Happiness, Fullness, and Energy) are also loaded from the saved record.

[show home screen with cat name and stats]

[click "Play"]

The play screen presents a math question. Answering correctly will update the cat's stats and eventually reward items.

[answer a question]

Now we'll demonstrate persistence. Log out, wait a moment, and log back in.

[log out, wait briefly, log back in]

On login, the application calculates how many minutes elapsed since the last save and applies stat decay proportionally. You will eventually see the Happiness and Fullness bars have dropped: that time passed offline, and the cat's stats reflect it. The data survived the session.

[point to the reduced stat bars after login]

---

## 6. PM Tool Usage

We used two separate GitHub Projects to manage our work.

[show `Oogly Boogly - User Stories` GitHub Project]

The User Stories project is our backlog. It has four views: User Stories (grouped by epic), Priority Board, Team Items, and My Items. Each card links to a GitHub issue with acceptance criteria, story size estimate, and assignee.

[show Priority Board view, point out the swimlanes and priority ordering]

[switch to `Oogly Boogly - Sprint Board` GitHub Project]

The Sprint Board is our iteration-level tool. The Roadmap view shows which stories are assigned to which sprint and their duration.

[show Roadmap, pointing to Sprint 2 and Sprint 3 bars]

[switch to Board view, scroll through columns]

Tasks are assigned to team members on the issue. Completed items are closed and marked done on the board automatically through our GitHub workflow automation.

[click into one issue, show the assignee, sprint label, and linked PR or commits]

---

## 7. Version Control Workflow

[open GitHub repository, go to commit history on the `dev` branch]

We committed regularly throughout the project. You can see contributions from all active team members across the history.

[scroll through commits, point out different author names]

We follow the Conventional Commits standard, documented in `Technical-Requirements.md`. Every commit follows the format `type(scope): description`, for example `feat(auth): add login button` or `fix(db): handle null last saved on load`.

[highlight a few well-formed commit messages in the history]

For branching, each feature or issue gets its own branch named after the feature or the issue number, e.g. `user-login` or `issue-6-broken-landing-page`.

[show the branch list]

All merges go through pull requests. You can see the closed PRs here; each one links back to the issue it resolves.

[show closed pull requests, point out the linked issues]

We also have GitHub Actions set up for CI/CD. On every push, the pipeline builds the project and runs the test suite automatically.
(I don't think we have this yet tho)

[show the Actions tab, point to a passing workflow run]

---

## 8. Java Code Walkthrough

### 8a. JavaFX UI Structure

[open project in IDE, show `src/main/java/com/mathcat/mathcat/` folder structure]

Our screens are defined in FXML files under `src/main/resources/`. Each screen has a paired controller in the `controllers/` package. This maps to the MVC pattern: the FXML is the View, the controller is the Controller, and the models and services form the Model layer.

[show `resources/` folder, list the FXML files: initial-view, login-view, createpet-view, home-view, play-view]

[open `AuthController.java`]

`AuthController` handles both the login and registration screens. It reads from the FXML text fields annotated with `@FXML`, delegates validation to `UserService`, and delegates DB queries to `UserDAO`. The controller itself contains no business logic and no SQL.

[highlight the `onLoginConfirm` method, point to the delegation to `UserService` and `UserDAO`]

[open `homeController.java`]

The `initialize()` method is called automatically by JavaFX when the FXML loads. Here it pulls the current user's cat from the database via `CatDAO.load()` and populates the cat name label. Navigation between screens is handled by loading a new FXML root onto the existing stage; no new windows, just root-swapping.

[highlight `initialize()` and `onPressPlay()`]

### 8b. Persistence Layer

[open `DatabaseManager.java`]

`DatabaseManager` manages the SQLite connection. It exposes a `useInMemoryDatabase()` method (covered in the test section) and an `initialiseDatabase()` method that creates tables if they don't exist.

[open `CatDAO.java`]

`CatDAO` is a pure data access object: no business logic, only SQL. The `save()` method implements an upsert: if the cat's ID is 0 it hasn't been persisted yet, so we INSERT and capture the generated key; otherwise we UPDATE the existing row. Callers never need to know whether they're doing a create or an update; `save()` handles both.

[highlight the `if (cat.getCatId() == 0)` branch in `save()`]

[open `UserDAO.java`]

`UserDAO` follows the same pattern. `findByEmail` and `findByUsername` are separate methods because login accepts either, so the controller tries both.

[show `findByEmail` and `findByUsername` side by side]

### 8c. OO Design Elements

[open `IQuestion.java`]

`IQuestion` is an interface defining the contract for any math question: `getText()`, `getAnswer()`, `getType()`, and `getDifficulty()`. The system works against this interface, not a concrete class.

[open `Question.java`]

`Question` is the concrete implementation. The constructor takes a `QuestionType`, a `Difficulty`, and an array of operands; it calculates the answer and formats the question text at construction time. The fields are all `final`, so a `Question` is immutable once built.

[highlight the `final` fields and the constructor]

`QuestionType` and `Difficulty` are both enums, type-safe representations of the question's operation and difficulty level. `ItemEffectType` is the same pattern for items.

[open `CatService.java`]

`CatService` is a static utility class; the constructor is private so it can't be instantiated. It handles all stat mutations, enforcing the min/max bounds through `clampStat()` on every operation. The key method here is `applyOfflineDecay()`:

[highlight `applyOfflineDecay()`]

When the user logs in, we compare `LocalDateTime.now()` to the cat's `lastSaved` timestamp, calculate minutes elapsed, and subtract proportional decay. The cat's stats reflect real time that passed while the app was closed; this is the offline simulation the state machine diagram described.

To summarise the layers: `Cat` holds state, `CatService` mutates it, `CatDAO` persists it. Controllers call services, services call DAOs.

---

## 9. Test Suite

### 9a. Test Purpose

[open `src/test/` folder, show the test files: `CatDAOTest`, `CatServiceTest`, `UserDAOTest`, `UserServicesTest`, `AIServiceTest`]

Our tests focus on behaviour: what the system does, not how it's implemented internally. We avoid testing getters and setters in isolation.

[open `CatServiceTest.java`]

`CatServiceTest` covers the core business logic: stat increases and decreases clamp correctly at 0 and 100, energy regenerates proportionally to fullness, offline decay applies the correct amount based on elapsed minutes, and item use applies the effect and removes the item from inventory. These tests run entirely in memory, no database required.

[open `CatDAOTest.java`]

`CatDAOTest` tests the persistence layer. Notice the `@BeforeAll` setup:

[highlight `DatabaseManager.useInMemoryDatabase()` in `@BeforeAll`]

We swap the database to an in-memory SQLite instance before any test runs. This means tests are isolated, fast, and don't touch any file on disk. It's how we tested DAO behaviour without mocking; we use a real database engine with a real schema, just in memory.

The tests are organised into Nested classes by method (`Save`, `Load`, `Delete`, `ClearForTesting`), so the structure mirrors the class being tested.

[scroll through the nested classes briefly]

### 9b. Red-Green-Refactor Evidence

[open GitHub commit history, filter to commits touching `CatService` and `CatServiceTest`]

You can see the TDD cycle in the commit history. Tests for a feature were written first; the commit adds a failing test. The next commit adds the minimal implementation to make it pass. Any cleanup or generalisation after that is a separate refactor commit.

[point to a specific group of commits showing this pattern, e.g., the offline decay feature]

### 9c. Run Tests

[open the test runner in IntelliJ, run the full test suite]

All tests pass. You can see the breakdown by class: `CatServiceTest`, `CatDAOTest`, `UserDAOTest`, and `UserServicesTest` all green.

[let the results load, point out the pass count and zero failures]

The test suite currently covers the authentication layer, the persistence layer for both users and cats, and the core cat stat business logic. As we add the math question system in Sprint 3, tests for `QuestionService` and `QuestionBank` will be added the same way.
