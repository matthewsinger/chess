interface Player {
  Color getColor();
  String getName();
  Action desiredAction(Board currentState);
  boolean isHuman();
}
