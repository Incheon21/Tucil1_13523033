import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.awt.image.BufferedImage;

public class Main extends JFrame {
  private JLabel imageLabel;
  private JLabel timeLabel;
  private JLabel casesLabel;
  private FileIO fileIO;
  private Board board;
  private List<Piece> pieces;

  public Main() {
    setTitle("Sigma Boy Solver");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());

    JButton uploadButton = new JButton("Upload File");
    uploadButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(Main.this);
        if (result == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          try {
            processFile(selectedFile);
            showBoard();
          } catch (IOException ex) {
            JOptionPane.showMessageDialog(Main.this, "Error processing file: " + ex.getMessage(), "Error",
                JOptionPane.ERROR_MESSAGE);
          }
        }
      }
    });

    JButton solveButton = new JButton("Solve");
    solveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (board != null && pieces != null) {
          solvePuzzle();
        } else {
          JOptionPane.showMessageDialog(Main.this, "Please upload a file first.", "Error",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    JButton saveTxtButton = new JButton("Save Solution as .txt");
    saveTxtButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (board != null) {
          saveSolutionAsTxt();
        } else {
          JOptionPane.showMessageDialog(Main.this, "No solution to save.", "Error",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    JButton saveImageButton = new JButton("Save Solution as Image");
    saveImageButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (board != null) {
          saveSolutionAsImage();
        } else {
          JOptionPane.showMessageDialog(Main.this, "No solution to save.", "Error",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    buttonPanel.add(uploadButton);
    buttonPanel.add(solveButton);
    buttonPanel.add(saveTxtButton);
    buttonPanel.add(saveImageButton);

    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(JLabel.CENTER);

    timeLabel = new JLabel("Time: 0 ms");
    casesLabel = new JLabel("Cases Tried: 0");

    JPanel statusPanel = new JPanel();
    statusPanel.setLayout(new FlowLayout());
    statusPanel.add(timeLabel);
    statusPanel.add(casesLabel);

    add(buttonPanel, BorderLayout.SOUTH);
    add(new JScrollPane(imageLabel), BorderLayout.CENTER);
    add(statusPanel, BorderLayout.NORTH);
  }

  private void processFile(File file) throws IOException {
    imageLabel.setIcon(null);
    System.gc();

    fileIO = new FileIO();
    fileIO.readInputFile(file.getAbsolutePath());

    board = fileIO.board;
    pieces = fileIO.pieces;
  }

  private void showBoard() throws IOException {
    String tempFolderName = "temp";
    String tempFileName = "board.png";

    File tempFile = new File(tempFolderName + "/" + tempFileName);
    if (tempFile.exists()) {
      tempFile.delete();
    }

    fileIO.createBoardImage(tempFolderName, tempFileName);

    BufferedImage bufferedImage = ImageIO.read(tempFile);
    Image scaledImage = bufferedImage.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(),
        Image.SCALE_SMOOTH);
    ImageIcon imageIcon = new ImageIcon(scaledImage);

    imageLabel.setIcon(imageIcon);
    imageLabel.revalidate();
    imageLabel.repaint();
  }

  private void solvePuzzle() {

    long startTime = System.currentTimeMillis();

    Solver solver = new Solver(board, pieces);
    boolean solved = solver.solve(0);

    long endTime = System.currentTimeMillis();

    // Print results
    if (!solved) {
      JOptionPane.showMessageDialog(this, "No solution found.", "Result", JOptionPane.INFORMATION_MESSAGE);
    } else {
      try {
        String baseName = "solution";
        String outputFolderName = "test/output/" + baseName;

        new File(outputFolderName).mkdirs();

        File solutionTxt = new File(outputFolderName + "/solution.txt");
        File solutionPng = new File(outputFolderName + "/solution.png");
        if (solutionTxt.exists())
          solutionTxt.delete();
        if (solutionPng.exists())
          solutionPng.delete();

        fileIO.writeBoardToFile(outputFolderName, "solution.txt");
        fileIO.createBoardImage(outputFolderName, "solution.png");

        imageLabel.setIcon(null);
        System.gc();

        BufferedImage bufferedImage = ImageIO.read(solutionPng);
        Image scaledImage = bufferedImage.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(),
            Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);

        imageLabel.setIcon(imageIcon);
        imageLabel.revalidate();
        imageLabel.repaint();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    timeLabel.setText("Time: " + (endTime - startTime) + " ms");
    casesLabel.setText("Cases Tried: " + solver.totalCaseChecked);
  }

  private void saveSolutionAsTxt() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Solution as .txt");
    int result = fileChooser.showSaveDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      try {
        fileIO.writeBoardToFile(file.getParent(), file.getName() + ".txt");
        JOptionPane.showMessageDialog(this, "Solution saved successfully as .txt.", "Success",
            JOptionPane.INFORMATION_MESSAGE);
      } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error saving solution: " + e.getMessage(), "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void saveSolutionAsImage() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Solution as Image");
    int result = fileChooser.showSaveDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      try {
        fileIO.createBoardImage(file.getParent(), file.getName() + ".png");
        JOptionPane.showMessageDialog(this, "Solution saved successfully as image.", "Success",
            JOptionPane.INFORMATION_MESSAGE);
      } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error saving solution: " + e.getMessage(), "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new Main().setVisible(true);
      }
    });
  }
}