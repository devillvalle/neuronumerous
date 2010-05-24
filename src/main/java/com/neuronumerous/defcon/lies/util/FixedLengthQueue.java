package com.neuronumerous.defcon.lies.util;

import java.util.AbstractList;
import java.util.LinkedList;
import java.util.Queue;

public class FixedLengthQueue<T> extends AbstractList<T> implements Queue<T> {

  private static final long serialVersionUID = 3534101191712535433L;

  private final LinkedList<T> internal;
  
  private int capacity = 0;
  
  public FixedLengthQueue(int capacity) {
    super();
    this.capacity = capacity;
    this.internal = new LinkedList<T>();
  }

  public void setCapacity(int capacity) { this.capacity = capacity; }

  public int getCapacity() { return capacity; }

  @Override
  public T element() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean offer(T e) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public T peek() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public T poll() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public T remove() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public T get(int index) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int size() {
    // TODO Auto-generated method stub
    return 0;
  }

 
  
}
