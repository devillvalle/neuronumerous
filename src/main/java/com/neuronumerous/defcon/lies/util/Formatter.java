/*
 * Copyright (c) 2010 Neuronumerous Collective 
 * Copyright (c) 2010 Christian Edward Gruber
 */
package com.neuronumerous.defcon.lies.util;

/**
 * A simple formatter interface.
 * @author christianedwardgruber@gmail.com (Christian Edward Gruber)
 *
 * @param <T> The source time to be formatted
 * @param <V> The resulting format output type (i.e., a String)
 */
public interface Formatter<T, V> {
  public V format (T t);
}