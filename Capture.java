import java.util.Objects;

public class Capture implements Action {

  private final Piece pieceMoving;
  private final Piece pieceCaptured;
  
  public Capture(Piece pieceMoving, Piece pieceCaptured) {
    this.pieceMoving = pieceMoving;
    this.pieceCaptured = pieceCaptured;
  }
  
  public Board applyAction(Board oldBoard) {
    return oldBoard.removePiece(pieceMoving)
        .removePiece(pieceCaptured)
        .addPiece(pieceMoving.moveTo(goalPosition()));
  }

  public boolean capturesKing() {
    return pieceCaptured.isKing();
  }
  
  public boolean equals(Object o) {
    if (o instanceof Capture) {
      Capture other = (Capture) o;
      return this.pieceMoving.equals(other.pieceMoving) && this.pieceCaptured.equals(other.pieceCaptured);
    }
    return false;
  }
  
  public int hashCode() {
    return Objects.hash(this.pieceMoving, this.pieceCaptured);
  }

  public Posn goalPosition() {
    return pieceCaptured.getPosition();
  }

}
