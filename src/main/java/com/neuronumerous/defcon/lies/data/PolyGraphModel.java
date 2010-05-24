package com.neuronumerous.defcon.lies.data;

import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class PolyGraphModel {
  
  private Set<ActionListener> listeners = new HashSet<ActionListener>();
  
  private Queue<PolyData> dataElements = new FixedSizeQueue<PolyData>(30);
  
  public PolyGraphModel() {
    
  }

  public void addActionListener(ActionListener actionListener) {
    listeners.add(actionListener);
  }
  
  
  private static class FixedSizeQueue<T> extends LinkedList<T> {

    private int capacity = 0;
    
    public FixedSizeQueue(int capacity) {
      super();
      this.capacity = capacity;
    }
    
    public void setCapacity(int capacity) { this.capacity = capacity; }
    
    public int getCapacity() { return capacity; }
    
  }
}
