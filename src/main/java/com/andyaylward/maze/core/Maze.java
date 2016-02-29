package com.andyaylward.maze.core;

public class Maze {
  private final FillablePoint[][] rows;

  public Maze(FillablePoint[][] rows) {
    this.rows = rows;
  }

  public FillablePoint[][] getRows() {
    return rows;
  }
}
