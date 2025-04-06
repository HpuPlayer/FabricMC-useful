package top.ioart.items.tools;

import net.minecraft.block.SoulSandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
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
import org.jetbrains.annotations.Nullable;

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
        Box box = user.getBoundingBox().expand(16); // 定义探测范围
        List<Entity> otherEntities = world.getOtherEntities(user, box);
//        List<Entity> nearbyEntities = user.getWorld().getEntitiesByClass(Entity.class, box, entity -> true);
        for (Entity entity : otherEntities) {
            Vec3d pos = entity.getPos();
            ItemStack offHandStack = user.getOffHandStack().copy();
            if (!offHandStack.isEmpty()&&offHandStack.getItem() instanceof FireworkRocketItem){
                FireworkRocketEntity reading = new FireworkRocketEntity(world, offHandStack, pos.x, pos.y, pos.z, true);
                world.spawnEntity(reading);
                entity.startRiding(reading,true);
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        Box expand = entity.getBoundingBox().expand(1.5);
        List<Entity> otherEntities = world.getOtherEntities(entity, expand);
        for (Entity otherEntity : otherEntities) {
            if (!(otherEntity instanceof ProjectileEntity))continue;
            Vec3d negate = otherEntity.getVelocity().negate();
//            otherEntity.setVelocity(negate);
            otherEntity.addVelocity(negate);
            otherEntity.setNoGravity(true);
//            otherEntity.addVelocity(negate);
//            otherEntity.addVelocity(negate);
//            otherEntity.addVelocity(negate);
//            otherEntity.addVelocity(negate);
        }
        for (double i = expand.minX; i < expand.maxX; i+= 0.4) {
            world.spawnParticles(ParticleTypes.ENCHANT, i, expand.maxY, expand.minZ, 1, 0, 0, 0, 0.01);
        }
        for (double i = expand.minX; i < expand.maxX; i+= 0.4) {
            world.spawnParticles(ParticleTypes.ENCHANT, i, expand.maxY, expand.maxZ, 1, 0, 0, 0, 0.01);
        }
        /*for (double j = expand.minY; j < expand.maxY; j+= 0.2){
            world.spawnParticles(ParticleTypes.ENCHANT, expand.minX, j, expand.minZ, 1, 0, 0, 0, 0.01);
        }*/
        for (double k = expand.minZ; k < expand.maxZ; k+= 0.4){
            world.spawnParticles(ParticleTypes.ENCHANT, expand.minX, expand.maxY, k, 1, 0, 0, 0, 0.01);
        }
        for (double k = expand.minZ; k < expand.maxZ; k+= 0.4){
            world.spawnParticles(ParticleTypes.ENCHANT, expand.maxX, expand.maxY, k, 1, 0, 0, 0, 0.01);
        }
        super.inventoryTick(stack, world, entity, slot);
    }
}
