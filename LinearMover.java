import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class LinearMover extends Piece {

  private final List<Delta> deltas;
  
  protected LinearMover(Color color, Posn position, List<Delta> deltas) {
    super(color, position);
    this.deltas = deltas;
  }

  protected List<Move> getMoves(Board currentState) {
    return deltas.stream()
        .flatMap(delta -> this.placesToMove(currentState, delta).stream())
        .map(posn -> new Move(this, posn))
        .collect(Collectors.toList());
  }
  
  private List<Posn> placesToMove(Board currentState, Delta delta) {
    List<Posn> toReturn = new ArrayList<>(10);
    Posn position = this.getPosition();
    while(position.canMove(delta)) {
      position = position.plus(delta);
      if (currentState.anyPieceOn(position)) {
        return toReturn;
      }
      toReturn.add(position);
    }
    return toReturn;
  }
  
  protected List<Capture> getCaptures(Board currentState) {
    return deltas.stream()
        .flatMap(delta -> this.pieceToCapture(currentState, delta).stream())
        .map(piece -> new Capture(this, piece))
        .collect(Collectors.toList());
  }
  
  private Optional<Piece> pieceToCapture(Board currentState, Delta delta) {
    Posn position = this.getPosition();
    while(position.canMove(delta)) {
      position = position.plus(delta);
      Optional<Piece> enemyPiece = currentState.pieceOn(position, this.enemyColor());
      if (enemyPiece.isPresent()) {
        return enemyPiece;
      }
      Optional<Piece> friendlyPiece = currentState.pieceOn(position, this.getColor());
      if (friendlyPiece.isPresent()) {
        return Optional.empty();
      }
    }
    return Optional.empty();
  }

}
