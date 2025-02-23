import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.io.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Queue;
import java.util.Set;
import java.util.LinkedList;

public class FileIO {
  public int N, M, P;
  public List<Piece> pieces;
  public char[][] shape;
  public String caseType;
  public Board board;

  public void readInputFile(String filename) throws IOException {
    BufferedReader br = new BufferedReader(new java.io.FileReader(filename));
    String line = br.readLine();

    if (line == null) {
      br.close();
      throw new IOException("The file is blank!");
    }
    StringTokenizer st = new StringTokenizer(line);
    if (st.countTokens() < 3) {
      br.close();
      throw new IOException("Missing board dimensions or number of pieces!");
    }
    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    P = Integer.parseInt(st.nextToken());
    line = br.readLine();
    if (line == null || line.trim().isEmpty()) {
      br.close();
      throw new IOException("Missing case type!");
    }
    caseType = line.trim();
    if (caseType.equals("CUSTOM")) {
      board = new Board(N, M);
      for (int i = 0; i < N; i++) {
        line = br.readLine();
        if (line == null || line.length() != M) {
          br.close();
          throw new IOException("Invalid board configuration!");
        }
        for (int j = 0; j < M; j++) {
          board.board[i][j] = line.charAt(j);
        }
      }
      modifyBoard();
    } else {
      board = new Board(N, M);
    }

    pieces = new ArrayList<>();
    List<String> shape = new ArrayList<>();
    Set<Character> usedCharacters = new HashSet<>();

    line = br.readLine();
    if (line == null) {
      br.close();
      throw new IOException("No pieces found in the file!");
    }

    char currentPiece = findFirstLetter(line);
    usedCharacters.add(currentPiece);

    while (line != null) {
      char firstLetter = findFirstLetter(line);
      if (firstLetter != currentPiece) {
        if (!shape.isEmpty()) {
          char[][] pieceArray = stringListtoCharArray(shape);
          if (!isPieceConnected(pieceArray)) {
            br.close();
            throw new IOException("Piece is not connected!");
          }
          pieces.add(new Piece(pieceArray));
        }
        if (usedCharacters.contains(firstLetter)) {
          br.close();
          throw new IOException("Piece character '" + firstLetter + "' is used more than once!");
        }
        shape = new ArrayList<>();
        currentPiece = firstLetter;
        usedCharacters.add(currentPiece);
      }
      shape.add(line);
      line = br.readLine();
    }
    if (!shape.isEmpty()) {
      char[][] pieceArray = stringListtoCharArray(shape);
      if (!isPieceConnected(pieceArray)) {
        br.close();
        throw new IOException("Piece is not connected!");
      }
      pieces.add(new Piece(pieceArray));
    }
    br.close();
    if (pieces.size() != P) {
      showDifferentTotalPiecesWarning();
      throw new IOException("The number of pieces doesn't match!");
    }
  }

  private void modifyBoard() {
    for (int i = 0; i < board.rows; i++) {
      for (int j = 0; j < board.cols; j++) {
        if (board.board[i][j] == 'X') {
          board.board[i][j] = '$';
        }
      }
    }
  }

