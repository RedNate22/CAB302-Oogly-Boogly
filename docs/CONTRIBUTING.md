# Basic Workflow

## Setup your environment

1. Clone the repo: `git clone https://github.com/RedNate22/CAB302-Oogly-Boogly.git`

2. Create a branch: `git switch -c branch_name` / `git checkout -b branch_name` (name it after the feature/story being implemented - short/descriptive)

3. Open the project in your IDE if you haven't already and make sure it builds/runs before starting work

## Look over your assigned task(s)

1. Look at the acceptance criteria for your assigned user story

2. Note any potential challenges/blockers and ask questions if needed + research potential solutions

3. On the sprint board: move your user story(s) into `In progress`

4. Begin working on the acceptance criteria within your assigned story(s)

## Make your changes

1. Make your changes and commit regularly:
    - Stage: `git add .` / `git add fileName.extension`
    - Commit: `git commit -m "type: descriptive message"` (present tense; "change" not "changes")

2. Push your branch: `git push origin branch_name` / `git push`
    - **First push on a new branch:** `git push -u origin branch_name`

3. Tick off acceptance criteria as they are completed

## Keep your branch up to date (warning: make your commits or stash your changes before this)

If `main` has been updated while you were working, pull the latest changes to avoid conflicts:

1. `git switch main` (go to main)

2. `git fetch origin` (force Git to check if anything happened remotely)

3. `git status` (after fetching, Git will tell you if you're behind `main` and can be fast-forwarded; if so, run `git pull` while on `main` branch)

4. Switch back to your branch (`git switch [your-branch])`

5. a) Two ways to merge:

- `git merge origin/main`
- **or** `git rebase origin/main` if you prefer a cleaner history

b) Resolve any conflicts

c) Depending on which merge type you did:

- **If** you did `merge`: make another commit (`git commit -m "merge: main into [branch-name]"`)
- **If** you did `rebase`: run `git rebase --continue`

## Finished your branch? Open a Pull Request (PR)

**Test your code first obviously**

1. Go to the repo on GitHub

2. Click **Compare & pull request** (shown after pushing) or go to **Pull requests > New pull request**

3. Set the base branch to `main` and compare branch to your branch

4. Link your pull request to the user story (issue): [how to](https://docs.github.com/en/issues/tracking-your-work-with-issues/using-issues/linking-a-pull-request-to-an-issue) - each issue has a `#` number

5. Write a short description of what your PR does and any notes for reviewers

6. Assign a reviewer(s) (Nate & Leonora), then click **Create pull request**

7. Address feedback:
   -push new commits to the same branch
   -resolve conversations you have addressed (click **Resolve conversation**)
   -request a re-review so the reviewer(s) know you're ready

8. Once approved, @weredingo or @smolbebby will perform the merge

9. _If @weredingo / @smolbebby did not already do this:_ Delete the remote branch after merging (GitHub will prompt you / `git branch -d branch_name`)

10. Mark user story `closed` + move to `completed` (sprint board

## Start the next task

Loop back to the top: create a new branch off the updated `main`:

1. `git switch main` / `git checkout main`
2. `git pull origin main`
3. `git switch -c new_branch_name`

### Optional: Change branches (testing)

Unstaged changes will carry over with you to the other branch (unless a conflict occurs); switch back before staging/committing them

1. See what branch you're currently on: `git branch` (displays an asterisk `*` next to the branch you're on)

2. `git switch branch_name`

### PR's FROM `MAIN` WILL NOT BE ACCEPTED

All PR's must be made and updated with `dev`.

The main branch is intended to remain 'clean' of any issues wherever possible, and represents the current 'final' state of our app. Active development is to remain on the `dev` branch and only periodically merged into `main` upon releases or critical fixes.
