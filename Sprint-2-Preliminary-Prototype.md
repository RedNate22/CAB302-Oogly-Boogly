# Sprint 2 - Preliminary Prototype Video Walkthrough

---

## 1. Brief Introduction

MathCat is a Java/JavaFX virtual pet application built around the unit theme, "Living, Learning, Working with AI." Users care for a pixelated cat by solving math problems to earn items and XP. The cat has three stats (Happiness, Fullness, and Energy) that decay in real time and must be maintained through item use and regular play. An optional AI chatbot assists using the "I do, we do, you do" method, guiding users toward answers without giving them away.

The project was developed by team OoglyBoogly: composed by myself, Nathan, Alexander, Leonora, Colin, and Zayan. Late through Sprint 2 we lost Colin as he dropped out of the unit. He had been assigned to the math problem system, and as a result, his tasks were re-assigned to myself and we ran a scope review and re-planned the remaining sprints, which we'll cover in more detail in the planning section.

---

## 2. User Stories

I will now give an overview of our user stories.

[show User Stories board: `Oogly Boogly - User Stories` GitHub Project]

We organised our work into epics, each containing related user stories.

We have five core epics: User Authentication, User Interface, User Experience, Math Learning System, and Data Persistence. After our scope review, we added three further columns going forward: Enhancements, Refactoring, and Out of Scope.

[scroll across the board columns, left to right]

[show CHONK chart image, our estimation scale]

For story sizing, we took a light-hearted approach and used a custom estimation scale based on the CHONK chart, a meme that grades cat body fat from "A Fine Boi" (smallest) up to "OH LAWD HE COMIN" (largest). These labels map to our story size complexity estimates. To prioritse stories, a 3 level system was implemented from P0 to P2, where P0 stories claim the highest priority and the function of the application is dependent on them. P1 represents user stories which are significant to the application's functionality and user experience but are not required for the app to function whilst P2 stories are features that rather polish the app and provide a finished product.

The acceptance criteria for each story are defined on the issue. For example:

[open one completed user story, e.g., #4 "1. As a user, I want to create an account so that I can save my pet's progress"]

The acceptance criteria is listed like a checklist, so as we progressively develop a story, each criteria can be marked off as completed, ensuring our goals stay clear and we never lose track.

On the side we can see further details, including labels to mark this issue as a user story and a matching label for the epic.

[Click on 'Labels']

If we click here, we can see the many other labels an issue or user story can have. For the user stories these are very rigid, but in further sprints when refactoring and improvements come into play, these labels become very handy in tracking what is a: Bug Fix, an enhancement such as a new feature or an improvement upon an existing one, or a refactor.

[Close 'Labels']

[Scroll down]

Further, each story has a priority, and time estimate for successful implementation. As seen on the chonk chart, 'A Heckin Chonker' is roughly equivalent to a 1 day task, so the estimate is set to '1'. And we've decided this story is very important, so it's priority has been set to 'P0'.

While each user story is listed in the appropriate columns, they are also linked to the associated epics using Github's relationship feature.

As we can see this user story's parent issue is the epic itself. As a user story is closed, it is tracked by the parent issue and updated at the top of the column.

User stories are also linked directly to their associated pull requests. This means there is a traceable path from the backlog item through to the branch, commits, and code review that delivered it.

[close user story]

We can see for the User Authentication epic, 5 out of 5 user stories have been completed, as indicated by the progress bar and 100% completion.

[open Epic: User Authentication]

And if we click on this epic, we can quickly see all user stories linked as sub-issues, and the state of each.

This tight integration into our repository allows us to dynamically track and close issues, gives us a traceable history throughout development, and a clear view into the overall progress of each epic at any point in the project.

---

## 3. Planning Artefacts

Moving onto our sprint planning...

[switch to 'Oogly Boogly - Sprint Board' GitHub Project]

Each user story is listed in either of 3 columns: Todo, In Progress, and Done. The Todo column contains all user stories within our backlog to be completed, and as a user story is being worked on, we move it to the In Progress column, finish it, make a Pull Request to merge, and then finally move it into the Done column.

If we now switch over to the Roadmap view...

[switch to `Oogly Boogly - Sprint Board` GitHub Project, Roadmap view]

This shows all sprints throughout the development lifecycle. Starting in Sprint 2, which ran from April 15 till the 28th, it contained the first 10 user stories to be completed. Sprint 3 runs from April 29 to May 12 and contains the bulk of the remaining work. And Sprint 4 will be the final run on refactoring and improvement.

[scroll right on roadmap to show Sprint 3 stories]

When we lost our team member in Sprint 2, we ran an informal sprint retrospective and redesigned the scope. The changes are visible in the board:

[switch back to User Stories]

- We added three new columns (Enhancements, Refactoring, and Out of Scope) to categorise work that was deprioritised or deferred
- The item shop and bonus challenge system were moved to Out of Scope
- The math question system was split from one large story into three smaller ones: basic question generation, randomisation, and difficulty weighting, so we could deliver the core functionality first without being blocked by the full feature

[show the Math Learning System epic (#25) with its sub-issues]

Originally, the cat's energy was to be spent on bonus challenges which would provide much greater rewards, and the amount of energy that could be refilled by food items was capped at a daily limit. As these challenges have now been deemed out of scope, we were left wondering what purpose could energy serve. This led us to come up with a rework:

- Energy is now spent on all challenges
- When energy depletes fully, the user can still continue solving problems, but rewards are no longer received.
- This puts a soft cap on the amount of items and XP the user can receive per day, but encourages repetitive but spaced learning

---

## 4. Low-Fidelity UI Prototypes

In this section I'll walk through the three design artefacts we produced before writing any code: an architecture diagram outlining the system's layers, a state machine modelling the cat's behaviour, and a medium-fidelity wireframe mapping the UI flow. All three are stored in the `Diagrams/` folder.

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
