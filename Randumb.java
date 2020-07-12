import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Randumb implements Player {

  Color c;
  
  Randumb(Color c) {
    this.c = c;
  }
  
  public Color getColor() {
    return c;
  }

  public String getName() {
    return "Randumb";
  }

  public Action desiredAction(Board currentState) {
    List<Action> allActions = currentState.playerPieces(getColor())
        .stream()
        .flatMap(piece -> piece.getAllPossibleActions(currentState).stream())
        .collect(Collectors.toList());
    return allActions.get((int)(Math.random() * allActions.size()));
  }

  public boolean isHuman() {
    return false;
  }

}
