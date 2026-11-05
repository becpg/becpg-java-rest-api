# AI Assistant Guidelines

This document gives AI assistants (like Claude) the conventions to follow on this project.

## Project Overview

**becpg-java-rest-api** is the Java SDK of the beCPG remote API: a Spring Boot library that
out-of-process applications use to read and write beCPG entities (`EntityAPI`, `ChannelAPI`,
models such as `RemoteEntity` and `RemoteEntitySchema`). Its public classes are a contract with
the integrating applications: keep them backward compatible, and document any breaking change.

- **Java 17**, Spring Boot 4, Jackson 3 (`tools.jackson` packages; the annotations stay in
  `com.fasterxml.jackson.annotation`)
- The remote API it consumes is documented in the beCPG documentation
  (`becpg-docs/becpg-plm/latest/{en,fr}/development/API.md`), whose "Versions" table gives the
  remote API version of each beCPG version

## Coding Guidelines

- Follow the indentation and style of the file you edit
- Explicit types, no `var`; no fully-qualified class names in code, use imports
- Everything in the repository is in English: identifiers, comments, Javadoc, logs, commits
- A field the repository may not publish (older beCPG version) stays nullable, and its Javadoc
  says from which remote API version it is filled
- Keep new dependencies to a minimum and justify any addition

## Tests

- Unit tests with JUnit 5, in `src/test/java`, same package as the covered class, named
  `<ClassName>Test`
- Run them before any commit: `./mvnw test`

## Release

Releases are made with `./release.sh` (Maven release plugin). **Before each release, update the
documentation:**

1. **`README.md`**: add a line for the released version at the top of the "Versions" table:
   - **Version**: the version being released (the pom version without `-SNAPSHOT`);
   - **beCPG Version** and **Version API**: the oldest beCPG version and remote API version the
     release works with, taken from the "Versions" table of the remote API documentation; never
     invent an API version that is not in that table;
   - **Version JAVA**: the Java version required by the library;
   - **Changes**: one short sentence per notable change.
   Also update the rest of the README (configuration, methods) when the release changes them.
2. **`MIGRATION.md`**: describe every breaking change, short, with what the integrating
   application must do.
3. **`CHANGELOG.md`**: do not edit it by hand, `release.sh` appends the commits of the release.

## Git Commit Message Format

One line, in English:

```
[InProgress #ticket - |Fix #ticket - ][Type] Description
```

Types: `[Feature]`, `[Bug]`, `[Setup]`, `[Cleanup]`, `[Migration]`, `[Security]`, `[Merge]`.

```
Fix #38128 - [Feature] Read definedBy in RemoteEntitySchema
[Setup] Update README and MIGRATION for 2.0.2
```

- Stage only the files you changed, never `git add .`
- No `Co-Authored-By` line, no "Generated with" mention
- No push, merge, tag or release without an explicit request
