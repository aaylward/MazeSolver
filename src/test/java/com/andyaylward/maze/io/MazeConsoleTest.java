package com.andyaylward.maze.io;

import com.andyaylward.maze.MazeTestUtil;
import com.andyaylward.maze.core.Point;
import com.andyaylward.maze.core.SolveStatistics;
import org.junit.Test;

import java.util.Optional;

public class MazeConsoleTest {
  @Test
  public void itCanWriteMazeSolutions() {
    Point start = new Point(3,2);
    Point end = new Point(4,0);
    Optional<SolveStatistics> solveStatisticsMaybe = MazeTestUtil.getSolveStatsFor("singlepath.maze", start, end);
    MazeTestUtil.MAZE_CONSOLE.writeSolutionToMaze(solveStatisticsMaybe.get());
    Point[][] rows = solveStatisticsMaybe.get().getMaze().getRows();
    
  }
}
