package org.chartsy.macd;

import java.awt.Color;
import java.awt.Stroke;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.chartsy.main.utils.StrokeGenerator;

/**
 *
 * @author viorel.gheba
 */
public class IndicatorProperties implements Serializable {

    private static final long serialVersionUID = 101L;

    public static final int FAST = 12;
    public static int SLOW = 26;
    public static int SMOOTH = 9;
    public static String LABEL = "MACD";
    public static boolean MARKER = true;
    public static Color ZERO_LINE_COLOR = new Color(0xeeeeec);
    public static int ZERO_STROKE_INDEX = 0;
    public static boolean ZERO_LINE_VISIBILITY = true;
    public static Color HISTOGRAM_COLOR = new Color(0x204a87);
    public static Color SIGNAL_COLOR = new Color(0x5c3566);
    public static int SIGNAL_STROKE_INDEX = 0;
    public static Color MACD_COLOR = new Color(0x4e9a06);
    public static int MACD_STROKE_INDEX = 0;

    private int fast = FAST;
    private int slow = SLOW;
    private int smooth = SMOOTH;
    private String label = LABEL;
    private boolean marker = MARKER;
    private Color zeroLineColor = ZERO_LINE_COLOR;
    private int zeroLineStrokeIndex = ZERO_STROKE_INDEX;
    private boolean zeroLineVisibility = ZERO_LINE_VISIBILITY;
    private Color histogramColor = HISTOGRAM_COLOR;
    private Color signalColor = SIGNAL_COLOR;
    private int signalStrokeIndex = SIGNAL_STROKE_INDEX;
    private Color macdColor = MACD_COLOR;
    private int macdStrokeIndex = MACD_STROKE_INDEX;

    public IndicatorProperties() {}

    public int getFast() { return fast; }
    public void setFast(int i) { fast = i; }

    public int getSlow() { return slow; }
    public void setSlow(int i) { slow = i; }

    public int getSmooth() { return smooth; }
    public void setSmooth(int i) { smooth = i; }

    public String getLabel() { return label; }
    public void setLabel(String s) { label = s; }

    public boolean getMarker() { return marker; }
    public void setMarker(boolean b) { marker = b; }

    public Color getZeroLineColor() { return zeroLineColor; }
    public void setZeroLineColor(Color c) { zeroLineColor = c; }

    public int getZeroLineStrokeIndex() { return zeroLineStrokeIndex; }
    public void setZeroLineStrokeIndex(int i) { zeroLineStrokeIndex = i; }
    public Stroke getZeroLineStroke() { return StrokeGenerator.getStroke(zeroLineStrokeIndex); }
    public void setZeroLineStroke(Stroke s) { zeroLineStrokeIndex = StrokeGenerator.getStrokeIndex(s); }

    public boolean getZeroLineVisibility() { return zeroLineVisibility; }
    public void setZeroLineVisibility(boolean b) { zeroLineVisibility = b; }

    public Color getHistogramColor() { return histogramColor; }
    public void setHistogramColor(Color c) { histogramColor = c; }

    public Color getSignalColor() { return signalColor; }
    public void setSignalColor(Color c) { signalColor = c; }

    public int getSignalStrokeIndex() { return signalStrokeIndex; }
    public void setSignalStrokeIndex(int i) { signalStrokeIndex = i; }
    public Stroke getSignalStroke() { return StrokeGenerator.getStroke(signalStrokeIndex); }
    public void setSignalStroke(Stroke s) { signalStrokeIndex = StrokeGenerator.getStrokeIndex(s); }

    public Color getMacdColor() { return macdColor; }
    public void setMacdColor(Color c) { macdColor = c; }

    public int getMacdStrokeIndex() { return macdStrokeIndex; }
    public void setMacdStrokeIndex(int i) { macdStrokeIndex = i; }
    public Stroke getMacdStroke() { return StrokeGenerator.getStroke(macdStrokeIndex); }
    public void setMacdStroke(Stroke s) { macdStrokeIndex = StrokeGenerator.getStrokeIndex(s); }

    private List listeners = Collections.synchronizedList(new LinkedList());

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        listeners.add(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        listeners.remove(pcl);
    }

    private void fire(String propertyName, Object old, Object nue) {
        PropertyChangeListener[] pcls = (PropertyChangeListener[]) listeners.toArray(new PropertyChangeListener[0]);
        for (int i = 0; i < pcls.length; i++) {
            pcls[i].propertyChange(new PropertyChangeEvent(this, propertyName, old, nue));
        }
    }

}