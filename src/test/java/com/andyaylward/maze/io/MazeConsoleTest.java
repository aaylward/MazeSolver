package com.andyaylward.maze.io;

import com.andyaylward.maze.MazeTestUtil;
import com.andyaylward.maze.core.Point;
import com.andyaylward.maze.core.SolveStatistics;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class MazeConsoleTest {
  /*
   * ┌-┐xx
   * |x└-┐
   * |xxx|
   * |xs-┘
   * exxx.
   */
  @Test
  public void itCanWriteMazeSolutions() {
    Point start = new Point(3, 2);
    Point end = new Point(4, 0);
    Optional<SolveStatistics> solveStatisticsMaybe = MazeTestUtil.getSolveStatsFor("singlepath.maze", start, end);
    MazeTestUtil.MAZE_CONSOLE.writeSolutionToMaze(solveStatisticsMaybe.get());
    Point[][] rows = solveStatisticsMaybe.get().getMaze().getRows();
    assertThat(rows[3][2].toString()).isEqualTo(MazeConsole.START);
    assertThat(rows[4][0].toString()).isEqualTo(MazeConsole.END);
    assertThat(rows[0][0].toString()).isEqualTo(MazeConsole.UP_RIGHT);
    assertThat(rows[1][2].toString()).isEqualTo(MazeConsole.DOWN_RIGHT);
    assertThat(rows[3][4].toString()).isEqualTo(MazeConsole.RIGHT_UP);
    assertThat(rows[0][2].toString()).isEqualTo(MazeConsole.RIGHT_DOWN);
    assertThat(rows[1][3].toString()).isEqualTo(MazeConsole.HORIZONTAL);
    assertThat(rows[1][0].toString()).isEqualTo(MazeConsole.VERTICAL);
  }
}
