package com.andyaylward.maze.io;

import com.andyaylward.maze.config.IOModule.StandardOutput;
import com.andyaylward.maze.core.Maze;
import com.andyaylward.maze.core.Point;
import com.andyaylward.maze.core.SolveStatistics;
import com.andyaylward.maze.exceptions.InvalidPointInputException;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MazeConsole {
  private static final String RIGHT_UP = "┘";
  private static final String DOWN_RIGHT = "└";
  private static final String UP_RIGHT = "┌";
  private static final String RIGHT_DOWN = "┐";
  private static final String VERTICAL = "|";
  private static final String HORIZONTAL = "-";

  private final MazeReader mazeReader;
  private final PrintStream printStream;

  @Inject
  public MazeConsole(MazeReader mazeReader,
                     @StandardOutput PrintStream printStream) {
    this.mazeReader = mazeReader;
    this.printStream = printStream;
  }

  public Maze getMaze() {
    printStream.println("Please enter your maze:");
    return mazeReader.readMazeFromInput();
  }

  public Point getStart() {
    return getPoint("Enter start point: ", "You must provide a start point.");
  }

  public Point getEnd() {
    return getPoint("Enter end point: ", "You must provide an end point.");
  }

  public void reportStatistics(Optional<SolveStatistics> solveStatisticsMaybe) {
    printStream.println();
    if (solveStatisticsMaybe.isPresent()) {
      reportSuccessfulSolve(solveStatisticsMaybe.get());
    } else {
      reportFailedSolve();
    }
  }

  private void reportSuccessfulSolve(SolveStatistics solveStatistics) {
    printStream.println("The shortest path is " + solveStatistics.getShortestPathLength() + " steps long.");
    printStream.println("MazeSolver took " + solveStatistics.getRunDuration() + " ms to compute it.");
    printStream.println();
    printStream.println("Path taken was:");
    printSolvedMaze(solveStatistics);
  }

  private void printSolvedMaze(SolveStatistics solveStatistics) {
    writeSolutionToMaze(solveStatistics);
    printRows(solveStatistics.getMaze());
  }

  private void printRows(Maze maze) {
    for (Point[] row : maze.getRows()) {
      for (Point point : row) {
        printStream.print(point);
      }
      printStream.println();
    }
  }

  public void writeSolutionToMaze(SolveStatistics solveStatistics) {
    Maze maze = solveStatistics.getMaze();
    List<Point> reversedPath = getReversedPath(Optional.of(solveStatistics.getEndPoint()));
    for (int i = 0; i < reversedPath.size(); i++) {
      int x = reversedPath.get(i).x;
      int y = reversedPath.get(i).y;
      if (i == 0) {
        maze.getRows()[x][y] = Point.from(reversedPath.get(i), "e");
      } else if (i == reversedPath.size()-1) {
        maze.getRows()[x][y] = Point.from(reversedPath.get(i), "s");
      } else {
        maze.getRows()[x][y] = getMidPathPoint(reversedPath.get(i+1), reversedPath.get(i), reversedPath.get(i-1));
      }
    }
  }

  private Point getMidPathPoint(Point previousPoint, Point thisPoint, Point nextPoint) {
    if (previousPoint.x == thisPoint.x && thisPoint.x == nextPoint.x) {
      return Point.from(thisPoint, HORIZONTAL);
    }
    if (previousPoint.y == thisPoint.y && thisPoint.y == nextPoint.y) {
      return Point.from(thisPoint, VERTICAL);
    }
    if (previousPoint.y < thisPoint.y) {
      if (nextPoint.x > thisPoint.x) {
        return Point.from(thisPoint, RIGHT_DOWN);
      }
      return Point.from(thisPoint, RIGHT_UP);
    }
    if (previousPoint.y > thisPoint.y) {
      if (nextPoint.x > thisPoint.x) {
        return Point.from(thisPoint, UP_RIGHT);
      }
      return Point.from(thisPoint, DOWN_RIGHT);
    }
    if (previousPoint.x < thisPoint.x) {
      if (nextPoint.y > thisPoint.y) {
        return Point.from(thisPoint, DOWN_RIGHT);
      }
      return Point.from(thisPoint, RIGHT_UP);
    }
    if (previousPoint.x > thisPoint.x) {
      if (nextPoint.y > thisPoint.y) {
        return Point.from(thisPoint, UP_RIGHT);
      }
      return Point.from(thisPoint, RIGHT_DOWN);
    }
    throw new InvalidPointInputException();
  }

  private List<Point> getReversedPath(Optional<Point> point) {
    List<Point> path = new ArrayList<>();
    while (point.isPresent()) {
      path.add(point.get());
      point = point.get().parent;
    }
    return path;
  }

  private void reportFailedSolve() {
    printStream.println("No path found!");
  }

  private Point getPoint(String prompt, String error) {
    printStream.print(prompt);
    Optional<Point> pointMaybe = mazeReader.readPointFromInput();
    Preconditions.checkArgument(pointMaybe.isPresent(), error);
    return pointMaybe.get();
  }
}
