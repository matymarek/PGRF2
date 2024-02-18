package control;

import raster.Raster;
import raster.TriangleRasterizer;
import raster.ZBuffer;
import transforms.Col;
import view.Panel;

import java.awt.event.*;

public class Controller3D implements Controller {
    private final Panel panel;
    private ZBuffer zBuffer;
    private TriangleRasterizer triangleRasterizer;


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
                400, 0, 0.5,
                0,300, 0.5,
                799, 599, 0.5,
                new Col(0xff0000)
        );
        panel.repaint();
    }
}
