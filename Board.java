import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Board {
  private final List<Piece> pieces;
  
  public static Board newBoard() {
    List<Piece> pieces = new ArrayList<>(32);
    pieces.addAll(Bishop.initialPieces());
    pieces.addAll(King.initialPieces());
    pieces.addAll(Knight.initialPieces());
    pieces.addAll(Pawn.initialPieces());
    pieces.addAll(Queen.initialPieces());
    pieces.addAll(Rook.initialPieces());
    return new Board(pieces);
  }
  
  private Board(List<Piece> pieces) {
    this.pieces = pieces;
  }
  
  public boolean playerIsInCheck(Color color) {
    return this.enemyPieces(color).stream()
        .flatMap(piece -> piece.getCaptures(this).stream())
        .anyMatch(capture -> capture.capturesKing());
  }
  
  public Board addPiece(Piece piece) {
    List<Piece> newPieces = new ArrayList<>(pieces);
    newPieces.add(piece);
    return new Board(newPieces);
  }
  
  public Board removePiece(Piece piece) {
    return new Board(pieces.stream().filter(p -> !p.equals(piece)).collect(Collectors.toList()));
  }
  
  public List<Piece> allPieces() {
    return pieces;
  }
  
  public List<Piece> playerPieces(Color color) {
    return color.equals(Color.WHITE) ? whitePieces() : blackPieces();
  }
  
  public List<Piece> enemyPieces(Color color) {
    return playerPieces(color.invert());
  }
  
  private List<Piece> whitePieces()  {
    return matching(pieces, Color.WHITE);
  }
  
  private List<Piece> blackPieces()  {
    return matching(pieces, Color.BLACK);
  }
  
  public Optional<Piece> pieceOn(Posn p) {
    return this.pieces.stream().filter(piece -> piece.matches(p)).findAny();
  }
  
  public Optional<Piece> pieceOn(Posn p, Color c) {
    return this.pieceOn(p).filter(piece -> piece.matches(c));
  }
  
  public boolean anyPieceOn(Posn p) {
    return this.pieceOn(p).isPresent();
  }
  
  public List<Piece> getUnmovedRooks(Color c)  {
    return playerPieces(c).stream().filter(piece -> piece.isUnmovedRook()).collect(Collectors.toList());
  }
  
  private static List<Piece> matching(List<Piece> allPieces, Color color)  {
    return allPieces.stream()
        .filter(p -> p.matches(color))
        .collect(Collectors.toList());
  }

  public boolean nothingBetween(Posn p1, Posn p2) {
    if (p1.equals(p2) || !p1.onSameRow(p2)) {
      throw new IllegalArgumentException(String.format(
          "Must check between two different positions on the same row, given %s and %s", p1, p2));
    }
    Posn left;
    Posn right;
    if (p1.leftOf(p2)) {
      left = p1;
      right = p2;
    } else {
      left = p2;
      right = p1;
    }
    Delta goRight = new Delta(1, 0);
    while (!left.equals(right)) {
      left = left.plus(goRight);
      if (this.anyPieceOn(left)) {
        return false;
      }
    }
    return true;
  }
}
