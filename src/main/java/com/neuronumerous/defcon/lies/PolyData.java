package com.neuronumerous.defcon.lies;

public interface PolyData {

  int getTimestamp();

  int getGSR();

  int getPleth();

  int getBreath();

  int getBlush();

  public static enum PolyDataFields {
    Timestamp, GSR, pleth, breath, blush;
  }
}
