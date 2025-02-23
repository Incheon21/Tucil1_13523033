import java.util.List;

public class Solver {
  public Board board;
  public List<Piece> pieces;
  public boolean isSolved;
  public int totalCaseChecked;

  public Solver(Board board, List<Piece> pieces) {
    this.board = board;
    this.pieces = pieces;
    this.isSolved = false;
    this.totalCaseChecked = 0;
  }

  public boolean isBoardFull() {
    for (int i = 0; i < board.rows; i++) {
      for (int j = 0; j < board.cols; j++) {
        if (board.board[i][j] == '$') {
          return false;
        }
      }
    }
    return true;
  }

  public boolean solve(int index) {
    if (isBoardFull() && index < pieces.size()) {
      return false;
    }

    if (index == pieces.size() && !isBoardFull()) {
      return false;
    }

    if (index == pieces.size()) {
      System.out.println("\nFinal Solution:");
      board.printBoard();
      isSolved = true;
      return true;
    }

    Piece piece = pieces.get(index);
    for (int x = 0; x < board.rows; x++) {
      for (int y = 0; y < board.cols; y++) {
        for (Piece p : piece.getAllOrientations()) {
          totalCaseChecked++;
          if (board.placePiece(p, x, y)) {
            // System.out.println("Case #" + totalCaseChecked + ":");
            // board.printBoard();
            if (solve(index + 1))
              return true;
            // System.out.println("\nBacktracking: removing piece " + piece.shape[0][0] + "
            // from (" + x + "," + y + ")");
            board.removePiece(p, x, y);
            // board.printBoard();
          }
        }
      }
    }
    return false;
  }
}
