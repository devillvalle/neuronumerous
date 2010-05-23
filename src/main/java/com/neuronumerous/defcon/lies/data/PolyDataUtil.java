/*
 * Copyright (c) 2010 Neuronumerous Collective 
 * Copyright (c) 2010 Christian Edward Gruber
 */
package com.neuronumerous.defcon.lies.data;

import java.util.List;

public class PolyDataUtil {

  public PolyData aggregatePolyDatas(int timestamp, List<PolyData> elements) {
    if (elements.size() <= 0) return new PolyDataImpl(timestamp,null,null,null,null);
    long gsr = 0, breath = 0, pleth = 0, blush = 0; 
    for (PolyData data : elements) {
      gsr+=data.getGsr();
      breath+=data.getBreath();
      pleth+=data.getPleth();
      blush+=data.getBlush();    
    }
    return new PolyDataImpl(
        timestamp, 
        (int)(gsr/elements.size()), 
        (int)(pleth/elements.size()), 
        (int)(breath/elements.size()), 
        (int)(blush/elements.size()));
  }
  
}
