# Logging

This project uses SLF4J with Logback. Log output is controlled via `src/main/resources/logback.xml`.

## Adding a logger to a class

Add this field at the top of the class, replacing `MyClass` with the actual class name:

```java
private static final Logger LOG = LoggerFactory.getLogger(MyClass.class);
```

Imports:

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
```

## Writing log statements

Use `{}` as placeholders instead of string concatenation:

```java
log.debug("serving question: {}, answer: {}", question.getText(), question.getAnswer());
log.info("user logged in: {}", username);
log.warn("cat energy low: {}", cat.getEnergy());
log.error("failed to save cat", e); // always pass the exception as the last argument
```

## Log levels

| Level   | Use for                               |
|---------|---------------------------------------|
| `debug` | Diagnostic info during development    |
| `info`  | Significant normal app events         |
| `warn`  | Unexpected but recoverable problems   |
| `error` | Failures (always include the exception) |

## Toggling output

In `logback.xml`, change the level on the `com.mathcat` logger line:

| Level   | Effect                          |
|---------|---------------------------------|
| `DEBUG` | Shows all logs                  |
| `INFO`  | Hides debug logs                |
| `WARN`  | Hides debug and info logs       |
| `ERROR` | Shows errors only               |
| `OFF`   | Silences all logs               |
