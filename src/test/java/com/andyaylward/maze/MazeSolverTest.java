package com.andyaylward.maze;

import com.andyaylward.maze.core.FillablePoint;
import com.andyaylward.maze.core.Maze;
import com.andyaylward.maze.core.Point;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MazeSolverTest {
  private static final int SIZE = 5;
  private FillablePoint[][] rows = new FillablePoint[SIZE][SIZE];

  @Before
  public void setUp() {
    //    . . . x x
    //    . x . . .
    //    . x x x .
    //    . x s . .
    //    e x x x .
    for (int i=0; i<SIZE; i++) {
      for (int j=0; j<SIZE; j++) {
        rows[i][j] = new FillablePoint(i, j, false);
      }
    }

    emptyPoint(0, 0);
    emptyPoint(0, 1);
    emptyPoint(0, 2);
    emptyPoint(1, 0);
    emptyPoint(1, 2);
    emptyPoint(1, 3);
    emptyPoint(1, 4);
    emptyPoint(2, 0);
    emptyPoint(2, 4);
    emptyPoint(3, 0);
    emptyPoint(3, 2);
    emptyPoint(3, 3);
    emptyPoint(3, 4);
    emptyPoint(4, 0);
    emptyPoint(4, 4);
  }

  @Test
  public void itCanFindAnOnlyPath() {
    Point start = new Point(3, 2);
    Point end = new Point(4, 0);
    assertThat(MazeSolver.shortestPath(new Maze(rows), start, end)).isEqualTo(13);
  }

  private void emptyPoint(int x, int y) {
    rows[x][y] = new FillablePoint(x, y, true);
  }
}
