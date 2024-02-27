package raster;

import solid.Vertex;
import transforms.Col;

public class TriangleRasterizer {
    private final ZBuffer zBuffer;

    public TriangleRasterizer(ZBuffer zBuffer) {
        this.zBuffer = zBuffer;
    }

    public void rasterize(Vertex a, Vertex b, Vertex c) {

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
            Vertex AB = a.mul(1-tAB).add(b.mul(tAB));
            double tAC = (y-a.getIntY())/(double)(c.getIntY()-a.getIntY());
            Vertex AC = a.mul(1-tAC).add(c.mul(tAC));

            if (AB.getIntX() > AC.getIntX()) {
                Vertex temp = AB; AB = AC; AC = temp;
            }

            //crop by x
            for (int x = Math.max(AB.getIntX(), 0); x <= Math.min(AC.getIntX(), zBuffer.getWidth()-1); x++) {
                double t = (x-AB.getIntX())/(double)(AC.getIntX()-AB.getIntX());
                Vertex finalVertex = AB.mul(1-t).add(AC.mul(t));
                zBuffer.setPixelWithZTest(x,y,finalVertex.getPos().getZ(), finalVertex.getColor());
            }
        }

        for (int y = Math.max(b.getIntY(), 0); y <= Math.min(c.getIntY(), zBuffer.getHeight()-1); y++) {

            double tBC = (y-b.getIntY())/(double)(c.getIntY()-b.getIntY());
            Vertex BC = b.mul(1-tBC).add(c.mul(tBC));
            double tAC = (y-a.getIntY())/(double)(c.getIntY()-a.getIntY());
            Vertex AC = a.mul(1-tAC).add(c.mul(tAC));

            if (BC.getIntX() > AC.getIntX()) {
                Vertex temp = BC; BC = AC; AC = temp;
            }

            for (int x = Math.max(BC.getIntX(), 0); x < Math.min(AC.getIntX(), zBuffer.getWidth()-1); x++) {
                double t = (x-BC.getIntX())/(double)(AC.getIntX()-BC.getIntX());
                Vertex finalVertex = BC.mul(1-t).add(AC.mul(t));
                zBuffer.setPixelWithZTest(x,y,finalVertex.getPos().getZ(), finalVertex.getColor());
            }
        }
    }
}