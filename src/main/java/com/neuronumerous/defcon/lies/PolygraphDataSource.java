/*
 * Copyright (c) 2010 Neuronumerous Collective Copyright (c) 2010 Christian
 * Edward Gruber
 */
package com.neuronumerous.defcon.lies;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.neuronumerous.defcon.lies.PolyData.PolyDataFields;

public class PolygraphDataSource implements Source<PolyData> {

  private final BufferedReader reader;
  
  @Deprecated
  public PolygraphDataSource() {
    this.reader = null;
  }
  
  public PolygraphDataSource(BufferedReader reader) {
    this.reader = reader;
  }
  
  @Override public PolyData next() {
    return null;
  }

  public List<PolyData> createFromRawData(String rawData) {
    List<PolyData> data = new ArrayList<PolyData>();
    // TODO(cgruber) ensure Timestamp at start of data set.
    List<String> lines = Arrays.asList(rawData.split("\n\n*"));
    Iterator<String> lineIterator = lines.iterator();
    if (!lineIterator.hasNext())
      return data; // bail early, empty list.
    DataField field = getDataFieldFromLine(lineIterator.next());
    if (field == null)
      throw new RuntimeException("Why the fuck is this null?");
    while (PolyDataFields.valueOf(field.name) != PolyDataFields.Timestamp && lineIterator.hasNext()) {
      field = getDataFieldFromLine(lineIterator.next());
    }
    while (lineIterator.hasNext()) {
      DataField timestamp = field,
                      GSR = getDataFieldFromLine(lineIterator.next()),
                    pleth = getDataFieldFromLine(lineIterator.next()),
                   breath = getDataFieldFromLine(lineIterator.next()),
                    blush = getDataFieldFromLine(lineIterator.next());
      PolyDataImpl pd = new PolyDataImpl();
      pd.timestamp = parseTimestamp(timestamp.data);
      if (GSR != null) pd.GSR = Integer.valueOf(GSR.data);
      if (pleth != null) pd.pleth = Integer.valueOf(pleth.data);
      if (breath != null) pd.breath = Integer.valueOf(breath.data);
      if (blush != null) pd.blush = Integer.valueOf(blush.data);
      data.add(pd);
      // Prepare for next loop.
      if (lineIterator.hasNext())
        field = getDataFieldFromLine(lineIterator.next());
    }
    return data;
  }

  /**
   * Parses the HH:MM:SS timestamp into raw seconds.
   * 
   * @return timestamp in seconds
   */
  private Integer parseTimestamp(String timestampString) {
    String[] parts = timestampString.split("[:]");
    int timestamp = 0;
    timestamp += Integer.valueOf(parts[0]) * 60 * 60;
    timestamp += Integer.valueOf(parts[1]) * 60;
    timestamp += Integer.valueOf(parts[2]);
    return timestamp;
  }

  public DataField getDataFieldFromLine(String line) {
    String[] pair = line.trim().split("[:]\\s\\s*");
    if (pair.length == 2) {
      return new DataField(pair[0], pair[1]);
    } else {
      return null;
    }
  }
}
