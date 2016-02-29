package com.andyaylward.maze.io;

import com.andyaylward.maze.config.IOModule.StandardOutput;
import com.andyaylward.maze.core.Maze;
import com.andyaylward.maze.core.Point;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import java.io.PrintStream;
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
    return getPoint("Enter start point:", "You must provide a start point.");
  }

  public Point getEnd() {
    return getPoint("Enter end point:", "You must provide an end point.");
  }

  private Point getPoint(String prompt, String error) {
    printStream.println(prompt);
    Optional<Point> pointMaybe = mazeReader.readPointFromInput();
    Preconditions.checkArgument(pointMaybe.isPresent(), error);
    return pointMaybe.get();
  }
}
