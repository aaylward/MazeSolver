package com.andyaylward.maze;

import com.andyaylward.maze.config.IOModule.StandardOutput;
import com.andyaylward.maze.config.MazeModule;
import com.andyaylward.maze.core.Maze;
import com.andyaylward.maze.core.Point;
import com.andyaylward.maze.io.MazeReader;
import com.google.common.base.Preconditions;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.io.PrintStream;
import java.time.Clock;
import java.util.Optional;

public class Application {
  private final MazeReader mazeReader;
  private final PrintStream printStream;
  private final Clock clock;

  @Inject
  public Application(MazeReader mazeReader,
                     @StandardOutput PrintStream printStream,
                     Clock clock) {
    this.mazeReader = mazeReader;
    this.printStream = printStream;
    this.clock = clock;
  }

  public void run() {
    printStream.println("Please enter your maze:");
    Maze maze = mazeReader.readMazeFromInput();
    printStream.println("Enter start point:");
    Optional<Point> start = mazeReader.readPointFromInput();
    Preconditions.checkArgument(start.isPresent(), "You must provide a start point");

    printStream.println("Enter end point:");
    Optional<Point> end = mazeReader.readPointFromInput();
    Preconditions.checkArgument(end.isPresent(), "You must provide a start point");

    long startTime = clock.millis();
    int shortestPath = MazeSolver.shortestPath(maze, start.get(), end.get());
    long endTime = clock.millis();
    printStream.println("The shortest path is " + shortestPath + " steps long.");
    printStream.println("MazeSolver took " + (endTime - startTime) + " ms to compute it.");
  }

  public static void main(String... args) {
    Injector injector = Guice.createInjector(new MazeModule());
    Application application = injector.getInstance(Application.class);
    application.run();
  }
}
