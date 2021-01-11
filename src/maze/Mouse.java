package maze;

import java.util.ArrayList;
import java.util.List;

public class Mouse {

    private Maze maze;
    private int horizontalPosition;
    private int verticalPosition;
    private List<Block> path;

    public Mouse(Maze maze) {
        this.maze = maze;
        this.horizontalPosition = maze.getEntryPoint().getHorizontalPosition();
        this.verticalPosition = maze.getEntryPoint().getVerticalPosition();
        this.path = new ArrayList<>();
        path.add(maze.getEntryPoint());
    }

    public void findExit() {
        findExit(Direction.RIGHT, 1, 0);
    }

    private void findExit(Direction currentlyFacing, int horizontalMove, int verticalMove) {
        if (!maze.getBlocks()[verticalPosition][horizontalPosition].equals(maze.getExitPoint())) {
            updatePath(horizontalMove, verticalMove);
            updatePosition(horizontalMove, verticalMove);
            lookForNextMove(currentlyFacing);
        }
        path.add(maze.getExitPoint());
    }

    private void updatePath(int horizontalMove, int verticalMove) {
        if (path.contains(maze.getBlocks()[verticalPosition + verticalMove][horizontalPosition + horizontalMove])) {
            path.subList(path.indexOf(maze.getBlocks()[verticalPosition + verticalMove][horizontalPosition + horizontalMove]), path.size()).clear();
        }
        path.add(maze.getBlocks()[verticalPosition + verticalMove][horizontalPosition + horizontalMove]);
    }

    private void updatePosition(int horizontalMove, int verticalMove) {
        horizontalPosition += horizontalMove;
        verticalPosition += verticalMove;
    }

    private void lookForNextMove(Direction currentlyFacing) {
        switch (currentlyFacing) {
            case RIGHT: {
                tryToMoveUp();
                break;
            }
            case DOWN: {
                tryToMoveRight();
                break;
            }
            case LEFT: {
                tryToMoveDown();
                break;
            }
            case UP: {
                tryToMoveLeft();
                break;
            }
        }
    }

    private void tryToMoveRight() {
        if (horizontalPosition + 1 < maze.getBlocksWide()
                && maze.getBlocks()[verticalPosition][horizontalPosition + 1].getSurfaceType().equals(Surface.PASSABLE)) {
            findExit(Direction.RIGHT, 1, 0);
        } else {
            tryToMoveDown();
        }
    }

    private void tryToMoveDown() {
        if (verticalPosition + 1 < maze.getBlocksHigh()
                && maze.getBlocks()[verticalPosition + 1][horizontalPosition].getSurfaceType().equals(Surface.PASSABLE)) {
            findExit(Direction.DOWN, 0, 1);
        } else {
            tryToMoveLeft();
        }
    }

    private void tryToMoveLeft() {
        if (horizontalPosition - 1 >= 0
                && maze.getBlocks()[verticalPosition][horizontalPosition - 1].getSurfaceType().equals(Surface.PASSABLE)) {
            findExit(Direction.LEFT, -1, 0);
        } else {
            tryToMoveUp();
        }
    }

    private void tryToMoveUp() {
        if (verticalPosition - 1 >= 0
                && maze.getBlocks()[verticalPosition - 1][horizontalPosition].getSurfaceType().equals(Surface.PASSABLE)) {
            findExit(Direction.UP, 0, -1);
        } else {
            tryToMoveRight();
        }
    }

    public List<Block> getPath() {
        return path;
    }
}