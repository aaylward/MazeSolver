package com.andyaylward;

public class FillablePoint extends Point {
  public final boolean empty;

  public FillablePoint(int x, int y, boolean empty) {
    super(x, y);
    this.empty = empty;
  }
}
