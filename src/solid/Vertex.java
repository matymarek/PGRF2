package solid;

import transforms.Col;
import transforms.Mat4;
import transforms.Point3D;
import transforms.Vec2D;

public class Vertex implements Vectorizable<Vertex> {
    private Point3D pos;
    private final Col color;
    private final Vec2D uv;

    public Vertex(Point3D pos, Col color, Vec2D uv) {
        this.pos = pos;
        this.color = color;
        this.uv = uv;
    }
    @Override
    public Vertex mul(double k) {
        return new Vertex(pos.mul(k), color.mul(k), uv.mul(k));
    }
    public Vertex mulPos(Point3D point) { return new Vertex(new Point3D(pos.getX()*point.getX(), pos.getY()* point.getY(), pos.getZ()*point.getZ()), color, uv); }
    @Override
    public Vertex add(Vertex v) {
        return new Vertex(pos.add(v.getPos()), color.add(v.getColor()), uv.add(v.getUv()));
    }
    public Vertex addPos(Point3D point) { return new Vertex(pos.add(point), color, uv); }
    public Point3D getPos() {
        return pos;
    }
    public void setPos(Point3D pos) { this.pos = pos;}
    public int getIntX(){return (int) Math.round(pos.getX());}
    public int getIntY(){return (int) Math.round(pos.getY());}
    public double getW() {return pos.getW();}
    public Col getColor() {
        return color;
    }
    public Vec2D getUv() {
        return uv;
    }
}
