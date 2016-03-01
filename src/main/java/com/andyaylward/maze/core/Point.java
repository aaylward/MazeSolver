package com.andyaylward.maze.core;

import java.util.Objects;
import java.util.Optional;

public class Point {
  public final int x;
  public final int y;
  public final boolean empty;
  public final Optional<Point> parent;
  private final String printableRepresentation;

  public Point(int x, int y, boolean empty, Point parent) {
    this.x = x;
    this.y = y;
    this.empty = empty;
    this.parent = Optional.ofNullable(parent);
    this.printableRepresentation = empty ? "." : "x";
  }

  public Point(int x, int y, boolean empty) {
    this.x = x;
    this.y = y;
    this.empty = empty;
    this.parent = Optional.empty();
    this.printableRepresentation = empty ? "." : "x";
  }

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
    this.empty = true;
    this.parent = Optional.empty();
    this.printableRepresentation = ".";
  }

  private Point(int x, int y, boolean empty, Optional<Point> parent, String printableRepresentation) {
    this.x = x;
    this.y = y;
    this.empty = empty;
    this.parent = parent;
    this.printableRepresentation = printableRepresentation;
  }

  public static Point from(Point point, String printableRepresentation) {
    return new Point(point.x, point.y, point.empty, point.parent, printableRepresentation);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Point point = (Point) o;
    return x == point.x &&
        y == point.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public String toString() {
    return printableRepresentation;
  }
}
