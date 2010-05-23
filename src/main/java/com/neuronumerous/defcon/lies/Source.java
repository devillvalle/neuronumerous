/*
 * Copyright (c) 2010 Neuronumerous Collective 
 * Copyright (c) 2010 Christian Edward Gruber
 */
package com.neuronumerous.defcon.lies;

/**
 * A blocking data source
 *
 */
public interface Source<T> {

  public T next();
  
}
