package tugraz.digitallibraries.ui;

import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import javafx.geometry.Point2D;

import java.awt.Cursor;

import javafx.event.EventHandler;

import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractGraphMousePlugin;
import edu.uci.ics.jung.visualization.transform.MutableTransformer;
import javafx.scene.input.MouseEvent;

/**
 * CustomGraphMousePlugin uses a MouseButtonOne press and
 * drag gesture to translate the graph display in the x and y
 * direction. The default MouseButtonOne modifier can be overridden
 * to cause a different mouse gesture to translate the display.
 *
 * @author Tom Nelson, modified by Florian Werkl
 */
public class CustomGraphMousePlugin<V,E> extends AbstractGraphMousePlugin
        implements EventHandler<MouseEvent>
{

    /**
     *
     */
    public CustomGraphMousePlugin()
    {
        this(0);
    }

    /**
     * create an instance with passed modifer value
     *
     * @param modifiers the mouse event modifier to activate this function
     */
    public CustomGraphMousePlugin(int modifiers)
    {
        super(0); //passing zero because the modifiers are not supported in javafx
        this.cursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
    }

    @Override
    public void handle(MouseEvent e)
    {
        System.out.println("Yes.\n");

        if(e.getEventType() == MouseEvent.MOUSE_PRESSED)
            mousePressed(e);
        else if(e.getEventType() == MouseEvent.MOUSE_RELEASED)
            mouseReleased(e);
        else if(e.getEventType() == MouseEvent.MOUSE_DRAGGED)
            mouseDragged(e);
    }

    /**
     * Check the event modifiers. Set the 'down' point for later
     * use. If this event satisfies the modifiers, change the cursor
     * to the system 'move cursor'
     *
     * @param e the event
     */
    public void mousePressed(MouseEvent e)
    {
        VisualizationViewer<?, ?> vv = (VisualizationViewer<?, ?>) e.getSource();
        down_fx = new Point2D(e.getX(), e.getY());
        vv.setCursor(cursor);
    }

    /**
     * unset the 'down' point and change the cursoe back to the system
     * default cursor
     */
    public void mouseReleased(MouseEvent e)
    {
        VisualizationViewer<?, ?> vv = (VisualizationViewer<?, ?>) e.getSource();
        down = null;
        vv.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * chack the modifiers. If accepted, translate the graph according
     * to the dragging of the mouse pointer
     *
     * @param e the event
     */
    public void mouseDragged(MouseEvent e)
    {
        VisualizationViewer<?, ?> vv = (VisualizationViewer<?, ?>) e.getSource();

        MutableTransformer modelTransformer = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT);
        vv.setCursor(cursor);
        try
        {
            //Point2D q = vv.getRenderContext().getMultiLayerTransformer().inverseTransform(down);
            //Point2D p = vv.getRenderContext().getMultiLayerTransformer().inverseTransform(new Point((int)e.getX(), (int)e.getY()));
            Point2D p = down_fx;
            Point2D q = new Point2D(e.getX(), e.getY());

            Point2D delta = p.subtract(q);

            /*
            float dx = (float) (p.getX() - q.getX());
            float dy = (float) (p.getY() - q.getY());*/

            modelTransformer.translate(delta.getX(), delta.getY());
            down_fx = q;

        } catch (RuntimeException ex)
        {
            System.err.println("down = " + down + ", e = " + e);
            throw ex;
        }

        e.consume();
        vv.repaint();
    }

    public void mouseClicked(MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

    public void mouseEntered(MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

    public void mouseExited(MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

    public void mouseMoved(MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

    protected Point2D down_fx;
}
