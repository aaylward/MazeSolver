package com.andyaylward.maze.config;

import com.google.inject.AbstractModule;

import java.time.Clock;

public class MazeModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new IOModule());
    install(new TimeModule());
    bind(Clock.class).toInstance(Clock.systemUTC());
  }
}
