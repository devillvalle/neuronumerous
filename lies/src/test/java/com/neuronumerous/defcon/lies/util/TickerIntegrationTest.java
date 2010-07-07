package com.neuronumerous.defcon.lies.util;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;


public class TickerIntegrationTest {

  @Test
  public void testTimer() throws Throwable {
    Ticker t = new Ticker();
    Assert.assertTrue(t.block(1,TimeUnit.MICROSECONDS));
  }

}
