package com.andyaylward.maze.io;

import com.andyaylward.maze.MazeTestUtil;
import com.andyaylward.maze.core.Point;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MazeReaderTest {
  @Test
  public void itCanReadPoints() {
    String input = "<3,2>";
    Point point = MazeTestUtil.MAZE_READER.parsePoint(input);
    assertThat(point.x).isEqualTo(3);
    assertThat(point.y).isEqualTo(2);
  }
}
