package solid;

import java.util.ArrayList;

public abstract class Solid {
    protected final ArrayList<Vertex> vertexBuffer = new ArrayList<>();
    protected final ArrayList<Integer> indexBuffer = new ArrayList<>();
    protected final ArrayList<Part> partBuffer = new ArrayList<>();
}
