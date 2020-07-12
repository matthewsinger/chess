public enum Color {
  WHITE,
  BLACK;
  
  Color invert() {
    return this.equals(WHITE) ? BLACK : WHITE;
  }
}
