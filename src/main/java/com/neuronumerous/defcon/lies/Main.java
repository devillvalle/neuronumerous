/*
 * Copyright (c) 2010 Neuronumerous Collective 
 * Copyright (c) 2010 Christian Edward Gruber
 */
package com.neuronumerous.defcon.lies;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.javabuilders.BuildResult;
import org.javabuilders.BuilderConfig;
import org.javabuilders.swing.SwingJavaBuilder;

import com.google.inject.Injector;
import com.neuronumerous.defcon.lies.data.PolyDataImpl;
import com.neuronumerous.defcon.lies.data.PolyGraphModel;
import com.neuronumerous.defcon.lies.ui.MainFrame;
import com.neuronumerous.defcon.lies.ui.PolyGraph;
import com.neuronumerous.defcon.lies.util.ConfigUtil;

public class Main {
  
//  public static String APP_PACKAGE = "com/neuronumerous/defcon/lies";

  private static void registerCustomComponents(BuilderConfig config) {
    config.addType("PolyGraph", PolyGraph.class);
  }

  /**
   * Main entry point
   */
  public static void main(String[] args) throws InterruptedException {
  
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
      @Override public void run() {
        System.out.println("Shutdown detected - exiting.");
        System.exit(0);
      }
    }));

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
