package com.neuronumerous.defcon.lies.ui;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.javabuilders.BuildResult;
import org.javabuilders.annotations.DoInBackground;
import org.javabuilders.event.BackgroundEvent;
import org.javabuilders.event.CancelStatus;
import org.javabuilders.swing.SwingJavaBuilder;

import com.neuronumerous.defcon.lies.data.PolyData;
import com.neuronumerous.defcon.lies.data.PolyDataImpl;
import com.neuronumerous.defcon.lies.data.PolyDataSource;
import com.neuronumerous.defcon.lies.data.PolyGraphModel;
import com.neuronumerous.defcon.lies.util.Source;

@SuppressWarnings("unused")
public class MainFrame extends JFrame {
  private static final long serialVersionUID = 6493344221859264556L;
  private static final Logger logger = Logger.getLogger(MainFrame.class.getName());

  private BuildResult       result;

  private PolyGraphModel    polyModel;
  
  private BackgroundEvent   currentStartEvent = null;
  
  private Source<PolyData>  source;
  
  private File              currentFolder;

  private File              input;
  
  private File              output;
  

  public PolyGraphModel getPolyModel() {
    return polyModel;
  }

  public void setPolyModel(PolyGraphModel model) {
    this.polyModel = model;
  }

  public MainFrame() {
    result = SwingJavaBuilder.build(this);
  }
  
  private void onFileMenuSimulate() {
    this.source = new Source<PolyData>() {
      private final Random r = new Random();      
      public PolyData next() {
        try{ 
          Thread.sleep(100); 
        } catch (InterruptedException e) {
          return null;
        }
        return new PolyDataImpl(0, 95 + r.nextInt(10), 140 + r.nextInt(20), 130 + r.nextInt(10), 120 + r.nextInt(10));
      }
    };
  } 
  
  private void onFileMenuStream() {
    logger.info("onFileMenuStream was invoked!"); 
    File input = selectFile("Open input device or file to be streamed...");
    if (input != null && input.exists()) {
      this.input = input;
    } else {
      this.input = null;
      this.output = null;
      return;
    }
    File output = selectFile("Open file to save the streamed data...");
    if (output != null && output.exists()) {
      this.output = output;
    } else {
      this.output = null;
      return;
    }
    try {
      BufferedReader reader = new BufferedReader(new FileReader(input));
      this.source = new PolyDataSource(reader, Logger.getLogger(PolyDataSource.class.getName())){
        @Override public PolyData next() {
          PolyData pd = super.next();
          logger.info("Processing PolyData node: " + pd);
          return pd;
        }
      };
    } catch (FileNotFoundException e) {
      logger.severe("File not found: " + input);
      this.source = null;
      this.input = null;
      this.output = null;
    }
     
  } 

  private void onFileMenuReplay(ActionEvent e) {
    logger.info("onFileMenuReplay was invoked!"); 
    File input = selectFile("Open file to be replayed...");
    output = null;
    if (input != null && input.exists()) {
      this.input = input;
    } else {
      this.input = null;
      return;
    }
    try {
      BufferedReader reader = new BufferedReader(new FileReader(input));
      this.source = new PolyDataSource(reader, Logger.getLogger(PolyDataSource.class.getName()));
    } catch (FileNotFoundException fnfe) {
      logger.severe("File not found: " + input);
      this.source = null;
      this.input = null;
      this.output = null;
    }
  }
  
  private boolean isStartEnabled() {
    return source != null;
  }

  private File selectFile(String dialogTitle) {
    
    JFileChooser jfc = new JFileChooser(currentFolder);
    jfc.setFileHidingEnabled(false);
    jfc.setDialogTitle(dialogTitle);

    int returnVal = jfc.showOpenDialog(this);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = jfc.getSelectedFile();
      //This is where a real application would open the file.
      logger.info("Opening: " + file.getName() + ".");
      if (file != null) {
        currentFolder = file.getParentFile();
      }
      return file;
    } else {
      logger.info("Open command cancelled by user.");
      return null;
    }
  }
  @DoInBackground(cancelable = true, blocking = false)
  private void start(BackgroundEvent evt) {
    logger.info("Starting visualization.");
    if (source == null) {
      JOptionPane.showMessageDialog(this, 
              "Please select a data source from the menu.", 
              "No Data Source...", 
              JOptionPane.ERROR_MESSAGE);
      return;
    }
    this.currentStartEvent = evt;
    while (evt.getCancelStatus() != CancelStatus.REQUESTED && source != null) {
      try {
        populateData();
      } catch (Throwable e) {
        break;
      }
    }
    evt.setCancelStatus(CancelStatus.PROCESSING);
    logger.info("Cancelling visualization.");
    evt.setCancelStatus(CancelStatus.COMPLETED);
    currentStartEvent = null;
    logger.info("Visualization Stopped.");
  }

  private void stop() {
    logger.info("Stopping visualization.");
    if (currentStartEvent != null && currentStartEvent.isCancelable()) {
      currentStartEvent.setCancelStatus(CancelStatus.REQUESTED);
    }
  }

  private void populateData() {
    PolyData pd = source.next();
    polyModel.offer(pd);
  }

  private void replot() {
    if (polyModel != null) {
      polyModel.notifyListeners();
    }
  }

  public BuildResult getJavaBuilderResult() {
    return result;
  }

}
