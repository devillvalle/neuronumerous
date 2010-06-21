package com.neuronumerous.defcon.lies.ui;

import java.util.Random;

import javax.swing.JFrame;

import org.javabuilders.BuildResult;
import org.javabuilders.annotations.DoInBackground;
import org.javabuilders.event.BackgroundEvent;
import org.javabuilders.event.CancelStatus;
import org.javabuilders.swing.SwingJavaBuilder;

import com.neuronumerous.defcon.lies.data.PolyDataImpl;
import com.neuronumerous.defcon.lies.data.PolyGraphModel;

@SuppressWarnings("unused")
public class MainFrame extends JFrame {
  private static final long serialVersionUID = 6493344221859264556L;

  private BuildResult       result;

  private PolyGraphModel    polyModel;
  
  private BackgroundEvent   currentStartEvent = null;

  public PolyGraphModel getPolyModel() {
    return polyModel;
  }

  public void setPolyModel(PolyGraphModel model) {
    this.polyModel = model;
  }

  public MainFrame() {
    result = SwingJavaBuilder.build(this);
  }

  @DoInBackground(cancelable = true, blocking = false)
  private void start(BackgroundEvent evt) {
    System.out.println("Fuzzing data.");
    this.currentStartEvent = evt;
    while (true) {
      if (evt.getCancelStatus() != CancelStatus.REQUESTED) {
        try {
          Thread.sleep(100);
          fuzz();
        } catch (InterruptedException e) {
          break;
        }
      } else {
        break;
      }
    }
    evt.setCancelStatus(CancelStatus.PROCESSING);
    System.out.println("Cancelling...");
    evt.setCancelStatus(CancelStatus.COMPLETED);
    currentStartEvent = null;
    System.out.println("Stopped.");
  }

  private void stop() {
    if (currentStartEvent != null) {
      currentStartEvent.setCancelStatus(CancelStatus.REQUESTED);
    }
  }

  private void fuzz() {
    Random r = new Random();
    polyModel.offer(new PolyDataImpl(0, 95 + r.nextInt(10), 140 + r.nextInt(20), 130 + r
            .nextInt(10), 120 + r.nextInt(10)));
  }

  private void replot() {
    if (polyModel != null) {
      polyModel.notifyListeners();
    }
  }

  public BuildResult getJavaBuilderResult() {
    return result;
  }

}
