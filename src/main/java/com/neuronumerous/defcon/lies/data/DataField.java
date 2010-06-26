/*
 * Copyright (c) 2010 Neuronumerous Collective 
 * Copyright (c) 2010 Christian Edward Gruber
 */
package com.neuronumerous.defcon.lies.data;

public class DataField {

  public final String name;
  public final String data;
  public final String stringRep;

  public DataField(final String name, final String data) {
    this.name = name;
    this.data = data;
    this.stringRep = getClass().getSimpleName() + "[" 
      + "name=" + name + ","
      + "data=" + data
      + "]";
  }

  @Override
  public String toString() {
    return stringRep;
  }
}
