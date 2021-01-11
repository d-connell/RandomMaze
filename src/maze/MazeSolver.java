package maze;

import java.util.List;

public abstract class MazeSolver {

    public static List<Block> solveMaze(Maze maze) {
        Mouse mouse = new Mouse(maze);
        mouse.findExit();
        return mouse.getPath();
    }

}