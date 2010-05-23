package com.neuronumerous.defcon.lies;

import junit.framework.Assert;

import org.junit.Test;


public class PolyDataImplTest {
  
  @Test
  public void testParseTimestamp() {
    Assert.assertEquals((Integer)304,PolyDataImpl.parseTimestamp("0:5:4"));
    Assert.assertEquals((Integer)124,PolyDataImpl.parseTimestamp("0:2:4"));
    Assert.assertEquals((Integer)124,PolyDataImpl.parseTimestamp(":2:4"));
    Assert.assertEquals((Integer)4,PolyDataImpl.parseTimestamp("0::4"));
    Assert.assertEquals((Integer)(65*60+4),PolyDataImpl.parseTimestamp("0:65:4"));
  }

}
