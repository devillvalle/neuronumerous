package com.neuronumerous.defcon.lies.ui;

import java.awt.Canvas;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Queue;

import javax.swing.JPanel;

import jcckit.GraphicsPlotCanvas;
import jcckit.data.DataCurve;
import jcckit.data.DataPlot;
import jcckit.data.DataPoint;

import com.neuronumerous.defcon.lies.data.PolyData;
import com.neuronumerous.defcon.lies.data.PolyGraphModel;
import com.neuronumerous.defcon.lies.util.ConfigUtil;


public class PolyGraph extends JPanel {
  
  private static final long serialVersionUID = -1244045942116012973L;

  private final GraphicsPlotCanvas manager;
  private final Canvas delegate;
  
  private int window = 1;
  
  private PolyGraphModel _model;
  
  private final ActionListener listener = new ActionListener() {
    public void actionPerformed(ActionEvent event) {
      if (event.getID() == PolyGraphModel.DATA_UPDATED) {
        replot();
      }
    }
  };

  public PolyGraph() {
    manager = new GraphicsPlotCanvas(ConfigUtil.getConfigParameters());
    manager.setDoubleBuffering(false);
    
    setLayout(new FlowLayout());
    
    delegate = manager.getGraphicsCanvas();
    delegate.setSize(760,200);
    add(delegate);
  }
  
  public void setModel(PolyGraphModel model) {
    if (this._model != null) model.removeActionListener(listener);
    this._model = model;
    if (model != null) {
      model.addActionListener(listener);
    }
  }
  
  public void setWindow(int window) {
    this.window = window;
  }
  
  public int getWindow() {
    return window;
  }
  
  public PolyGraphModel getModel() {
    return this._model;
  }
  
  public void replot() { 
    DataPlot plot = new DataPlot();
    DataCurve plethCurve = new DataCurve("pleth");
    DataCurve breathCurve = new DataCurve("breath");
    DataCurve gsrCurve = new DataCurve("GSR");
    DataCurve blushCurve = new DataCurve("Blush");
    Queue<PolyData> dataSet = _model.getDataElements();
    
    Iterator<PolyData> i = dataSet.iterator();
    for (int ordinal = window ; ordinal > 0 ; ordinal-- ) {
      if (i.hasNext()) {
        PolyData datum = i.next();
        plethCurve.addElement(new DataPoint(ordinal, datum.getPleth()));
        breathCurve.addElement(new DataPoint(ordinal, datum.getBreath()));
        gsrCurve.addElement(new DataPoint(ordinal, datum.getGsr()));
        blushCurve.addElement(new DataPoint(ordinal, datum.getBlush()));
      } else {
        plethCurve.addElement(new DataPoint(ordinal, 0));
        breathCurve.addElement(new DataPoint(ordinal, 0));
        gsrCurve.addElement(new DataPoint(ordinal, 0));
        blushCurve.addElement(new DataPoint(ordinal, 0));
      }
    }
    plot.addElement(plethCurve);
    plot.addElement(breathCurve);
    plot.addElement(gsrCurve);
    plot.addElement(blushCurve);
    manager.connect(plot);
  }

  @Override
  public void setSize(int width, int height) {
    super.setSize(width, height);
    delegate.setSize(width, height);
  }

  
}
