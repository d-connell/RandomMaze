package maze;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Display extends Canvas {

    private int blockSize = 10;
    private Maze maze;
    private List<Block> solution;
    private int width;
    private int height;

    public Display(Maze maze, List<Block> solution) {
        this.maze = maze;
        this.solution = solution;
        this.width = maze.getBlocksWide() * blockSize;
        this.height = maze.getBlocksHigh() * blockSize;

        createJFrame();
    }

    private void createJFrame() {
        JFrame frame = new JFrame("Random Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MyPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private class MyPanel extends JPanel {

        public MyPanel() {
        }

        public Dimension getPreferredSize() {
            return new Dimension(width, height);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            displayMaze(g);
            displaySolution(g);
        }

        private void displayMaze(Graphics graphics) {
            for (int verticalPosition = 0; verticalPosition < maze.getBlocksHigh(); ++verticalPosition) {
                for (int horizontalPosition = 0; horizontalPosition < maze.getBlocksWide(); ++horizontalPosition) {
                    if (maze.getBlocks()[verticalPosition][horizontalPosition].getSurfaceType().equals(Surface.PASSABLE)) {
                        graphics.setColor(Color.WHITE);
                    } else {
                        graphics.setColor(Color.DARK_GRAY);
                    }
                    graphics.fillRect(horizontalPosition * blockSize, verticalPosition * blockSize, blockSize, blockSize);
                }
            }
        }

        private void displaySolution(Graphics graphics) {
            graphics.setColor(new Color(12, 236, 240));
            for (Block block : solution) {
                graphics.fillOval(block.getHorizontalPosition() * blockSize, block.getVerticalPosition() * blockSize, blockSize, blockSize);
            }
        }
    }

}