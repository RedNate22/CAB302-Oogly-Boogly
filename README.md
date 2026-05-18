# MathCat: Virtual Pet Application
---
## Overview
This project is for CAB302 Agile Project Development at Queensland University of Technology. It is a group-based project, with the group "OoglyBoogly" ([Mohamed Zayan Eangapadalil](https://github.com/notzayan), [Alexander Grossi](https://github.com/agrossi178), [Nathan Pithie](https://github.com/RedNate22), and [Leonora Van de Sande](https://github.com/smolbebby) being equal collaborators.

**MathCat** is a Java-based GUI application that simulates a virtual pixelated cat. Users care for the pet by solving simple math problems to earn items, currency, and XP. The pet has dynamic attributes that change over time and in response to user interactions. The virtual cat has three distinct stats the user must manage; Happiness, Energy, Fullness.

The project embraces the theme "**Living, Learning, Working with AI**," combining gamified learning with AI assistance. An optional AI chatbot helps users using the "I do, we do, you do" method, guiding toward solutions without giving direct answers. Using the AI affects bonus rewards to encourage independent problem solving.

Users maintain and improve their pet's stats through earned items. Items can also provide cosmetic customisation, such as costumes, fur (colour) patterns etc. which have no effect on gameplay.

## Technical Requirements
For a low-level overview of the gameplay loop and technical specifications, see [Technical-Requirements.md](https://github.com/RedNate22/CAB302-Oogly-Boogly/blob/main/Technical-Requirements.md).

## Building the project
Run the following commands:

```bash
cd mathcat/
```

```bash
./mvnw package -DskipTests
```

## Generating Javadocs
Run the following commands:

(Skip if already in `mathcat/`)
```bash
cd mathcat/
```

```bash
./mvnw javadoc:javadoc
```