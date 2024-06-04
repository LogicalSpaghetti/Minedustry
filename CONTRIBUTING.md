# Contributing



## Basic Guidelines

### Use an IDE.
Specifically, IntelliJ IDEA. Download the (free) Community Edition of it [here](https://www.jetbrains.com/idea/download/). Some people use other tools, like VS Code, but I would personally not recommend them for Java development.

### Always test your changes.
Do not submit something without at least running the game to see if it compiles.  
If you are submitting a new block, make sure it has a name and description, and that it works correctly in-game. If you are changing existing mechanics, test them out first.

### Do not make large changes before discussing them first.
If you are interested in adding a large mechanic/feature or changing large amounts of code, first contact me (LogicalSpaghetti) via [Discord](https://discord.gg/tuYCKNUsDw) - either via PM or by posting in the `#pulls` channel.
For most changes, this should not be necessary. I just want to know if you're doing something big so I can offer advice and/or make sure you're not wasting your time on it.

### Feel free to make formatting PRs.
Yes, there are occurrences of trailing spaces, extra newlines, empty indents, and other tiny errors. No, I don't want to merge, view, or get notified by your 1-line PR fixing it. If you're implementing a PR with modification of *actual code*, feel free to fix formatting in the general vicinity of your changes, but please don't waste everyone's time with pointless changes.

## Style Guidelines

### Follow the formatting guidelines.
This means:
- No spaces around parentheses: `if(condition){`, `SomeType s = (SomeType)object`
- top braces should always be in-line, not below
- Same-line braces.
- 4 spaces indentation
- `camelCase` for variables and methods
- `SCREAMING_SNAKE_CASE` for constants and enums
- `PascalCase` for classes
- folders and everything else should use 'snake_case'
- variable and method names should fully explain their purpose while still being as concise as possible