package com.neuronumerous.defcon.lies;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class PolygraphDataSourceTest {

  @Test
  public void testParseLDData() {
    String rawData = "Timestamp:	0:4:59\n\n" + "GSR:		158\n\n" + "pleth:		248\n\n"
            + "breath:		180\n\n" + "blush:		1";

    PolygraphDataSource ds = new PolygraphDataSource();
    List<PolyData> datas = ds.createFromRawData(rawData);
    Assert.assertNotNull(datas);
    PolyData pd = datas.get(0);
    Assert.assertNotNull(pd);
    Assert.assertEquals(299, pd.getTimestamp());
    Assert.assertEquals(158, pd.getGSR());
    Assert.assertEquals(248, pd.getPleth());
    Assert.assertEquals(180, pd.getBreath());
    Assert.assertEquals(1, pd.getBlush());

  }

  @Test
  public void testGetDataFieldFromLine() {
    String fieldData = "Timestamp:     0:5:4";
    DataField result = new PolygraphDataSource().getDataFieldFromLine(fieldData);
    Assert.assertEquals("Timestamp", result.name);
    Assert.assertEquals("0:5:4", result.data);
  }
}
