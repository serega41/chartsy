/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.chartsy.htphasor;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import org.chartsy.main.ChartFrame;
import org.chartsy.main.chart.Indicator;
import org.chartsy.main.data.DataItem;
import org.chartsy.main.data.Dataset;
import org.chartsy.main.utils.DefaultPainter;
import org.chartsy.main.utils.Range;
import org.chartsy.main.utils.SerialVersion;
import org.chartsy.main.utils.StrokeGenerator;
import org.chartsy.talib.TaLibInit;
import org.chartsy.talib.TaLibUtilities;
import org.openide.nodes.AbstractNode;

/**
 *
 * @author joshua.taylor
 */
public class HTPhasor extends Indicator{

    private static final long serialVersionUID = SerialVersion.APPVERSION;
    public static final String FULL_NAME = "Hilbert Transform InPhase Quadrature";
    public static final String IN_PHASE = "htdcphasorinphase";
    public static final String QUADRATURE = "htdcphasorquadrature";


    private IndicatorProperties properties;

    //variables for TA-Lib utilization
    private int lookback;
    private double[] outputInPhase;
    private double[] outputQuadrature;
    private transient MInteger outBegIdx;
    private transient MInteger outNbElement;
    private transient Core core;

    //variables specific to Average Directional Index
    //NONE...

    //the next variable is used for fast calculations
    private Dataset calculatedDatasetInPhase;
    private Dataset calculatedDatasetQuadrature;

    public HTPhasor() {
        super();
        properties = new IndicatorProperties();
    }

    @Override
    public String getName(){ return FULL_NAME;}

    @Override
    public String getLabel() { return properties.getLabel(); }

    @Override
    public String getPaintedLabel(ChartFrame cf){ return ""; }

    @Override
    public Indicator newInstance(){ return new HTPhasor(); }

    @Override
    public boolean hasZeroLine(){ return true; }

    @Override
    public boolean getZeroLineVisibility(){ return true; }

    @Override
    public Color getZeroLineColor(){ return properties.getZeroLineColor(); }

    @Override
    public Stroke getZeroLineStroke(){ return StrokeGenerator.getStroke(1); }

    @Override
    public boolean hasDelimiters(){ return true; }

    @Override
    public boolean getDelimitersVisibility(){ return false; }

    @Override
    public double[] getDelimitersValues(){ return new double[] {}; }

    @Override
    public Color getDelimitersColor(){ return null; }

    @Override
    public Stroke getDelimitersStroke(){ return null; }

    @Override
    public Color[] getColors(){ return new Color[] {properties.getInPhaseLineColor(), properties.getQuadratureLineColor()}; }

    @Override
    public boolean getMarkerVisibility(){ return properties.getMarker(); }

    @Override
    public AbstractNode getNode(){ return new IndicatorNode(properties); }

    @Override
    public LinkedHashMap getHTML(ChartFrame cf, int i)
    {
        LinkedHashMap ht = new LinkedHashMap();

        DecimalFormat df = new DecimalFormat("#,##0.00");
        double[] values = getValues(cf, i);
        String[] labels = {"In Phase:", "Quadrature:"};

        ht.put(getLabel(), " ");
        if (values.length > 0)
        {
            Color[] colors = getColors();
            for (int j = 0; j < values.length; j++)
            {
                ht.put(getFontHTML(colors[j], labels[j]), getFontHTML(colors[j], df.format(values[j])));
            }
        }

        return ht;
    }

    @Override
    public Range getRange(ChartFrame cf)
    {
        Range range = super.getRange(cf);
        return range;
    }

    @Override
    public void paint(Graphics2D g, ChartFrame cf, Rectangle bounds)
    {
        Dataset inPhase = visibleDataset(cf, IN_PHASE);
        Dataset quadrature = visibleDataset(cf, QUADRATURE);

        if (inPhase != null && quadrature != null)
        {
            if (maximized)
            {
                Range range = getRange(cf);

                DefaultPainter.line(g, cf, range, bounds, inPhase, properties.getInPhaseLineColor(), properties.getInPhaseLineStroke()); // paint the signal
                DefaultPainter.line(g, cf, range, bounds, quadrature, properties.getQuadratureLineColor(), properties.getQuadratureLineStroke()); // paint the MACD
            }
        }
    }

    @Override
    public double[] getValues(ChartFrame cf)
    {
        Dataset inPhase = visibleDataset(cf, IN_PHASE);
        Dataset quadrature = visibleDataset(cf, QUADRATURE);

        double[] values = new double[2];

        if(inPhase != null && quadrature != null){
            values[0] = inPhase.getLastClose();
            values[1] = quadrature.getLastClose();
        }
        else
            return new double[] {,};

        return values;
    }

    @Override
    public double[] getValues(ChartFrame cf, int i)
    {
        Dataset inPhase = visibleDataset(cf, IN_PHASE);
        Dataset quadrature = visibleDataset(cf, QUADRATURE);

        double[] values = new double[2];

        if(inPhase != null && quadrature != null){
            values[0] = inPhase.getDataItem(i) != null ? inPhase.getCloseAt(i) : 0;
            values[1] = quadrature.getDataItem(i) != null ? quadrature.getCloseAt(i) : 0;
        }
        else
            return new double[] {,};

        return values;
    }

    @Override
    public void calculate()
    {
        Dataset initial = getDataset();
        int count = 0;
        if (initial != null && !initial.isEmpty())
            count = initial.getItemsCount();

        /**********************************************************************/
        //This entire method is basically a copy/paste action into your own
        //code. The only thing you have to change is the next few lines of code.
        //Choose the 'lookback' method and appropriate 'calculation function'
        //from TA-Lib for your needs. You'll also need to ensure you gather
        //everything for your calculation as well. Everything else should stay
        //basically the same

        //prepare ta-lib variables
        outputInPhase = new double[count];
        outputQuadrature = new double[count];
        outBegIdx = new MInteger();
        outNbElement = new MInteger();
        core = TaLibInit.getCore();//needs to be here for serialization issues

        //[your specific indicator variables need to be set first]
        //NONE...

        //now do the calculation over the entire dataset
        //[First, perform the lookback call if one exists]
        //[Second, do the calculation call from TA-lib]
        lookback = core.htPhasorLookback();
        core.htPhasor(0, count-1, initial.getCloseValues(), outBegIdx, outNbElement, outputInPhase, outputQuadrature);
        //Everything between the /***/ lines is what needs to be changed.
        //Everything else remains the same. You are done with your part now.
        /**********************************************************************/

        //fix the output array's structure. TA-Lib does NOT match
        //indicator index and dataset index automatically. That's what
        //this function does for us.
        outputInPhase = TaLibUtilities.fixOutputArray(outputInPhase, lookback);
        outputQuadrature = TaLibUtilities.fixOutputArray(outputQuadrature, lookback);

        calculatedDatasetInPhase = Dataset.EMPTY(initial.getItemsCount());
        for (int i = 0; i < outputInPhase.length; i++)
            calculatedDatasetInPhase.setDataItem(i, new DataItem(initial.getTimeAt(i), outputInPhase[i]));

        calculatedDatasetQuadrature = Dataset.EMPTY(initial.getItemsCount());
        for (int i = 0; i < outputQuadrature.length; i++)
            calculatedDatasetQuadrature.setDataItem(i, new DataItem(initial.getTimeAt(i), outputQuadrature[i]));

        addDataset(IN_PHASE, calculatedDatasetInPhase);
        addDataset(QUADRATURE, calculatedDatasetQuadrature);
    }


}
