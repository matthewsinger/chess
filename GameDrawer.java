import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javalib.impworld.WorldScene;
import javalib.worldimages.AboveImage;
import javalib.worldimages.AlignModeX;
import javalib.worldimages.AlignModeY;
import javalib.worldimages.BesideImage;
import javalib.worldimages.CircleImage;
import javalib.worldimages.EmptyImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.OverlayOffsetAlign;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

public class GameDrawer {
  protected final static int TILE_SIZE = 50;
  private final static WorldImage BACKGROUND = getBackground();
  private final static WorldImage DOT = new CircleImage(TILE_SIZE / 4, OutlineMode.OUTLINE, java.awt.Color.MAGENTA);
  private final static GameDrawer DRAWER = new GameDrawer();

  protected static GameDrawer drawer() {
    return DRAWER;
  }
  
  private GameDrawer() {}
  
  private static WorldImage getBackground() {
    WorldImage base = new EmptyImage();
    for (int i = 0; i < 8; i++) {
      base = new AboveImage(base, drawColumn(i));
    }
    return base;
  }

  private static WorldImage drawColumn(int i) {
    WorldImage base = new EmptyImage();
    for (int j = 0; j < 8; j++) {
      base = new BesideImage(base, new RectangleImage(TILE_SIZE, TILE_SIZE, 
          OutlineMode.SOLID, (i+j)%2 == 0 ? java.awt.Color.WHITE : java.awt.Color.DARK_GRAY));
    }
    return base;
  }
  
  public WorldScene placeBoard(WorldScene scene, Board pieces, Optional<Piece> currentlySelected) {
    scene.placeImageXY(BACKGROUND, TILE_SIZE * 4, TILE_SIZE * 4);
    for (Piece p : pieces.allPieces()) {
      placeImage(p.getImage(), p.getPosition(), scene);
    }
    currentlySelected.ifPresent(piece -> piece.getAllPossibleActions(pieces).forEach(action -> {
      placeImage(DOT, action.goalPosition(), scene);
    }));
    return scene;
  }

  private void placeImage(WorldImage image, Posn position, WorldScene scene) {
    int x = (int) ((position.getX() + 0.5) * TILE_SIZE);
    int y = (int) ((7 - position.getY() + 0.5) * TILE_SIZE);
    scene.placeImageXY(image, x, y);
  }
}
