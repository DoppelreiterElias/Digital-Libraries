package tugraz.digitallibraries;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import tugraz.digitallibraries.dataclasses.Author;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

/**
 * a GraphMousePlugin that offers popup menu support
 */
public class PopupGraphMousePlugin extends AbstractPopupGraphMousePlugin
    implements MouseListener {

    public PopupGraphMousePlugin() {
        this(MouseEvent.BUTTON3_MASK);
    }

    public PopupGraphMousePlugin(int modifiers) {
        super(modifiers);
    }

    /**
     * If this event is over a Vertex, pop up a menu to allow the user to
     * increase/decrease the voltage attribute of this Vertex
     *
     * @param e
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void handlePopup(MouseEvent e) {
        final VisualizationViewer<Author, Number> vv = (VisualizationViewer<Author, Number>) e
            .getSource();
        Point2D p = e.getPoint();// vv.getRenderContext().getBasicTransformer().inverseViewTransform(e.getPoint());

        GraphElementAccessor<Author, Number> pickSupport = vv.getPickSupport();
        if (pickSupport != null) {
            final Author v = pickSupport.getVertex(vv.getGraphLayout(), p.getX(), p.getY());
            if (v != null) {
                System.out.println("found author " + v.getForenames()[0] + " " + v.getSurnames()[0]);
                JPopupMenu popup = new JPopupMenu();
                popup.add(new AbstractAction("Open Author") {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        System.out.println("action performed" + e.getActionCommand());
                        // TODO: open author into detailed view
                        vv.repaint();
                    }
                });
                popup.add(new AbstractAction("...") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("action performed" + e.getActionCommand());
                        vv.repaint();
                    }
                });
                popup.show(vv, e.getX(), e.getY());
            } else {
                System.out.println("no author here");
                final Number edge = pickSupport.getEdge(vv.getGraphLayout(), p.getX(),
                    p.getY());
                if (edge != null) {
                    JPopupMenu popup = new JPopupMenu();
                    popup.add(new AbstractAction(edge.toString()) {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.err.println("got " + edge);
                        }
                    });
                    popup.show(vv, e.getX(), e.getY());

                }
            }
        }
    }
}