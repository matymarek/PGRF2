package shader;

import solid.Vertex;
import transforms.Col;

public interface Shader {
    Col getColor(Vertex v);
}
