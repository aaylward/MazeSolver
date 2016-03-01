package com.andyaylward.maze;

import com.andyaylward.maze.core.Point;
import com.andyaylward.maze.core.SolveStatistics;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class MazeSolverTest {
  @Test
  public void itCanFindAnOnlyPath() {
    Point start = new Point(3,2);
    Point end = new Point(4,0);
    Optional<SolveStatistics> solveStatisticsMaybe = MazeTestUtil.getSolveStatsFor("singlepath.maze", start, end);
    assertThat(solveStatisticsMaybe).isPresent();
    assertThat(solveStatisticsMaybe.get().getShortestPathLength()).isEqualTo(13);
  }

  @Test
  public void itCannotFindPathsIfThereAreNone() {
    Point start = new Point(2,2);
    Point end = new Point(4,0);
    Optional<SolveStatistics> solveStatisticsMaybe = MazeTestUtil.getSolveStatsFor("nopath.maze", start, end);
    assertThat(solveStatisticsMaybe).isEmpty();
  }
}
