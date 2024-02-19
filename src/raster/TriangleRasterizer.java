package raster;

import solid.Vertex;
import transforms.Col;

public class TriangleRasterizer {
    private final ZBuffer zBuffer;

    public TriangleRasterizer(ZBuffer zBuffer) {
        this.zBuffer = zBuffer;
    }

    public void rasterize(Vertex a, Vertex b, Vertex c, Col color){
        int aX = (int) a.getPos().getX();
        int aY = (int) a.getPos().getY();
        double aZ = (int) a.getPos().getZ();
        int bX = (int) b.getPos().getX();
        int bY = (int) b.getPos().getY();
        double bZ = (int) b.getPos().getZ();
        int cX = (int) c.getPos().getX();
        int cY = (int) c.getPos().getY();
        double cZ = (int) c.getPos().getZ();

        if (aY > bY){
            double temp = aY; aY = bY; bY = (int) temp;
            temp = aX; aX = bX; bX = (int) temp;
            temp = aZ; aZ = bZ; bZ = temp;
        }
        if (bY > cY){
            double temp = bY; bY = cY; cY = (int) temp;
            temp = bX; bX = cX; cX = (int) temp;
            temp = bZ; bZ = cZ; cZ = temp;
        }

        for (int y = aY; y <= bY; y++) {

            double tAB = (y - aY) / (double) (bY - aY);
            int xAB = (int) Math.round((1 - tAB) * aX + tAB * bX);
            color.mul(1 - tAB).add(color.mul(tAB));
            int zAB = (int) Math.round((1 - tAB) * aZ + tAB * bZ);

            double tAC = (y - aY) / (double) (cY - aY);
            int xAC = (int) Math.round((1 - tAC) * aX + tAC * cX);
            color.mul(1 - tAC).add(color.mul(tAC));
            int zAC = (int) Math.round((1 - tAC) * aZ + tAC * cZ);

            // TODO: x1 < x2????

            for (int x = xAB; x <= xAC; x++) {
                double tZ = (x - xAB) / (double) (xAC - xAB);
                double z = (1 - tZ) * zAB + tZ * zAC;
                zBuffer.setPixelWithZTest(x, y, 0.2, color);
            }

            // TODO: druhou část

        }

    }
}
