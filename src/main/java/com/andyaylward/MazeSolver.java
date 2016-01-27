package com.andyaylward;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MazeSolver {
  private final FillablePoint[][] maze;
  private final Set<Point> visitedPoints;

  public MazeSolver(FillablePoint[][] maze) {
    this.maze = maze;
    this.visitedPoints = new HashSet<>();
  }

  public int shortestPath(Point start, Point end) {
    int distanceTravelled = 0;
    Set<Point> currentPossibilities = new HashSet<>();
    currentPossibilities.add(start);

    while (!currentPossibilities.isEmpty()) {
      if (currentPossibilities.contains(end)) {
        return distanceTravelled;
      }

      visitedPoints.addAll(currentPossibilities);

      currentPossibilities = generateNewPossibilities(currentPossibilities);
      distanceTravelled++;
    }

    throw new NoPathFoundException();
  }

  private Set<Point> generateNewPossibilities(Set<Point> currentPoints) {
    return currentPoints.stream()
        .map(this::getPossibleAdjacentPoints)
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());
  }

  private Set<Point> getPossibleAdjacentPoints(Point point) {
    Set<Point> newPoints = new HashSet<>();

    if (point.x > 0 && maze[point.x-1][point.y].empty) {
      addPointIfNotVisited(newPoints, point.x-1, point.y);
    }

    if (point.x < maze.length-1 && maze[point.x + 1][point.y].empty) {
      addPointIfNotVisited(newPoints, point.x+1, point.y);
    }

    if (point.y > 0 && maze[point.x][point.y-1].empty) {
      addPointIfNotVisited(newPoints, point.x, point.y-1);
    }

    if (point.y < maze[0].length-1 && maze[point.x][point.y + 1].empty) {
      addPointIfNotVisited(newPoints, point.x, point.y+1);
    }

    return newPoints;
  }

  private void addPointIfNotVisited(Set<Point> newPoints, int x, int y) {
    Point newPoint = new Point(x, y);
    if (!visitedPoints.contains(newPoint)) {
      newPoints.add(newPoint);
    }
  }
}
