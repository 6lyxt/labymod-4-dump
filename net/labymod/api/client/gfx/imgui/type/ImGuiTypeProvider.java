// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.imgui.type;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ImGuiTypeProvider
{
    ImGuiBooleanType booleanType();
    
    ImGuiBooleanType booleanType(final boolean p0);
    
    ImGuiBooleanType booleanType(final ImGuiBooleanType p0);
    
    ImGuiDoubleType doubleType();
    
    ImGuiDoubleType doubleType(final double p0);
    
    ImGuiDoubleType doubleType(final ImGuiDoubleType p0);
    
    ImGuiFloatType floatType();
    
    ImGuiFloatType floatType(final float p0);
    
    ImGuiFloatType floatType(final ImGuiFloatType p0);
    
    ImGuiIntegerType integerType();
    
    ImGuiIntegerType integerType(final int p0);
    
    ImGuiIntegerType integerType(final ImGuiIntegerType p0);
    
    ImGuiLongType longType();
    
    ImGuiLongType longType(final long p0);
    
    ImGuiLongType longType(final ImGuiLongType p0);
}
