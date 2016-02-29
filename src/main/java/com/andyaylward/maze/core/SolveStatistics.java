package com.andyaylward.maze.core;

public class SolveStatistics {
  private final int shortestPathLength;
  private final long runDuration;

  public SolveStatistics(int shortestPathLength, long runDuration) {
    this.shortestPathLength = shortestPathLength;
    this.runDuration = runDuration;
  }

  public int getShortestPathLength() {
    return shortestPathLength;
  }

  public long getRunDuration() {
    return runDuration;
  }
}
