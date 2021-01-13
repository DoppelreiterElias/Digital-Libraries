package tugraz.digitallibraries.graph;

import edu.uci.ics.jung.algorithms.layout.FRLayout2;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.DefaultEdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.DefaultVertexLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import javafx.embed.swing.SwingNode;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;
import tugraz.digitallibraries.PopupGraphMousePlugin;
import tugraz.digitallibraries.dataclasses.Author;
import tugraz.digitallibraries.dataclasses.AuthorType;
import tugraz.digitallibraries.ui.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import static tugraz.digitallibraries.graph.GraphUtils.MAX_EDGE_WIDTH;
import static tugraz.digitallibraries.graph.GraphUtils.MAX_VERTEX_SIZE;

public class GraphVisualizer {


    VisualizationViewer<Author, EdgeCoAuthorship> vv_co;
    VisualizationViewer<Author, EdgeCitation> vv_ci;

    protected Transformer<Author, String> vertex_label_none = new ConstantTransformer(null);
    protected Transformer<Author, String> vertex_labels_co;
    protected Transformer<Author, String> vertex_labels_ci;

    protected Transformer<EdgeCoAuthorship, String> co_edge_label_none = new ConstantTransformer(null);
    protected Transformer<EdgeCoAuthorship, String> co_edge_label;

    protected Transformer<EdgeCitation, String> ci_edge_label_none = new ConstantTransformer(null);
    protected Transformer<EdgeCitation, String> ci_edge_label;

    protected DefaultModalGraphMouse gm = new DefaultModalGraphMouse();


    // swing node is a javaFX element, we can insert into swingNode java swing elements, and make them in javafx visible
    private final SwingNode cit_graph_node_ = new SwingNode();
    private final SwingNode co_auth_graph_node_ = new SwingNode();

    public GraphVisualizer() {
    }

    public SwingNode getCoAuthGraphNode() { return co_auth_graph_node_;}
    public SwingNode getCitGraphNode() { return cit_graph_node_;}


    public void updateBothGraphsAndCreateSubgraphs(Author author, MainController mainController) {

        if(author.getAuthorType() == AuthorType.PaperAuthor || author.getAuthorType() == AuthorType.BOTH) {

            Graph coauthor_subgraph = GraphCreator.getInstance().createSubgraph(GraphUtils.GraphType.COAUTHOR_GRAPH, author);
            showCoAuthorGraph(coauthor_subgraph, mainController);
        }
        else {
            // show empty graph in CoAuthorGraph because it is a cited author without CoAuthors
            showCoAuthorGraph(new UndirectedSparseGraph<>(), mainController);
        }

        Graph citation_subgraph = GraphCreator.getInstance().createSubgraph(GraphUtils.GraphType.CITATION_GRAPH, (Author) author);
        showCitationGraph(citation_subgraph, mainController);
    }




    private void setVertexSizeCO(final Graph g) {

        Transformer<Author,Shape> vertexSize = new Transformer<Author,Shape>(){
            public Shape transform(Author i){
                // VERTEX SIZE - the bigger the vertex, the more coAuthors this author has
                Ellipse2D circle = new Ellipse2D.Double(-10, -10, 20, 20);
                int max_size_of_graph = GraphCreator.getInstance().getCoAuthorMaxDegree();
                float scaling = ((float)g.inDegree(i) / max_size_of_graph) * MAX_VERTEX_SIZE;

                scaling = Math.max(scaling, 1);
                return AffineTransform.getScaleInstance(scaling, scaling).createTransformedShape(circle);
            }
        };

        vv_co.getRenderContext().setVertexShapeTransformer(vertexSize);
    }

