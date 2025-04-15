package top.ioart.entity.flyingsword;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.RotationAxis;
import top.ioart.items.UsefulItems;

@Environment(EnvType.CLIENT)
public class FlyingSwordEntityRender extends EntityRenderer<FlyingSwordEntity,FlyingSwordEntityModel> {

    private final ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
    private final ItemStack itemStack = new ItemStack(Items.IRON_SWORD);

    public FlyingSwordEntityRender(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public FlyingSwordEntityModel createRenderState() {
        return new FlyingSwordEntityModel();
    }

    @Override
    public void render(FlyingSwordEntityModel state, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(state.yaw-90)); // 水平旋转
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(state.pitch-45)); // 俯仰角旋转
        itemRenderer.renderItem(
                itemStack,
                ItemDisplayContext.GROUND,
                i,10,
                matrixStack,
                vertexConsumerProvider,
                null,
                1
        );
        matrixStack.pop();
        super.render(state, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public void updateRenderState(FlyingSwordEntity entity, FlyingSwordEntityModel state, float tickProgress) {
        super.updateRenderState(entity, state, tickProgress);
        state.pitch = entity.getLerpedPitch(tickProgress);
        state.yaw = entity.getLerpedYaw(tickProgress);
        state.shake = entity.shake - tickProgress;
    }
}
