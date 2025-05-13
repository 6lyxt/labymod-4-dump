// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.lightning.legacy;

import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.SchematicAccessor;

public class ColoredLegacyLightEngine implements WorldLightAccessor, LegacyLightEngine
{
    private final SchematicAccessor world;
    private final DefaultLegacyLightEngine redEngine;
    private final DefaultLegacyLightEngine greenEngine;
    private final DefaultLegacyLightEngine blueEngine;
    
    public ColoredLegacyLightEngine(final SchematicAccessor world, final int width, final int height, final int length, final int redSkyLevel, final int greenSkyLevel, final int blueSkyLevel) {
        this.world = world;
        this.redEngine = new DefaultLegacyLightEngine(this, width, height, length, redSkyLevel);
        this.greenEngine = new DefaultLegacyLightEngine(this, width, height, length, greenSkyLevel);
        this.blueEngine = new DefaultLegacyLightEngine(this, width, height, length, blueSkyLevel);
    }
    
    @Override
    public Block getBlockAt(final int x, final int y, final int z) {
        return this.world.getBlockAt(x, y, z);
    }
    
    @Override
    public boolean isLightSource(final int x, final int y, final int z) {
        return this.world.isLightSource(x, y, z);
    }
    
    @Override
    public boolean isTranslucent(final int x, final int y, final int z) {
        return this.world.isTranslucent(x, y, z);
    }
    
    @Override
    public boolean isFullBlock(final int x, final int y, final int z) {
        return this.world.isFullBlock(x, y, z);
    }
    
    @Override
    public int getLightLevel(final DefaultLegacyLightEngine engine, final Block block) {
        final int maxLevel = block.material().getLight(block);
        final int color = block.material().getLightColor(block);
        int red = color >> 16 & 0xFF;
        int green = color >> 8 & 0xFF;
        int blue = color & 0xFF;
        final int maxColor = Math.max(Math.max(red, green), blue);
        if (maxColor > 0) {
            final float factor = 255.0f / maxColor;
            red = this.applyColorCorrectionPre(red, factor, 0);
            green = this.applyColorCorrectionPre(green, factor, 1);
            blue = this.applyColorCorrectionPre(blue, factor, 2);
        }
        if (engine == this.redEngine) {
            return (int)(maxLevel / 255.0f * red);
        }
        if (engine == this.greenEngine) {
            return (int)(maxLevel / 255.0f * green);
        }
        if (engine == this.blueEngine) {
            return (int)(maxLevel / 255.0f * blue);
        }
        return 0;
    }
    
    @Override
    public int getLightAt(final int x, final int y, final int z) {
        return Math.max(Math.max(this.redEngine.getLightAt(x, y, z), this.greenEngine.getLightAt(x, y, z)), this.blueEngine.getLightAt(x, y, z));
    }
    
    @Override
    public float getRedStrengthAt(final int x, final int y, final int z) {
        final float level = this.redEngine.getAverageLightLevelAt(x, y, z);
        return this.applyColorCorrectionPost(this.getStrengthFrom(level), 0);
    }
    
    @Override
    public float getGreenStrengthAt(final int x, final int y, final int z) {
        final float level = this.greenEngine.getAverageLightLevelAt(x, y, z);
        return this.applyColorCorrectionPost(this.getStrengthFrom(level), 1);
    }
    
    @Override
    public float getBlueStrengthAt(final int x, final int y, final int z) {
        final float level = this.blueEngine.getAverageLightLevelAt(x, y, z);
        return this.applyColorCorrectionPost(this.getStrengthFrom(level), 2);
    }
    
    @Override
    public void handleLightUpdates() {
        this.redEngine.handleLightUpdates();
        this.greenEngine.handleLightUpdates();
        this.blueEngine.handleLightUpdates();
    }
    
    @Override
    public boolean isDirty() {
        return this.redEngine.isDirty() || this.greenEngine.isDirty() || this.blueEngine.isDirty();
    }
    
    @Override
    public boolean isInProgress() {
        return this.redEngine.isInProgress() || this.greenEngine.isInProgress() || this.blueEngine.isInProgress();
    }
    
    @Override
    public void reset() {
        this.redEngine.reset();
        this.greenEngine.reset();
        this.blueEngine.reset();
    }
    
    @Override
    public float getAverageLightLevelAt(final int x, final int y, final int z) {
        return Math.max(Math.max(this.redEngine.getAverageLightLevelAt(x, y, z), this.greenEngine.getAverageLightLevelAt(x, y, z)), this.blueEngine.getAverageLightLevelAt(x, y, z));
    }
    
    @Override
    public byte[] getData() {
        throw new UnsupportedOperationException("Select an engine first");
    }
    
    @Override
    public void setData(final byte[] data) {
        throw new UnsupportedOperationException("Select an engine first");
    }
    
    private int applyColorCorrectionPre(final int value, final float factor, final int channel) {
        return (int)MathHelper.clamp(value * factor * 1.45f, 0.0f, 255.0f);
    }
    
    private float applyColorCorrectionPost(final float strength, final int channel) {
        return (float)Math.min(Math.pow(2.718281828459045, -2.5f + strength * 2.5f), 1.0);
    }
    
    @Override
    public float applyBrightness(final float value) {
        final float x = ColoredLegacyLightEngine.brightnessSetting.get();
        final float curve = (float)(-Math.pow(2.718281828459045, 1.0f - x * 1.2f)) + 1.718f;
        return Math.min(value * curve, 1.0f);
    }
    
    public DefaultLegacyLightEngine getRedEngine() {
        return this.redEngine;
    }
    
    public DefaultLegacyLightEngine getGreenEngine() {
        return this.greenEngine;
    }
    
    public DefaultLegacyLightEngine getBlueEngine() {
        return this.blueEngine;
    }
}
