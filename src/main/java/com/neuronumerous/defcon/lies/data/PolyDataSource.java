/*
 * Copyright (c) 2010 Neuronumerous Collective 
 * Copyright (c) 2010 Christian Edward Gruber
 */
package com.neuronumerous.defcon.lies.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.neuronumerous.defcon.lies.util.Source;

public class PolyDataSource implements Source<PolyData> {

  private static final String STARTING_FIELD = "Timestamp";
  
  private final Logger logger = Logger.getLogger(PolyDataSource.class.getSimpleName());
  private final BufferedReader reader;
  private final PolyDataParser parser;

  public PolyDataSource(BufferedReader reader) {
    this(
      reader,
      new PolyDataParser());
  }
  
  public PolyDataSource(BufferedReader reader, PolyDataParser parser) {
    this.parser = parser;
    this.reader = reader;
  }
  
  @Override public PolyData next() {
    String line = parser.nextNonEmptyLine(reader);
    while (line != null && !line.startsWith(STARTING_FIELD)) {
      line = parser.nextNonEmptyLine(reader);
    }
    // catch null fall-through
    if (line == null) {
      logger.info("No more data.");
      return null;
    }
    return parser.createPolyDataFromLines(line, parser.nextNonEmptyLine(reader), 
        parser.nextNonEmptyLine(reader), parser.nextNonEmptyLine(reader), 
        parser.nextNonEmptyLine(reader));
  }
  

}
