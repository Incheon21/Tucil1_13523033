

public class Board {
  public int rows, cols;
  public char[][] board;

  public Board(int rows, int cols) {
      this.rows = rows;
      this.cols = cols;
      this.board = new char[rows][cols];
      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            board[i][j] = '.';
        }
    }
  }

  public boolean placePiece(Piece piece, int x, int y) {
    if (!canPlace(piece, x, y)) return false;
    for (int i = 0; i < piece.height; i++) {
        for (int j = 0; j < piece.width; j++) {
            if (piece.shape[i][j] != '.') {
                board[x + i][y + j] = piece.shape[i][j];
            }
        }
    }
    return true;
}

  public void removePiece(Piece piece, int x, int y) {
      for (int i = 0; i < piece.height; i++) {
          for (int j = 0; j < piece.width; j++) {
              if (piece.shape[i][j] != '.') {
                  board[x + i][y + j] = '.';
              }
          }
      }
  }

  public boolean canPlace(Piece piece, int x, int y) {
      for (int i = 0; i < piece.height; i++) {
          for (int j = 0; j < piece.width; j++) {
              int nx = x + i, ny = y + j;
              if (piece.shape[i][j] != '.' && (nx < 0 || nx >= rows || ny < 0 || ny >= cols || board[nx][ny] != '.')) {
                  return false;
              }
          }
      }
      return true;
  }

  public void printBoard() {
      for (char[] row : board) {
          for (char cell : row) System.out.print(cell + " ");
          System.out.println();
      }
  }
}
