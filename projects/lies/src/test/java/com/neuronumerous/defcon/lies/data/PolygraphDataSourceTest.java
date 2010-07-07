package com.neuronumerous.defcon.lies.data;

import java.io.BufferedReader;
import java.io.StringReader;

import junit.framework.Assert;

import org.junit.Test;

import com.neuronumerous.defcon.lies.util.Source;

public class PolygraphDataSourceTest {

  private final String timestamp1 = "Timestamp:  0:4:59\n\n" 
                                  + "GSR:        158\n\n" 
                                  + "pleth:      248\n\n"
                                  + "breath:     180\n\n" 
                                  + "blush:      1\n\n";

  private final String timestamp2 = "Timestamp:  0:3:19\n\n" 
                                  + "GSR:        44\n\n" 
                                  + "pleth:      23\n\n"
                                  + "breath:     9\n\n" 
                                  + "blush:      4\n\n";


  @Test
  public void testNextWithCleanData() {
    String dataset = timestamp1 + timestamp2 + timestamp1;
    Source<PolyData> ds = new PolyDataSource(new BufferedReader(new StringReader(dataset)));
    PolyData pd = ds.next();
    Assert.assertNotNull(pd);
    Assert.assertEquals((Integer)299, pd.getTimestamp());
    Assert.assertEquals((Integer)158, pd.getGsr());
    Assert.assertEquals((Integer)248, pd.getPleth());
    Assert.assertEquals((Integer)180, pd.getBreath());
    Assert.assertEquals((Integer)1, pd.getBlush());
    Assert.assertNotNull(ds.next());
    Assert.assertNotNull(ds.next());
    Assert.assertNull(ds.next());
  }
  
  @Test
  public void testNextWithUnevenData() {
    String dataset = "\n\nGSR:    5\n" + timestamp1 + timestamp2 + timestamp1;
    Source<PolyData> ds = new PolyDataSource(new BufferedReader(new StringReader(dataset)));
    PolyData pd = ds.next();
    Assert.assertNotNull(pd);
    Assert.assertEquals((Integer)299, pd.getTimestamp());
    Assert.assertEquals((Integer)158, pd.getGsr());
    Assert.assertEquals((Integer)248, pd.getPleth());
    Assert.assertEquals((Integer)180, pd.getBreath());
    Assert.assertEquals((Integer)1, pd.getBlush());
    Assert.assertNotNull(ds.next());
    Assert.assertNotNull(ds.next());
    Assert.assertNull(ds.next());
  }
}
