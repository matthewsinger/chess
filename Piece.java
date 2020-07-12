import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javalib.worldimages.FromFileImage;
import javalib.worldimages.WorldImage;

public abstract class Piece {
  private final Color color;
  private final Posn position;
  
  Piece(Color color, Posn position) {
    this.color = color;
    this.position = position;
  }
  
  public List<Action> getAllPossibleActions(Board currentState) {
    List<Action> allActions = new ArrayList<>();
    allActions.addAll(this.getMoves(currentState));
    allActions.addAll(this.getCaptures(currentState));
    allActions.addAll(this.getCastles(currentState));
    allActions.addAll(this.getPromotions(currentState));
    allActions.addAll(this.getPassant(currentState));
    return allActions.stream()
        .filter(action -> !action.putsSelfInCheck(this.color, currentState))
        .collect(Collectors.toList());
  }
  
  protected abstract List<Move> getMoves(Board currentState);
  protected abstract List<Capture> getCaptures(Board currentState);
  
  protected List<Castle> getCastles(Board currentState) {
    return Collections.emptyList();
  }
  
  protected List<Promotion> getPromotions(Board currentState) {
    return Collections.emptyList();
  }
  
  protected List<EnPassant> getPassant(Board currentState) {
    return Collections.emptyList();
  }
  
  protected boolean matches(Color color, Posn p) {
    return this.matches(color) && this.matches(p);
  }
  
  protected boolean matches(Color color) {
    return this.color.equals(color);
  }
  
  protected boolean matches(Posn p) {
    return this.position.equals(p);
  }
  
  public boolean equals(Object o) {
    if (o instanceof Piece) {
      Piece other = (Piece) o;
      return this.position.equals(other.position) && this.color.equals(other.color);
    }
    return false;
  }
  
  public int hashCode() {
    return Objects.hash(position, color);
  }
  
  protected boolean canMove(Delta delta) {
    return this.position.canMove(delta);
  }
  
  protected Posn getPosition() {
    return this.position;
  }
  
  protected Color getColor() {
    return this.color;
  }
  
  protected Color enemyColor() {
    return this.color.invert();
  }
  
  abstract Piece moveTo(Posn newPosition);

  protected boolean isKing() {
    return false;
  }
  
  protected boolean isUnmovedRook() {
    return false;
  }
  
  protected boolean justDoubledMovedPawn() {
    return false;
  }
  
  protected Piece clearDoubledMovePawnDataOfColor(Color c) {
    return this;
  }
  
  protected WorldImage getImage() {
    return new FromFileImage("src/resources/" + getImageFileName(this.color));
  }
  
  private String getImageFileName(Color c) {
    String color = c.equals(Color.WHITE) ? "white" : "black";
    String className = this.getClass().getSimpleName();
    return color + className + ".png";
  }
}
