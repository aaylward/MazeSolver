package com.andyaylward.maze;

import com.andyaylward.maze.config.MazeModule;
import com.andyaylward.maze.core.Maze;
import com.andyaylward.maze.core.Point;
import com.andyaylward.maze.core.SolveStatistics;
import com.andyaylward.maze.io.MazeConsole;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.Optional;

public class Application {
  private final MazeConsole mazeConsole;
  private final MazeSolver mazeSolver;

  @Inject
  public Application(MazeConsole mazeConsole,
                     MazeSolver mazeSolver) {
    this.mazeConsole = mazeConsole;
    this.mazeSolver = mazeSolver;
  }

  public void run() {
    Maze maze = mazeConsole.getMaze();
    Point start = mazeConsole.getStart();
    Point end = mazeConsole.getEnd();

    Optional<SolveStatistics> solveStatistics = mazeSolver.shortestPath(maze, start, end);
    mazeConsole.reportStatistics(solveStatistics);
  }

  public static void main(String... args) {
    Injector injector = Guice.createInjector(new MazeModule());
    Application application = injector.getInstance(Application.class);
    application.run();
  }
}
