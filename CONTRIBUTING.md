# Contributing



## Basic Guidelines

### Use an IDE.
Specifically, IntelliJ IDEA. Download the (free) Community Edition of it [here](https://www.jetbrains.com/idea/download/). Some people use other tools, like VS Code, but I would personally not recommend them for Java development.

### Always test your changes.
Do not submit something without at least running the game to see if it compiles.  
If you are submitting a new block, make sure it has a name and description, and that it works correctly in-game. If you are changing existing mechanics, test them out first.

### Do not make large changes before discussing them first.
If you are interested in adding a large mechanic/feature or changing large amounts of code, first contact me (LogicalSpaghetti) via [Discord](https://discord.gg/tuYCKNUsDw) - either via PM or by posting in the `#suggestions-and-contributions` channel.
For most changes, this should not be necessary. I just want to know if you're doing something big, so I can offer advice
and/or make sure you're not wasting your time on it.

### No 1-line PRs
Yes, there are occurrences of trailing spaces, extra newlines, empty indents, and other tiny errors. No, I don't want to merge, view, or get notified by your 1-line PR fixing it.
Please only either include formatting changes with other pull requests, or reformat in bulk, thank you!
## Style Guidelines
### Required:
- same-line curly braces and parentheses
- 4 space indentation (IntelliJ default)
- `snake_case` for folders
- `camelCase` for variables and methods
- `PascalCase` for classes
- `SCREAMING_SNAKE_CASE` or `snake_case` for constants and enums

- clear, concise names
  - names should almost always be 1-4 words long, and be enough to understand what the method does

### Suggestions:
- If a line overflows the edge of the screen, add newlines where it feels right
- include comments explaining any verbose lines
