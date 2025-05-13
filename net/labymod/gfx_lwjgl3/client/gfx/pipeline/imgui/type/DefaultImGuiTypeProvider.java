// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.type;

import net.labymod.api.client.gfx.imgui.type.ImGuiLongType;
import net.labymod.api.client.gfx.imgui.type.ImGuiIntegerType;
import net.labymod.api.client.gfx.imgui.type.ImGuiFloatType;
import net.labymod.api.client.gfx.imgui.type.ImGuiDoubleType;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider;

@Singleton
@Implements(ImGuiTypeProvider.class)
public class DefaultImGuiTypeProvider implements ImGuiTypeProvider
{
    @Override
    public ImGuiBooleanType booleanType() {
        return new DefaultImGuiBooleanType();
    }
    
    @Override
    public ImGuiBooleanType booleanType(final boolean value) {
        return new DefaultImGuiBooleanType(value);
    }
    
    @Override
    public ImGuiBooleanType booleanType(final ImGuiBooleanType other) {
        return new DefaultImGuiBooleanType(other);
    }
    
    @Override
    public ImGuiDoubleType doubleType() {
        return new DefaultImGuiDoubleType();
    }
    
    @Override
    public ImGuiDoubleType doubleType(final double value) {
        return new DefaultImGuiDoubleType(value);
    }
    
    @Override
    public ImGuiDoubleType doubleType(final ImGuiDoubleType other) {
        return new DefaultImGuiDoubleType(other);
    }
    
    @Override
    public ImGuiFloatType floatType() {
        return new DefaultImGuiFloatType();
    }
    
    @Override
    public ImGuiFloatType floatType(final float value) {
        return new DefaultImGuiFloatType(value);
    }
    
    @Override
    public ImGuiFloatType floatType(final ImGuiFloatType other) {
        return new DefaultImGuiFloatType(other);
    }
    
    @Override
    public ImGuiIntegerType integerType() {
        return new DefaultImGuiIntegerType();
    }
    
    @Override
    public ImGuiIntegerType integerType(final int value) {
        return new DefaultImGuiIntegerType(value);
    }
    
    @Override
    public ImGuiIntegerType integerType(final ImGuiIntegerType other) {
        return new DefaultImGuiIntegerType(other);
    }
    
    @Override
    public ImGuiLongType longType() {
        return new DefaultImGuiLongType();
    }
    
    @Override
    public ImGuiLongType longType(final long value) {
        return new DefaultImGuiLongType(value);
    }
    
    @Override
    public ImGuiLongType longType(final ImGuiLongType other) {
        return new DefaultImGuiLongType(other);
    }
}
