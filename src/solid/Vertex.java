package solid;

import transforms.Col;
import transforms.Point3D;

public class Vertex implements Vectorizable<Vertex> {
    private final Point3D pos;
    private final Col color;

    public Vertex(Point3D pos, Col color) {
        this.pos = pos;
        this.color = color;
    }
    @Override
    public Vertex mul(double k) {
        return new Vertex(pos.mul(k), color.mul(k));
    }
    @Override
    public Vertex add(Vertex v) {
        return new Vertex(pos.add(v.getPos()), color.add(v.color));
    }
    public Point3D getPos() {
        return pos;
    }
    public int getIntX(){return (int) Math.round(pos.getX());}
    public int getIntY(){return (int) Math.round(pos.getY());}
    public Col getColor() {
        return color;
    }
}
