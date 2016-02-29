package com.andyaylward.maze.config;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;

import java.io.PrintStream;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Scanner;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class IOModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(PrintStream.class)
        .annotatedWith(StandardOutput.class)
        .toInstance(System.out);
    bind(Scanner.class)
        .annotatedWith(StandardInput.class)
        .toInstance(new Scanner(System.in));
  }

  @BindingAnnotation
  @Target({FIELD, PARAMETER, METHOD})
  @Retention(RUNTIME)
  public @interface StandardInput {
  }

  @BindingAnnotation
  @Target({FIELD, PARAMETER, METHOD})
  @Retention(RUNTIME)
  public @interface StandardOutput {
  }
}
