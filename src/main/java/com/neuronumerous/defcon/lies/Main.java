/*
 * Copyright (c) 2010 Neuronumerous Collective 
 * Copyright (c) 2010 Christian Edward Gruber
 */
package com.neuronumerous.defcon.lies;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.neuronumerous.defcon.lies.data.PolyData;
import com.neuronumerous.defcon.lies.data.PolyDataImpl;
import com.neuronumerous.defcon.lies.data.PolyDataSource;
import com.neuronumerous.defcon.lies.util.Source;
import com.neuronumerous.defcon.lies.util.Ticker;

public class Main {
  
  private Logger logger;
  public static Injector APP_INJECTOR;
  
  public Main(Logger logger) {
    this.logger = logger;
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
		  pd = aggregatePolyDatas(pd.getTimestamp(), pds);
		  System.out.println(pd);
		  pd = currentPd;
		  ticker.block(1, TimeUnit.SECONDS);
		}
	}

  public PolyData aggregatePolyDatas(int timestamp, List<PolyData> elements) {
    if (elements.size() <= 0) return new PolyDataImpl(timestamp,null,null,null,null);
    long gsr = 0, breath = 0, pleth = 0, blush = 0; 
    for (PolyData data : elements) {
      gsr+=data.getGsr();
      breath+=data.getBreath();
      pleth+=data.getPleth();
      blush+=data.getBlush();    
    }
    return new PolyDataImpl(
        timestamp, 
        (int)(gsr/elements.size()), 
        (int)(pleth/elements.size()), 
        (int)(breath/elements.size()), 
        (int)(blush/elements.size()));
  }

  /**
   * Main entry point
   */
  public static void main(String[] args) throws InterruptedException {
    APP_INJECTOR = Guice.createInjector(new MainModule());
    Main app = new Main(Logger.getLogger(Main.class.getName()));
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
      @Override public void run() {
        System.out.println("Shutdown detected - exiting.");
        System.exit(0);
      }
    }));
    app.run();
  }

}
