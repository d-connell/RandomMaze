package maze;

public class Maze {

    private final int blocksWide;
    private final int blocksHigh;
    private final Block[][] blocks;
    private Block entryPoint;
    private Block exitPoint;

    public Maze(int blocksWide, int blocksHigh) {
        this.blocksWide = blocksWide;
        this.blocksHigh = blocksHigh;
        this.blocks = new Block[blocksHigh][blocksWide];

        createBlocks();
    }

    private void createBlocks() {
        for (int verticalPosition = 0; verticalPosition < blocksHigh; ++verticalPosition) {
            for (int horizontalPosition = 0; horizontalPosition < blocksWide; ++horizontalPosition) {
                blocks[verticalPosition][horizontalPosition] = new Block(verticalPosition, horizontalPosition);
            }
        }
    }

    public int getBlocksWide() {
        return blocksWide;
    }

    public int getBlocksHigh() {
        return blocksHigh;
    }

    public Block[][] getBlocks() {
        return blocks;
    }

    public Block getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(Block entryPoint) {
        this.entryPoint = entryPoint;
    }

    public Block getExitPoint() {
        return exitPoint;
    }

    public void setExitPoint(Block exitPoint) {
        this.exitPoint = exitPoint;
    }

}