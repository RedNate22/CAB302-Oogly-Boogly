# Sprint 3 - Preliminary Prototype Video Walkthrough

---

## 1. Brief Introduction

[show powerpoint - intro slide]

MathCat is a Java virtual pet application built around the unit theme, "Living, Learning, Working with AI." Users care for a pixelated cat by solving math problems to earn items and XP. The cat has three stats (Happiness, Fullness, and Energy) that decay in real time and must be maintained through item use and regular problem solving. An optional AI chatbot assists using the "I do, we do, you do" method, guiding users toward answers without giving them away.

The project is being developed by team OoglyBoogly: composed by myself, Nathan, Alexander, Leonora, and Zayan. Late through Sprint 2 we lost a group member as he dropped out of the unit. He had been assigned to the math problem system, and as a result, his tasks were re-assigned to myself and we ran a scope review and re-planned the remaining sprints, which we'll cover in more detail in the planning section.

---

## 2. User Stories

I will now give an overview of our user stories.

[show User Stories board: `Oogly Boogly - User Stories` GitHub Project]

We organised our work into five core epics: User Authentication, User Interface, User Experience, Math Learning System, and Data Persistence. After our scope review, we added three further columns going forward: Enhancements, Refactoring, and Out of Scope.

[scroll across the board columns, left to right]

[show powerpoint - Chonk chart]

For story sizing, we took a light-hearted approach and used a custom estimation scale based on the CHONK chart, a meme that grades cat body fat from "A Fine Boi" (smallest) up to "OH LAWD HE COMIN" (largest). These labels map to our story size complexity estimates.

Stories are prioritised P0 to P2: P0 is must-have, P1 is significant but non-critical, and P2 is polish.

[show User Story board]

The acceptance criteria for each story are defined on the issue. For example:

