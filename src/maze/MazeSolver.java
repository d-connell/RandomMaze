package maze;

import java.util.ArrayList;
import java.util.List;

public abstract class MazeSolver {

    private static final int DISTANCE_UNKNOWN = -2;
    private static final int IMPASSABLE = -1;

    private static Maze maze;
    private static int[][] distancesGrid;
    private static int currentDistanceFromEntry = 1;
    private static boolean solved = false;
    private static List<Block> fastestSolution;

    public static List<Block> getFastestSolution(Maze maze) {
        MazeSolver.maze = maze;
        initialiseDistancesGrid();
        findDistances();
        determineFastestSolution();
        return fastestSolution;
    }

    private static void initialiseDistancesGrid() {
        distancesGrid = new int[maze.getBlocksHigh()][maze.getBlocksHigh()];
        for (int verticalPosition = 0; verticalPosition < maze.getBlocksHigh(); verticalPosition++) {
            for (int horizontalPosition = 0; horizontalPosition < maze.getBlocksWide(); horizontalPosition++) {
                distancesGrid[verticalPosition][horizontalPosition] = DISTANCE_UNKNOWN;
            }
        }
        distancesGrid[maze.getEntryPoint().getVerticalPosition()][maze.getEntryPoint().getHorizontalPosition()] = 0;
        distancesGrid[maze.getEntryPoint().getVerticalPosition()][maze.getEntryPoint().getHorizontalPosition() + 1] = 1;
    }

    private static void findDistances() {
        ArrayList<Block> newList = new ArrayList<>();
        newList.add(maze.getBlocks()[maze.getEntryPoint().getVerticalPosition()][maze.getEntryPoint().getHorizontalPosition() + 1]);
        assessBlocksForDistanceFromEntry(newList);
    }

    private static void assessBlocksForDistanceFromEntry(ArrayList<Block> currentEndsOfPaths) {
        currentDistanceFromEntry++;
        ArrayList<Block> newEndsOfPaths = new ArrayList<>();
        for (Block block : currentEndsOfPaths) {
            checkBlock(maze.getBlocks()[block.getVerticalPosition() - 1][block.getHorizontalPosition()], newEndsOfPaths);
            checkBlock(maze.getBlocks()[block.getVerticalPosition()][block.getHorizontalPosition() + 1], newEndsOfPaths);
            checkBlock(maze.getBlocks()[block.getVerticalPosition() + 1][block.getHorizontalPosition()], newEndsOfPaths);
            checkBlock(maze.getBlocks()[block.getVerticalPosition()][block.getHorizontalPosition() - 1], newEndsOfPaths);
        }
        if (!solved) {
            assessBlocksForDistanceFromEntry(newEndsOfPaths);
        }
    }

    private static void checkBlock(Block block, ArrayList<Block> newEndsOfPaths) {
        if (distancesGrid[block.getVerticalPosition()][block.getHorizontalPosition()] != DISTANCE_UNKNOWN) {
            return;
        }
        if (block.getSurfaceType().equals(Surface.IMPASSABLE)) {
            distancesGrid[block.getVerticalPosition()][block.getHorizontalPosition()] = IMPASSABLE;
            return;
        }
        distancesGrid[block.getVerticalPosition()][block.getHorizontalPosition()] = currentDistanceFromEntry;
        newEndsOfPaths.add(block);
        if (block.equals(maze.getExitPoint())) {
            solved = true;
        }
    }

    private static void determineFastestSolution() {
        fastestSolution = new ArrayList<>();
        fastestSolution.add(maze.getExitPoint());
        currentDistanceFromEntry--;
        fastestSolution.add(maze.getBlocks()[maze.getExitPoint().getVerticalPosition()][maze.getExitPoint().getHorizontalPosition() - 1]);
        while (currentDistanceFromEntry > 0) {
            currentDistanceFromEntry--;
            getNextBlockInPath(fastestSolution.get(fastestSolution.size() - 1));
        }
    }

    private static void getNextBlockInPath(Block block) {
        if (distancesGrid[block.getVerticalPosition() - 1][block.getHorizontalPosition()] == currentDistanceFromEntry) {
            fastestSolution.add(maze.getBlocks()[block.getVerticalPosition() - 1][block.getHorizontalPosition()]);
            return;
        }
        if (distancesGrid[block.getVerticalPosition()][block.getHorizontalPosition() + 1] == currentDistanceFromEntry) {
            fastestSolution.add(maze.getBlocks()[block.getVerticalPosition()][block.getHorizontalPosition() + 1]);
            return;
        }
        if (distancesGrid[block.getVerticalPosition() + 1][block.getHorizontalPosition()] == currentDistanceFromEntry) {
            fastestSolution.add(maze.getBlocks()[block.getVerticalPosition() + 1][block.getHorizontalPosition()]);
            return;
        }
        if (distancesGrid[block.getVerticalPosition()][block.getHorizontalPosition() - 1] == currentDistanceFromEntry) {
            fastestSolution.add(maze.getBlocks()[block.getVerticalPosition()][block.getHorizontalPosition() - 1]);
        }
    }

}