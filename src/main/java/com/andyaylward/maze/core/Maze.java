package com.andyaylward.maze.core;

public class Maze {
  private final Point[][] rows;

  public Maze(Point[][] rows) {
    this.rows = rows;
  }

  public Point[][] getRows() {
    return rows;
  }
}
