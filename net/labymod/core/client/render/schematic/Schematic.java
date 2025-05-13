// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic;

import net.labymod.api.configuration.labymod.main.laby.appearance.DynamicBackgroundConfig;
import net.labymod.core.client.render.schematic.block.material.material.Material;
import net.labymod.api.nbt.tags.NBTTagList;
import net.labymod.api.nbt.NBTFactory;
import java.util.Iterator;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.render.schematic.lightning.legacy.WorldLightAccessor;
import net.labymod.core.client.render.schematic.lightning.legacy.DefaultLegacyLightEngine;
import net.labymod.core.client.render.schematic.block.material.MaterialRegistry;
import net.labymod.api.nbt.tags.NBTTagInt;
import java.util.Map;
import net.labymod.api.Laby;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.InputStream;
import net.labymod.api.util.io.IOUtil;
import java.nio.file.Path;
import java.io.IOException;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.render.schematic.lightning.legacy.LegacyLightEngine;
import net.labymod.core.client.render.schematic.lightning.legacy.ColoredLegacyLightEngine;
import net.labymod.api.nbt.NBTTag;
import java.util.HashMap;
import java.util.List;
import net.labymod.core.client.render.schematic.block.Block;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.labymod.api.util.logging.Logging;

public class Schematic implements SchematicAccessor
{
    private final Logging logger;
    private final int paletteMax;
    private final int version;
    private final int dataVersion;
    private final short width;
    private final short height;
    private final short length;
    private final Int2ObjectMap<Block> palette;
    private final List<HashMap<String, NBTTag<?>>> blockEntities;
    private final byte[] blocks;
    private final int[] offset;
    private final Block[] blockCache;
    private final Block airBlock;
    private final Boolean[] translucentCache;
    private final Boolean[] fullBlockCache;
    private final Boolean[] isLightSourceCache;
    private final ColoredLegacyLightEngine legacyColoredLightEngine;
    private final LegacyLightEngine legacyDefaultLightEngine;
    private final int chunksInWidth;
    private final int chunksInHeight;
    private final int chunksInLength;
    private WEOffset weOffsetX;
    
    public Schematic(final ResourceLocation resourceLocation) throws IOException {
        this(resourceLocation.openStream());
    }
    
    public Schematic(final Path path) throws IOException {
        this(IOUtil.newInputStream(path));
    }
    
