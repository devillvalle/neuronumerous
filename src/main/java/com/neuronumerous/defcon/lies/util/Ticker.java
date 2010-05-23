package com.neuronumerous.defcon.lies.util;

import java.util.concurrent.TimeUnit;

public class Ticker {
  
  private final long start;
  private long now;
  
  public Ticker() {
    start = System.currentTimeMillis();
    now = start;
  }

  public boolean block(long timeout, TimeUnit timeUnit) throws InterruptedException {
    long millis = TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
    while ((now - start) < millis) {
      Thread.sleep(10);
      now = System.currentTimeMillis();
    }
    return true;
  }

}
