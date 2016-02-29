package com.andyaylward.maze.io;

import com.andyaylward.maze.core.Point;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class MazeReaderTest {
  @Mock
  private Scanner fakeScanner;
  private MazeReader mazeReader;

  @Before
  public void setUp() {
    this.mazeReader = new MazeReader(fakeScanner);
  }

  @Test
  public void itCanReadPoints() {
    String input = "<3,2>";
    Point point = mazeReader.parsePoint(input);
    assertThat(point.x).isEqualTo(3);
    assertThat(point.y).isEqualTo(2);
  }
}
