package maze;

import java.util.ArrayList;
import java.util.List;

public abstract class MazeBuilder {

    private static Maze maze;
    private final static int blocksWide = 50;
    private final static int blocksHigh = 50;
    private static List<Block> exposedBlocks;
    private static List<Block> impassableBlocks;

    public static Maze generateMaze() {
        maze = new Maze(blocksWide, blocksHigh);
        createBoundary();
        createEntryPoint();
        initialiseExposedBlocks();
        initialiseImpassableBlocks();
        createMazeInterior();
        createExitPoint();
        return maze;
    }

    private static void createBoundary() {
        for (int horizontalPosition = 0; horizontalPosition < blocksWide; ++horizontalPosition) {
            maze.getBlocks()[0][horizontalPosition].setSurfaceType(Surface.IMPASSABLE);
        }
        for (int horizontalPosition = 0; horizontalPosition < blocksWide; ++horizontalPosition) {
            maze.getBlocks()[blocksHigh - 1][horizontalPosition].setSurfaceType(Surface.IMPASSABLE);
        }
        for (int verticalPosition = 0; verticalPosition < blocksHigh; ++verticalPosition) {
            maze.getBlocks()[verticalPosition][0].setSurfaceType(Surface.IMPASSABLE);
        }
        for (int verticalPosition = 0; verticalPosition < blocksHigh; ++verticalPosition) {
            maze.getBlocks()[verticalPosition][blocksWide - 1].setSurfaceType(Surface.IMPASSABLE);
        }
    }

    private static void createEntryPoint() {
        // Subtract three from blocksHigh to ensure the entry is not in a corner block.
        Block entryPoint = maze.getBlocks()[(int) (Math.random() * (blocksHigh - 3) + 1)][0];
        entryPoint.setSurfaceType(Surface.PASSABLE);
        maze.setEntryPoint(entryPoint);
    }

    private static void initialiseExposedBlocks() {
        exposedBlocks = new ArrayList<>();
        exposedBlocks.add(maze.getBlocks()[maze.getEntryPoint().getVerticalPosition()][maze.getEntryPoint().getHorizontalPosition() + 1]);
    }

    private static void initialiseImpassableBlocks() {
        impassableBlocks = new ArrayList<>();
    }

    private static void createMazeInterior() {
        createPath();
        setRemainingBlocksAsImpassable();
        createLoops();
    }

    private static void createPath() {
        while (exposedBlocks.size() > 0) {
            Block currentBlock = exposedBlocks.get((int) (Math.random() * (exposedBlocks.size() - 1)));
            if (countAdjacentPassableBlocks(currentBlock) == 1) {
                currentBlock.setSurfaceType(Surface.PASSABLE);
                addAdjacentBlocksToExposedBlocks(currentBlock);
            } else {
                currentBlock.setSurfaceType(Surface.IMPASSABLE);
                impassableBlocks.add(currentBlock);
            }
            exposedBlocks.remove(currentBlock);
        }
    }

    private static void addAdjacentBlocksToExposedBlocks(Block currentBlock) {
        addToExposedBlocks(maze.getBlocks()[currentBlock.getVerticalPosition() - 1][currentBlock.getHorizontalPosition()]);
        addToExposedBlocks(maze.getBlocks()[currentBlock.getVerticalPosition()][currentBlock.getHorizontalPosition() + 1]);
        addToExposedBlocks(maze.getBlocks()[currentBlock.getVerticalPosition() + 1][currentBlock.getHorizontalPosition()]);
        addToExposedBlocks(maze.getBlocks()[currentBlock.getVerticalPosition()][currentBlock.getHorizontalPosition() - 1]);
    }

    private static void addToExposedBlocks(Block block) {
        if (block.getSurfaceType().equals(Surface.UNDEFINED) && !exposedBlocks.contains(block)) {
            exposedBlocks.add(block);
        }
    }

    private static int countAdjacentPassableBlocks(Block currentBlock) {
        int numberOfAdjacentFloorBlocks = 0;
        if (maze.getBlocks()[currentBlock.getVerticalPosition() - 1][currentBlock.getHorizontalPosition()].getSurfaceType().equals(Surface.PASSABLE)) {
            numberOfAdjacentFloorBlocks++;
        }
        if (maze.getBlocks()[currentBlock.getVerticalPosition()][currentBlock.getHorizontalPosition() + 1].getSurfaceType().equals(Surface.PASSABLE)) {
            numberOfAdjacentFloorBlocks++;
        }
        if (maze.getBlocks()[currentBlock.getVerticalPosition() + 1][currentBlock.getHorizontalPosition()].getSurfaceType().equals(Surface.PASSABLE)) {
            numberOfAdjacentFloorBlocks++;
        }
        if (maze.getBlocks()[currentBlock.getVerticalPosition()][currentBlock.getHorizontalPosition() - 1].getSurfaceType().equals(Surface.PASSABLE)) {
            numberOfAdjacentFloorBlocks++;
        }
        return numberOfAdjacentFloorBlocks;
    }

