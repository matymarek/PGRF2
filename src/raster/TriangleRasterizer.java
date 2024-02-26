package raster;

import solid.Vertex;
import transforms.Col;

public class TriangleRasterizer {
    private final ZBuffer zBuffer;

    public TriangleRasterizer(ZBuffer zBuffer) {
        this.zBuffer = zBuffer;
    }

    public void rasterize(
            Vertex a,
            Vertex b,
            Vertex c,
            Col color
    ) {
        int aX = (int) Math.round(a.getPos().getX());
        int aY = (int) Math.round(a.getPos().getY());
        double aZ = a.getPos().getZ();

        int bX = (int) Math.round(b.getPos().getX());
        int bY = (int) Math.round(b.getPos().getY());
        double bZ = b.getPos().getZ();

        int cX = (int) Math.round(c.getPos().getX());
        int cY = (int) Math.round(c.getPos().getY());
        double cZ = c.getPos().getZ();

        if (aY > bY) {
            int tempY = aY;
            aY = bY;
            bY = tempY;

            int tempX = aX;
            aX = bX;
            bX = tempX;

            double tempZ = aZ;
            aZ = bZ;
            bZ = tempZ;
        }

        if (bY > cY) {
            int tempY = bY;
            bY = cY;
            cY = tempY;

            int tempX = bX;
            bX = cX;
            cX = tempX;

            double tempZ = bZ;
            bZ = cZ;
            cZ = tempZ;
        }

        if (aY > bY) {
            int tempY = aY;
            aY = bY;
            bY = tempY;

            int tempX = aX;
            aX = bX;
            bX = tempX;

            double tempZ = aZ;
            aZ = bZ;
            bZ = tempZ;
        }

        fillTriangle(aX, aY, aZ, bX, bY, bZ, cX, cY, cZ, color);
        System.out.println(aX);
        System.out.println(aY);
        System.out.println(aZ);
        System.out.println(cX);
        System.out.println(cY);
        System.out.println(cZ);
        System.out.println(color);
    }

    private void fillTriangle(int aX, int aY, double aZ, int bX, int bY, double bZ, int cX, int cY, double cZ, Col color) {
        //ořezání podle Y
        for (int y = aY; y < bY; y++) {
            double tAB = getInterpolationCoefficient(y, aY, bY);
            int xAB = getInterpolatedX(tAB, aX, bX);
            double zAB = getInterpolatedZ(tAB, aZ, bZ);

            double tAC = getInterpolationCoefficient(y, aY, cY);
            int xAC = getInterpolatedX(tAC, aX, cX);
            double zAC = getInterpolatedZ(tAC, aZ, cZ);

            if (xAB > xAC) {
                int temp = xAB;
                xAB = xAC;
                xAC = temp;

                double temp2 = zAB;
                zAB = zAC;
                zAC = temp2;
            }

            //ořezání podle X
            for (int x = xAB; x < xAC; x++) {
                double t = getInterpolationCoefficient(x, xAB, xAC);
                double z = getInterpolatedZ(t, zAB, zAC);

                zBuffer.setPixelWithZTest(x,y,z, color);
            }
        }

        for (int y = bY; y < cY; y++) {
            double tBC = getInterpolationCoefficient(y, bY, cY);
            int xBC = getInterpolatedX(tBC, bX, cX);
            double zBC = getInterpolatedZ(tBC, bZ, cZ);

            double tAC = getInterpolationCoefficient(y, aY, cY);
            int xAC = getInterpolatedX(tAC, aX, cX);
            double zAC = getInterpolatedZ(tAC, aZ, cZ);

            if (xBC > xAC) {
                int temp = xBC;
                xBC = xAC;
                xAC = temp;

                double temp2 = zBC;
                zBC = zAC;
                zAC = temp2;
            }

            for (int x = xBC; x < xAC; x++) {
                double t = getInterpolationCoefficient(x, xBC, xAC);
                double z = getInterpolatedZ(t, zBC, zAC);

                zBuffer.setPixelWithZTest(x,y,z, color);
            }
        }
    }

    private double getInterpolationCoefficient(int i, int aI, int bI) {
        return (i-aI)/(double)(bI-aI);
    }

    private int getInterpolatedX(double t, int aX, int bX) {
        return (int) Math.round((1-t) * aX + t * bX);
    }

    private double getInterpolatedZ(double t, double aZ, double bZ) {
        return (1-t) * aZ + t * bZ;
    }

}