package com.andyaylward.maze.config;

import com.google.inject.AbstractModule;

import java.time.Clock;

public class TimeModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(Clock.class).toInstance(Clock.systemUTC());
  }
}
