import java.util.List;

public class Solver {
  public Board board;
  public List<Piece> pieces;
  public boolean isSolved;
  public int totalCaseChecked;
<<<<<<< HEAD

  public Solver(Board board, List<Piece> pieces) {
    this.board = board;
    this.pieces = pieces;
    this.isSolved = false;
    this.totalCaseChecked = 0;
=======
  
  public Solver(Board board, List<Piece> pieces) {
      this.board = board;
      this.pieces = pieces;
      this.isSolved = false;
      this.totalCaseChecked = 0;
>>>>>>> 11b3cc15d4e42019d338e74eb1f9f4c36eba5b34
  }

  public boolean isBoardFull() {
    for (int i = 0; i < board.rows; i++) {
<<<<<<< HEAD
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
=======
        for (int j = 0; j < board.cols; j++) {
            if (board.board[i][j] == '.') {
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
  if (index == pieces.size()) {
>>>>>>> 11b3cc15d4e42019d338e74eb1f9f4c36eba5b34
      System.out.println("\nFinal Solution:");
      board.printBoard();
      isSolved = true;
      return true;
<<<<<<< HEAD
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
=======
  }
  
  Piece piece = pieces.get(index);
  for (int x = 0; x < board.rows; x++) {
    for (int y = 0; y < board.cols; y++) {
        for (Piece p : piece.getAllOrientations()) {
              totalCaseChecked++;
              if (board.placePiece(p, x, y)) {
                  System.out.println("\nTrying piece " + piece.shape[0][0] + " at position (" + x + "," + y + ")");
                  System.out.println("Case #" + totalCaseChecked + ":");
                  board.printBoard();
                  
                  if (solve(index + 1)) return true;
                  
                  System.out.println("\nBacktracking: removing piece " + piece.shape[0][0] + " from (" + x + "," + y + ")");
                  board.removePiece(p, x, y);
                  board.printBoard();
              }
          }
      }
  }
  return false;
}
>>>>>>> 11b3cc15d4e42019d338e74eb1f9f4c36eba5b34
}
