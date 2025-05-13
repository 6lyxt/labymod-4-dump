// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material;

import net.labymod.core.client.render.schematic.block.material.material.LeverMaterial;
import net.labymod.core.client.render.schematic.block.material.material.SnowBlockMaterial;
import net.labymod.core.client.render.schematic.block.material.material.PumpkinMaterial;
import net.labymod.core.client.render.schematic.block.material.material.IceMaterial;
import net.labymod.core.client.render.schematic.block.material.material.SnowMaterial;
import net.labymod.core.client.render.schematic.block.material.material.GlowstoneMaterial;
import net.labymod.core.client.render.schematic.block.material.material.SlabMaterial;
import net.labymod.core.client.render.schematic.block.material.material.RailMaterial;
import net.labymod.core.client.render.schematic.block.material.material.FenceMaterial;
import net.labymod.core.client.render.schematic.block.material.material.GrassMaterial;
import net.labymod.core.client.render.schematic.block.material.material.CrossPlanesMaterial;
import net.labymod.core.client.render.schematic.block.material.material.LiquidMaterial;
import net.labymod.core.client.render.schematic.block.material.material.TorchMaterial;
import net.labymod.core.client.render.schematic.block.material.material.FurnaceMaterial;
import net.labymod.core.client.render.schematic.block.material.material.CraftingTableMaterial;
import net.labymod.core.client.render.schematic.block.material.material.GrassBlockMaterial;
import net.labymod.core.client.render.schematic.block.material.material.SolidMaterial;
import net.labymod.core.client.render.schematic.block.material.material.EmptyMaterial;
import java.util.HashMap;
import org.jetbrains.annotations.Nullable;
import net.labymod.core.client.render.schematic.block.material.material.Material;
import java.util.Map;

public class MaterialRegistry
{
    private static final Map<String, Material> REGISTRY;
    public static final Material AIR;
    public static final Material STONE;
    public static final Material GRASS_BLOCK;
    public static final Material OAK_PLANKS;
    public static final Material COBBLESTONE;
    public static final Material COAL_ORE;
    public static final Material IRON_ORE;
    public static final Material GOLD_ORE;
    public static final Material DIAMOND_ORE;
    public static final Material CRAFTING_TABLE;
    public static final Material FURNACE;
    public static final Material DIRT;
    public static final Material GRAVEL;
    public static final Material TORCH;
    public static final Material WALL_TORCH;
    public static final Material SOUL_TORCH;
    public static final Material SOUL_WALL_TORCH;
    public static final Material LAVA;
    public static final Material COBWEB;
    public static final Material GRASS;
    public static final Material OAK_FENCE;
    public static final Material RAIL;
    public static final Material OAK_SLAB;
    public static final Material COBBLESTONE_SLAB;
    public static final Material DANDELION;
    public static final Material POPPY;
    public static final Material GLOWSTONE;
    public static final Material SNOW;
    public static final Material ICE;
    public static final Material PUMPKIN;
    public static final Material CARVED_PUMPKIN;
    public static final Material SNOW_BLOCK;
    public static final Material LEVER;
    
    public static void register(final Material material) {
        MaterialRegistry.REGISTRY.put(material.getId(), material);
    }
    
    public static boolean has(final String id) {
        return MaterialRegistry.REGISTRY.containsKey(id);
    }
    
    @Nullable
    public static Material getById(final String id) {
        return MaterialRegistry.REGISTRY.get(id);
    }
    
    static {
        REGISTRY = new HashMap<String, Material>();
        AIR = new EmptyMaterial("air");
        STONE = new SolidMaterial("stone");
        GRASS_BLOCK = new GrassBlockMaterial();
        OAK_PLANKS = new SolidMaterial("oak_planks");
        COBBLESTONE = new SolidMaterial("cobblestone");
        COAL_ORE = new SolidMaterial("coal_ore");
        IRON_ORE = new SolidMaterial("iron_ore");
        GOLD_ORE = new SolidMaterial("gold_ore");
        DIAMOND_ORE = new SolidMaterial("diamond_ore");
        CRAFTING_TABLE = new CraftingTableMaterial();
        FURNACE = new FurnaceMaterial();
        DIRT = new SolidMaterial("dirt");
        GRAVEL = new SolidMaterial("gravel");
        TORCH = new TorchMaterial("torch", TorchMaterial.Type.DEFAULT);
        WALL_TORCH = new TorchMaterial("wall_torch", TorchMaterial.Type.DEFAULT);
        SOUL_TORCH = new TorchMaterial("soul_torch", TorchMaterial.Type.SOUL);
        SOUL_WALL_TORCH = new TorchMaterial("soul_wall_torch", TorchMaterial.Type.SOUL);
        LAVA = new LiquidMaterial("lava");
        COBWEB = new CrossPlanesMaterial("cobweb");
        GRASS = new GrassMaterial();
        OAK_FENCE = new FenceMaterial("oak_fence");
        RAIL = new RailMaterial("rail");
        OAK_SLAB = new SlabMaterial("oak_slab");
        COBBLESTONE_SLAB = new SlabMaterial("cobblestone_slab");
        DANDELION = new CrossPlanesMaterial("dandelion");
        POPPY = new CrossPlanesMaterial("poppy");
        GLOWSTONE = new GlowstoneMaterial();
        SNOW = new SnowMaterial();
        ICE = new IceMaterial("ice");
        PUMPKIN = new PumpkinMaterial("pumpkin");
        CARVED_PUMPKIN = new PumpkinMaterial("carved_pumpkin");
        SNOW_BLOCK = new SnowBlockMaterial();
        LEVER = new LeverMaterial();
    }
}
