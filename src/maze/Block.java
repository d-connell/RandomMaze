package maze;

import java.util.Objects;

public class Block {

    private int horizontalPosition;
    private int verticalPosition;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return horizontalPosition == block.horizontalPosition &&
                verticalPosition == block.verticalPosition &&
                surfaceType == block.surfaceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(horizontalPosition, verticalPosition, surfaceType);
    }
}