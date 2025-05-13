// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.render.model;

import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.Transformation;

public class ModelPartAnimationTransformation extends Transformation
{
    public ModelPartAnimationTransformation(final geo modelPart, final Transformation modelPartTransform) {
        super(new ModelPartPositionVector(modelPart, modelPartTransform.getTranslation()), new ModelPartRotationVector(modelPart), new ModelPartScaleVector(modelPart));
    }
    
    private static class ModelPartPositionVector extends FloatVector3
    {
        private final geo modelPart;
        private final FloatVector3 modelPartTranslation;
        
        public ModelPartPositionVector(final geo modelPart, final FloatVector3 modelPartTranslation) {
            this.modelPart = modelPart;
            this.modelPartTranslation = modelPartTranslation;
        }
        
        @Override
        public float getX() {
            return this.modelPart.b - this.modelPartTranslation.getX();
        }
        
        @Override
        public void setX(final float x) {
            this.modelPart.b = this.modelPartTranslation.getX() + x;
        }
        
        @Override
        public float getY() {
            return this.modelPart.c - this.modelPartTranslation.getY();
        }
        
        @Override
        public void setY(final float y) {
            this.modelPart.c = this.modelPartTranslation.getY() + y;
        }
        
        @Override
        public float getZ() {
            return this.modelPart.d - this.modelPartTranslation.getZ();
        }
        
        @Override
        public void setZ(final float z) {
            this.modelPart.d = this.modelPartTranslation.getZ() + z;
        }
    }
    
    private static class ModelPartRotationVector extends FloatVector3
    {
        private final geo modelPart;
        
        public ModelPartRotationVector(final geo modelPart) {
            this.modelPart = modelPart;
        }
        
        @Override
        public void set(final float x, final float y, final float z) {
            this.modelPart.e = x;
            this.modelPart.f = y;
            this.modelPart.g = z;
        }
        
        @Override
        public void set(final FloatVector3 vector) {
            this.modelPart.e = vector.getX();
            this.modelPart.f = vector.getY();
            this.modelPart.g = vector.getZ();
        }
        
        @Override
        public float getX() {
            return this.modelPart.e;
        }
        
        @Override
        public void setX(final float x) {
            this.modelPart.e = x;
        }
        
        @Override
        public float getY() {
            return this.modelPart.f;
        }
        
        @Override
        public void setY(final float y) {
            this.modelPart.f = y;
        }
        
        @Override
        public float getZ() {
            return this.modelPart.g;
        }
        
        @Override
        public void setZ(final float z) {
            this.modelPart.g = z;
        }
    }
    
    private static class ModelPartScaleVector extends FloatVector3
    {
        private final geo modelPart;
        
        public ModelPartScaleVector(final geo modelPart) {
            this.modelPart = modelPart;
        }
        
        @Override
        public void set(final float x, final float y, final float z) {
            this.modelPart.h = x;
            this.modelPart.i = y;
            this.modelPart.j = z;
        }
        
        @Override
        public float getX() {
            return this.modelPart.h;
        }
        
        @Override
        public void setX(final float x) {
            this.modelPart.h = x;
        }
        
        @Override
        public float getY() {
            return this.modelPart.i;
        }
        
        @Override
        public void setY(final float y) {
            this.modelPart.i = y;
        }
        
        @Override
        public float getZ() {
            return this.modelPart.j;
        }
        
        @Override
        public void setZ(final float z) {
            this.modelPart.j = z;
        }
    }
}
