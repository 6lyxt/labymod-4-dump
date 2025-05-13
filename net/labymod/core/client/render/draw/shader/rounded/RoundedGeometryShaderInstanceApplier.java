// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw.shader.rounded;

import net.labymod.api.util.ColorUtil;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder;
import javax.inject.Inject;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public final class RoundedGeometryShaderInstanceApplier
{
    @Inject
    public RoundedGeometryShaderInstanceApplier() {
    }
    
    public void apply(final RoundedGeometryShaderInstance shaderInstance, final RoundedGeometryBuilder.RoundedData data) {
        shaderInstance.radiusUniform().set(data.rightBottomRadius(), data.rightTopRadius(), data.leftBottomRadius(), data.leftTopRadius());
        shaderInstance.lowerEdgeSoftnessUniform().set(data.lowerEdgeSoftness());
        shaderInstance.upperEdgeSoftnessUniform().set(data.upperEdgeSoftness());
        shaderInstance.verticesUniform().set(data.left(), data.top(), data.right(), data.bottom());
        shaderInstance.sizeRectUniform().set(data.right() - data.left(), data.bottom() - data.top());
        shaderInstance.borderThicknessUniform().set(data.borderThickness());
        shaderInstance.borderSoftnessUniform().set(data.borderSoftness());
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        final int borderColor = ColorUtil.combineAlpha(data.borderColor());
        shaderInstance.borderColorUniform().set(colorFormat.normalizedRed(borderColor), colorFormat.normalizedGreen(borderColor), colorFormat.normalizedBlue(borderColor), colorFormat.normalizedAlpha(borderColor));
    }
}
