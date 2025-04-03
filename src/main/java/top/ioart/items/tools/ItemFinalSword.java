package top.ioart.items.tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class ItemFinalSword extends Item {
    public ItemFinalSword(Settings settings) {
        super(settings.maxCount(1).rarity(Rarity.EPIC).fireproof());
    }

    private final Random random = new Random();


    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        //user.playSound(SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT);
        //user.setNoGravity(!user.hasNoGravity());
        /*Vec3d start = user.getCameraPosVec(1.0F); // 玩家眼睛位置
        Vec3d end = start.add(user.getRotationVec(1.0F).multiply(20)); // 视线终点
        HitResult result = world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, user));
        user.setPosition(result.getPos().add(0, 0.5, 0));*/
        Box box = user.getBoundingBox().expand(10); // 定义探测范围
        List<Entity> nearbyEntities = user.getWorld().getEntitiesByClass(Entity.class, box, entity -> true);
        for (Entity entity : nearbyEntities) {
            System.out.println("Found entity: " + entity.getType());
        }
        return super.use(world, user, hand);
    }
}
