package com.andyaylward.maze;

import com.andyaylward.maze.core.FillablePoint;
import com.andyaylward.maze.core.Maze;
import com.andyaylward.maze.core.Point;
import com.andyaylward.maze.exceptions.NoPathFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MazeSolver {
  private MazeSolver() {
  }

  public static int shortestPath(Maze maze, Point start, Point end) {
    int distanceTravelled = 0;
    Set<Point> visitedPoints = new HashSet<>();
    Set<Point> currentPossibilities = new HashSet<>();
    currentPossibilities.add(start);

    while (!currentPossibilities.isEmpty()) {
      if (currentPossibilities.contains(end)) {
        return distanceTravelled;
      }

      visitedPoints.addAll(currentPossibilities);

      currentPossibilities = generateNewPossibilities(maze.getRows(), visitedPoints, currentPossibilities);
      distanceTravelled++;
    }

    throw new NoPathFoundException();
  }

  private static Set<Point> generateNewPossibilities(FillablePoint[][] rows, Set<Point> visitedPoints, Set<Point> currentPoints) {
    return currentPoints.stream()
        .map((p) -> getPossibleAdjacentPoints(rows, visitedPoints, p))
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());
  }

  private static Set<Point> getPossibleAdjacentPoints(FillablePoint[][] rows, Set<Point> visitedPoints, Point point) {
    Set<Point> newPoints = new HashSet<>();

    if (point.x > 0 && rows[point.x - 1][point.y].empty) {
      addPointIfNotVisited(visitedPoints, newPoints, point.x - 1, point.y);
    }

    if (point.x < rows.length - 1 && rows[point.x + 1][point.y].empty) {
      addPointIfNotVisited(visitedPoints, newPoints, point.x + 1, point.y);
    }

    if (point.y > 0 && rows[point.x][point.y - 1].empty) {
      addPointIfNotVisited(visitedPoints, newPoints, point.x, point.y - 1);
    }

    if (point.y < rows[0].length - 1 && rows[point.x][point.y + 1].empty) {
      addPointIfNotVisited(visitedPoints, newPoints, point.x, point.y + 1);
    }

    return newPoints;
  }

  private static void addPointIfNotVisited(Set<Point> visitedPoints, Set<Point> newPoints, int x, int y) {
    Point newPoint = new Point(x, y);
    if (!visitedPoints.contains(newPoint)) {
      newPoints.add(newPoint);
    }
  }
}
