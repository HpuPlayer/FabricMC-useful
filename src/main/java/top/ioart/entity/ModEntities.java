package top.ioart.entity;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import top.ioart.entity.flyingsword.FlyingSwordEntity;

public class ModEntities implements ModInitializer {

    public static final EntityType<FlyingSwordEntity> FLYING_SWORD = register("flying_sword", FlyingSwordEntity::new, 0.5f, 0.5f);
    @Override
    public void onInitialize() {

    }

    public static <T extends Entity> EntityType<T> register(String path, EntityFactory<T> factory,float width, float height) {
        Identifier id = Identifier.of("useful", path);
        RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, id);
        EntityType<T> entityType = EntityType.Builder.create(factory::create, SpawnGroup.MISC)
                .dimensions(width, height)
                .build(key);
        Registry.register(Registries.ENTITY_TYPE, key, entityType);
        return entityType;
    }

    @FunctionalInterface
    public interface EntityFactory<T extends Entity> {
        T create(EntityType<T> type, World world);
    }
}