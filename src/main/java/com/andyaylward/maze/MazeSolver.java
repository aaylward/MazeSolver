package com.andyaylward.maze;

import com.andyaylward.maze.core.Maze;
import com.andyaylward.maze.core.Point;
import com.andyaylward.maze.core.SolveStatistics;
import com.google.inject.Inject;

import java.time.Clock;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MazeSolver {
  private final Clock clock;

  @Inject
  public MazeSolver(Clock clock) {
    this.clock = clock;
  }

  public Optional<SolveStatistics> shortestPath(Maze maze, Point start, Point end) {
    long startTime = clock.millis();

    int distanceTravelled = 0;
    Set<Point> visitedPoints = new HashSet<>();
    Set<Point> currentPossibilities = new HashSet<>();
    currentPossibilities.add(start);

    while (!currentPossibilities.isEmpty()) {
      if (currentPossibilities.contains(end)) {
        long endTime = clock.millis();
        Point tracedEndPoint = currentPossibilities.stream()
            .filter(end::equals)
            .findFirst()
            .get();
        return Optional.of(new SolveStatistics(distanceTravelled, endTime - startTime, tracedEndPoint));
      }

      visitedPoints.addAll(currentPossibilities);

      currentPossibilities = generateNewPossibilities(maze.getRows(), visitedPoints, currentPossibilities);
      distanceTravelled++;
    }

    return Optional.empty();
  }

  private Set<Point> generateNewPossibilities(Point[][] rows, Set<Point> visitedPoints, Set<Point> currentPoints) {
    return currentPoints.stream()
        .map((p) -> getPossibleAdjacentPoints(rows, visitedPoints, p))
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());
  }

  private Set<Point> getPossibleAdjacentPoints(Point[][] rows, Set<Point> visitedPoints, Point point) {
    Set<Point> newPoints = new HashSet<>();

    if (point.x > 0 && rows[point.x - 1][point.y].empty) {
      addPointIfNotVisited(visitedPoints, newPoints, point.x - 1, point.y, point);
    }

    if (point.x < rows.length - 1 && rows[point.x + 1][point.y].empty) {
      addPointIfNotVisited(visitedPoints, newPoints, point.x + 1, point.y, point);
    }

    if (point.y > 0 && rows[point.x][point.y - 1].empty) {
      addPointIfNotVisited(visitedPoints, newPoints, point.x, point.y - 1, point);
    }

    if (point.y < rows[0].length - 1 && rows[point.x][point.y + 1].empty) {
      addPointIfNotVisited(visitedPoints, newPoints, point.x, point.y + 1, point);
    }

    return newPoints;
  }

  private void addPointIfNotVisited(Set<Point> visitedPoints, Set<Point> newPoints, int x, int y, Point parent) {
    Point newPoint = new Point(x, y, true, parent);
    if (!visitedPoints.contains(newPoint)) {
      newPoints.add(newPoint);
    }
  }
}
