package com.neuronumerous.defcon.lies.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.neuronumerous.defcon.lies.util.Formatter;

public class PolyDataParser {
  private final Logger logger = Logger.getLogger(PolyDataParser.class.getName());
  
  public static final Formatter<PolyData, String> FORMATTER = new Formatter<PolyData, String>(){
    @Override public String format(PolyData t) {
      StringBuilder sb = new StringBuilder();
      sb.append("Timestamp: \t").append(convert(t.getTimestamp())).append("\n");
      sb.append("GSR: \t").append(t.getGsr()).append("\n");
          sb.append("pleth: \t").append(t.getPleth()).append("\n");
          sb.append("breath: \t").append(t.getBreath()).append("\n");
          sb.append("blush: \t").append(t.getBlush()).append("\n");
          return sb.toString();
    }
    private String convert(Integer i) {
      return "::"+i;
    }
  };

  public PolyDataParser() {}
  
  public String nextNonEmptyLine(BufferedReader reader) {
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
      parseTimestamp(timestampField.data),
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

  public DataField getDataFieldFromLine(String line) {
    String[] pair = line.trim().split("[:]\\s\\s*");
    if (pair.length == 2) {
      return new DataField(pair[0], pair[1]);
    } else {
      return null;
    }
  }
  
  /**
   * Parses the HH:MM:SS timestamp into raw seconds.
   * 
   * @return timestamp in seconds
   */
  public Integer parseTimestamp(String timestampString) {
    String[] parts = timestampString.trim().split("[ ]+");
    switch (parts.length) {
      case 1: return parseRelativeTimestamp(parts[0]);
      case 2: return parseAbsoluteTimestamp(parts[0], parts[1]);
      default:
        logger.info("Too many pieces parsing timestamp: " + Arrays.asList(parts));
        return -1;
    } 
  }

  private Integer parseAbsoluteTimestamp(String date, String time) {
    // TODO Auto-generated method stub
    return null;
  }

  private Integer parseRelativeTimestamp(String time) {
    String[] parts = time.trim().split("[:]");
    for (int i = 0; i < parts.length; i++) {
      if (parts[i] == null || parts[i].isEmpty()) {
        parts[i] = "0";
      }
    }
    int timestamp = 0;
    try {
      timestamp += Integer.valueOf(parts[0]) * 60 * 60;
      timestamp += Integer.valueOf(parts[1]) * 60;
      timestamp += Integer.valueOf(parts[2]);
    } catch (Exception e) {
      logger.info("Error parsing timestamp: " + Arrays.asList(parts));
      return -1;
    }
    return timestamp;
  }
}