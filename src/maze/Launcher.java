package maze;

import java.util.List;

public class Launcher {

    public static void main(String[] args) {
        Maze maze = MazeBuilder.generateMaze();
        List<Block> fastestSolution = MazeSolver.solveMaze(maze);
        new Display(maze, fastestSolution);
    }

}