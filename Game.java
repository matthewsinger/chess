import java.util.List;
import java.util.Optional;

import javalib.impworld.World;
import javalib.impworld.WorldScene;

// TODO: 
// Game over
// Tying
// Checking validity of moves
// Clearing out en passant "recently moved" flag
// Not dumb AI

public class Game extends World {
    
  private Board currentState;
  private Player p1;
  private Player p2;
  private GameDrawer drawer;
  private Optional<Piece> currentlySelected;

  Game(Player p1, Player p2) {
    this.p1 = checkColor(p1, Color.WHITE);
    this.p2 = checkColor(p2, Color.BLACK);
    this.currentState = Board.newBoard();
    this.drawer = GameDrawer.drawer();
    this.currentlySelected = Optional.empty();
  }

  private static Player checkColor(Player p, Color c) {
    if (p.getColor().equals(c)) {
      return p;
    }
    throw new IllegalArgumentException("bad colors");
  }

  public WorldScene makeScene() {
    return drawer.placeBoard(this.getEmptyScene(), currentState, currentlySelected);
  }
  
  public void onTick() {
    if (!p1.isHuman()) {
      this.endTurn(p1.desiredAction(currentState));
    }
  }
  
  public void onMouseClicked(javalib.worldimages.Posn click) {
    if (!p1.isHuman()) {
      return;
    }
    Posn p = Posn.fromClick(click);
    if (currentlySelected.isEmpty()) {
      currentlySelected = currentState.playerPieces(p1.getColor())
          .stream()
          .filter(piece -> piece.matches(p))
          .findAny();
    } else {
      List<Action> possibleActions = currentlySelected.get().getAllPossibleActions(currentState);
      possibleActions.stream()
          .filter(action -> action.goalPosition().equals(p))
          .findAny()
          .ifPresent(action -> this.endTurn(action));
    }
  }
  
  public void onKeyEvent(String key) {
    if (key.equals("escape")) {
      clearSelection();
    }
  }
  
  private void clearSelection() {
    this.currentlySelected = Optional.empty();
  }
  
  private void endTurn(Action action) {
    currentState = action.applyAction(currentState);
    Player temp = p1;
    p1 = p2;
    p2 = temp;
    clearSelection();
  }
   
  public static void main(String args[]) {
    Game g = new Game(new Human("foo", Color.WHITE), new Human("Matt", Color.BLACK));
    int size = GameDrawer.TILE_SIZE * 8;
    g.bigBang(size, size, 1);
  }
}
