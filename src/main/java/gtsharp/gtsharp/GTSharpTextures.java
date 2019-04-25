package gtsharp.gtsharp;

import gregtech.api.render.OrientedOverlayRenderer;
import gregtech.api.render.SimpleCubeRenderer;

public class GTSharpTextures {

    public static SimpleCubeRenderer REACTOR_PRESSURE_VESSEL;
    public static OrientedOverlayRenderer FISSION_REACTOR;

    public static void init() {
        REACTOR_PRESSURE_VESSEL = new SimpleCubeRenderer("casings/fusion_vessel");

        FISSION_REACTOR = new OrientedOverlayRenderer("machines/fission_reactor", new OrientedOverlayRenderer.OverlayFace[]{OrientedOverlayRenderer.OverlayFace.FRONT});
    }
}
