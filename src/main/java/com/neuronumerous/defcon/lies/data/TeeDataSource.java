package com.neuronumerous.defcon.lies.data;

import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.neuronumerous.defcon.lies.util.Formatter;
import com.neuronumerous.defcon.lies.util.Source;

public class TeeDataSource<T> implements Source<T> {
  
  private final Logger logger = Logger.getLogger(TeeDataSource.class.getSimpleName());

	private Source<T> source;
	
	private FileWriter writer;

	private Formatter<T, String> formatter;
	
	public TeeDataSource(Source<T> source, FileWriter writer, Formatter<T, String> formatter) {
      this.source = source;
      this.writer = writer;
      this.formatter = formatter; 
	}
	@Override public T next() {
      T t = source.next();
      try {
  	    writer.append(formatter.format(t));
        writer.flush();
      } catch (Exception e) {
    	logger.log(Level.SEVERE, "Failed to write to writer.", e);
      }
      return t;
	}
}