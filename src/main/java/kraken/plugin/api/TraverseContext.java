package kraken.plugin.api;

public class TraverseContext {

    private int skipThreshold;
    private Vector3i lastTile;
    private Vector3i lastWalk;
    private boolean finished;

    public int getSkipThreshold() {
        return skipThreshold;
    }

    public void setSkipThreshold(int skipThreshold) {
        this.skipThreshold = skipThreshold;
    }

    public Vector3i getLastTile() {
        return lastTile;
    }

    public void setLastTile(Vector3i lastTile) {
        this.lastTile = lastTile;
    }

    public Vector3i getLastWalk() {
        return lastWalk;
    }

    public void setLastWalk(Vector3i lastWalk) {
        this.lastWalk = lastWalk;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
