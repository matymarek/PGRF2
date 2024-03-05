package raster;

import shader.Shader;
import solid.Vertex;
import transforms.Col;
import transforms.Point3D;
import utils.Lerp;

public class TriangleRasterizer {
    private final ZBuffer zBuffer;
    private final Lerp<Vertex> lerp;
    private Shader shader;

    public TriangleRasterizer(ZBuffer zBuffer, Shader shader) {
        this.zBuffer = zBuffer;
        this.shader = shader;
        this.lerp = new Lerp<>();
    }

    public void rasterize(Vertex v1, Vertex v2, Vertex v3) {

        v1 = v1.mul(1 / v1.getW());
        v2 = v2.mul(1 / v2.getW());
        v3 = v3.mul(1 / v3.getW());


        Vertex a = transformToWindow(v1);
        Vertex b = transformToWindow(v2);
        Vertex c = transformToWindow(v3);


        if (a.getPos().getY() > b.getPos().getY()) {
            Vertex temp = a; a = b; b = temp;
        }
        if (b.getPos().getY() > c.getPos().getY()) {
            Vertex temp = b; b = c; c = temp;
        }
        if (a.getPos().getY() > b.getPos().getY()) {
            Vertex temp = a; a = b; b = temp;
        }

        //crop by y
        for (int y = Math.max(a.getIntY(), 0); y <= Math.min(b.getIntY(), zBuffer.getHeight()-1); y++) {

            double tAB = (y-a.getIntY())/(double)(b.getIntY()-a.getIntY());
            Vertex AB = lerp.lerp(a, b, tAB);
            double tAC = (y-a.getIntY())/(double)(c.getIntY()-a.getIntY());
            Vertex AC = lerp.lerp(a, c, tAC);

            if (AB.getIntX() > AC.getIntX()) {
                Vertex temp = AB; AB = AC; AC = temp;
            }

            //crop by x
            for (int x = Math.max(AB.getIntX(), 0); x <= Math.min(AC.getIntX(), zBuffer.getWidth()-1); x++) {
                double t = (x-AB.getIntX())/(double)(AC.getIntX()-AB.getIntX());
                Vertex finalVertex = lerp.lerp(AB, AC, t);
                zBuffer.setPixelWithZTest(x,y,finalVertex.getPos().getZ(), shader.getColor(finalVertex));
            }
        }

        for (int y = Math.max(b.getIntY(), 0); y <= Math.min(c.getIntY(), zBuffer.getHeight()-1); y++) {

            double tBC = (y-b.getIntY())/(double)(c.getIntY()-b.getIntY());
            Vertex BC = lerp.lerp(b, c, tBC);
            double tAC = (y-a.getIntY())/(double)(c.getIntY()-a.getIntY());
            Vertex AC = lerp.lerp(a, c, tAC);

            if (BC.getIntX() > AC.getIntX()) {
                Vertex temp = BC; BC = AC; AC = temp;
            }

            for (int x = Math.max(BC.getIntX(), 0); x < Math.min(AC.getIntX(), zBuffer.getWidth()-1); x++) {
                double t = (x-BC.getIntX())/(double)(AC.getIntX()-BC.getIntX());
                Vertex finalVertex = lerp.lerp(BC, AC, t);
                zBuffer.setPixelWithZTest(x,y,finalVertex.getPos().getZ(), shader.getColor(finalVertex));
            }
        }
    }
    private Vertex transformToWindow(Vertex v){
        return v.mulPos(new Point3D(1, -1, 1))
                .addPos(new Point3D(1, 1, 0))
                .mulPos(new Point3D((double) (zBuffer.getWidth() - 1) /2, (double) (zBuffer.getHeight() - 1) /2, 1));
    }
}