  private boolean isPieceConnected(char[][] piece) {
    int rows = piece.length;
    int cols = piece[0].length;
    boolean[][] visited = new boolean[rows][cols];
    int[] rowDirections = { -1, 1, 0, 0, -1, -1, 1, 1 };
    int[] colDirections = { 0, 0, -1, 1, -1, 1, -1, 1 };

    Queue<int[]> queue = new LinkedList<>();
    boolean foundFirst = false;

    for (int i = 0; i < rows && !foundFirst; i++) {
      for (int j = 0; j < cols && !foundFirst; j++) {
        if (piece[i][j] != '$') {
          queue.add(new int[] { i, j });
          visited[i][j] = true;
          foundFirst = true;
        }
      }
    }

    if (!foundFirst)
      return false;

    int count = 0;
    while (!queue.isEmpty()) {
      int[] current = queue.poll();
      int currentRow = current[0];
      int currentCol = current[1];
      count++;

      for (int k = 0; k < 8; k++) {
        int newRow = currentRow + rowDirections[k];
        int newCol = currentCol + colDirections[k];

        if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && !visited[newRow][newCol]
            && piece[newRow][newCol] != '$') {
          queue.add(new int[] { newRow, newCol });
          visited[newRow][newCol] = true;
        }
      }
    }

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (piece[i][j] != '$' && !visited[i][j]) {
          return false;
        }
      }
    }

    return true;
  }

  private void showDifferentTotalPiecesWarning() {
    JOptionPane.showMessageDialog(null, "The number of pieces doesn't match!", "Warning!!!",
        JOptionPane.WARNING_MESSAGE);
  }

  private char findFirstLetter(String line) {
    for (char c : line.toCharArray()) {
      if (Character.isLetter(c)) {
        return c;
      }
    }
    return '$';
  }

  private char[][] stringListtoCharArray(List<String> shapeLines) {
    int maxChars = 0;
    for (String line : shapeLines) {
      maxChars = Math.max(maxChars, line.length());
    }

    this.shape = new char[shapeLines.size()][maxChars];

    for (int i = 0; i < shapeLines.size(); i++) {
      String line = shapeLines.get(i);
      for (int j = 0; j < maxChars; j++) {
        if (j < line.length()) {
          char currentChar = line.charAt(j);
          this.shape[i][j] = (currentChar == ' ') ? '$' : currentChar;
        } else {
          this.shape[i][j] = '$';
        }
      }
    }

    return this.shape;
  }

  public void writeBoardToFile(String folderName, String filename) throws IOException {
    File folder = new File(folderName);
    if (!folder.exists()) {
      folder.mkdirs();
    }
    BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(new File(folder, filename)));
    for (char[] row : board.board) {
      for (char cell : row) {
        writer.write(cell);
      }
      writer.newLine();
    }
    writer.close();
  }

  public void createBoardImage(String folderName, String filename) throws IOException {
    File folder = new File(folderName);
    if (!folder.exists()) {
      folder.mkdirs();
    }
    int cellSize = 200; // size of each cell in the image
    int width = board.cols * cellSize;
    int height = board.rows * cellSize;

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = image.createGraphics();

    // Fill background with white
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);

    // Draw the board
    for (int i = 0; i < board.rows; i++) {
      for (int j = 0; j < board.cols; j++) {
        char cell = board.board[i][j];
        if (cell != '$') {
          g.setColor(getColorForCell(cell));
          g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
          g.setColor(Color.BLACK);
          g.drawString(String.valueOf(cell), j * cellSize + cellSize / 2 - 5, i * cellSize + cellSize / 2 + 5);
        }
        g.setColor(Color.BLACK);
        g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
      }
    }

    g.dispose();
    ImageIO.write(image, "png", new File(folder, filename));
  }

  private Color getColorForCell(char cell) {
    switch (cell) {
      case 'A':
        return new Color(255, 0, 0);
      case 'B':
        return new Color(0, 128, 0);
      case 'C':
        return new Color(255, 255, 0);
      case 'D':
        return new Color(0, 0, 255);
      case 'E':
        return new Color(255, 0, 255);
      case 'F':
        return new Color(0, 255, 255);
      case 'G':
        return new Color(255, 192, 203);
      case 'H':
        return new Color(255, 165, 0);
      case 'I':
        return new Color(211, 211, 211);
      case 'J':
        return new Color(128, 128, 128);
      case 'K':
        return new Color(169, 169, 169);
      case 'L':
        return new Color(0, 0, 0);
      case 'M':
        return new Color(139, 0, 0);
      case 'N':
        return new Color(0, 100, 0);
      case 'O':
        return new Color(139, 139, 0);
      case 'P':
        return new Color(0, 0, 139);
      case 'Q':
        return new Color(139, 0, 139);
      case 'R':
        return new Color(0, 139, 139);
      case 'S':
        return new Color(255, 105, 180);
      case 'T':
        return new Color(255, 140, 0);
      case 'U':
        return new Color(169, 169, 169);
      case 'V':
        return new Color(105, 105, 105);
      case 'W':
        return new Color(47, 79, 79);
      case 'X':
        return new Color(0, 0, 0);
      case 'Y':
        return new Color(255, 69, 0);
      case 'Z':
        return new Color(0, 255, 0);
      default:
        return new Color(0, 0, 0);
    }
  }
}