package top.ioart.items.tools;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.util.math.Vec3d;

public class ItemTravelStick extends Item {
    public ItemTravelStick(Settings settings) {
        super(settings.maxCount(1).rarity(Rarity.RARE).fireproof().useCooldown(1));
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        Vec3d start = user.getCameraPosVec(1.0F); // 玩家眼睛位置
        Vec3d end = start.add(user.getRotationVec(1.0F).multiply(16)); // 视线终点
        BlockHitResult result = world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, user));
        BlockPos pos = result.getBlockPos().add(0,1,0);
        double x = pos.getX()+0.5;
        double y = pos.getY();
        double z = pos.getZ()+0.5;
        BlockState state = world.getBlockState(pos);
        if (!state.isAir()){
            user.sendMessage(Text.of("Teleport Failed: BLOCKS ON THE WAY"),true);
        }else {
            user.setPosition(x,y,z);
            user.sendMessage(Text.of("Teleport: "+x+" "+y+" "+z),true);
            user.setVelocity(0, 0, 0);
        }
        return super.use(world, user, hand);
    }

}
