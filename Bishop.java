import java.util.Arrays;
import java.util.List;

public class Bishop extends LinearMover {

  private Bishop(Color color, Posn position) {
    super(color, position, Delta.DIAGONALS);
  }

  Piece moveTo(Posn newPosition) {
    return new Bishop(this.getColor(), newPosition);
  }

  public static Bishop fromPawn(Pawn pawn, Posn moveOneRow) {
    return new Bishop(pawn.getColor(), moveOneRow);
  }
  
  static List<Bishop> initialPieces() {
    return Arrays.asList(new Bishop(Color.WHITE, new Posn(2, 0)),
        new Bishop(Color.WHITE, new Posn(5, 0)),
        new Bishop(Color.BLACK, new Posn(2, 7)),
        new Bishop(Color.BLACK, new Posn(5, 7)));
  }

}
