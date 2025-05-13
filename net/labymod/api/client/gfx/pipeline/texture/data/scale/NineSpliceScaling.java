// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.texture.data.scale;

record NineSpliceScaling(int width, int height, Border border) implements SpriteScaling {
    record Border(int left, int top, int right, int bottom) {}
}
