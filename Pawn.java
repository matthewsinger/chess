import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Pawn extends Piece {
  
  private final PawnMovement pawnMovement;
  private final boolean justDoubleMoved;
  
  private static class PawnMovement {
    int startingRow;
    Delta singleMove;
    Delta doubleMove;
    List<Delta> attackingMoves;
    int promotionRow;
    
    PawnMovement(int startingRow, Delta singleMove, 
        Delta doubleMove, List<Delta> attackingMoves, int promotionRow) {
      this.startingRow = startingRow;
      this.singleMove = singleMove;
      this.doubleMove = doubleMove;
      this.attackingMoves = attackingMoves;
      this.promotionRow = promotionRow;
    }
    
    Delta leftAttackMove() {
      return attackingMoves.get(0).isLeft() ? attackingMoves.get(0) : attackingMoves.get(1);
    }
    
    Delta rightAttackMove() {
      return attackingMoves.get(0).isLeft() ? attackingMoves.get(1) : attackingMoves.get(0);
    }
  }
  
  private static final PawnMovement WHITE_MOVEMENT = new PawnMovement(1, new Delta(0, 1), new Delta(0, 2),
      Arrays.asList(new Delta(1, 1), new Delta(-1, 1)), 7);
  private static final PawnMovement BLACK_MOVEMENT = new PawnMovement(6, new Delta(0, -1), new Delta(0, -2),
      Arrays.asList(new Delta(1, -1), new Delta(-1, -1)), 0);
  
  Pawn(Color color, Posn position, boolean justDoubleMoved) {
    super(color, position);
    this.pawnMovement = color.equals(Color.WHITE) ? WHITE_MOVEMENT : BLACK_MOVEMENT;
    this.justDoubleMoved = justDoubleMoved;
  }

  protected List<Move> getMoves(Board currentState) {
    List<Move> result = new ArrayList<>();
    Posn moveOneRow = this.getPosition().plus(pawnMovement.singleMove);
    if (currentState.anyPieceOn(moveOneRow) || moveOneRow.onRow(pawnMovement.promotionRow)) {
      return result;
    }
    result.add(new Move(this, moveOneRow));
    if (this.getPosition().onRow(pawnMovement.startingRow)) {
      Posn moveTwoRows = this.getPosition().plus(pawnMovement.doubleMove);
      if (!currentState.anyPieceOn(moveTwoRows)) {
        result.add(new Move(this, moveTwoRows));
      }
    }
    return result;
  }

  protected List<Capture> getCaptures(Board currentState) {
    return FixedMover.getCaptures(this, currentState, pawnMovement.attackingMoves);
  }
  
  protected List<Promotion> getPromotions(Board currentState) {
    Posn moveOneRow = this.getPosition().plus(pawnMovement.singleMove);
    if (!moveOneRow.onRow(pawnMovement.promotionRow)) {
      return Collections.emptyList();
    }
    List<Piece> newPossiblePieces = Arrays.asList(Queen.fromPawn(this, moveOneRow), 
        Bishop.fromPawn(this, moveOneRow), Rook.fromPawn(this, moveOneRow), Knight.fromPawn(this, moveOneRow));
    return newPossiblePieces.stream()
        .map(newPiece -> new Promotion(this, newPiece))
        .collect(Collectors.toList());
  }
  
  protected List<EnPassant> getPassant(Board currentState) {
    List<Piece> enemyPawnsJustDoubleMoved = currentState.enemyPieces(this.getColor())
        .stream()
        .filter(piece -> piece.justDoubledMovedPawn())
        .collect(Collectors.toList());
    List<Posn> attackablePositions = pawnMovement.attackingMoves.stream()
        .filter(delta -> this.getPosition().canMove(delta))
        .map(delta -> this.getPosition().plus(delta))
        .collect(Collectors.toList());
    List<Piece> piecesToTake = enemyPawnsJustDoubleMoved.stream()
        .filter(enemyPawn -> // moving enemy pawn backwards - what just one move would have looked like
          attackablePositions.contains(enemyPawn.getPosition().plus(pawnMovement.singleMove)))
        .collect(Collectors.toList());
    return piecesToTake.stream()
        .map(taking -> {
          Delta movement = this.getPosition().leftOf(taking.getPosition()) 
              ? pawnMovement.rightAttackMove() 
              : pawnMovement.leftAttackMove();
              return new EnPassant(this, taking, this.getPosition().plus(movement));
        })
        .collect(Collectors.toList());
  }

  Piece moveTo(Posn newPosition) {
    return new Pawn(this.getColor(), newPosition, this.getPosition().absoluteRowDifference(newPosition) == 2);
  }
  
  protected boolean justDoubledMovedPawn() {
    return this.justDoubleMoved;
  }
  
  protected Piece clearDoubledMovePawnDataOfColor(Color c) {
    return this.getColor().equals(c) && this.justDoubleMoved 
        ? new Pawn(this.getColor(), this.getPosition(), false)
        : this;
  }
  
  protected static List<Pawn> initialPieces() {
    List<Pawn> result =  new ArrayList<Pawn>(16);
    for (int i = 0; i < 8; i++) {
      result.add(new Pawn(Color.WHITE, new Posn(i, 1), false));
      result.add(new Pawn(Color.BLACK, new Posn(i, 6), false));
    }
    return result;
  }
}
