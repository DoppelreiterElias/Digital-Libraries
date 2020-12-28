package tugraz.digitallibraries.graph;

import edu.uci.ics.jung.algorithms.layout.FRLayout2;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.DefaultVertexLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;
import tugraz.digitallibraries.PopupGraphMousePlugin;
import tugraz.digitallibraries.dataclasses.Author;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import static tugraz.digitallibraries.graph.GraphUtils.MAX_EDGE_WIDTH;
import static tugraz.digitallibraries.graph.GraphUtils.MAX_VERTEX_SIZE;

public class GraphVisualizer {


    GraphCreator graphCreator;
    VisualizationViewer<Author, EdgeCoAuthorship> vv;

    protected Transformer<Author, String> vertex_label_none = new ConstantTransformer(null);
    protected Transformer<Author, String> vertex_label;

    protected DefaultModalGraphMouse gm = new DefaultModalGraphMouse();


    public GraphVisualizer(GraphCreator graphCr) {
        this.graphCreator = graphCr;
    }

    public VisualizationViewer<Author, EdgeCoAuthorship>  createCoAuthorVisualizer(Graph g) {

        // KKLayout or FRLayout or ISOMLayout
        FRLayout2<Author, EdgeCoAuthorship> layout = new FRLayout2<Author, EdgeCoAuthorship>(g);
        layout.setSize(new Dimension(400, 400));
        layout.initialize();

        vv = new VisualizationViewer<Author, EdgeCoAuthorship>(layout);
        vv.setPreferredSize(new Dimension(950, 950));

        setToTransformingMode();
        vv.setGraphMouse(gm);
        vv.setBackground(Color.gray);

        setVertexColor();
        setVertexSize(g);
        setVertexLabel();
        setEdgeSize();


        // right click menu
        gm.add(new PopupGraphMousePlugin());

        return vv;
    }

    private void setEdgeSize() {
        Transformer<EdgeCoAuthorship, Stroke> edgeStroke = new Transformer<EdgeCoAuthorship, Stroke>() {
            public Stroke transform(EdgeCoAuthorship s) {
                // EDGE SIZE
                int max_size_of_graph = graphCreator.getCoAuthorMaxEdgeWith();
                float scaling = ((float)s.getWeight() / max_size_of_graph) * MAX_EDGE_WIDTH;
                scaling = Math.max(scaling, 1);
                return new BasicStroke(scaling);
            }
        };
        vv.getRenderContext().setEdgeStrokeTransformer(edgeStroke);
    }

    private void setVertexSize(final Graph g) {

        Transformer<Author,Shape> vertexSize = new Transformer<Author,Shape>(){
            public Shape transform(Author i){
                Ellipse2D circle = new Ellipse2D.Double(-10, -10, 20, 20);
                int max_size_of_graph = graphCreator.getCoAuthorMaxDegree();
                float scaling = ((float)g.inDegree(i) / max_size_of_graph) * MAX_VERTEX_SIZE;

                scaling = Math.max(scaling, 1);
                return AffineTransform.getScaleInstance(scaling, scaling).createTransformedShape(circle);
            }
        };

        vv.getRenderContext().setVertexShapeTransformer(vertexSize);
    }


    private void setVertexColor() {
        Transformer<Author,Paint> vertexColor = new Transformer<Author,Paint>() {
            public Paint transform(Author i) {
                // TODO: make costum vertex color
                return Color.GREEN;
            }
        };
        vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);

    }

    public void setToPickingMode() {
        gm.setMode(ModalGraphMouse.Mode.PICKING);
    }

    public void setToTransformingMode() {
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
    }

    public void hideVertexLabels() {
        vv.getRenderContext().setVertexLabelTransformer(vertex_label_none);
    }

    public void showVertexLabels() {
        vv.getRenderContext().setVertexLabelTransformer(vertex_label);
    }

    private void setVertexLabel() {

        final Color vertexLabelColor = Color.WHITE;
        DefaultVertexLabelRenderer vertexLabelRenderer =
            new DefaultVertexLabelRenderer(vertexLabelColor)
            {
                @Override
                public <V> Component getVertexLabelRendererComponent(
                    JComponent vv, Object value, Font font,
                    boolean isSelected, V vertex)
                {
                    super.getVertexLabelRendererComponent(
                        vv, value, font, isSelected, vertex);
                    setForeground(vertexLabelColor);
                    return this;
                }
            };

        vv.getRenderContext().setVertexLabelRenderer(vertexLabelRenderer);
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.AUTO);
        vertex_label = new ToStringLabeller<Author>();
        showVertexLabels();
    }

    public VisualizationViewer<Author, EdgeCitation> createCitationVisualizer(Graph g) {

        return null;
    }

}
