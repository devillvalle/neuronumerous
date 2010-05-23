package com.neuronumerous.defcon.lies.util;

import java.util.concurrent.TimeUnit;

public class Ticker {
  
  private final long start;
  
  public Ticker() {
    start = System.currentTimeMillis();
  }

  public boolean block(long timeout, TimeUnit timeUnit) throws InterruptedException {
    long millis = TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
    long now = System.currentTimeMillis();
    long blockStart = now;
    while ((now - blockStart) < millis) {
      Thread.sleep(10);
      now = System.currentTimeMillis();
    }
    return true;
  }

}
