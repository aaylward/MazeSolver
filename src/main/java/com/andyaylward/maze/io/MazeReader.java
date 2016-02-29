package com.andyaylward.maze.io;

import com.andyaylward.maze.config.IOModule.StandardInput;
import com.andyaylward.maze.core.FillablePoint;
import com.andyaylward.maze.core.Maze;
import com.andyaylward.maze.core.Point;
import com.andyaylward.maze.exceptions.InvalidPointInputException;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MazeReader {
  private static final char EMPTY_POINT = '.';
  private static final String POINT_SEPARATOR = "[<,>]";
  private final Scanner scanner;

  @Inject
  public MazeReader(@StandardInput Scanner scanner) {
    this.scanner = scanner;
  }

  public Maze readMazeFromInput() {
    List<FillablePoint[]> rows = new ArrayList<>();
    for (int lineNumber=0; scanner.hasNextLine(); lineNumber++) {
      String rawRow = scanner.nextLine();

      if (rawRow.isEmpty()) {
        break;
      }

      FillablePoint[] row = parseMazeRow(rawRow, lineNumber);
      rows.add(row);
    }
    return new Maze(rows.toArray(new FillablePoint[0][0]));
  }

  public Optional<Point> readPointFromInput() {
    if (!scanner.hasNextLine()) {
      return Optional.empty();
    }

    String rawLine = scanner.nextLine();
    return rawLine.isEmpty() ? Optional.empty() : Optional.of(parsePoint(rawLine));
  }

  Point parsePoint(String line) {
    String[] coordinates = line.split(POINT_SEPARATOR);

    if (coordinates.length != 3) {
      throw new InvalidPointInputException();
    }

    int xCoordinate = Integer.parseInt(coordinates[1]);
    int yCoordinate = Integer.parseInt(coordinates[2]);

    return new Point(xCoordinate, yCoordinate);
  }

  private FillablePoint[] parseMazeRow(String line, int lineNumber) {
    FillablePoint[] row = new FillablePoint[line.length()];
    for (int i=0; i<line.length(); i++) {
      row[i] = new FillablePoint(i, lineNumber, line.charAt(i) == EMPTY_POINT);
    }

    return row;
  }
}
