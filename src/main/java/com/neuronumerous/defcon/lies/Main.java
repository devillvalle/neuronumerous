/*
 * Copyright (c) 2010 Neuronumerous Collective 
 * Copyright (c) 2010 Christian Edward Gruber
 */
package com.neuronumerous.defcon.lies;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.javabuilders.BuildResult;
import org.javabuilders.BuilderConfig;
import org.javabuilders.swing.SwingJavaBuilder;

import com.google.inject.Injector;
import com.neuronumerous.defcon.lies.data.PolyData;
import com.neuronumerous.defcon.lies.data.PolyDataImpl;
import com.neuronumerous.defcon.lies.data.PolyDataSource;
import com.neuronumerous.defcon.lies.data.PolyDataUtil;
import com.neuronumerous.defcon.lies.data.PolyGraphModel;
import com.neuronumerous.defcon.lies.ui.MainFrame;
import com.neuronumerous.defcon.lies.ui.PolyGraph;
import com.neuronumerous.defcon.lies.util.ConfigUtil;
import com.neuronumerous.defcon.lies.util.Source;
import com.neuronumerous.defcon.lies.util.Ticker;

public class Main {
  
  public static String APP_PACKAGE = "com/neuronumerous/defcon/lies";
  public static Injector APP_INJECTOR;
  private final Logger logger;
  private final PolyDataUtil polyDataUtil;  
  
  public Main(Logger logger, PolyDataUtil polyDataUtil) {
    this.logger = logger;
    this.polyDataUtil = polyDataUtil;
  }



  
  
  
  /**
   * Main event loop.
   */
  public void run() throws InterruptedException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    //BufferedReader reader = Files.newReader(aFile, Charsets.US_ASCII);
    runFromReader(reader);
  }

  public void runFromReader(BufferedReader reader) throws InterruptedException {
    Source<PolyData> source = new PolyDataSource(reader, logger);
    PolyData pd = source.next();
    Ticker ticker = new Ticker();
		while (pd != null) {
		  // Aggregate
      PolyData currentPd = pd;
		  List<PolyData> pds = new ArrayList<PolyData>();
		  while (currentPd != null && currentPd.getTimestamp() == pd.getTimestamp()) {
		    pds.add(currentPd);
		    currentPd = source.next();
		  }
		  pd = polyDataUtil.aggregatePolyDatas(pd.getTimestamp(), pds);
		  System.out.println(pd);
		  pd = currentPd;
		  ticker.block(1, TimeUnit.SECONDS);
		}
	}


  private static void registerCustomComponents(BuilderConfig config) {
    config.addType("PolyGraph", PolyGraph.class);
  }

  /**
   * Main entry point
   */
  public static void main(String[] args) throws InterruptedException {
//    APP_INJECTOR = Guice.createInjector(new MainModule());
//    Main app = APP_INJECTOR.getInstance(Main.class);
//    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//      @Override public void run() {
//        System.out.println("Shutdown detected - exiting.");
//        System.exit(0);
//      }
//    }));
//    app.run();
    

    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        BuilderConfig config = SwingJavaBuilder.getConfig();
        registerCustomComponents(config);
        config.addResourceBundle("MainFrame");
        try {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
          
          MainFrame mainFrame = new MainFrame();
          BuildResult result = mainFrame.getJavaBuilderResult();
          PolyGraph pg = (PolyGraph)result.get("polygraph");

          PolyGraphModel model = new PolyGraphModel(ConfigUtil.DATA_POINTS);
          // can't seem to make binding work directly.  Do this in the mean-time.
          mainFrame.setPolyModel(model);
          pg.setModel(model);
          pg.setWindow(ConfigUtil.DATA_POINTS);
          for (int i = 0 ; i < ConfigUtil.DATA_POINTS ; i++) {
            model.offer(new PolyDataImpl(i, 0, 0, 0, 0));
          }
          mainFrame.setVisible(true);

        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    
    
  }
  

}
