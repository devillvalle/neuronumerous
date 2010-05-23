/*
 * Copyright (c) 2010 Neuronumerous Collective 
 * Copyright (c) 2010 Christian Edward Gruber
 */
package com.neuronumerous.defcon.lies;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class PolyDataSource implements Source<PolyData> {

  private static final String STARTING_FIELD = "Timestamp";
  
  private final BufferedReader reader;
  private final Logger logger;
  
  public PolyDataSource(BufferedReader reader) {
    this(reader,Logger.getLogger(PolyDataSource.class.getName()));
  }
  
  @Inject
  public PolyDataSource(@Assisted BufferedReader reader, Logger logger) {
    this.logger = logger;
    this.reader = reader;
  }
  
  @Override public PolyData next() {
    String line = nextNonEmptyLine(reader);
    while (line != null && !line.startsWith(STARTING_FIELD)) {
      line = nextNonEmptyLine(reader);
    }
    // catch null fall-through
    if (line == null) {
      logger.info("No more data.");
      return null;
    }
    return createPolyDataFromLines(line, nextNonEmptyLine(reader), 
        nextNonEmptyLine(reader), nextNonEmptyLine(reader), nextNonEmptyLine(reader));
  }
  
  public static String nextNonEmptyLine(BufferedReader reader) {
    String line = null;
    try {
      line = reader.readLine();
      while (line != null && line.isEmpty()) line = reader.readLine();
      return line;
    } catch (IOException e) {
      return null;
    }
  }

  public PolyData createPolyDataFromLines(
      String timestamp, 
      String gsr, 
      String pleth, 
      String breath, 
      String blush) {
    
    DataField timestampField = getDataFieldFromLine(timestamp),
                    gsrField = getDataFieldFromLine(gsr),
                  plethField = getDataFieldFromLine(pleth),
                 breathField = getDataFieldFromLine(breath),
                  blushField = getDataFieldFromLine(blush);
    try {
      return new PolyDataImpl(
      PolyDataImpl.parseTimestamp(timestampField.data),
      getIntOrNull(gsrField),
      getIntOrNull(plethField),
      getIntOrNull(breathField),
      getIntOrNull(blushField));  
    } catch (NumberFormatException e) {
      logger.log(Level.INFO, "Error parsing numbers for data.", e);
      return null;
    }
  }

  private Integer getIntOrNull(DataField field) {
    return field == null               ? null
         : field.data == null          ? null
         : field.data.trim().isEmpty() ? null
         :                               Integer.valueOf(field.data.trim());
  }

  public static DataField getDataFieldFromLine(String line) {
    String[] pair = line.trim().split("[:]\\s\\s*");
    if (pair.length == 2) {
      return new DataField(pair[0], pair[1]);
    } else {
      return null;
    }
  }
}
