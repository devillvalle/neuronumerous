/*
 * Copyright (c) 2010 Neuronumerous Collective 
 * Copyright (c) 2010 Christian Edward Gruber
 */
package com.neuronumerous.defcon.lies.data;

import java.util.logging.Logger;


public class PolyDataImpl implements PolyData {
	
  private final static Logger LOG = Logger.getLogger(PolyDataImpl.class.getName());
	
  private Integer blush;
  private Integer breath;
  private Integer gsr;
  private Integer pleth;
  private Integer timestamp;
  
  public PolyDataImpl(
      Integer timestamp, 
      Integer gsr, 
      Integer pleth, 
      Integer breath, 
      Integer blush) {
    
    this.timestamp = timestamp;
    this.gsr = gsr;
    this.pleth = pleth;
    this.breath = breath;
    this.blush = blush;
  }

  @Override
  public Integer getBlush() {
    return blush;
  }

  @Override
  public Integer getBreath() {
    return breath;
  }

  @Override
  public Integer getGsr() {
    return gsr;
  }

  @Override
  public Integer getPleth() {
    return pleth;
  }

  @Override
  public Integer getTimestamp() {
    return timestamp;
  }
  
  @Override
  public String toString() {
    return getClass().getSimpleName() + "[" 
        + "timestamp=" + timestamp + ", "
        + "gsr=" + gsr + ", "
        + "breath=" + breath + ", "
        + "pleth=" + pleth + ", "
        + "blush=" + blush
        + "]";
  }
}
