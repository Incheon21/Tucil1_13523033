import java.util.HashMap;
import java.util.Map;

public class Board {
  public int rows, cols;
  public char[][] board;
  private static final Map<Character, String> colorMap = new HashMap<>();

  static {
    String[] colors = {
        "\u001B[31m",
        "\u001B[32m",
        "\u001B[33m",
        "\u001B[34m",
        "\u001B[35m",
        "\u001B[36m",
        "\u001B[91m",
        "\u001B[92m",
        "\u001B[93m",
        "\u001B[94m",
        "\u001B[95m",
        "\u001B[96m",
        "\u001B[41m",
        "\u001B[42m",
        "\u001B[43m",
        "\u001B[44m",
        "\u001B[45m",
        "\u001B[46m",
        "\u001B[101m",
        "\u001B[102m",
        "\u001B[103m",
        "\u001B[104m",
        "\u001B[105m",
        "\u001B[106m",
        "\u001B[31;1m",
        "\u001B[32;1m",
        "\u001B[33;1m",
        "\u001B[34;1m",
        "\u001B[35;1m",
        "\u001B[36;1m"
    };
    for (int i = 0; i < 26; i++) {
      colorMap.put((char) ('A' + i), colors[i]);
    }
  }

  public Board(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.board = new char[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        board[i][j] = '$';
      }
    }
  }

  public boolean placePiece(Piece piece, int x, int y) {
    if (!canPlace(piece, x, y))
      return false;
    for (int i = 0; i < piece.height; i++) {
      for (int j = 0; j < piece.width; j++) {
        if (piece.shape[i][j] != '$') {
          board[x + i][y + j] = piece.shape[i][j];
        }
      }
    }
    return true;
  }

  public void removePiece(Piece piece, int x, int y) {
    for (int i = 0; i < piece.height; i++) {
      for (int j = 0; j < piece.width; j++) {
        if (piece.shape[i][j] != '$') {
          board[x + i][y + j] = '$';
        }
      }
    }
  }

  public boolean canPlace(Piece piece, int x, int y) {
    for (int i = 0; i < piece.height; i++) {
      for (int j = 0; j < piece.width; j++) {
        int nx = x + i, ny = y + j;
        if (piece.shape[i][j] != '$'
            && (nx < 0 || nx >= rows || ny < 0 || ny >= cols || board[nx][ny] != '$')) {
          return false;
        }
      }
    }
    return true;
  }

  public void printBoard() {
    for (char[] row : board) {
      for (char cell : row) {
        if (colorMap.containsKey(cell)) {
          System.out.print(colorMap.get(cell) + cell + "\u001B[0m ");
        } else {
          System.out.print(cell + " ");
        }
      }
      System.out.println();
    }
  }
}
