package solid;

import transforms.Col;
import transforms.Point3D;
import transforms.Vec2D;

import java.awt.*;

public class Triangl extends Solid{
    public Triangl(){
        vertexBuffer.add(new Vertex(new Point3D(2, 0, 0.5), new Col(Color.yellow.getRGB()), new Vec2D()));
        vertexBuffer.add(new Vertex(new Point3D(3, 0, 0.5), new Col(Color.blue.getRGB()), new Vec2D()));
        vertexBuffer.add(new Vertex(new Point3D(2.5, 0, 1.5), new Col(Color.red.getRGB()), new Vec2D()));
        vertexBuffer.add(new Vertex(new Point3D(2.5, 1.5, 1), new Col(Color.pink.getRGB()), new Vec2D()));
        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(3);
        indexBuffer.add(0);
        indexBuffer.add(2);
        indexBuffer.add(3);
        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(3);
        partBuffer.add(new Part(TopologyType.TRIANGLE, 0, 4));
        initCenter();
    }
}
