package gtsharp.gtsharp;

import gregtech.api.render.OrientedOverlayRenderer;
import gregtech.api.render.SimpleCubeRenderer;

public class GTSharpTextures {

    public static SimpleCubeRenderer REACTOR_PRESSURE_VESSEL;
    public static OrientedOverlayRenderer FISSION_REACTOR;
    public static SimpleCubeRenderer HV_COMPUTER_CASING;
    public static OrientedOverlayRenderer RESEARCH_COMPUTER;
    public static SimpleCubeRenderer HV_CONNECTOR_CASING;


    public static void init() {
        REACTOR_PRESSURE_VESSEL = new SimpleCubeRenderer("casings/fusion_vessel");

        FISSION_REACTOR = new OrientedOverlayRenderer("machines/fission_reactor", new OrientedOverlayRenderer.OverlayFace[]{OrientedOverlayRenderer.OverlayFace.FRONT});

        HV_COMPUTER_CASING = new SimpleCubeRenderer("casings/hv_computer_casing");

        RESEARCH_COMPUTER = new OrientedOverlayRenderer("machines/research-computer", new OrientedOverlayRenderer.OverlayFace[]{OrientedOverlayRenderer.OverlayFace.FRONT});

        HV_CONNECTOR_CASING =  new SimpleCubeRenderer("casings/hv_connector_casing");
    }
}
