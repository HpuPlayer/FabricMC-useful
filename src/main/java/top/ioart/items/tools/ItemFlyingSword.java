package top.ioart.items.tools;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import top.ioart.entity.ModEntities;
import top.ioart.entity.flyingsword.FlyingSwordEntity;

import java.util.Random;


public class ItemFlyingSword extends Item {
    public ItemFlyingSword(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        double x = user.getX();
        double y = user.getY();
        double z = user.getZ();
        FlyingSwordEntity sword = new FlyingSwordEntity(ModEntities.FLYING_SWORD, world,user);
        sword.setPos(x, y + user.getEyeHeight(user.getPose()), z);

        float speed = 3;

        // 获取玩家的 yaw 和 pitch
        float yaw = user.getYaw();
        float pitch = user.getPitch();

        // 计算视线方向
        float yawRadians = (float) Math.toRadians(yaw);
        float pitchRadians = (float) Math.toRadians(pitch);

        double dx = -Math.sin(yawRadians) * Math.cos(pitchRadians);
        double dy = -Math.sin(pitchRadians);
        double dz = Math.cos(yawRadians) * Math.cos(pitchRadians);

        // 归一化方向向量（可选，如果需要确保速度大小精确）
        float length = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
        dx /= length;
        dy /= length;
        dz /= length;

        sword.addVelocity(dx*speed, dy*speed, dz*speed);

        // 根据运动方向计算初始旋转
        float arrowYaw = (float) Math.toDegrees(Math.atan2(dx, dz));
        float arrowPitch = (float) Math.toDegrees(Math.asin(dy));

        if (Math.abs(arrowPitch - 90) > 0.1f){
            sword.setYaw(arrowYaw);
            sword.setPitch(arrowPitch);
        }else {
            sword.setPitch(arrowPitch);
        }
        sword.setNoGravity(true);
        world.spawnEntity(sword);
        return super.use(world, user, hand);
    }


}
