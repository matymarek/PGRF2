package solid;

import transforms.Col;
import transforms.Point3D;

public class Arrow extends Solid{

    public Arrow(){
        vertexBuffer.add(new Vertex(new Point3D(100, 300, 0), new Col(0xff0000)));
        vertexBuffer.add(new Vertex(new Point3D(250, 300, 0), new Col(0xff0000)));
        vertexBuffer.add(new Vertex(new Point3D(250, 100, 0), new Col(0xff0000)));
        vertexBuffer.add(new Vertex(new Point3D(250, 400, 0), new Col(0xff0000)));
        vertexBuffer.add(new Vertex(new Point3D(450, 300, 0), new Col(0xff0000)));
        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(3);
        indexBuffer.add(4);
        partBuffer.add(new Part(TopologyType.LINES, 0, 1));
        partBuffer.add(new Part(TopologyType.TRIANGLE, 2, 1));
    }
}
