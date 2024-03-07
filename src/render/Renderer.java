package render;

import raster.LineRasterizer;
import raster.TriangleRasterizer;
import solid.Part;
import solid.Solid;
import solid.Vertex;
import transforms.*;
import utils.Lerp;
import view.Panel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Renderer {
    private final TriangleRasterizer triangleRasterizer;
    private final LineRasterizer lineRasterizer;
    private final Lerp<Vertex> lerp;
    private final Panel panel;

    private Mat4 view;
    private Mat4 proj;

    public Renderer(TriangleRasterizer triangleRasterizer, LineRasterizer lineRasterizer, Panel panel) {
        this.triangleRasterizer = triangleRasterizer;
        this.lineRasterizer = lineRasterizer;
        this.panel = panel;
        this.lerp = new Lerp<>();
        this.view = new Mat4Identity();
        this.proj = new Mat4Identity();
    }

    public void render(Solid solid){
        for(Part part : solid.getPartBuffer()){
            int startIndex;
            switch (part.getType()){
                case POINTS:
                    for(int i = 0; i < part.getCount(); i++) {
                        Vertex a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(part.getStart()));
                        //rasterize somehow
                    }
                    break;
                case LINES:
                    startIndex = part.getStart();
                    for(int i = 0; i < part.getCount(); i++) {
                        Vertex a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(startIndex));
                        Vertex b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(startIndex+1));
                        if(!solid.isAxis()) {
                            a = new Vertex(a.getPos().mul(solid.getModel()).mul(view).mul(proj), a.getColor(), a.getUv());
                            b = new Vertex(b.getPos().mul(solid.getModel()).mul(view).mul(proj), b.getColor(), b.getUv());
                        }
                        else {
                            a = new Vertex(a.getPos().mul(view).mul(proj), a.getColor(), a.getUv());
                            b = new Vertex(b.getPos().mul(view).mul(proj), b.getColor(), b.getUv());
                        }
                        clipLine(a, b);
                        startIndex+=2;
                    }
                    break;
                case LINE_STRIP:
                    break;
                case TRIANGLE:
                    startIndex = part.getStart();
                    for(int i = 0; i < part.getCount(); i++){
                        Vertex a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(startIndex));
                        Vertex b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(startIndex+1));
                        Vertex c = solid.getVertexBuffer().get(solid.getIndexBuffer().get(startIndex+2));
                        if(solid.isAxis()) {
                            a = new Vertex(a.getPos().mul(view).mul(proj), a.getColor(), a.getUv());
                            b = new Vertex(b.getPos().mul(view).mul(proj), b.getColor(), b.getUv());
                            c = new Vertex(c.getPos().mul(view).mul(proj), c.getColor(), c.getUv());
                        }
                        //else if(solid.withTexture()){
                        //    a = new Vertex(a.getPos().mul(solid.getModel()).mul(view).mul(proj), a.getColor(), a.getUv());
                        //    b = new Vertex(b.getPos().mul(solid.getModel()).mul(view).mul(proj), b.getColor(), b.getUv());
                        //    c = new Vertex(c.getPos().mul(solid.getModel()).mul(view).mul(proj), c.getColor(), c.getUv());
                        //}
                        else {
                            a = new Vertex(a.getPos().mul(solid.getModel()).mul(view).mul(proj), a.getColor(), a.getUv());
                            b = new Vertex(b.getPos().mul(solid.getModel()).mul(view).mul(proj), b.getColor(), b.getUv());
                            c = new Vertex(c.getPos().mul(solid.getModel()).mul(view).mul(proj), c.getColor(), c.getUv());
                        }
                        clipTriangle(a, b, c, solid.withTexture());
                        startIndex+=3;
                    }
                    break;
                case TRIANGLE_STRIP:
                    break;
            }
        }
    }

    public void render(List<Solid> solids){
        for(Solid solid : solids){
            render(solid);
        }
    }

    private void clipTriangle(Vertex a, Vertex b, Vertex c, boolean withTexture){
        //TODO: fast clip

        if (a.getPos().getZ() < b.getPos().getZ()) {
            Vertex temp = a; a = b; b = temp;
        }
        if (b.getPos().getZ() < c.getPos().getZ()) {
            Vertex temp = b; b = c; c = temp;
        }
        if (a.getPos().getZ() < b.getPos().getZ()) {
            Vertex temp = a; a = b; b = temp;
        }

        double zMin = 0;
        if (a.getPos().getZ() < zMin) return;
        if (b.getPos().getZ() < zMin) {
            double tAB = (zMin-a.getPos().getZ()/(b.getPos().getZ()-a.getPos().getZ()));
            Vertex v1 = lerp.lerp(a, b, tAB);
            double tAC = (zMin-a.getPos().getZ()/(c.getPos().getZ()-a.getPos().getZ()));
            Vertex v2 = lerp.lerp(a, c, tAC);
            triangleRasterizer.rasterize(v1, v2, c, withTexture);
            return;
        }
        if(c.getPos().getZ() < zMin){
            double tBC = (zMin-b.getPos().getZ()/(c.getPos().getZ()-b.getPos().getZ()));
            Vertex v1 = lerp.lerp(b, c, tBC);

            double tAC = (zMin-a.getPos().getZ()/(c.getPos().getZ()-a.getPos().getZ()));
            Vertex v2 = lerp.lerp(a, c, tAC);

            triangleRasterizer.rasterize(a, b, v1, withTexture);
            triangleRasterizer.rasterize(a, v1, v2, withTexture);
            return;

        }
        triangleRasterizer.rasterize(a, b, c, withTexture);
    }

    private void clipLine(Vertex a, Vertex b){
        if (a.getPos().getZ() < b.getPos().getZ()) {
            Vertex temp = a; a = b; b = temp;
        }
        double zMin = 0;
        if (a.getPos().getZ() < zMin) return;
        if (b.getPos().getZ() < zMin) {
            double tAB = (zMin-a.getPos().getZ()/(b.getPos().getZ()-a.getPos().getZ()));
            Vertex v1 = lerp.lerp(a, b, tAB);
            lineRasterizer.rasterize(a, v1);
            return;
        }
        lineRasterizer.rasterize(a, b);
    }

    public void setCenters(List<Solid> solids){
        for(Solid solid : solids){
            if(!solid.isAxis()){
                Vertex center = solid.getCenter();
                solid.setCenter(new Vertex(center.getPos().mul(solid.getModel()).mul(view).mul(proj), null, null));

            }
        }
    }

    public ArrayList<Vertex> getCenters(List<Solid> solids){
        ArrayList<Vertex> centers = new ArrayList<>();
        for(Solid solid : solids){
            if(!solid.isAxis()){
                if (solid.getCenter().getPos().dehomog().isPresent()){
                    centers.add(transformToWindow(new Vertex(new Point3D(solid.getCenter().getPos().dehomog().get()), null, null)));
                }
            }
        }
        return centers;
    }

    private Vertex transformToWindow(Vertex v){
        return v.mulPos(new Point3D(1, -1, 1))
                .addPos(new Point3D(1, 1, 0))
                .mulPos(new Point3D((double) (panel.getWidth() - 1) /2, (double) (panel.getHeight() - 1) /2, 1));
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public void setProj(Mat4 proj) {
        this.proj = proj;
    }
}
