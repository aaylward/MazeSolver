package com.andyaylward.maze.io;

import com.andyaylward.maze.config.IOModule.StandardOutput;
import com.andyaylward.maze.core.Maze;
import com.andyaylward.maze.core.Point;
import com.andyaylward.maze.core.SolveStatistics;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MazeConsole {
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
    printStream.println("Path taken was:");
    Optional<Point> point = Optional.of(solveStatistics.getEndPoint());
    List<Point> path = new ArrayList<>();
    while (point.isPresent()) {
      path.add(point.get());
      point = point.get().parent;
    }

    Lists.reverse(path).forEach(printStream::println);
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
