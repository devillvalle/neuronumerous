package com.neuronumerous.defcon.lies.data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class PolyGraphModel {
  
  public final int window;

  public static final int DATA_UPDATED = 0;
  
  private Set<ActionListener> listeners = new HashSet<ActionListener>();
  
  private final Queue<PolyData> dataElements;
  
  public PolyGraphModel(int window) {
    this.window = window;
    dataElements = new FixedSizeQueue<PolyData>(window);
  }

  public void addActionListener(ActionListener actionListener) {
    listeners.add(actionListener);
  }
  
  public void removeActionListener(ActionListener actionListener) {
    listeners.remove(actionListener);
  }
  
  public Queue<PolyData> getDataElements() {
    LinkedList<PolyData> copy = new LinkedList<PolyData>(dataElements);
    for (int i = window - copy.size(); i < window; i++) {
      copy.addLast(new PolyDataImpl(0, 0, 0, 0, 0));
    }
    return copy;
  }
  
  public void offer(PolyData pd) {
    dataElements.offer(pd);
    notifyListeners();
  }
  
  public void notifyListeners() {
    for (ActionListener listener : listeners) {
      listener.actionPerformed(new ActionEvent(this, DATA_UPDATED, "Data Updated"));
    }
  }

  private static class FixedSizeQueue<T> extends LinkedList<T> {
    private static final long serialVersionUID = 5059541195757873121L;
    
    private int capacity = 0;
    
    public FixedSizeQueue(int capacity) {
      super();
      this.capacity = capacity;
    }

    /**
     * Takes an object and, if the queue is at capacity, removes the last object and adds the
     * given object.  Otherwise it merely adds the object to the queue.
     */
    @Override public boolean offer(T e) { 
      if (size() >= capacity) {
        removeLast();
      } 
      addFirst(e);
      return true;
    }
    
    
  }
}
