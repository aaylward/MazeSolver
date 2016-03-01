package com.andyaylward.maze;

import com.andyaylward.maze.core.Maze;
import com.andyaylward.maze.core.Point;
import com.andyaylward.maze.core.SolveStatistics;
import com.andyaylward.maze.io.MazeConsole;
import com.andyaylward.maze.io.MazeReader;
import com.google.common.base.Throwables;
import com.google.common.io.Resources;
import org.mockito.Mock;

import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.Clock;
import java.util.Optional;
import java.util.Scanner;

public class MazeTestUtil {
  @Mock
  private static Scanner fakeScanner;
  @Mock
  private static PrintStream fakePrintStream;
  public static final MazeSolver MAZE_SOLVER;
  public static final MazeReader MAZE_READER;
  public static final MazeConsole MAZE_CONSOLE;

  static {
    MAZE_READER = new MazeReader(fakeScanner);
    MAZE_SOLVER = new MazeSolver(Clock.systemUTC());
    MAZE_CONSOLE = new MazeConsole(MAZE_READER, fakePrintStream);
  }

  public static Optional<SolveStatistics> getSolveStatsFor(String path, Point start, Point end) {
    Maze maze;
    try {
      maze = MazeTestUtil.MAZE_READER.readMazeFromFile(Paths.get(Resources.getResource(path).toURI()));
    } catch (URISyntaxException u) {
      throw Throwables.propagate(u);
    }
    return MazeTestUtil.MAZE_SOLVER.shortestPath(maze, start, end);
  }
}
