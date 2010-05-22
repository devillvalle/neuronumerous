package com.neuronumerous.defcon.lies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

  public final PolygraphDataSource polysource;
  
  public Main(PolygraphDataSource polysource) {
    this.polysource = polysource;
  }
  
  /**
   * Main event loop.
   */
  public void run() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    //BufferedReader reader = Files.newReader(aFile, Charsets.US_ASCII);
    readFromReader(reader);
  }

  public void readFromReader(BufferedReader reader) {
		StringBuilder sb = new StringBuilder();
		String line = null;
		while (line != null) {
			try {
			    line = reader.readLine();
			} catch (IOException io) {
				System.err.println("Error reading line");
			}
			while(line != null) {
				sb.append(line);
				try {
				    line = reader.readLine();
				} catch (IOException io) {
	        System.err.println("Error reading line");
				}
			}
		}
		List<PolyData> pd = polysource.createFromRawData(sb.toString());
		System.out.println(pd);
	}
  
  /**
   * Main entry point
   */
  public static void main(String[] args) {
    Main app = new Main(new PolygraphDataSource());
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
      @Override public void run() {
        System.out.println("Shutdown detected - exiting.");
        System.exit(0);
      }
    }));
    app.run();
  }

}
