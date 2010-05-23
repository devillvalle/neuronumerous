/*
 * Copyright (c) 2010 Neuronumerous Collective 
 * Copyright (c) 2010 Christian Edward Gruber
 */
package com.neuronumerous.defcon.lies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
  
  private final Logger logger;
  
  public Main(Logger logger) {
    this.logger = logger;
  }
  
  /**
   * Main event loop.
   */
  public void run() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    //BufferedReader reader = Files.newReader(aFile, Charsets.US_ASCII);
    runFromReader(reader);
  }

  public void runFromReader(BufferedReader reader) {
    Source<PolyData> source = new PolyDataSource(reader,logger);
    PolyData pd = source.next();
		while (pd != null) {
		  System.out.println(pd);
		}
	}
  
  /**
   * Main entry point
   */
  public static void main(String[] args) {
    Main app = new Main(LoggerFactory.getLogger(Main.class));
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
      @Override public void run() {
        System.out.println("Shutdown detected - exiting.");
        System.exit(0);
      }
    }));
    app.run();
  }

}
