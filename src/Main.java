import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // Create a 5x5 board
        Board board = new Board(5, 5);

        // Create list of pieces
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(new char[][]{{'A', 'A'}, {'A', '.'}}));
        pieces.add(new Piece(new char[][]{{'B', 'B'}, {'B', '.'}}));
        pieces.add(new Piece(new char[][]{{'C', 'C'}, {'C', '.'}}));
        pieces.add(new Piece(new char[][]{{'D', 'D'}, {'D', '.'}}));
        pieces.add(new Piece(new char[][]{{'E', 'E'}, {'E', 'E'}, {'E', '.'}}));
        pieces.add(new Piece(new char[][]{{'F', 'F'}, {'F', 'F'}, {'F', '.'}}));
        pieces.add(new Piece(new char[][]{{'G', 'G', 'G'}}));
        
        
        

        // Record start time
        long startTime = System.currentTimeMillis();
        
        // Create and run solver
        Solver solver = new Solver(board, pieces);
        boolean solved = solver.solve(0);
        
        // Record end time
        long endTime = System.currentTimeMillis();

        // Print results
        if (!solved) {
            System.out.println("Tidak ada solusi yang ditemukan.");
        }

        System.out.println("Waktu pencarian: " + (endTime - startTime) + " ms");
        System.out.println("Banyak kasus yang ditinjau: " + solver.totalCaseChecked);
    }
}