package render;

import raster.TriangleRasterizer;
import solid.Part;
import solid.Solid;
import solid.Vertex;
import transforms.Col;

import java.awt.*;
import java.util.List;

public class Renderer {
    private final TriangleRasterizer triangleRasterizer;
    //TODO: proj, view

    public Renderer(TriangleRasterizer triangleRasterizer) {
        this.triangleRasterizer = triangleRasterizer;
    }

    public void render(Solid solid){
        for(Part part : solid.getPartBuffer()){
            int startIndex;
            switch (part.getType()){
                case POINTS:
                    for(int i = 0; i < part.getCount(); i++) {
                        Vertex a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(part.getStart()));
                        //rasterize somehow
                    }
                    break;
                case LINES:
                    startIndex = part.getStart();
                    for(int i = 0; i < part.getCount(); i++) {
                        Vertex a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(startIndex));
                        Vertex b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(startIndex+1));
                        //rasterize somehow
                        startIndex+=2;
                    }
                    break;
                case LINE_STRIP:
                    break;
                case TRIANGLE:
                    startIndex = part.getStart();
                    for(int i = 0; i < part.getCount(); i++){
                        int indexA = startIndex;
                        int indexB = startIndex+1;
                        int indexC = startIndex+2;

                        Vertex a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexA));
                        Vertex b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexB));
                        Vertex c = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexC));

                        triangleRasterizer.rasterize(a, b, c);
                        startIndex+=3;
                    }
                    break;
                case TRIANGLE_STRIP:
                    break;
            }
        }
    }

    public void render(List<Solid> solids){
        for(Solid solid : solids){
            render(solid);
        }
    }
}
