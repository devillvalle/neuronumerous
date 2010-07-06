package com.neuronumerous.defcon.lies.data;

import java.io.BufferedReader;
import java.io.StringReader;

import junit.framework.Assert;

import org.junit.Test;

public class PolyDataParserTest {
  
  PolyDataParser parser = new PolyDataParser();

  @Test
  public void testGetDataFieldFromLine() {
    String fieldData = "Timestamp:     0:5:4";
    DataField result = parser.getDataFieldFromLine(fieldData);
    Assert.assertEquals("Timestamp", result.name);
    Assert.assertEquals("0:5:4", result.data);
  }

  @Test
  public void testGetNextNonEmptyLine() {
    BufferedReader reader = new BufferedReader(new StringReader("\n\n\nblah\n\n"));
    Assert.assertEquals("blah", parser.nextNonEmptyLine(reader));
    Assert.assertEquals(null, parser.nextNonEmptyLine(reader));
  }

  @Test
  public void testParseTimestamp() {
    Assert.assertEquals((Integer)304, parser.parseTimestamp("0:5:4"));
    Assert.assertEquals((Integer)124, parser.parseTimestamp("0:2:4"));
    Assert.assertEquals((Integer)124, parser.parseTimestamp(":2:4"));
    Assert.assertEquals((Integer)4, parser.parseTimestamp("0::4"));
    Assert.assertEquals((Integer)(65*60+4), parser.parseTimestamp("0:65:4"));
  }
}
