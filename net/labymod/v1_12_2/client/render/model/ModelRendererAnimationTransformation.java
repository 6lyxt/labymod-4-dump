// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.render.model;

import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.Transformation;

public class ModelRendererAnimationTransformation extends Transformation
{
    public ModelRendererAnimationTransformation(final brs modelRenderer, final Transformation modelPartTransform) {
        super(new ModelRendererPositionVector(modelRenderer, modelPartTransform.getTranslation()), new ModelRendererRotationVector(modelRenderer), new FloatVector3(1.0f, 1.0f, 1.0f));
    }
    
    private static class ModelRendererPositionVector extends FloatVector3
    {
        private final brs modelRenderer;
        private final FloatVector3 modelPartTranslation;
        
        public ModelRendererPositionVector(final brs modelRenderer, final FloatVector3 modelPartTranslation) {
            this.modelRenderer = modelRenderer;
            this.modelPartTranslation = modelPartTranslation;
        }
        
        @Override
        public float getX() {
            return this.modelRenderer.c - this.modelPartTranslation.getX();
        }
        
        @Override
        public void setX(final float x) {
            this.modelRenderer.c = this.modelPartTranslation.getX() + x;
        }
        
        @Override
        public float getY() {
            return this.modelRenderer.d - this.modelPartTranslation.getY();
        }
        
        @Override
        public void setY(final float y) {
            this.modelRenderer.d = this.modelPartTranslation.getY() + y;
        }
        
        @Override
        public float getZ() {
            return this.modelRenderer.e - this.modelPartTranslation.getZ();
        }
        
        @Override
        public void setZ(final float z) {
            this.modelRenderer.e = this.modelPartTranslation.getZ() + z;
        }
    }
    
    private static class ModelRendererRotationVector extends FloatVector3
    {
        private final brs modelRenderer;
        
        public ModelRendererRotationVector(final brs modelRenderer) {
            this.modelRenderer = modelRenderer;
        }
        
        @Override
        public float getX() {
            return this.modelRenderer.f;
        }
        
        @Override
        public void setX(final float x) {
            this.modelRenderer.f = x;
        }
        
        @Override
        public float getY() {
            return this.modelRenderer.g;
        }
        
        @Override
        public void setY(final float y) {
            this.modelRenderer.g = y;
        }
        
        @Override
        public float getZ() {
            return this.modelRenderer.h;
        }
        
        @Override
        public void setZ(final float z) {
            this.modelRenderer.h = z;
        }
        
        @Override
        public void set(final float x, final float y, final float z) {
            this.modelRenderer.f = x;
            this.modelRenderer.g = y;
            this.modelRenderer.h = z;
        }
        
        @Override
        public void set(final FloatVector3 vector) {
            this.modelRenderer.f = vector.getX();
            this.modelRenderer.g = vector.getY();
            this.modelRenderer.h = vector.getZ();
        }
    }
}
