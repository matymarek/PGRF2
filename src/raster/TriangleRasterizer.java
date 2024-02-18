package raster;

import transforms.Col;

public class TriangleRasterizer {
    private final ZBuffer zBuffer;

    public TriangleRasterizer(ZBuffer zBuffer) {
        this.zBuffer = zBuffer;
    }

    public void rasterize(
            int aX, int aY, double aZ,
            int bX, int bY, double bZ,
            int cX, int cY, double cZ,
            Col color
    ) {

        // TODO: seřadit vrcholy podle y

        for (int y = aY; y <= bY; y++) {
            // odečtu minumum, dělím rozsahem
            double tAB = (y - aY) / (double) (bY - aY);
            int xAB = (int) Math.round((1 - tAB) * aX + tAB * bX);
            color.mul(1 - tAB).add(color.mul(tAB));
            // TODO: zAB

            double tAC = (y - aY) / (double) (cY - aY);
            int xAC = (int) Math.round((1 - tAC) * aX + tAC * cX);
            // TODO: zAC

            // TODO: x1 < x2????
            for (int x = xAB; x <= xAC; x++) {
                // TODO: tZ
                // TODO: final z
                zBuffer.setPixelWithZTest(x, y, 0.2, color);
            }

            // TODO: druhou část

        }

    }
}
