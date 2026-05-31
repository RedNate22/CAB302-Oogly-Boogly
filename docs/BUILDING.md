# Building

### Building the project

The following commands must be done in the order provided, from within the `mathcat/` directory.

```bash
cd mathcat
```

### Checkstyle

Checkstyle is a static analysis tool that automatically enforces coding style and naming conventions at build time. You can find all the settings under [mathcat/checkstyle.xml](../mathcat/checkstyle.xml), and you can find all our code conventions under [5.1 Code Standards](TECHNICAL-REQUIREMENTS.md#51-code-standards).

Checkstyle Documentation: [checkstyle.sourceforge.io](https://checkstyle.sourceforge.io/)

| Element | Convention | Example |
|---|---|---|
| Classes | PascalCase | `HomeController` |
| Interfaces | PascalCase with `I` prefix | `IQuestion` |
| Methods | camelCase | `feedPet()` |
| Local variables | camelCase | `petAge` |
| Parameters | camelCase | `mathProblem` |
| Fields | camelCase | `petHealth` |
| Constants | UPPER_SNAKE_CASE | `MAX_HUNGER` |

Checkstyle runs automatically as part of the build. If there are violations, the build will fail and the violations will be printed to the terminal with the file, line number, and rule that was broken (note: build occurs on pushes but wont print to the terminal unless you explicitly run the below build command).

```bash
./mvnw package
```

### Running Checkstyle independently

To check for violations without running the full build:

```bash
./mvnw checkstyle:check
```

Violations are printed to the terminal in the following format:

```
[INFO] Starting audit...
[ERROR] src/main/java/com/mathcat/mathcat/controllers/AuthController.java:70:16: Name 'username_email' must match pattern '^[a-z][a-zA-Z0-9]*$'. [LocalVariableName]
Audit done.
[INFO] BUILD FAILURE
```

Each error shows the file, line, column, what was expected, and the rule that was violated. A successful check ends with `BUILD SUCCESS`. You can also view the results of the latest build's Checkstyle run on GitHub under `Actions > Java CI with Maven > build`.

After resolving the error(s), re-run checkstyle command and you should get:

```
..
[INFO] Starting audit...
Audit done.
[INFO] You have 0 Checkstyle violations.
..
[INFO] BUILD SUCCESS
```

### Clearing the build cache

If errors persist after fixing violations, or the build behaves unexpectedly, clean the cached output and rebuild:

```bash
./mvnw clean package
```

### Generating Javadocs

```bash
./mvnw javadoc:javadoc
```

### Opening Javadocs

```bash
start target/docs/javadoc/index.html
```
