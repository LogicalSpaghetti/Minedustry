# todo
## BlockEntity inheritance structure:
- MinedustryBlockEntity
  - everything a block could possibly be
- Layer 2: general categories such as "production"
- Layer 3: any more specific groups with a lot of functionality in common
  - There shouldn't be any need for another layer
- Layer 4: The individual classes for blocks
- This structure ensures every edge-case block can be defined relatively easily, and nothing ever has to be reworked to support a new property
## Current:
- fail outputs into blocks facing this one
- add all basic belts
  - duct texture
- add a check for armored belts
## Short-term goals:
### Stacking:
- custom handling for stacks exceeding 64
### Belts:
- belts curve items
- belts visually curve
### Texturing:
- create textures
- have a separate texture for active blocks which cycles through an animation
- Everything references the same file, so it can be split without worrying
- silverfish-style textures, using a single rectangle with empty space
- start with very basic textures directly copying the originals
- create my own mostly original textures
- revamp the old textures to not have weirdness and to fit better in 3D
    - while preserving their style
- only include mine in the pack if they're better than the originals
### Add more blocks
- start bty adding every block with differing functions (i.e. Impact Reactor)
### Fluid handling
### Sounds
### Power
- Grids
  - Sharing data
  - Adding on
  - Breaking
- Generation
- Transference
- Usage
### Heat
### Shift-Move limiting
### Cores
### More belts
### Turrets
### Ores
- Full block that works with both drill types
### Drills
- Floor
- Wall
### Status indicator
### Item source
### Handle partial unloading
### Blocks should kill themselves 
- if they notice they're partially broken
## Long term goals:
- orthographic camera
- redo all models
- Logic
- ask Anuke if I may publish this mod
## Design notes:
- Ctrl+W expands code selection
- Ctrl+N opens the search-for-class dialogue
