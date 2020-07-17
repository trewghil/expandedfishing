package com.trewghil.expandedfishing;

import com.trewghil.expandedfishing.client.render.ExpandedFishingRenderers;
import net.fabricmc.api.ClientModInitializer;

public class ExpandedFishingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ExpandedFishingRenderers.init();
    }
}
