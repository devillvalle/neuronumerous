package com.neuronumerous.defcon.lies.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jcckit.GraphicsPlotCanvas;
import jcckit.util.ConfigParameters;

import com.neuronumerous.defcon.lies.data.PolyGraphModel;


public class PolyGraph extends GraphicsPlotCanvas {
  
  private final PolyGraphModel model;

  public PolyGraph(@Polygraph ConfigParameters config, PolyGraphModel model) {
    super(config);
    this.model = model;
    model.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        replot();
      }
    });
    replot();
  }
  
  private void replot() { 
    
  }

  @Retention(RetentionPolicy.RUNTIME) 
  @Target({ElementType.PARAMETER})
  public static @interface Polygraph {}
}
