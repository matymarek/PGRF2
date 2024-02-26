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
        raster.setDefaultValue(new Col(0x101010));
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
        triangleRasterizer.rasterize(
                new Vertex(new Point3D(400, 0, 0.5), new Col(0xff0000)),
                new Vertex(new Point3D(0,300, 0.5), new Col(0xff0000)),
                new Vertex(new Point3D(799, 599, 0.5), new Col(0xff0000)),
                new Col(0xff0000)
        );

        triangleRasterizer.rasterize(
                new Vertex(new Point3D(500, 0, 0.3), new Col(0xff0000)),
                new Vertex(new Point3D(0,350, 0.7), new Col(0xff0000)),
                new Vertex(new Point3D(400, 599, 0.7), new Col(0xff0000)),
                new Col(0xff0000)
        );

        //renderer.render(new Arrow());
        panel.repaint();
    }
}