    public Schematic(final InputStream inputStream) throws IOException {
        this.logger = Logging.getLogger();
        this.palette = (Int2ObjectMap<Block>)new Int2ObjectOpenHashMap();
        final NBTTagCompound root = Laby.references().nbtFactory().readCompressed(inputStream);
        final Map<String, NBTTag<?>> items = root.value();
        if (items == null) {
            throw new IOException("Schematic file is empty");
        }
        this.paletteMax = items.get("PaletteMax").value();
        this.version = items.get("Version").value();
        this.dataVersion = items.get("DataVersion").value();
        this.offset = items.get("Offset").value();
        final Map<String, NBTTagInt> metadata = items.get("Metadata").value();
        if (metadata != null) {
            final NBTTagInt xTag = metadata.get("WEOffsetX");
            final NBTTagInt yTag = metadata.get("WEOffsetY");
            final NBTTagInt zTag = metadata.get("WEOffsetZ");
            if (xTag != null && yTag != null && zTag != null) {
                final Integer x = xTag.value();
                final Integer y = yTag.value();
                final Integer z = zTag.value();
                if (x != null && y != null && z != null) {
                    this.weOffsetX = new WEOffset(x, y, z);
                }
            }
        }
        this.width = items.get("Width").value();
        this.height = items.get("Height").value();
        this.length = items.get("Length").value();
        final Map<String, NBTTagInt> palette = items.get("Palette").value();
        if (palette != null) {
            for (final Map.Entry<String, NBTTagInt> entry : palette.entrySet()) {
                final String[] args = entry.getKey().split(":", 2);
                final String namespace = args[0];
                final String id = args[1];
                final Integer index = entry.getValue().value();
                if (index != null) {
                    this.palette.put((int)index, (Object)new Block(namespace, id));
                }
            }
        }
        this.blocks = items.get("BlockData").value();
        assert this.blocks != null;
        this.blockEntities = items.get("BlockEntities").value();
        this.blockCache = new Block[this.blocks.length];
        this.translucentCache = new Boolean[this.blocks.length];
        this.fullBlockCache = new Boolean[this.blocks.length];
        this.isLightSourceCache = new Boolean[this.blocks.length];
        this.airBlock = new Block("minecraft", MaterialRegistry.AIR);
        this.legacyColoredLightEngine = new ColoredLegacyLightEngine(this, this.width, this.height, this.length, 1, 1, 1);
        this.legacyDefaultLightEngine = new DefaultLegacyLightEngine(this, this.width, this.height, this.length, 1);
        final NBTTag<?> blockLight = root.get("BlockLight");
        if (blockLight != null) {
            this.legacyDefaultLightEngine.setData((byte[])(Object)blockLight.value());
        }
        else {
            this.logger.warn("Schematic file does not contain BlockLight. Calculate it and save it using Schematic#saveTo(Path)", new Object[0]);
        }
        final NBTTag<?> coloredBlockRed = root.get("BlockLightRed");
        final NBTTag<?> coloredBlockGreen = root.get("BlockLightGreen");
        final NBTTag<?> coloredBlockBlue = root.get("BlockLightBlue");
        if (coloredBlockRed != null && coloredBlockGreen != null && coloredBlockBlue != null) {
            final byte[] red = (Object)coloredBlockRed.value();
            final byte[] green = (Object)coloredBlockGreen.value();
            final byte[] blue = (Object)coloredBlockBlue.value();
            if (red != null && green != null && blue != null) {
                this.legacyColoredLightEngine.getRedEngine().setData(red);
                this.legacyColoredLightEngine.getGreenEngine().setData(green);
                this.legacyColoredLightEngine.getBlueEngine().setData(blue);
            }
        }
        else {
            this.logger.warn("Schematic file does not contain colored BlockLight. Calculate it and save it using Schematic#saveTo(Path)", new Object[0]);
        }
        this.chunksInWidth = MathHelper.ceil(this.width / 16.0f);
        this.chunksInHeight = MathHelper.ceil(this.height / 16.0f);
        this.chunksInLength = MathHelper.ceil(this.length / 16.0f);
        inputStream.close();
    }
    
    public void saveTo(final Path path) throws IOException {
        final NBTFactory factory = Laby.references().nbtFactory();
        final NBTTagCompound root = factory.compound();
        if (IOUtil.exists(path)) {
            IOUtil.delete(path);
        }
        root.set("PaletteMax", factory.create(this.paletteMax));
        root.set("Version", factory.create(this.version));
        root.set("DataVersion", factory.create(this.dataVersion));
        root.set("Offset", factory.create(this.offset));
        root.set("Width", factory.create(this.width));
        root.set("Height", factory.create(this.height));
        root.set("Length", factory.create(this.length));
        final NBTTagCompound metadata = factory.compound();
        if (this.weOffsetX != null) {
            metadata.set("WEOffsetX", factory.create(this.weOffsetX.getX()));
            metadata.set("WEOffsetY", factory.create(this.weOffsetX.getY()));
            metadata.set("WEOffsetZ", factory.create(this.weOffsetX.getZ()));
        }
        root.set("Metadata", metadata);
        final NBTTagCompound palette = factory.compound();
        for (final Map.Entry<Integer, Block> entry : ((Map<Object, Object>)this.palette).entrySet()) {
            palette.set(entry.getValue().toString(), factory.create(entry.getKey()));
        }
        root.set("Palette", palette);
        root.set("BlockData", factory.create(this.blocks));
        final NBTTagList<?, NBTTagCompound> blockEntities = factory.list();
        for (final HashMap<String, NBTTag<?>> attributes : this.blockEntities) {
            final NBTTagCompound blockEntity = factory.compound();
            for (final Map.Entry<String, NBTTag<?>> entry2 : attributes.entrySet()) {
                blockEntity.set(entry2.getKey(), entry2.getValue());
            }
            blockEntities.add(blockEntity);
        }
        root.set("BlockEntities", blockEntities);
        if (!this.legacyDefaultLightEngine.isFullyCalculated()) {
            throw new IllegalStateException("Light not calculated for default block light. Did you preload it with the enabled setting?");
        }
        if (!this.legacyColoredLightEngine.isFullyCalculated()) {
            throw new IllegalStateException("Light not calculated for colored block light. Did you preload it with the enabled setting?");
        }
        root.set("BlockLight", factory.create(this.legacyDefaultLightEngine.getData()));
        final ColoredLegacyLightEngine coloredEngine = this.legacyColoredLightEngine;
        root.set("BlockLightRed", factory.create(coloredEngine.getRedEngine().getData()));
        root.set("BlockLightGreen", factory.create(coloredEngine.getGreenEngine().getData()));
        root.set("BlockLightBlue", factory.create(coloredEngine.getBlueEngine().getData()));
        factory.writeCompressed(root, IOUtil.newOutputStream(path));
        this.logger.info("Saved schematic to {}", path.toAbsolutePath());
    }
    
