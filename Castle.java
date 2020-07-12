import java.util.Objects;

public class Castle implements Action {

  King king;
  Piece rook;
  
  public Castle(King king, Piece rook) {
    this.king = king;
    this.rook = rook;
  }
  
  public Board applyAction(Board oldBoard) {
    return oldBoard.removePiece(king).removePiece(rook)
        .addPiece(king.moveTo(goalPosition()))
        .addPiece(rook.moveTo(king.getPosition()));
  }

  public boolean equals(Object o) {
    if (o instanceof Castle) {
      Castle other = (Castle) o;
      return this.rook.equals(other.rook) && this.king.equals(other.king);
    }
    return false;
  }
  
  public int hashCode() {
    return Objects.hash(this.king, this.rook);
  }

  public Posn goalPosition() {
    Posn kingPosition = king.getPosition();
    Posn rookPosition = rook.getPosition();
    int delta = kingPosition.leftOf(rookPosition) ? -2 : 2;
    return king.getPosition().plus(new Delta(delta, 0));
  }
}
