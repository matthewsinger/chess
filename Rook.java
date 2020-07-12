import java.util.Arrays;
import java.util.List;

public class Rook extends LinearMover {

  private final boolean hasMoved;
  
  private Rook(Color color, Posn position, boolean hasMoved) {
    super(color, position, Delta.FLATS);
    this.hasMoved = hasMoved;
  }

  Piece moveTo(Posn newPosition) {
    return new Rook(this.getColor(), newPosition, true);
  }
  
  protected boolean isUnmovedRook() {
    return !hasMoved;
  }

  public static Rook fromPawn(Pawn pawn, Posn moveOneRow) {
    return new Rook(pawn.getColor(), moveOneRow, true);
  }
  
  static List<Rook> initialPieces() {
    return Arrays.asList(new Rook(Color.WHITE, new Posn(0, 0),  false),
        new Rook(Color.WHITE, new Posn(7, 0), false),
        new Rook(Color.BLACK, new Posn(0, 7), false),
        new Rook(Color.BLACK, new Posn(7, 7), false));
  }

}