    @Override
    public Block getBlockAt(final int x, final int y, final int z) {
        final int index = this.getIndex(x, y, z);
        if (index < 0 || index >= this.blockCache.length) {
            return this.airBlock;
        }
        Block block = this.blockCache[index];
        if (block == null) {
            final int paletteId = this.getPaletteId(x, y, z);
            if (paletteId == -1) {
                return this.airBlock;
            }
            block = (Block)this.palette.get(paletteId);
            this.blockCache[index] = block;
        }
        return block;
    }
    
    @Override
    public boolean isTranslucent(final int x, final int y, final int z) {
        final int index = this.getIndex(x, y, z);
        if (index < 0 || index >= this.translucentCache.length) {
            return true;
        }
        Boolean translucent = this.translucentCache[index];
        if (translucent == null) {
            final Material material = this.getBlockAt(x, y, z).material();
            translucent = (this.translucentCache[index] = material.isTranslucent());
        }
        return translucent;
    }
    
    @Override
    public boolean isFullBlock(final int x, final int y, final int z) {
        final int index = this.getIndex(x, y, z);
        if (index < 0 || index >= this.fullBlockCache.length) {
            return false;
        }
        Boolean fullBlock = this.fullBlockCache[index];
        if (fullBlock == null) {
            final Material material = this.getBlockAt(x, y, z).material();
            fullBlock = (this.fullBlockCache[index] = material.isFullBlock());
        }
        return fullBlock;
    }
    
    @Override
    public boolean isLightSource(final int x, final int y, final int z) {
        final int index = this.getIndex(x, y, z);
        if (index < 0 || index >= this.isLightSourceCache.length) {
            return true;
        }
        Boolean isLightSource = this.isLightSourceCache[index];
        if (isLightSource == null) {
            final Block block = this.getBlockAt(x, y, z);
            isLightSource = (this.isLightSourceCache[index] = block.material().isLightSource(block));
        }
        return isLightSource;
    }
    
    private int getPaletteId(final int x, final int y, final int z) {
        final int index = this.getIndex(x, y, z);
        return (index < 0 || index >= this.blocks.length) ? -1 : (this.blocks[index] & 0xFF);
    }
    
    @Override
    public LegacyLightEngine legacyLightEngine() {
        final DynamicBackgroundConfig config = Laby.labyAPI().config().appearance().dynamicBackground();
        return (config.shader().get() || !config.coloredLight().get()) ? this.legacyDefaultLightEngine : this.legacyColoredLightEngine;
    }
    
    @Override
    public short getWidth() {
        return this.width;
    }
    
    @Override
    public short getHeight() {
        return this.height;
    }
    
    @Override
    public short getLength() {
        return this.length;
    }
    
    public int getChunksInLength() {
        return this.chunksInLength;
    }
    
    public int getChunksInHeight() {
        return this.chunksInHeight;
    }
    
    public int getChunksInWidth() {
        return this.chunksInWidth;
    }
    
    private int getIndex(final int x, final int y, final int z) {
        return (y * this.length + z) * this.width + x;
    }
    
    private static class WEOffset
    {
        private final int x;
        private final int y;
        private final int z;
        
        public WEOffset(final int x, final int y, final int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        public int getX() {
            return this.x;
        }
        
        public int getY() {
            return this.y;
        }
        
        public int getZ() {
            return this.z;
        }
    }
}
