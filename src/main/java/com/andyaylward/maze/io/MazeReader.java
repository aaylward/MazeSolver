package com.andyaylward.maze.io;

import com.andyaylward.maze.config.IOModule.StandardInput;
import com.andyaylward.maze.core.Maze;
import com.andyaylward.maze.core.Point;
import com.andyaylward.maze.exceptions.InvalidPointInputException;
import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.inject.Inject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    List<String> rawRows = new ArrayList<>();
    while (scanner.hasNextLine()) {
      String rawRow = scanner.nextLine();
      if (rawRow.isEmpty()) {
        break;
      }
      rawRows.add(rawRow);
    }

    return fromLines(rawRows);
  }

  public Maze readMazeFromFile(Path path) {
    try {
      return fromLines(Files.readAllLines(path, Charsets.UTF_8));
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
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

  private Maze fromLines(List<String> lines) {
    List<Point[]> rows = new ArrayList<>();

    for (int lineNumber=0; lineNumber<lines.size(); lineNumber++) {
      rows.add(parseMazeRow(lines.get(lineNumber), lineNumber));
    }

    return new Maze(rows.toArray(new Point[0][0]));
  }

  private Point[] parseMazeRow(String line, int lineNumber) {
    Point[] row = new Point[line.length()];
    for (int i=0; i<line.length(); i++) {
      row[i] = new Point(i, lineNumber, line.charAt(i) == EMPTY_POINT);
    }

    return row;
  }
}
