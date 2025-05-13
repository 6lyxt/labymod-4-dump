// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util;

import net.labymod.api.util.function.Functional;
import java.util.HashMap;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;

public class LegacyEntityTypeRegistry
{
    private static final String MINECRAFT_ID = "minecraft";
    private static final Map<Class<?>, ResourceLocation> IDS;
    private static final Map<String, String> LEGACY_IDS;
    
    public static void register(final Class<?> entityClass, final String id) {
        if (LegacyEntityTypeRegistry.IDS.containsKey(entityClass)) {
            final ResourceLocation entityId = LegacyEntityTypeRegistry.IDS.get(entityClass);
            throw new IllegalStateException("Entity class \"" + entityClass.getName() + "\" has already been registered with the value \"" + String.valueOf(entityId) + "\". (Other: " + id);
        }
        LegacyEntityTypeRegistry.IDS.put(entityClass, ResourceLocation.create("minecraft", id));
    }
    
    public static ResourceLocation getId(final Class<?> entityClass) {
        return LegacyEntityTypeRegistry.IDS.get(entityClass);
    }
    
    public static String getLegacyId(final String key) {
        return LegacyEntityTypeRegistry.LEGACY_IDS.getOrDefault(key, key);
    }
    
    static {
        IDS = new HashMap<Class<?>, ResourceLocation>();
        LEGACY_IDS = Functional.of((HashMap)new HashMap(), mappings -> {
            mappings.put("Item", "item");
            mappings.put("XPOrb", "xp_orb");
            mappings.put("AreaEffectCloud", "area_effect_cloud");
            mappings.put("ElderGuardian", "elder_guardian");
            mappings.put("WitherSkeleton", "wither_skeleton");
            mappings.put("Stray", "stray");
            mappings.put("ThrownEgg", "egg");
            mappings.put("LeashKnot", "leash_knot");
            mappings.put("Painting", "painting");
            mappings.put("Arrow", "arrow");
            mappings.put("Snowball", "snowball");
            mappings.put("Fireball", "fireball");
            mappings.put("SmallFireball", "small_fireball");
            mappings.put("ThrownEnderpearl", "ender_pearl");
            mappings.put("EyeOfEnderSignal", "eye_of_ender_signal");
            mappings.put("ThrownPotion", "potion");
            mappings.put("ThrownExpBottle", "xp_bottle");
            mappings.put("ItemFrame", "item_frame");
            mappings.put("WitherSkull", "wither_skull");
            mappings.put("PrimedTnt", "tnt");
            mappings.put("FallingSand", "falling_block");
            mappings.put("FireworksRocketEntity", "fireworks_rocket");
            mappings.put("Husk", "husk");
            mappings.put("SpectralArrow", "spectral_arrow");
            mappings.put("ShulkerBullet", "shulker_bullet");
            mappings.put("DragonFireball", "dragon_fireball");
            mappings.put("ZombieVillager", "zombie_villager");
            mappings.put("SkeletonHorse", "skeleton_horse");
            mappings.put("ZombieHorse", "zombie_horse");
            mappings.put("ArmorStand", "armor_stand");
            mappings.put("Donkey", "donkey");
            mappings.put("Mule", "mule");
            mappings.put("EvocationFangs", "evocation_fangs");
            mappings.put("EvocationIllager", "evocation_illager");
            mappings.put("Vex", "vex");
            mappings.put("VindicationIllager", "vindication_illager");
            mappings.put("IllusionIllager", "illusion_illager");
            mappings.put("MinecartCommandBlock", "commandblock_minecart");
            mappings.put("Boat", "boat");
            mappings.put("MinecartRideable", "minecart");
            mappings.put("MinecartChest", "chest_minecart");
            mappings.put("MinecartFurnace", "furnace_minecart");
            mappings.put("MinecartTNT", "tnt_minecart");
            mappings.put("MinecartHopper", "hopper_minecart");
            mappings.put("MinecartSpawner", "spawner_minecart");
            mappings.put("Creeper", "creeper");
            mappings.put("Skeleton", "skeleton");
            mappings.put("Spider", "spider");
            mappings.put("Giant", "giant");
            mappings.put("Zombie", "zombie");
            mappings.put("Slime", "slime");
            mappings.put("Ghast", "ghast");
            mappings.put("PigZombie", "zombie_pigman");
            mappings.put("Enderman", "enderman");
            mappings.put("CaveSpider", "cave_spider");
            mappings.put("Silverfish", "silverfish");
            mappings.put("Blaze", "blaze");
            mappings.put("LavaSlime", "magma_cube");
            mappings.put("EnderDragon", "ender_dragon");
            mappings.put("WitherBoss", "wither");
            mappings.put("Bat", "bat");
            mappings.put("Witch", "witch");
            mappings.put("Endermite", "endermite");
            mappings.put("Guardian", "guardian");
            mappings.put("Shulker", "shulker");
            mappings.put("Pig", "pig");
            mappings.put("Sheep", "sheep");
            mappings.put("Cow", "cow");
            mappings.put("Chicken", "chicken");
            mappings.put("Squid", "squid");
            mappings.put("Wolf", "wolf");
            mappings.put("MushroomCow", "mooshroom");
            mappings.put("SnowMan", "snowman");
            mappings.put("Ozelot", "ocelot");
            mappings.put("VillagerGolem", "villager_golem");
            mappings.put("Horse", "horse");
            mappings.put("Rabbit", "rabbit");
            mappings.put("PolarBear", "polar_bear");
            mappings.put("Llama", "llama");
            mappings.put("LlamaSpit", "llama_spit");
            mappings.put("Parrot", "parrot");
            mappings.put("Villager", "villager");
            mappings.put("EnderCrystal", "ender_crystal");
        });
    }
}
