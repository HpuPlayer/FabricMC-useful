package top.ioart.items.tools;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FireworksComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class ItemMagicStick extends Item {
    public ItemMagicStick(Settings settings) {
        super(settings.maxCount(1).rarity(Rarity.EPIC));
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        //spawnFirework(world,user);
        int range = 16;
        Vec3d start = user.getCameraPosVec(1.0F); // 玩家眼睛位置
        Vec3d end = start.add(user.getRotationVec(1.0F).multiply(range)); // 视线终点
        Box userBox = user.getBoundingBox().expand(range); // 定义探测范围
        for (Entity entity : world.getOtherEntities(user, userBox)) {
            if (entity instanceof FireworkRocketEntity)continue;
            Box entityBox = entity.getBoundingBox();
            Optional<Vec3d> hitResult = entityBox.raycast(start, end);
            if (hitResult.isPresent()){
                user.sendMessage(Text.of("Found entity: " + entity.getType()),true);
                entity.setVelocity(user.getRotationVec(2.0F));
                //user.startRiding(entity);
            }
        }
        return super.use(world, user, hand);
    }

    private void getStack(PlayerEntity user, Hand hand){
        ItemStack defaultStack = user.getStackInHand(hand);
        user.sendMessage(Text.of(defaultStack.toString()),true);
    }

    private void spawnFirework(World world, PlayerEntity user){
        Vec3d lookDirection = user.getRotationVector(); // 获取玩家视线方向
        Vec3d startPos = user.getEyePos().add(0,1,0); // 获取玩家眼睛位置
        user.sendMessage(Text.of("Look Direction: " + lookDirection.toString()),true);
        // 循环生成粒子
        for (double i = 0; i < 16; i += 0.3) {
            Vec3d particlePos = startPos.add(lookDirection.multiply(i)); // 计算粒子位置
            world.addParticleClient(ParticleTypes.FIREWORK, particlePos.x, particlePos.y, particlePos.z, 0, 0, 0);
        }
    }


}
