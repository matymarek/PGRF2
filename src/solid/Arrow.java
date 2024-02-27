package solid;

import transforms.Col;
import transforms.Point3D;

import java.awt.*;

public class Arrow extends Solid{

    public Arrow(){
        vertexBuffer.add(new Vertex(new Point3D(100, 300, 0.1), new Col(Color.yellow.getRGB())));
        vertexBuffer.add(new Vertex(new Point3D(250, 300, 0.1), new Col(Color.blue.getRGB())));
        vertexBuffer.add(new Vertex(new Point3D(250, 200, 0.1), new Col(Color.red.getRGB())));
        vertexBuffer.add(new Vertex(new Point3D(250, 400, 0.1), new Col(Color.pink.getRGB())));
        vertexBuffer.add(new Vertex(new Point3D(450, 300, 0.1), new Col(Color.magenta.getRGB())));
        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(3);
        indexBuffer.add(4);
        partBuffer.add(new Part(TopologyType.LINES, 0, 1));
        partBuffer.add(new Part(TopologyType.TRIANGLE, 2, 1));
    }
}
