package maze;

public class Block {

    private final int horizontalPosition;
    private final int verticalPosition;
    private Surface surfaceType;

    public Block(int verticalPosition, int horizontalPosition) {
        this.horizontalPosition = horizontalPosition;
        this.verticalPosition = verticalPosition;
        this.surfaceType = Surface.UNDEFINED;
    }

    public int getHorizontalPosition() {
        return horizontalPosition;
    }

    public int getVerticalPosition() {
        return verticalPosition;
    }

    public Surface getSurfaceType() {
        return surfaceType;
    }

    public void setSurfaceType(Surface surfaceType) {
        this.surfaceType = surfaceType;
    }

}