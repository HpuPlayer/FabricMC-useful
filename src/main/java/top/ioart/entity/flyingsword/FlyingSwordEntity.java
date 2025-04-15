package top.ioart.entity.flyingsword;

import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class FlyingSwordEntity extends ArrowEntity {

    private int leftTime = 0;

    public FlyingSwordEntity(EntityType<? extends ArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    public FlyingSwordEntity(EntityType<? extends ArrowEntity> entityType, World world, Entity entity) {
        super(entityType, world);
        setOwner(entity);
    }

    public FlyingSwordEntity(World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(world, x, y, z, stack, shotFrom);
    }

    public FlyingSwordEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(world, owner, stack, shotFrom);
    }

    @Override
    public void setOwner(@Nullable Entity entity) {
        super.setOwner(entity);
    }

    @Override
    public double getEyeY() {
        return super.getEyeY();
    }


    @Override
    public void tick() {
        leftTime++;
        if (leftTime>100){
            this.discard();
        }
        super.tick();
    }

    @Override
    protected void onHit(LivingEntity target) {
        World world = this.getWorld();
        if (world instanceof ServerWorld){
            target.damage((ServerWorld) world, this.getDamageSources().magic(), 100);
            world.createExplosion(target,target.getX(),target.getY(),target.getZ(),8, World.ExplosionSourceType.TNT);
        }

        if (target instanceof EnderDragonEntity){
            target.onDeath(this.getDamageSources().magic());
            System.out.println("==");
        }

        if (getOwner() instanceof PlayerEntity entity){
            entity.sendMessage(Text.of("剩余血量: "+target.getHealth()),true);
        }
    }





    @Override
    protected boolean tryPickup(PlayerEntity player) {
        return false;
    }


}
