package com.andyaylward.maze;

import com.andyaylward.maze.core.Maze;
import com.andyaylward.maze.core.Point;
import com.andyaylward.maze.core.SolveStatistics;
import com.andyaylward.maze.io.MazeConsole;
import com.andyaylward.maze.io.MazeReader;
import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.Clock;
import java.util.Optional;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class MazeSolverTest {
  @Mock
  private Scanner fakeScanner;
  @Mock
  private PrintStream fakePrintStream;
  private MazeSolver mazeSolver;
  private MazeReader mazeReader;
  private MazeConsole mazeConsole;

  @Before
  public void setUp() {
    mazeReader = new MazeReader(fakeScanner);
    mazeSolver = new MazeSolver(Clock.systemUTC());
    mazeConsole = new MazeConsole(mazeReader, fakePrintStream);
  }

  @Test
  public void itCanFindAnOnlyPath() throws URISyntaxException {
    Maze singlePath = mazeReader.readMazeFromFile(Paths.get(Resources.getResource("singlepath.maze").toURI()));
    Point start = new Point(3, 2);
    Point end = new Point(4, 0);
    Optional<SolveStatistics> solveStatisticsMaybe = mazeSolver.shortestPath(singlePath, start, end);
    assertThat(solveStatisticsMaybe).isPresent();
    assertThat(solveStatisticsMaybe.get().getShortestPathLength()).isEqualTo(13);

    mazeConsole.writeSolutionToMaze(solveStatisticsMaybe.get());
  }

  @Test
  public void itCannotFindPathsIfThereAreNon() throws URISyntaxException {
    Maze singlePath = mazeReader.readMazeFromFile(Paths.get(Resources.getResource("nopath.maze").toURI()));
    Point start = new Point(2, 2);
    Point end = new Point(4, 0);
    Optional<SolveStatistics> solveStatisticsMaybe = mazeSolver.shortestPath(singlePath, start, end);
    assertThat(solveStatisticsMaybe).isEmpty();
  }
}