[open one completed user story, e.g., #4 "1. As a user, I want to create an account so that I can save my pet's progress"]

The acceptance criteria is listed as a checklist, so as we progressively develop a story, each criteria can be marked off as completed, ensuring our goals stay clear and we never lose track.

On the side we can see further details, including labels to mark this issue as a user story and a matching label for the epic.

Labels also track issue type, which becomes useful in later sprints for distinguishing Bug Fixes, Enhancements, and Refactors. Each story also carries a priority (P0 to P2) and a size estimate mapped to the chonk scale.

[Scroll down to show priority and estimate fields]

Stories are linked to their parent epic via GitHub's relationship feature, and directly to their associated pull request, giving a traceable path from backlog item to code review. As stories close, the epic's progress bar updates automatically.

[open Epic: User Authentication]

This tight integration lets us track progress dynamically and maintain a clear view of each epic at any point in the project.

---

## 3. Planning Artefacts

Moving onto our planning artefacts...

[switch to 'Oogly Boogly - Sprint Board' GitHub Project]

Each user story is listed in either of 3 self-explanatory columns: Todo, In Progress, and Done. As development progresses, the stories are moved to the next column, and once a PR has been closed and merged for an associated story, it is automatically moved to the complete column by Github.

[switch to `Oogly Boogly - Sprint Board` GitHub Project, Roadmap view]

The roadmap view gives us a clear picture of what needs to be done within each sprint. Each sprint is marked as a distinct iteration with a defined start and end date, and the stories assigned to it are laid out visually across that timeframe. We can also once again see each epic's progress at a glance, so it is immediately obvious how much work remains in any given area.

When we lost our team member in Sprint 2, we ran an informal sprint retrospective and redesigned the scope. The changes are visible in the board:

[switch back to User Stories]

We added three new columns, Enhancements, Refactoring, and Out of Scope, to categorise work that was deprioritised or deferred. The item shop and bonus challenge system were moved to Out of Scope. The math question system was split from one large story into three smaller ones covering basic question generation, randomisation, and difficulty weighting, so we could deliver the core functionality first without being blocked by the full feature.

[show the Math Learning System epic (#25) with its sub-issues]

Originally, the cat's energy was to be spent on bonus challenges which would provide much greater rewards, and the amount of energy that could be refilled by food items was capped at a daily limit. As these challenges have now been deemed out of scope, we were left wondering what purpose could energy serve. This led us to come up with a rework:

Energy is now spent on all challenges, and when it depletes fully the user can still continue solving problems, but rewards are no longer received. This puts a soft cap on the amount of items and XP the user can receive per day, while still encouraging repetitive but spaced learning.

---

## 4. Low-Fidelity UI Prototypes

I will now walk through the design artefacts we produced during the planning stage.

[show `Diagrams/Desktop Pet Architecture Diagram.png`]

First, our **Architecture Diagram**, created by former member, Colin, shows the four-layer architecture: Presentation, Controller, Business Logic, and Data Access.

[show `Diagrams/MathCat_StateMachine.png`]

The **State Machine Diagram**, created by myself, shows how the cat's stats transition based on user actions, time decay, and hunger thresholds.

[show `Diagrams/CAB302_Project-LowWireframe-withInteractions.png`]

Both wireframes were created by Leonora. Here is the **Low-Fidelity Wireframe**...

[show `Diagrams/CAB302_Project-MediumWireframe-withInteractions.png`]

And the **Medium-Fidelity Wireframe**. These show all application screens and the navigation flow, from the initial screen through login, account creation, cat creation, home, and play screen.

These artefacts shaped the code structure; the layered architecture diagram maps one-to-one with our package layout, and the wireframe was the reference used when building the FXML files.

---

## 5. Functional Prototype Demo

Now for the demo.

[launch app]

Starting at the initial screen, we can create an account or login.

[click "Create Account"]

On the registration screen, the form validates all fields before submission. I will quickly demonstrate that with a weak password.

[attempt to register with a bad password, show the error message]

And now with a valid one.

[register successfully with valid credentials]

After account creation the user goes straight to the cat creation screen where three cat appearances and three accessories can be chosen. This is obviously still in development.

[create a cat, name it and submit]

We're now on the home screen. The heading shows the cat's name loaded from the database. The three stat bars right now are just static, but they will display the live stats, loaded from the database on screen load and updated periodically on a separate thread.

During our sprint retrospective, the functionality of the customise button has been redesigned from a separate screen into a simple modal. The purpose however remains the same, for accessing customisation and items.

This is also still in development, but we _can_ go into the play screen.

[click "Play"]

This view presents a math question generated from the in-memory storage. Answering correctly will update the cat's stats and eventually reward items. The skip button will also let users skip a question but receive no rewards.

[answer a question]

Our chatbot, Chatty, was just recently implemented into the UI skeleton.

[write a message to the Chatty]

We can see Chatty is happy to walk us through how to solve a math equation similar to our current one.

And I can quickly demonstrate persistance through our database by logging out completely.

[log out, close, open, log back in]

On login, the application will calculate how many minutes elapsed since the last save and applies the happiness stat decay proportionally, as seen in our state machine diagram. You will eventually see the Happiness and Fullness bars have dropped, and if you fed your cat, the Energy will be regained.

---

## 6. PM Tool Usage

[show 'Projects' page with the two projects listed]

In addition to the already discussed project management tools, both projects give us a Priority Board, Team Items view, and a My Items view for tracking workload.

[show `Priority Board view`, `Team Items view`, and `My items view`]

Completed items are closed and marked done on the board automatically through our GitHub workflow automation.

---

## 7. Version Control Workflow

[open GitHub repository, go to commit history on the `dev` branch]

I will now discuss our version control workflow.

[scroll commits]

We commit regularly throughout the project. You can see contributions from all active team members across the history.

[powerpoint - commit]

We follow the Conventional Commits standard, and all code conventions, dependencies, and full project details are documented in `Technical-Requirements.md`, which serves as the single source of truth for the project. Every commit follows the format: type, with optional scope, and description.

[show the branch list - `Git Graph`]

For branching, each feature or issue gets its own branch named after the feature or the issue number, for example one of my branches, `Math Problems 56`, where 56 corresponds to the auto-assigned issue number.

[show pull requests tab]

All merges go through pull requests. You can see the closed PRs here; each one links back to the issue it resolves.

[show closed pull requests, point out the linked issues]

[show the Actions tab, point to a passing workflow run]

We also have GitHub Actions set up for continous intregration. On every push, the pipeline builds the project and runs the test suite automatically.

We also adopted a `dev` branch as our default to keep the `main` branch stable.

---

## 8. Source Code Walkthrough

I will now briefly walk you through the source code.

### 8a. JavaFX UI Structure

Starting with how the UI is structured and how it connects to the rest of the application.

[open project in IDE, show folder structure]

Our screens are defined in FXML files under `resources/`, each paired with a controller in the `controllers/` package. This is MVC: the FXML is the View, the controller is the Controller, and the models and services form the Model layer.

[open `AuthController.java`, highlight `onLoginConfirm`]

`AuthController` handles both login and registration. It reads the `FXML` text fields, delegates validation to the `UserService` class, and delegates database queries to `UserDAO`. The controller itself contains no business logic and no SQL, which is the key principle here: each layer only does its own job.

[open `homeController.java`, highlight `onPressPlay()`]

Navigation between screens is handled by swapping the FXML root on the existing stage, so no new windows are opened.

### 8b. Persistence Layer

Moving on to how the application stores and retrieves data.

[open `DatabaseManager.java`]

`DatabaseManager` manages the SQLite connection and exposes `initialiseDatabase()` to create tables on first run. It also has a `useInMemoryDatabase()` method used in testing, which we will cover shortly.

[open `CatDAO.java`, highlight the `if (cat.getCatId() == 0)` branch in `save()`]

`CatDAO` is a pure data access object: no business logic, only SQL. The `save()` method is an upsert; if the cat has no ID yet it INSERTs and captures the generated key, otherwise it UPDATEs. `UserDAO` follows the same pattern, with separate `findByEmail` and `findByUsername` methods since login accepts either.

### 8c. OO Design Elements

Finally, a few examples of OO principles.

[open `IQuestion.java` and `Question.java` side by side]

`IQuestion` is an interface defining the contract for any math question. `Question` is the concrete implementation; its fields are all `final`, making it immutable once constructed.

['go to definition' (quickly) open either enum def]

`QuestionType` and `Difficulty` are both enums, giving us type-safe representations instead of raw strings or integers.

[open `CatService.java`, highlight `applyOfflineDecay()`]

`CatService` is a static utility class with a private constructor, enforcing that it cannot be instantiated. All stat mutations go through `clampStat()`, keeping bounds logic in one place. `applyOfflineDecay()` compares `LocalDateTime.now()` to the cat's last saved timestamp and subtracts proportional decay, simulating time that passed while the app was closed.

To summarise the separation of concerns: `Cat` holds state, `CatService` mutates it, `CatDAO` persists it. Controllers call services, services call DAOs.

---

## 9. Test Suite

And now an example of our test suite.

[open `src/test/` folder, open test panel with a few tests expanded]

Our tests focus on behaviour, not implementation. `CatServiceTest` covers the business logic layer (stat clamping, energy regeneration, and offline decay) and runs entirely in memory. `CatDAOTest` covers the persistence layer; in `@BeforeAll` we swap to an in-memory SQLite instance so tests are isolated and fast without mocking. Tests are organised into nested classes by method, so the structure mirrors the class being tested.

[run the test suite]

You will notice a failing test in `AIServiceTest`. Zayan recently opened a pull request to fix the root cause.

[open Zayan's pull request, point to the CI check]

This is a good example of the TDD cycle in practice: the failing test was committed first, the fix came in a separate commit, and the CI action running on the pull request confirms it now passes.

### 10. Final Remarks

Thank you for watching.
