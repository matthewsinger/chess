
public class Human implements Player {
  String name;
  Color c;
  
  Human(String name, Color c) {
    this.name = name;
    this.c = c;
  }
  
  public Color getColor() {
    return c;
  }

  public String getName() {
    return name;
  }

  public Action desiredAction(Board currentState) {
    throw new UnsupportedOperationException();
  }

  public boolean isHuman() {
    return true;
  }

}
