package raster;

import transforms.Col;

public class ZBuffer {
    private final ImageBuffer imageBuffer;
    private final DepthBuffer depthBuffer;

    public ZBuffer(ImageBuffer imageBuffer) {
        this.imageBuffer = imageBuffer;
        this.depthBuffer = new DepthBuffer(imageBuffer.getWidth(), imageBuffer.getHeight());
    }

    public void setPixelWithZTest(int x, int y, double z, Col color){
        //TODO: implement
        // load z DB
        // porovnám s input Z
        // je Z menší? přepíšu, jinak nic
    }
}
