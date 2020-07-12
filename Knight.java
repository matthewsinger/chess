import java.util.Arrays;
import java.util.List;

public class Knight extends FixedMover {

  public final static List<Delta> SHIFTS = Arrays.asList(new Delta(2, 1),
      new Delta(2, -1), new Delta(1, 2), new Delta(1, -2), 
      new Delta(-2, 1), new Delta(-2, -1), new Delta(-1, 2), new Delta(-1, -2));
  
  private Knight(Color color, Posn position) {
    super(color, position, SHIFTS);
  }

  Piece moveTo(Posn newPosition) {
    return new Knight(this.getColor(), newPosition);
  }

  public static Knight fromPawn(Pawn pawn, Posn moveOneRow) {
    return new Knight(pawn.getColor(), moveOneRow);
  }
  
  static List<Knight> initialPieces() {
    return Arrays.asList(new Knight(Color.WHITE, new Posn(1, 0)),
        new Knight(Color.WHITE, new Posn(6, 0)),
        new Knight(Color.BLACK, new Posn(1, 7)),
        new Knight(Color.BLACK, new Posn(6, 7)));
  }
}
