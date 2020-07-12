public interface Action {
  Board applyAction(Board oldBoard);
  Posn goalPosition();
  
  default boolean putsSelfInCheck(Color playerMoving, Board oldBoard) {
    return this.applyAction(oldBoard).playerIsInCheck(playerMoving);
  }
  
  default boolean putsOtherPlayerInCheck(Color playerMoving, Board oldBoard) {
    return this.applyAction(oldBoard).playerIsInCheck(playerMoving.invert());
  }
  
  default boolean putsSelfOutOfCheck(Color playerMoving, Board oldBoard) {
    return !putsSelfInCheck(playerMoving, oldBoard);
  }
}
