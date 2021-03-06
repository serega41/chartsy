package org.chartsy.demarkssetup;

import java.awt.Color;
import java.awt.Stroke;
import java.beans.PropertyEditorSupport;
import java.util.logging.Level;
import org.chartsy.main.chart.AbstractPropertiesNode;
import org.chartsy.main.utils.PricePropertyEditor;
import org.chartsy.main.utils.SerialVersion;
import org.chartsy.main.utils.StrokeGenerator;
import org.chartsy.main.utils.StrokePropertyEditor;
import org.openide.nodes.Sheet;

/**
 *
 * @author Viorel
 */
public class OverlayNode extends AbstractPropertiesNode {

    private static final long serialVersionUID = SerialVersion.APPVERSION;

    public OverlayNode()
    {
        super("DeMark's Setup Properties");
    }

    public OverlayNode(OverlayProperties overlayProperties)
    {
        super("DeMark's Setup Properties", overlayProperties);
    }

    @SuppressWarnings("unchecked")
    protected @Override Sheet createSheet()
    {
        Sheet sheet = new Sheet();
        sheet.put(getSets()[0]);
        return sheet;
    }

    public @Override Sheet.Set[] getSets()
    {
        Sheet.Set[] sets = new Sheet.Set[1];
        Sheet.Set set = getPropertiesSet();
        sets[0] = set;

        try
        {
            // Label
            set.put(getProperty(
                    "Label", // property name
                    "Sets the label", // property description
                    OverlayProperties.class, // properties class
                    String.class, // property class
                    PropertyEditorSupport.class, // property editor class (null if none)
                    "getLabel", // get method name
                    "setLabel", // set method name
                    OverlayProperties.LABEL // default property value
                    ));
            // Price
            set.put(getProperty(
                    "Price", // property name
                    "Sets the price type", // property description
                    OverlayProperties.class, // properties class
                    String.class, // property class
                    PricePropertyEditor.class, // property editor class (null if none)
                    "getPrice", // get method name
                    "setPrice", // set method name
                    OverlayProperties.PRICE // default property value
                    ));
            // Line Color
            set.put(getProperty(
                    "Line Color", // property name
                    "Sets the line color", // property description
                    OverlayProperties.class, // properties class
                    Color.class, // property class
                    null, // property editor class (null if none)
                    "getColor", // get method name
                    "setColor", // set method name
                    OverlayProperties.COLOR // default property value
                    ));
            // Line Style
            set.put(getProperty(
                    "Line Style", // property name
                    "Sets the line style", // property description
                    OverlayProperties.class, // properties class
                    Stroke.class, // property class
                    StrokePropertyEditor.class, // property editor class (null if none)
                    "getStroke", // get method name
                    "setStroke", // set method name
                    StrokeGenerator.getStroke(OverlayProperties.STROKE_INDEX) // default property value
                    ));
        }
        catch (NoSuchMethodException ex)
        {
            LOG.log(Level.SEVERE, "[LucasSeriesNode] : Method does not exist.", ex);
        }

        return sets;
    }

}
