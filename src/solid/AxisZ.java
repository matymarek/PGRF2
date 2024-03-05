package solid;

import transforms.Col;
import transforms.Point3D;
import transforms.Vec2D;

import java.awt.*;

public class AxisZ extends Solid{
    public AxisZ(){
        axis = true;
        vertexBuffer.add(new Vertex(new Point3D(0, 0, -1), new Col(Color.blue.getRGB()), new Vec2D()));
        vertexBuffer.add(new Vertex(new Point3D(0, 0, 3), new Col(Color.blue.getRGB()), new Vec2D()));
        vertexBuffer.add(new Vertex(new Point3D(0, 0.5, 3), new Col(Color.blue.getRGB()), new Vec2D()));
        vertexBuffer.add(new Vertex(new Point3D(0, -0.5, 3), new Col(Color.blue.getRGB()), new Vec2D()));
        vertexBuffer.add(new Vertex(new Point3D(0, 0, 3.5), new Col(Color.blue.getRGB()), new Vec2D()));
        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(3);
        indexBuffer.add(4);
        partBuffer.add(new Part(TopologyType.LINES, 0, 1));
        partBuffer.add(new Part(TopologyType.TRIANGLE, 2, 1));
    }
}
