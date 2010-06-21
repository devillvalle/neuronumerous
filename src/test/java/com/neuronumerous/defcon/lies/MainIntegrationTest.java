package com.neuronumerous.defcon.lies;

import java.io.BufferedReader;
import java.io.File;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.neuronumerous.defcon.lies.data.PolyDataUtil;

public class MainIntegrationTest {

  @Test
  public void testParseTimestamp() throws Throwable {
    Main main = new Main(Logger.getLogger(Main.class.getName()),new PolyDataUtil());
    File file = new File("src/test/resources/FakeDataFileBadInitialState.txt");
    Assert.assertTrue(file.exists());
    BufferedReader reader = Files.newReader(file, Charsets.US_ASCII);
    main.runFromReader(reader);
  }

}
