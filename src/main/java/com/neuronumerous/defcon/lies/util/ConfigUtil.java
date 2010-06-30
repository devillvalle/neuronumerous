/*
 * Copyright (c) 2010 Neuronumerous Collective 
 * Copyright (c) 2010 Christian Edward Gruber
 */
package com.neuronumerous.defcon.lies.util;

import java.util.Properties;

import jcckit.util.ConfigData;
import jcckit.util.ConfigParameters;
import jcckit.util.PropertiesBasedConfigData;

/**
 * A blocking data source
 *
 */
public class ConfigUtil {
  
  public static int DATA_POINTS = 200;

  public static ConfigParameters getConfigParameters() {
    Properties properties = new Properties();
    properties.setProperty("defaultAxisLabelAttributes/className", "jcckit.graphic.BasicGraphicAttributes");
    properties.setProperty("defaultAxisLabelAttributes/fontType", "bold");
    properties.setProperty("defaultAxisLabelAttributes/fontSize", "0.025");
    properties.setProperty("defaultTicLabelAttributes/className", "jcckit.graphic.BasicGraphicAttributes");
    properties.setProperty("defaultTicLabelAttributes/fontType", "bold");
    //properties.setProperty("defaultTicLabelAttributes/fontSize", "0.025");
    properties.setProperty("plot/coordinateSystem/origin", "-0.02 0.1");
    properties.setProperty("plot/coordinateSystem/xAxis/axisLength", "0.95");
    properties.setProperty("plot/coordinateSystem/xAxis/maximum", ""+DATA_POINTS);
    //properties.setProperty("plot/coordinateSystem/xAxis/axisLabelAttributes/", "defaultAxisLabelAttributes/");
    properties.setProperty("plot/coordinateSystem/xAxis/automaticTicCalculation", "true");
    //properties.setProperty("plot/coordinateSystem/xAxis/numberOfTics", "7");
    //properties.setProperty("plot/coordinateSystem/xAxis/ticLabelFormat", "%0f");
    properties.setProperty("plot/coordinateSystem/xAxis/axisLabel", "Readings over time");
    //properties.setProperty("plot/coordinateSystem/xAxis/ticLabelAttributes/", "defaultTicLabelAttributes/");
    properties.setProperty("plot/coordinateSystem/xAxis/axisLabelPosition", "0 -0.04");
    properties.setProperty("plot/coordinateSystem/yAxis/axisLabel", "breath, pleth, GSR, blush");
    properties.setProperty("plot/coordinateSystem/yAxis/axisLabelAttributes/", "defaultAxisLabelAttributes/");
    properties.setProperty("plot/coordinateSystem/yAxis/automaticTicCalculation", "true");
    //properties.setProperty("plot/coordinateSystem/yAxis/numberOfTics", "6");
    //properties.setProperty("plot/coordinateSystem/yAxis/axisLength", "1.0");
    properties.setProperty("plot/coordinateSystem/yAxis/ticLabelAttributes/", "defaultTicLabelAttributes/");
    properties.setProperty("plot/coordinateSystem/yAxis/maximum", "210");
    properties.setProperty("plot/coordinateSystem/yAxis/minimum", "130");
    properties.setProperty("plot/coordinateSystem/yAxis/axisLabelPosition", "-0.15 0");
    properties.setProperty("plot/curveFactory/definitions", "def null");
    properties.setProperty("plot/curveFactory/def/symbolFactory/className", "jcckit.plot.SquareSymbolFactory");
    properties.setProperty("plot/curveFactory/def/symbolFactory/size", "0.004");
    properties.setProperty("plot/curveFactory/def/symbolFactory/attributes/className", "jcckit.graphic.ShapeAttributes");
    properties.setProperty("plot/legend/boxHeight", "0.14");
    properties.setProperty("plot/legend/boxWidth", "0.13");
    properties.setProperty("plot/legend/upperRightCorner", "1.08 0.485");
    //properties.setProperty("plot/legend/", "");
    
    ConfigData cd = new PropertiesBasedConfigData(properties);
    return new ConfigParameters(cd);
  }
  
 
}
