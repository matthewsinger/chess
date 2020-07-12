import java.util.List;
import java.util.stream.Collectors;

public abstract class FixedMover extends Piece {
  private final List<Delta> deltas;
  
  protected FixedMover(Color color, Posn position, List<Delta> deltas) {
    super(color, position);
    this.deltas = deltas;
  }
  
  protected List<Move> getMoves(Board currentState) {
    return deltas.stream()
        .filter(delta -> this.getPosition().canMove(delta))
        .map(delta -> this.getPosition().plus(delta))
        .filter(newPosition -> !currentState.anyPieceOn(newPosition))
        .map(newPosition -> new Move(this, newPosition))
        .collect(Collectors.toList());
  }
  
  protected List<Capture> getCaptures(Board currentState) {
    return getCaptures(this, currentState, this.deltas);
  }
  
  static List<Capture> getCaptures(Piece attacker, Board currentState, List<Delta> deltas) {
    return deltas.stream()
    .filter(delta -> attacker.getPosition().canMove(delta))
    .flatMap(delta -> currentState.pieceOn(attacker.getPosition().plus(delta), attacker.enemyColor()).stream())
    .map(piece -> new Capture(attacker, piece))
    .collect(Collectors.toList());
  }
}
