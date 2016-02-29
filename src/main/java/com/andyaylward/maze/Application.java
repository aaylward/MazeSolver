package com.andyaylward.maze;

import com.andyaylward.maze.config.IOModule.StandardOutput;
import com.andyaylward.maze.config.MazeModule;
import com.andyaylward.maze.core.Maze;
import com.andyaylward.maze.core.Point;
import com.andyaylward.maze.io.MazeConsole;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.io.PrintStream;
import java.time.Clock;

public class Application {
  private final MazeConsole mazeConsole;
  private final Clock clock;
  private final PrintStream printStream;

  @Inject
  public Application(MazeConsole mazeConsole,
                     Clock clock,
                     @StandardOutput PrintStream printStream) {
    this.mazeConsole = mazeConsole;
    this.clock = clock;
    this.printStream = printStream;
  }

  public void run() {
    Maze maze = mazeConsole.getMaze();
    Point start = mazeConsole.getStart();
    Point end = mazeConsole.getEnd();

    long startTime = clock.millis();
    int shortestPath = MazeSolver.shortestPath(maze, start, end);
    long endTime = clock.millis();

    printStream.println();
    printStream.println("The shortest path is " + shortestPath + " steps long.");
    printStream.println("MazeSolver took " + (endTime - startTime) + " ms to compute it.");
  }

  public static void main(String... args) {
    Injector injector = Guice.createInjector(new MazeModule());
    Application application = injector.getInstance(Application.class);
    application.run();
  }
}
