
public class AlgebraicCoordinate {
  private final char file; // stored as a-h
  private final int rank;
  
  private AlgebraicCoordinate(char file, int rank) {
   if (65 <= file && file <= 72) {
     this.file = (char) ((int) file + 32);
   }
   else if (97 <= file && file <= 104) {
     this.file = file;
   } else {
     throw new IllegalArgumentException("file must be a-h or A-H, given " + file);
   }
   if (1 <= rank && rank <= 8) {
     this.rank = rank;
   } else {
     throw new IllegalArgumentException("rank must be 1-8, given " + rank);
   }
  }
  
  public static AlgebraicCoordinate fromString(String str) {
    str = str.strip();
    if (str.length() != 2) {
      throw new IllegalArgumentException("String must be of size 2, given " + str);
    }
    try {
      Integer.valueOf(str.substring(1));
    } catch (Exception e) {
      throw new IllegalArgumentException("Second character must be an integer, given " + str);
    }
    return new AlgebraicCoordinate(str.charAt(0), Integer.valueOf(str.substring(1)));
  }
  
  public String toString() {
    return String.valueOf(file) + rank;
  }
  
  public Posn toPosition() {
    return new Posn(file-97, rank-1);
  }
}
