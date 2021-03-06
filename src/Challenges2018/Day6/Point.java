package Challenges2018.Day6;

/**
 * Class that represents a Point on the "infinite" grid.
 * @Author Afaq Anwar
 * @Version 10/20/2019
 */
public class Point {
    private int xPoint;
    private int yPoint;
    private boolean associated;
    private Point associatedPoint;
    private int distanceFromAssociatedPoint;

    public Point(int xPoint, int yPoint) {
        this.xPoint = xPoint;
        this.yPoint = yPoint;
        this.associated = false;
    }

    public int getPointX() { return xPoint; }
    public int getPointY() { return yPoint; }

    public boolean isAssociated() { return associated; }
    public void setAssociated(boolean associated) { this.associated = associated; }

    public Point getAssociatedPoint() { return this.associatedPoint; }
    public void setAssociatedPoint(Point associatedPoint) { this.associatedPoint = associatedPoint; }

    public int getDistanceFromAssociatedPoint() { return distanceFromAssociatedPoint; }
    public void setDistanceFromAssociatedPoint(int distanceFromAssociatedPoint) { this.distanceFromAssociatedPoint = distanceFromAssociatedPoint; }

    @Override
    public String toString() {
        return this.xPoint + ", " + this.yPoint;
    }
}
