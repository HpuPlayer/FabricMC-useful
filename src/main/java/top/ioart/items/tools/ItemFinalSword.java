package top.ioart.items.tools;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;

import java.util.Random;

public class ItemFinalSword extends Item {
    public ItemFinalSword(Settings settings) {
        super(settings.maxCount(1).rarity(Rarity.EPIC).fireproof());
    }

    private final Random random = new Random();


    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        user.playSound(SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT);
        return super.use(world, user, hand);
    }

}
