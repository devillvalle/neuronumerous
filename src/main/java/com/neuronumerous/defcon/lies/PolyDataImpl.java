package com.neuronumerous.defcon.lies;

public class PolyDataImpl implements PolyData {

  /* package */int blush;
  /* package */int breath;
  /* package */int GSR;
  /* package */int pleth;
  /* package */int timestamp;

  @Override
  public int getBlush() {
    return blush;
  }

  @Override
  public int getBreath() {
    return breath;
  }

  @Override
  public int getGSR() {
    return GSR;
  }

  @Override
  public int getPleth() {
    return pleth;
  }

  @Override
  public int getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[" 
        + "timestamp=" + timestamp + ","
        + "GSR=" + GSR + ","
        + "breath=" + breath + ","
        + "pleth=" + pleth + ","
        + "blush=" + blush + ","
        + "]";
  }
}
