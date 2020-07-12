import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// 0, 0 is lower left
public class Posn {
  private final int x;
  private final int y;
    
  Posn(int x, int y) {
    this.x = checkCoordinate(x);
    this.y = checkCoordinate(y);
  }
  
  public boolean equals(Object o) {
    if (o instanceof Posn) {
      Posn other = (Posn) o;
      return other.x == this.x && other.y == this.y;
    }
    return false;
  }
  
  public int hashCode() {
    return Objects.hash(x, y);
  }
  
  public String toString() {
    return String.format("(%d,%d)", x, y);
  }
  
  private static int checkCoordinate(int coord) {
    if (!validCoordinate(coord)) {
      throw new IllegalArgumentException("need coordinate to be 0-7, given " + coord);
    }
    return coord;
  }
  
  public AlgebraicCoordinate toAlgebraic() {
    return AlgebraicCoordinate.fromString(String.valueOf((char)(x+97)) + (y+1));
  }
  
  public Posn plus(Delta delta) {
    return new Posn(delta.addX(this.x), delta.addY(this.y));
  }
  
  private static boolean validCoordinate(int coord) {
    return coord >= 0 && coord <= 7;
  }
  
  public boolean canMove(Delta delta) {
    return validCoordinate(delta.addX(this.x)) && validCoordinate(delta.addY(this.y));
  }

  public boolean leftOf(Posn other) {
    return this.x < other.x;
  }

  public boolean onSameRow(Posn other) {
    return onRow(other.y);
  }

  public boolean onRow(int startingRow) {
    return this.y == startingRow;
  }

  public int absoluteRowDifference(Posn newPosition) {
    return Math.abs(this.y - newPosition.y);
  }

  protected int getX() {
    return x;
  }
  
  protected int getY() {
    return y;
  }
  
  static Posn fromClick(javalib.worldimages.Posn p) {
    int x = p.x / GameDrawer.TILE_SIZE;
    int y = 7 - (p.y / GameDrawer.TILE_SIZE);
    return new Posn(x, y);
  }
}

class Delta {
  private final int x;
  private final int y;  
  
  public final static List<Delta> FLATS = Arrays.asList(new Delta(0, 1),
      new Delta(0, -1), new Delta(1, 0), new Delta(-1, 0));
  
  public final static List<Delta> DIAGONALS = Arrays.asList(new Delta(1, 1),
      new Delta(1, -1), new Delta(-1, 1), new Delta(-1, -1));
  
  public final static List<Delta> LINEAR = Arrays.asList(new Delta(1, 1),
      new Delta(1, -1), new Delta(-1, 1), new Delta(-1, -1), 
      new Delta(0, 1), new Delta(0, -1), new Delta(1, 0), new Delta(-1, 0));
  
  Delta(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int addX(int x) {
    return this.x + x;
  }
  

  public int addY(int y) {
    return this.y + y;
  }

  public boolean isLeft() {
    return x < 0;
  }
}
