package raster;


import solid.Vertex;
import raster.ZBuffer;
import shader.Shader;
import transforms.Point3D;
import transforms.Vec3D;
import utils.Lerp;
import java.awt.*;


public class LineRasterizer{
    ZBuffer zBuffer;
    private final Lerp<Vertex> lerp;

    public LineRasterizer(ZBuffer zBuffer) {
        this.zBuffer = zBuffer;
        this.lerp = new Lerp<>();
    }


    public void rasterize(Vertex v1, Vertex v2) {

        //TODO: interpolace podle víc os jinak budou jen tečky

        v1 = v1.mul(1 / v1.getW());
        v2 = v2.mul(1 / v2.getW());


        Vertex a = transformToWindow(v1);
        Vertex b = transformToWindow(v2);

        if (a.getPos().getY() > b.getPos().getY()) {
            Vertex temp = a; a = b; b = temp;
        }
        for (int y = Math.max(a.getIntY(), 0); y <= Math.min(b.getIntY(), zBuffer.getHeight()-1); y++) {

            double tAB = (y-a.getIntY())/(double)(b.getIntY()-a.getIntY());
            Vertex AB = lerp.lerp(a, b, tAB);
            for(int x = Math.max(AB.getIntX(),0); x<= Math.min(AB.getIntX(),zBuffer.getWidth()-1);x++){
                zBuffer.setPixelWithZTest(x,y,AB.getPos().getZ(),AB.getColor());
            }
        }
    }
    private Vertex transformToWindow(Vertex v){
        return v.mulPos(new Point3D(1, -1, 1))
                .addPos(new Point3D(1, 1, 0))
                .mulPos(new Point3D((double) (zBuffer.getWidth() - 1) /2, (double) (zBuffer.getHeight() - 1) /2, 1));
    }


}
