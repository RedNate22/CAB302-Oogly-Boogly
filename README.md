# MathCat: Virtual Pet Application
---
## Overview
This project is for CAB302 Agile Project Development at Queensland University of Technology. It is a group-based project, with the group "OoglyBoogly" ([Mohamed Zayan Eangapadalil](https://github.com/notzayan), [Alexander Grossi](https://github.com/agrossi178), [Nathan Pithie](https://github.com/RedNate22), and [Leonora Van de Sande](https://github.com/smolbebby) being equal collaborators.

**MathCat** is a Java-based GUI application that simulates a virtual pixelated cat. Users care for the pet by solving simple math problems to earn items, currency, and XP. The pet has dynamic attributes that change over time and in response to user interactions. The virtual cat has three distinct stats the user must manage; Happiness, Energy, Fullness.

The project embraces the theme "**Living, Learning, Working with AI**," combining gamified learning with AI assistance. An optional AI chatbot helps users using the "I do, we do, you do" method, guiding toward solutions without giving direct answers. Using the AI affects bonus rewards to encourage independent problem solving.

Users maintain and improve their pet's stats through earned items. Items can also provide cosmetic customisation, such as costumes, fur (colour) patterns etc. which have no effect on gameplay.

<img width="677" height="464" alt="image" src="https://github.com/user-attachments/assets/3dbddc6c-1614-45dc-820b-bff5af6ccd01" />


## Technical Requirements
For a low-level overview of the gameplay loop and technical specifications, see [Technical-Requirements.md](https://github.com/RedNate22/CAB302-Oogly-Boogly/blob/main/Technical-Requirements.md).


## Building the project and JavaDocs
The following commands must be done in the order provided, from within the `mathcat/` directory.

```bash
cd mathcat
```

### Building the project
Checkstyle runs automatically as part of the build. If there are violations, the build will fail and the violations will be printed to the terminal with the file, line number, and rule that was broken.

```bash
./mvnw package
```

### Running Checkstyle independently
To check for violations without running the full build:

```bash
./mvnw checkstyle:check
```

Violations are printed to the terminal. A successful check ends with `BUILD SUCCESS`. You can also view the results of the latest build's Checkstyle run on GitHub under `Actions > Java CI with Maven > build`.

Note: `checkstyle:check` runs against the compiled classes in `target/`, so if you have made changes since the last build, re-package first or just run `package` directly - it includes Checkstyle automatically.

```bash
./mvnw package
```

### Generating Javadocs

```bash
./mvnw javadoc:javadoc
```

### Opening Javadocs

```bash
start target/docs/javadoc/index.html
```

### Live Javadoc

Our live JavaDoc (hosted on GH Pages) can be found [here](https://rednate22.github.io/CAB302-Oogly-Boogly/com.mathcat.mathcat/module-summary.html).
