package com.neuronumerous.defcon.lies;

/**
 * A blocking data source
 *
 */
public interface Source<T> {

  public T next();
  
}
