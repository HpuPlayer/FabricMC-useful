package top.ioart.items.tools;

import net.minecraft.block.Blocks;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

public class ItemChuckCLear extends Item {

    public ItemChuckCLear(Settings settings) {
        super(settings.rarity(Rarity.EPIC).maxCount(1));
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient){
            WorldChunk worldChunk = world.getWorldChunk(user.getBlockPos());
            ChunkPos pos = worldChunk.getPos();
            user.sendMessage(Text.of(pos),true);
            int minX = pos.getStartX();
            int minZ = pos.getStartZ();
            int maxX = pos.getEndX();
            int maxZ = pos.getEndZ();
            for (int x = minX; x <= maxX; x++) {
                for (int z = minZ; z <= maxZ; z++){
                    for (int y = -64; y <= 255; y++){
                        BlockPos blockPos1 = new BlockPos(x,y,z);
                        world.setBlockState(blockPos1, Blocks.AIR.getDefaultState(),3);
                    }
                }
            }
        }
        return super.use(world, user, hand);
    }
}
