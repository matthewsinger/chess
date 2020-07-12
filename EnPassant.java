import java.util.Objects;

public class EnPassant implements Action {
  
  Pawn attacking;
  Piece toRemove;
  Posn newPosition;
  
  EnPassant(Pawn attacking, Piece toRemove, Posn newPosition) {
    this.attacking = attacking;
    this.toRemove = toRemove;
    this.newPosition = newPosition;
  }

  public Board applyAction(Board oldBoard) {
    return oldBoard.removePiece(attacking).removePiece(toRemove).addPiece(attacking.moveTo(newPosition));
  }

  public boolean equals(Object o) {
    if (o instanceof EnPassant) {
      EnPassant other = (EnPassant) o;
      return this.attacking.equals(other.attacking) && this.toRemove.equals(other.toRemove)
          && this.newPosition.equals(other.newPosition);
    }
    return false;
  }
  
  public int hashCode() {
    return Objects.hash(this.attacking, this.toRemove, this.newPosition);
  }

  public Posn goalPosition() {
    return newPosition;
  }
}
