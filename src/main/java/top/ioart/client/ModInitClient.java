package top.ioart.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import top.ioart.entity.ModEntities;
import top.ioart.entity.flyingsword.FlyingSwordEntityRender;

@Environment(EnvType.CLIENT)
public class ModInitClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.FLYING_SWORD, FlyingSwordEntityRender::new);
    }
}
