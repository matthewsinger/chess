import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class King extends FixedMover {

  private final boolean hasMoved;
  
  private King(Color color, Posn position, boolean hasMoved) {
    super(color, position, Delta.LINEAR);
    this.hasMoved = hasMoved;
  }

  protected Piece moveTo(Posn newPosition) {
    return new King(this.getColor(), newPosition, true);
  }
  
  protected boolean isKing() {
    return true;
  }
  
  protected List<Castle> getCastles(Board currentState) {
    if (this.hasMoved || currentState.playerIsInCheck(this.getColor())) {
      return new ArrayList<>(0);
    } else {
      return currentState.getUnmovedRooks(this.getColor())
          .stream()
          .filter(rook -> currentState.nothingBetween(this.getPosition(), rook.getPosition()))
          .map(rook -> new Castle(this, rook))
          .collect(Collectors.toList());
      
    }
  }

  static List<King> initialPieces() {
    return Arrays.asList(new King(Color.WHITE, new Posn(4, 0), false),
        new King(Color.BLACK, new Posn(4, 7), false));
  }
}
