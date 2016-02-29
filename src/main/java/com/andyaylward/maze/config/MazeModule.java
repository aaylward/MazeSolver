package com.andyaylward.maze.config;

import com.google.inject.AbstractModule;

public class MazeModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new IOModule());
    install(new TimeModule());
  }
}
