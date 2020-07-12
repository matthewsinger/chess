import java.util.Arrays;
import java.util.List;

public class Queen extends LinearMover {

  private Queen(Color color, Posn position) {
    super(color, position, Delta.LINEAR);
  }

  Piece moveTo(Posn newPosition) {
    return new Queen(this.getColor(), newPosition);
  }

  public static Queen fromPawn(Pawn pawn, Posn moveOneRow) {
    return new Queen(pawn.getColor(), moveOneRow);
  }

  static List<Queen> initialPieces() {
    return Arrays.asList(new Queen(Color.WHITE, new Posn(3, 0)),
        new Queen(Color.BLACK, new Posn(3, 7)));
  }
}
