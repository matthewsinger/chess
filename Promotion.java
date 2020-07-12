import java.util.Objects;

public class Promotion implements Action {
  Pawn oldPiece;
  Piece newPiece;
  
  Promotion(Pawn oldPiece, Piece newPiece) {
    this.oldPiece = oldPiece;
    this.newPiece = newPiece;
  }
  
  public Board applyAction(Board oldBoard) {
    return oldBoard.removePiece(oldPiece).addPiece(newPiece);
  }

  public boolean equals(Object o) {
    if (o instanceof Promotion) {
      Promotion other = (Promotion) o;
      return this.oldPiece.equals(other.oldPiece) && this.newPiece.equals(other.newPiece);
    }
    return false;
  }
  
  public int hashCode() {
    return Objects.hash(this.oldPiece, this.newPiece);
  }

  public Posn goalPosition() {
    return newPiece.getPosition();
  }
}
