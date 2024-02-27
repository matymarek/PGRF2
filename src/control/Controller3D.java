package control;

import raster.Raster;
import raster.TriangleRasterizer;
import raster.ZBuffer;
import render.Renderer;
import solid.Arrow;
import solid.Vertex;
import transforms.Col;
import transforms.Point3D;
import view.Panel;

import java.awt.*;
import java.awt.event.*;

public class Controller3D implements Controller {
    private final Panel panel;
    private ZBuffer zBuffer;
    private TriangleRasterizer triangleRasterizer;

    private Renderer renderer;


    public Controller3D(Panel panel) {
        this.panel = panel;
        initObjects(panel.getRaster());
        initListeners();
        redraw();
    }

    public void initObjects(Raster<Col> raster) {
        raster.setDefaultValue(new Col(Color.black.getRGB()));
        zBuffer = new ZBuffer(raster);
        triangleRasterizer = new TriangleRasterizer(zBuffer);
        renderer = new Renderer(triangleRasterizer);
    }

    @Override
    public void initListeners() {
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel.resize();
                initObjects(panel.getRaster());
            }
        });
    }

    private void redraw() {
        panel.clear();
        //triangleRasterizer.rasterize(
        //        new Vertex(new Point3D(400, -150, 0.5), new Col(Color.red.getRGB())),
        //        new Vertex(new Point3D(-125,300, 0.5), new Col(Color.pink.getRGB())),
        //        new Vertex(new Point3D(805, 599, 0.5), new Col(Color.magenta.getRGB()))
        //);
        //triangleRasterizer.rasterize(
        //        new Vertex(new Point3D(500, -100, 0.3), new Col(Color.yellow.getRGB())),
        //        new Vertex(new Point3D(-50,350, 0.7), new Col(Color.blue.getRGB())),
        //        new Vertex(new Point3D(400, 700, 0.7), new Col(Color.green.getRGB()))
        //);

        renderer.render(new Arrow());
        panel.repaint();
    }
}