    private void setVertexColor(VisualizationViewer vv) {
        Transformer<Author,Paint> vertexColor = new Transformer<Author,Paint>() {
            public Paint transform(Author i) {
                if(i.getAuthorType().equals(AuthorType.PaperAuthor))
                    return Color.GREEN;
                else if(i.getAuthorType().equals(AuthorType.ReferenceAuthor))
                    return Color.YELLOW;
                else
                    return Color.RED;
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

    public void setToGlobalView( MainController main)
    {
        showCitationGraph(GraphCreator.getInstance().getCitationGraph(), main);
        showCoAuthorGraph(GraphCreator.getInstance().getCoAuthorGraph(), main);
    }

    public void showCoAuthorGraph(final Graph g, MainController main)
    {
        VisualizationViewer<Author, EdgeCoAuthorship> vv = createCoAuthorVisualizationView(g, main);
        co_auth_graph_node_.setContent(vv);
    }

    public void showCitationGraph(final Graph g, MainController main)
    {
        VisualizationViewer<Author, EdgeCitation> vv = createCitationVisualizationView(g, main);
        cit_graph_node_.setContent(vv);
    }

    private void insertLegendToGraph(VisualizationViewer vv) {

        JLabel label = new JLabel();
        ImageIcon icon = new ImageIcon("src/main/resources/images/graph_legend.png");
        label.setIcon(icon);
        vv.add(label);
    }


    /*
    --------------------------------------------------------------------------------------------------------------------
    ---------------------------------------------------- CO AUTHOR GRAPH -----------------------------------------------
    --------------------------------------------------------------------------------------------------------------------
    */
    public VisualizationViewer<Author, EdgeCoAuthorship> createCoAuthorVisualizationView(Graph g, MainController main_controller) {

        // KKLayout or FRLayout or ISOMLayout
        FRLayout2<Author, EdgeCoAuthorship> layout = new FRLayout2<Author, EdgeCoAuthorship>(g);
        layout.setSize(new Dimension(800, 800));
        layout.initialize();

        vv_co = new VisualizationViewer<Author, EdgeCoAuthorship>(layout);
        vv_co.setPreferredSize(new Dimension(950, 950));

        setToTransformingMode();
        vv_co.setGraphMouse(gm);
        vv_co.setBackground(Color.gray);

        setVertexColor(vv_co);
        setVertexSizeCO(g);
        setVertexLabelCO();
        showVertexLabelsCO();

        setEdgeLabelCO();
        setEdgeSizeCO();

        // right click menu
        gm.add(new PopupGraphMousePlugin(main_controller));

        insertLegendToGraph(vv_co);

        return vv_co;
    }


    private void setVertexLabelCO() {

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

        vv_co.getRenderContext().setVertexLabelRenderer(vertexLabelRenderer);
        vv_co.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.AUTO);

        vertex_labels_co = new ToStringLabeller<Author>();

        // make names bold
        vv_co.getRenderContext().setVertexFontTransformer(new VertexFontTransformer<Author>());

    }

    private void setEdgeSizeCO() {
        Transformer<EdgeCoAuthorship, Stroke> edgeStroke = new Transformer<EdgeCoAuthorship, Stroke>() {
            public Stroke transform(EdgeCoAuthorship s) {
                // EDGE SIZE - shows how many papers two authors have released together
                int max_size_of_graph = GraphCreator.getInstance().getCoAuthorMaxEdgeWith();
                float scaling = ((float)s.getWeight() / max_size_of_graph) * MAX_EDGE_WIDTH;
                scaling = Math.max(scaling, 1);
                return new BasicStroke(scaling);
            }
        };
        vv_co.getRenderContext().setEdgeStrokeTransformer(edgeStroke);
    }

    public void hideVertexLabelsCO() {
        vv_co.getRenderContext().setVertexLabelTransformer(vertex_label_none);
        vv_co.repaint();
    }

    public void showVertexLabelsCO() {
        vv_co.getRenderContext().setVertexLabelTransformer(vertex_labels_co);
        vv_co.repaint();

    }

    public void showEdgeLabelsCO() {
        vv_co.getRenderContext().setEdgeLabelTransformer(co_edge_label);
        vv_co.repaint();
    }

    public void hideEdgeLabelsCO() {
        vv_co.getRenderContext().setEdgeLabelTransformer(co_edge_label_none);
        vv_co.repaint();
    }

    private void setEdgeLabelCO() {
        final Color vertexLabelColor = Color.ORANGE;
        DefaultEdgeLabelRenderer edgeLabelRenderer =
            new DefaultEdgeLabelRenderer(vertexLabelColor)
            {
                @Override
                public <V> Component getEdgeLabelRendererComponent(
                    JComponent vv, Object value, Font font,
                    boolean isSelected, V vertex)
                {
                    super.getEdgeLabelRendererComponent(
                        vv, value, font, isSelected, vertex);
                    setForeground(vertexLabelColor);
                    return this;
                }
            };

        vv_co.getRenderContext().setEdgeLabelRenderer(edgeLabelRenderer);

        co_edge_label = new ToStringLabeller<EdgeCoAuthorship>();
        showEdgeLabelsCO();
    }

    public void repaintCO()
    {
        vv_co.repaint();
    }

    /*
    --------------------------------------------------------------------------------------------------------------------
    ---------------------------------------------------- CITATION GRAPH -----------------------------------------------
    --------------------------------------------------------------------------------------------------------------------
    */
    public VisualizationViewer<Author, EdgeCitation> createCitationVisualizationView(Graph g, MainController main_controller) {

        // KKLayout or FRLayout or ISOMLayout
        FRLayout2<Author, EdgeCitation> layout = new FRLayout2<Author, EdgeCitation>(g);
        layout.setSize(new Dimension(800, 800));
        layout.initialize();

        vv_ci = new VisualizationViewer<Author, EdgeCitation>(layout);
        vv_ci.setPreferredSize(new Dimension(900, 1200));

        setToTransformingMode();
        vv_ci.setGraphMouse(gm);
        vv_ci.setBackground(Color.gray);

        setVertexColor(vv_ci);
        setVertexSizeCI(g);
        setVertexLabelCI();
        showVertexLabelsCI();

//        setEdgeLabelCI(); // edge labels not really necessary?
        setEdgeSizeCI();


        // right click menu
        gm.add(new PopupGraphMousePlugin(main_controller));

        insertLegendToGraph(vv_ci);
        return vv_ci;
    }


    private void setVertexLabelCI() {

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

        vv_ci.getRenderContext().setVertexLabelRenderer(vertexLabelRenderer);
        vv_ci.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.AUTO);

        vertex_labels_ci = new ToStringLabeller<Author>();

        // make names bold
        vv_ci.getRenderContext().setVertexFontTransformer(new VertexFontTransformer<Author>());

    }

    private void setEdgeSizeCI() {
        Transformer<EdgeCitation, Stroke> edgeStroke = new Transformer<EdgeCitation, Stroke>() {
            public Stroke transform(EdgeCitation s) {
                // EDGE SIZE - shows how many papers two authors have released together
                int max_size_of_graph = GraphCreator.getInstance().getCitationMaxEdgeWith();
                float scaling = ((float)s.getWeight() / max_size_of_graph) * MAX_EDGE_WIDTH;
                scaling = Math.max(scaling, 1);
                return new BasicStroke(scaling);
            }
        };
        vv_ci.getRenderContext().setEdgeStrokeTransformer(edgeStroke);
    }

    private void setEdgeLabelCI() {
        final Color vertexLabelColor = Color.ORANGE;
        DefaultEdgeLabelRenderer edgeLabelRenderer =
            new DefaultEdgeLabelRenderer(vertexLabelColor)
            {
                @Override
                public <V> Component getEdgeLabelRendererComponent(
                    JComponent vv, Object value, Font font,
                    boolean isSelected, V vertex)
                {
                    super.getEdgeLabelRendererComponent(
                        vv, value, font, isSelected, vertex);
                    setForeground(vertexLabelColor);
                    return this;
                }
            };

        vv_ci.getRenderContext().setEdgeLabelRenderer(edgeLabelRenderer);

        ci_edge_label = new ToStringLabeller<EdgeCitation>();
        showEdgeLabelsCI();
    }

    private void setVertexSizeCI(final Graph g) {

        Transformer<Author,Shape> vertexSize = new Transformer<Author,Shape>(){
            public Shape transform(Author i){
                // VERTEX SIZE - the bigger the vertex, the more coAuthors this author has
                Ellipse2D circle = new Ellipse2D.Double(-10, -10, 20, 20);
                int max_size_of_graph = GraphCreator.getInstance().getCitationMaxDegree();
                float scaling = ((float)g.inDegree(i) / max_size_of_graph) * MAX_VERTEX_SIZE;

                scaling = Math.max(scaling, 1);
                return AffineTransform.getScaleInstance(scaling, scaling).createTransformedShape(circle);
            }
        };

        vv_ci.getRenderContext().setVertexShapeTransformer(vertexSize);
    }

    public void hideVertexLabelsCI() {
        vv_ci.getRenderContext().setVertexLabelTransformer(vertex_label_none);
        vv_ci.repaint();
    }

    public void showVertexLabelsCI() {
        vv_ci.getRenderContext().setVertexLabelTransformer(vertex_labels_ci);
        vv_ci.repaint();

    }

    public void showEdgeLabelsCI() {
        vv_ci.getRenderContext().setEdgeLabelTransformer(ci_edge_label);
        vv_ci.repaint();
    }

    public void hideEdgeLabelsCI() {
        vv_ci.getRenderContext().setEdgeLabelTransformer(ci_edge_label_none);
        vv_ci.repaint();
    }



    public void repaintCI()
    {
        vv_ci.repaint();
    }

    private final static class VertexFontTransformer<E> implements Transformer<E, Font> {
        @Override
        public Font transform(E e) {
            return new Font("Helvetica", Font.BOLD, 12);
        }
    }

}
