// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.render.model;

import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.Transformation;

public class ModelPartAnimationTransformation extends Transformation
{
    public ModelPartAnimationTransformation(final dwn modelPart, final Transformation modelPartTransform) {
        super(new ModelPartPositionVector(modelPart, modelPartTransform.getTranslation()), new ModelPartRotationVector(modelPart), new FloatVector3(1.0f, 1.0f, 1.0f));
    }
    
    private static class ModelPartPositionVector extends FloatVector3
    {
        private final dwn modelPart;
        private final FloatVector3 modelPartTranslation;
        
        public ModelPartPositionVector(final dwn modelPart, final FloatVector3 modelPartTranslation) {
            this.modelPart = modelPart;
            this.modelPartTranslation = modelPartTranslation;
        }
        
        @Override
        public float getX() {
            return this.modelPart.a - this.modelPartTranslation.getX();
        }
        
        @Override
        public void setX(final float x) {
            this.modelPart.a = this.modelPartTranslation.getX() + x;
        }
        
        @Override
        public float getY() {
            return this.modelPart.b - this.modelPartTranslation.getY();
        }
        
        @Override
        public void setY(final float y) {
            this.modelPart.b = this.modelPartTranslation.getY() + y;
        }
        
        @Override
        public float getZ() {
            return this.modelPart.c - this.modelPartTranslation.getZ();
        }
        
        @Override
        public void setZ(final float z) {
            this.modelPart.c = this.modelPartTranslation.getZ() + z;
        }
    }
    
    private static class ModelPartRotationVector extends FloatVector3
    {
        private final dwn modelPart;
        
        public ModelPartRotationVector(final dwn modelPart) {
            this.modelPart = modelPart;
        }
        
        @Override
        public void set(final float x, final float y, final float z) {
            this.modelPart.d = x;
            this.modelPart.e = y;
            this.modelPart.f = z;
        }
        
        @Override
        public void set(final FloatVector3 vector) {
            this.modelPart.d = vector.getX();
            this.modelPart.e = vector.getY();
            this.modelPart.f = vector.getZ();
        }
        
        @Override
        public float getX() {
            return this.modelPart.d;
        }
        
        @Override
        public void setX(final float x) {
            this.modelPart.d = x;
        }
        
        @Override
        public float getY() {
            return this.modelPart.e;
        }
        
        @Override
        public void setY(final float y) {
            this.modelPart.e = y;
        }
        
        @Override
        public float getZ() {
            return this.modelPart.f;
        }
        
        @Override
        public void setZ(final float z) {
            this.modelPart.f = z;
        }
    }
}
