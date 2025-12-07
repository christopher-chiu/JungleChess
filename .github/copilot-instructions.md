# Copilot Instructions for JungleChess

## Project Overview
- **JungleChess** is a JavaFX implementation of Dou Shou Qi (Jungle Chess).
- The main entry point is `Main.java`, which sets up the UI using `layout.fxml` and manages the game state via the `Board` class.
- The board is a 7x9 grid, with each cell represented by a `Field` object. Fields have types (`TileType`) such as DEN, TRAP, SHORE, WATER, LAND.
- Game pieces (figures) are modeled as subclasses of `Figure` (e.g., `Lion`, `Tiger`, etc.) in `figure/`. Each piece has a color (`Color`), rank, and position.
- Game state can be saved/loaded using implementations of the `GameIO` interface. The default format uses JSON via `io/json/GameLoader.java`.

## Key Files & Structure
- `src/junglechess/Main.java`: Application entry, UI setup, board instantiation.
- `src/junglechess/Board.java`: Core board logic, field management, turn tracking, IO integration.
- `src/junglechess/Field.java`: Represents a cell on the board, handles tile type and piece placement.
- `src/junglechess/figure/`: Piece classes (`Figure`, `Lion`, `Tiger`, etc.), color and tile type enums.
- `src/junglechess/io/json/GameLoader.java`: JSON-based game state loader/saver.
- `resources/layout.fxml`: JavaFX UI layout.
- `resources/img/`: Piece and UI images.
- `resources/init.json`: Initial game state.

## Developer Workflows
- **Build & Run:** Use standard JavaFX build/run commands. No custom build scripts detected.
- **Debugging:** Use JavaFX IDE debugging tools. Board and piece logic are in `Board.java` and `figure/` classes.
- **Game State:** To load/save games, use the `GameIO` interface. JSON is the default format, but new formats can be added by implementing `GameIO` and registering via `META-INF/services/junglechess.GameIO`.

## Project-Specific Patterns
- **Figure Creation:** Use `FigureFactory.createFigure(Color, type, pos, firstTurn)` for instantiating pieces. Position is a two-digit string (e.g., "34" for x=3, y=4).
- **Field Types:** Tile type logic is hardcoded in `Field.java` based on coordinates.
- **Image Loading:** Piece images are loaded from `resources/img/` using a naming convention: `img/{name}-{color}.png`.
- **ServiceLoader:** GameIO implementations are discovered via Java's `ServiceLoader` using `META-INF/services`.

## Integration Points
- **JavaFX:** UI is defined in FXML and controlled via `Main.java`.
- **Gson:** Used for JSON serialization/deserialization in `GameLoader.java`.
- **Resource Files:** Images and initial state are loaded from the `resources/` directory.

## Example Patterns
- To add a new piece type, subclass `Figure` and update `FigureFactory`.
- To support a new save format, implement `GameIO` and register in `META-INF/services/junglechess.GameIO`.
- To change board layout, modify `Field.java` and `layout.fxml`.

---

If any section is unclear or missing, please provide feedback for further refinement.