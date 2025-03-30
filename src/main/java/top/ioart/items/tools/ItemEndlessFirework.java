package top.ioart.items.tools;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FireworksComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ItemEndlessFirework extends FireworkRocketItem {

    public ItemEndlessFirework(Item.Settings settings) {
        super(settings
                .rarity(Rarity.EPIC)
                .fireproof()
                .maxCount(1)
                .component(DataComponentTypes.FIREWORKS, new FireworksComponent(9, List.of())));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world instanceof ServerWorld serverWorld) {
            ItemStack itemStack = context.getStack();
            Vec3d vec3d = context.getHitPos();
            Direction direction = context.getSide();
            ProjectileEntity.spawn(
                    new FireworkRocketEntity(
                            world,
                            context.getPlayer(),
                            vec3d.x + direction.getOffsetX() * 0.15,
                            vec3d.y + direction.getOffsetY() * 0.15,
                            vec3d.z + direction.getOffsetZ() * 0.15,
                            itemStack
                    ),
                    serverWorld,
                    itemStack
            );
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (user.isGliding()) {
            ItemStack itemStack = user.getStackInHand(hand);
            if (world instanceof ServerWorld serverWorld) {
                ProjectileEntity.spawn(new FireworkRocketEntity(world, itemStack, user), serverWorld, itemStack);
                user.incrementStat(Stats.USED.getOrCreateStat(this));
            }

            return ActionResult.SUCCESS;
        } else {
            return ActionResult.PASS;
        }
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        ProjectileEntity entity = super.createEntity(world, pos, stack, direction);
        Entity holder = stack.getHolder();
        return entity;
    }
}
