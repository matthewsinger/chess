import java.util.Objects;

public class Move implements Action {

  private final Piece pieceMoving;
  private final Posn newPosition;
  
  public Move(Piece pieceMoving, Posn newPosition) {
    this.pieceMoving = pieceMoving;
    this.newPosition = newPosition;
  }
  
  public Board applyAction(Board oldBoard) {
    return oldBoard.removePiece(pieceMoving).addPiece(pieceMoving.moveTo(newPosition));
  }

  public boolean equals(Object o) {
    if (o instanceof Move) {
      Move other = (Move) o;
      return this.pieceMoving.equals(other.pieceMoving) && this.newPosition.equals(other.newPosition);
    }
    return false;
  }
  
  public int hashCode() {
    return Objects.hash(this.pieceMoving, this.newPosition);
  }

  public Posn goalPosition() {
    return newPosition;
  }
}