    private static void setRemainingBlocksAsImpassable() {
        for (int verticalPosition = 0; verticalPosition < blocksHigh; verticalPosition++) {
            for (int horizontalPosition = 0; horizontalPosition < blocksWide; horizontalPosition++) {
                if (maze.getBlocks()[verticalPosition][horizontalPosition].getSurfaceType().equals(Surface.UNDEFINED)) {
                    maze.getBlocks()[verticalPosition][horizontalPosition].setSurfaceType(Surface.IMPASSABLE);
                    impassableBlocks.add(maze.getBlocks()[verticalPosition][horizontalPosition]);
                }
            }
        }
    }

    private static void createLoops() {
        // blocksToOpen is limited rather than random, to avoid generating mazes which will take a very long time to solve.
        int blocksToOpen = 10;
        int blocksOpened = 0;
        while (blocksOpened < blocksToOpen && impassableBlocks.size() > 0) {
            Block activeBlock = impassableBlocks.get((int) (Math.random() * (exposedBlocks.size() - 1)));
            if (isOpenable(activeBlock)) {
                activeBlock.setSurfaceType(Surface.PASSABLE);
                blocksOpened++;
            }
            impassableBlocks.remove(activeBlock);
        }
    }

    private static boolean isOpenable(Block block) {
        if (block.getSurfaceType().equals(Surface.PASSABLE) ||countAdjacentPassableBlocks(block) == 0) {
            return false;
        }
        if (maze.getBlocks()[block.getVerticalPosition() - 1][block.getHorizontalPosition() - 1].getSurfaceType().equals(Surface.PASSABLE)
                && maze.getBlocks()[block.getVerticalPosition()][block.getHorizontalPosition() - 1].getSurfaceType().equals(Surface.PASSABLE)
                && maze.getBlocks()[block.getVerticalPosition() - 1][block.getHorizontalPosition()].getSurfaceType().equals(Surface.PASSABLE)) {
            return false;
        }
        if (maze.getBlocks()[block.getVerticalPosition() - 1][block.getHorizontalPosition() + 1].getSurfaceType().equals(Surface.PASSABLE)
                && maze.getBlocks()[block.getVerticalPosition()][block.getHorizontalPosition() + 1].getSurfaceType().equals(Surface.PASSABLE)
                && maze.getBlocks()[block.getVerticalPosition() - 1][block.getHorizontalPosition()].getSurfaceType().equals(Surface.PASSABLE)) {
            return false;
        }
        if (maze.getBlocks()[block.getVerticalPosition() + 1][block.getHorizontalPosition() - 1].getSurfaceType().equals(Surface.PASSABLE)
                && maze.getBlocks()[block.getVerticalPosition()][block.getHorizontalPosition() - 1].getSurfaceType().equals(Surface.PASSABLE)
                && maze.getBlocks()[block.getVerticalPosition() + 1][block.getHorizontalPosition()].getSurfaceType().equals(Surface.PASSABLE)) {
            return false;
        }
        if (maze.getBlocks()[block.getVerticalPosition() + 1][block.getHorizontalPosition() + 1].getSurfaceType().equals(Surface.PASSABLE)
                && maze.getBlocks()[block.getVerticalPosition()][block.getHorizontalPosition() + 1].getSurfaceType().equals(Surface.PASSABLE)
                && maze.getBlocks()[block.getVerticalPosition() + 1][block.getHorizontalPosition()].getSurfaceType().equals(Surface.PASSABLE)) {
            return false;
        }
        return true;
    }

    private static void createExitPoint() {
        while (maze.getExitPoint() == null) {
            // Subtract three from blocksHigh to ensure the exit is not in a corner block.
            Block exitPoint = maze.getBlocks()[(int) (Math.random() * (blocksHigh - 3)) + 1][blocksWide - 1];
            if (maze.getBlocks()[exitPoint.getVerticalPosition()][exitPoint.getHorizontalPosition() - 1].getSurfaceType().equals(Surface.PASSABLE)) {
                exitPoint.setSurfaceType(Surface.PASSABLE);
                maze.setExitPoint(exitPoint);
            }
        }
    }

}