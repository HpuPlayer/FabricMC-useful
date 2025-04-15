package top.ioart.items;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import top.ioart.items.tools.*;

import java.util.function.Function;

public class UsefulItems {
    private UsefulItems() {
    }

    public static final Item CUSTOM_ITEM = register("custom_item", Item::new, new Item.Settings());
    public static final Item ENDLESS_FIREWORK = register("endless_firework", ItemEndlessFirework::new, new Item.Settings());
    public static final Item FINAL_SWORD = register("final_sword", ItemFinalSword::new,new Item.Settings());
    public static final Item CHUCK_CLEAR = register("chuck_clear", ItemChuckCLear::new,new Item.Settings());
    public static final Item TRAVEL_STICK = register("travel_stick", ItemTravelStick::new,new Item.Settings());
    public static final Item MAGIC_STICK = register("magic_stick", ItemMagicStick::new,new Item.Settings());
    public static final Item FLYING_SWORD = register("flying_sword", ItemFlyingSword::new,new Item.Settings());

    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("useful", path));
        return Items.register(registryKey, factory, settings);
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register((itemGroup) -> itemGroup.add(UsefulItems.CUSTOM_ITEM));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register(group -> group.add(UsefulItems.ENDLESS_FIREWORK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register(group-> group.add(UsefulItems.FINAL_SWORD));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register(group -> group.add(UsefulItems.CHUCK_CLEAR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register(group -> group.add(UsefulItems.TRAVEL_STICK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register(group-> group.add(UsefulItems.MAGIC_STICK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register(group-> group.add(UsefulItems.FLYING_SWORD));
    }
}
