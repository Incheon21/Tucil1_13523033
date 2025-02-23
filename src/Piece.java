import java.util.ArrayList;
import java.util.List;

public class Piece {
  public char[][] shape;
  public int width;
  public int height;

  public Piece(char[][] shape) {
<<<<<<< HEAD
    this.shape = shape;
    this.height = shape.length;
    this.width = shape[0].length;
  }

  public Piece rotate() {
    char[][] newShape = new char[width][height];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        newShape[i][j] = shape[height - j - 1][i];
      }
    }
    return new Piece(newShape);
  }

  public Piece flip() {
    char[][] newShape = new char[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        newShape[i][j] = shape[i][width - j - 1];
      }
    }
    return new Piece(newShape);
=======
      this.shape = shape;
      this.height = shape.length;
      this.width = shape[0].length;
  }

  public Piece rotate() {
      char[][] newShape = new char[width][height];
      for (int i = 0; i < width; i++) {
          for (int j = 0; j < height; j++) {
              newShape[i][j] = shape[height - j - 1][i];
          }
      }
      return new Piece(newShape);
  }

  public Piece flip() {
      char[][] newShape = new char[height][width];
      for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
              newShape[i][j] = shape[i][width - j - 1];
          }
      }
      return new Piece(newShape);
>>>>>>> 11b3cc15d4e42019d338e74eb1f9f4c36eba5b34
  }

  public List<Piece> getAllOrientations() {
    List<Piece> orientations = new ArrayList<>();
    Piece current = this;
    for (int i = 0; i < 4; i++) {
      orientations.add(current);
<<<<<<< HEAD
      orientations.add(current.flip());
      current = current.rotate();
=======
      orientations.add(current.rotate());
      current = current.flip();
>>>>>>> 11b3cc15d4e42019d338e74eb1f9f4c36eba5b34
    }
    return orientations;
  }
  
  public void print() {
    System.out.println("Size: " + height + "x" + width);
    for (int i = 0; i < height; i++) {
<<<<<<< HEAD
      System.out.print("  "); 
      for (int j = 0; j < width; j++) {
        System.out.print(shape[i][j] + " ");
      }
      System.out.println();
    }
}

//   public static void main(String[] args) {
//     char[][] piece = {
//         {'A', 'E'},
//         {'B', 'C'},
//         {'D', '.'},
//     };
    
//     Piece p = new Piece(piece);
//     System.out.println("Original piece:");
//     p.print();
    
//     System.out.println("\nAll possible orientations:");
//     List<Piece> orientations = p.getAllOrientations();
//     for (int i = 0; i < orientations.size(); i++) {
//         System.out.println("\nOrientation " + (i + 1) + ":");
//         orientations.get(i).print();
//     }
    
//     System.out.println("\nTotal orientations: " + orientations.size());
//   }
=======
        System.out.print("  "); // Add indentation
        for (int j = 0; j < width; j++) {
            System.out.print(shape[i][j] + " ");
        }
        System.out.println();
    }
}
  
  public static void main(String[] args) {
    char[][] piece = {
        {'A', '.'},
        {'A', 'A'},
        {'A', '.'},
    };
    
    Piece p = new Piece(piece);
    System.out.println("Original piece:");
    p.print();
    
    System.out.println("\nAll possible orientations:");
    List<Piece> orientations = p.getAllOrientations();
    for (int i = 0; i < orientations.size(); i++) {
        System.out.println("\nOrientation " + (i + 1) + ":");
        orientations.get(i).print();
    }
    
    // Print total number of orientations
    System.out.println("\nTotal orientations: " + orientations.size());
  }
>>>>>>> 11b3cc15d4e42019d338e74eb1f9f4c36eba5b34
}

