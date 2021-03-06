package Challenges2018.Day6;

import Utilities.InputReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Solutions to the Day Six Puzzles.
 * @Author Afaq Anwar
 * @Version 10/24/2019
 */
public class Solution {
    static Point[][] grid;
    static ArrayList<Point> allPoints;

    public static String puzzleOne(ArrayList<String> input) {
        allPoints = new ArrayList<>();
        for (String line : input) {
            String[] splitLocations = line.split(",");
            Point point = new Point(Integer.parseInt(splitLocations[0].trim()), Integer.parseInt(splitLocations[1].trim()));
            point.setAssociated(true);
            point.setAssociatedPoint(point);
            point.setDistanceFromAssociatedPoint(0);
            allPoints.add(point);
        }

        grid = generateGrid(allPoints);
        // Points that should not be checked. Initially just the given points.
        ArrayList<Point> redundantPoints = new ArrayList<>(allPoints);

        for (Point[] pointRow : grid) {
            for (Point currentPoint : pointRow) {
                boolean isRedundant = false;
                for (Point givenPoint : allPoints) {
                    if (!redundantPoints.contains(currentPoint)) {
                        int distance = Math.abs(givenPoint.getPointX() - currentPoint.getPointX()) + Math.abs(givenPoint.getPointY() - currentPoint.getPointY());
                        if (!currentPoint.isAssociated()) {
                            currentPoint.setAssociated(true);
                            currentPoint.setAssociatedPoint(givenPoint);
                            currentPoint.setDistanceFromAssociatedPoint(distance);
                        } else {
                            int prevDistToPrevPoint = currentPoint.getDistanceFromAssociatedPoint();
                            if (prevDistToPrevPoint > distance) {
                                isRedundant = false;
                                currentPoint.setAssociatedPoint(givenPoint);
                                currentPoint.setDistanceFromAssociatedPoint(distance);
                            } else if (prevDistToPrevPoint == distance) {
                                isRedundant = true;
                            }
                        }
                    }
                }
                // Redundancy is acted upon after testing against all cases.
                if (isRedundant) {
                    currentPoint.setAssociated(false);
                    currentPoint.setAssociatedPoint(null);
                    currentPoint.setDistanceFromAssociatedPoint(0);
                    redundantPoints.add(currentPoint);
                }
            }
        }

        ArrayList<Point> infinitePoints = findInfinitePoints(grid);

        HashMap<Point, Integer> pointArea = calculateArea(grid, infinitePoints);

        return Integer.toString(findGreatestPointIntegerMapValue(pointArea));
    }

    /**
     * @param points ArrayList of Points.
     * @return 2D Array of Points that represents a finite map.
     */
    public static Point[][] generateGrid(ArrayList<Point> points) {
        int horizontalBound = 0;
        int verticalBound = 0;
        for (Point currPoint : points) {
            if (currPoint.getPointX() > horizontalBound) {
                horizontalBound = currPoint.getPointX();
            }
            if (currPoint.getPointY() > verticalBound) {
                verticalBound = currPoint.getPointY();
            }
        }

        Point[][] grid;
        if (horizontalBound > verticalBound) {
            grid = new Point[horizontalBound + 1][horizontalBound + 1];
        } else {
            grid = new Point[verticalBound + 1][verticalBound + 1];
        }

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                grid[y][x] = new Point(x, y);
            }
        }

        return grid;
    }

    /**
     * @param grid 2D Array of Points.
     * @param infinitePoints ArrayList that contains all the infinite points that shouldn't be checked.
     * @return HashMap with all finite point areas.
     */
    public static HashMap<Point, Integer> calculateArea (Point[][] grid, ArrayList<Point> infinitePoints) {
        HashMap<Point, Integer> pointArea = new HashMap<>();
        for (Point[] row : grid) {
            for (Point point : row) {
                Point associatedPoint = point.getAssociatedPoint();
                if (!infinitePoints.contains(associatedPoint) && point.isAssociated()) {
                    if (pointArea.containsKey(associatedPoint)) {
                        pointArea.replace(associatedPoint, pointArea.get(associatedPoint) + 1);
                    } else {
                        pointArea.put(associatedPoint, 1);
                    }
                }
            }
        }
        return pointArea;
    }

    /**
     * @param pointMap 2D Array of Points.
     * @return ArrayList of all Points that are considered infinite.
     * Infinite points are all Points that are, or have associated points, on the border of the grid.
     */
    public static ArrayList<Point> findInfinitePoints (Point[][] pointMap) {
        ArrayList<Point> infinitePoints = new ArrayList<>();
        for (int x = 0; x < pointMap.length; x++) {
            if (x == 0 || x == pointMap.length - 1) {
                for (int y = 1; y < pointMap[x].length; y++) {
                    if (pointMap[x][y].isAssociated()) {
                        if (!infinitePoints.contains(pointMap[x][y].getAssociatedPoint())) {
                            infinitePoints.add(pointMap[x][y].getAssociatedPoint());
                        }
                    }
                }
            }
            if (pointMap[x][0].isAssociated() && pointMap[x][pointMap[x].length - 1].isAssociated()) {
                if (!infinitePoints.contains(pointMap[x][0].getAssociatedPoint())) {
                    infinitePoints.add(pointMap[x][0].getAssociatedPoint());
                }
                if (!infinitePoints.contains(pointMap[x][pointMap[x].length - 1].getAssociatedPoint())) {
                    infinitePoints.add(pointMap[x][pointMap[x].length - 1].getAssociatedPoint());
                }
            }
        }
        return infinitePoints;
    }

    /**
     * @param map HashMap of Points and their respective areas.
     * @return Integer value of largest area.
     */
    public static int findGreatestPointIntegerMapValue (HashMap<Point, Integer> map) {
        int value = 0;
        for (int i : map.values()) {
            if (i > value) {
                value = i;
            }
        }
        return value;
    }

    public static String puzzleTwo(ArrayList<String> input) {
        ArrayList<Point> safePoints = new ArrayList<>();
        for (Point[] pointRow : grid) {
            for (Point point : pointRow) {
                int totalDistance = 0;
                for (Point givenPoint : allPoints) {
                    totalDistance += Math.abs(givenPoint.getPointX() - point.getPointX()) + Math.abs(givenPoint.getPointY() - point.getPointY());
                }
                if (totalDistance <= 10000) {
                    safePoints.add(point);
                }
            }
        }
        return Integer.toString(safePoints.size());
    }

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(new File("src/Challenges2018/Day6/input.txt"));
        System.out.println("First Puzzle Answer - " + puzzleOne(inputReader.getInput()));
        System.out.println("Second Puzzle Answer - " + puzzleTwo(inputReader.getInput()));
    }
}
