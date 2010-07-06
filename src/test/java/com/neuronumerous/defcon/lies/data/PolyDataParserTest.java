package com.neuronumerous.defcon.lies.data;

import java.io.BufferedReader;
import java.io.StringReader;

import junit.framework.Assert;

import org.junit.Test;

public class PolyDataParserTest {

  @Test
  public void testGetDataFieldFromLine() {
    String fieldData = "Timestamp:     0:5:4";
    DataField result = new PolyDataParser().getDataFieldFromLine(fieldData);
    Assert.assertEquals("Timestamp", result.name);
    Assert.assertEquals("0:5:4", result.data);
  }

  @Test
  public void testGetNextNonEmptyLine() {
    BufferedReader reader = new BufferedReader(new StringReader("\n\n\nblah\n\n"));
    Assert.assertEquals("blah", new PolyDataParser().nextNonEmptyLine(reader));
    Assert.assertEquals(null, new PolyDataParser().nextNonEmptyLine(reader));
  }

}
