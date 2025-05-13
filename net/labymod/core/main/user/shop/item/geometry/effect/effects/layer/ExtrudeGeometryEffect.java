// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.effect.effects.layer;

import net.labymod.api.client.render.model.box.ModelBoxQuad;
import net.labymod.api.util.math.Direction;
import net.labymod.api.client.render.model.box.ModelBox;
import java.util.Objects;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.core.main.user.shop.item.AbstractItem;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.core.main.user.shop.item.geometry.DepthMap;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;

public class ExtrudeGeometryEffect extends GeometryEffect
{
    private static final float DELTA = 0.5f;
    private static final float DEPTH = 0.01f;
    private int extraBoxes;
    private Rectangle extrudedRectangle;
    private DepthMap depthMap;
    
    public ExtrudeGeometryEffect(final String effectArgument, final ModelPart modelPart) {
        super(effectArgument, modelPart, Type.BUFFER_CREATION, 0);
    }
    
    @Override
    protected boolean processParameters() {
        return true;
    }
    
    @Override
    public void apply(final AbstractItem item, final Player player, final PlayerModel playerModel, final ItemMetadata itemMetadata, final ItemEffect.EffectData effectData) {
        if (this.modelPart.getBoxes().isEmpty()) {
            return;
        }
        if (this.extrudedRectangle == null) {
            this.transformCube();
        }
        if (!Objects.equals(this.depthMap, itemMetadata.getDepthMap())) {
            this.depthMap = itemMetadata.getDepthMap();
            this.applyDepthMap();
        }
    }
    
    private void transformCube() {
        final ModelBox modelBox = this.modelPart.getBoxes().remove(0);
        final float width = modelBox.getWidth();
        final float height = modelBox.getHeight();
        final float depth = modelBox.getDepth();
        this.extrudedRectangle = Rectangle.relative((float)this.modelPart.getTextureOffsetX(), (float)this.modelPart.getTextureOffsetY(), width, height);
        final float minX = modelBox.getMinX();
        final float minY = modelBox.getMinY();
        final float minZ = modelBox.getMinZ();
        final boolean mirror = modelBox.isMirror();
        if (depth == 0.0f) {
            this.hideSouthSide(this.modelPart.createAndAddBox(minX, minY, minZ - 0.5f, modelBox.getWidth(), modelBox.getHeight(), 0.0f, 0.0f, mirror));
            this.hideSouthSide(this.modelPart.createAndAddBox(minX, minY, minZ + 0.5f, modelBox.getWidth(), modelBox.getHeight(), 0.0f, 0.0f, mirror));
            this.extraBoxes += 2;
        }
        if (width == 0.0f) {
            this.hideSouthSide(this.modelPart.createAndAddBox(minX - 0.5f, minY, minZ, 0.0f, modelBox.getHeight(), depth, 0.0f, mirror));
            this.hideSouthSide(this.modelPart.createAndAddBox(minX + 0.5f, minY, minZ, 0.0f, modelBox.getHeight(), depth, 0.0f, mirror));
            this.extraBoxes += 2;
        }
        if (height == 0.0f) {
            this.hideSouthSide(this.modelPart.createAndAddBox(minX, minY - 0.5f, minZ, width, 0.0f, depth, 0.0f, mirror));
            this.hideSouthSide(this.modelPart.createAndAddBox(minX, minY + 0.5f, minZ, width, 0.0f, depth, 0.0f, mirror));
            this.extraBoxes += 2;
        }
        for (int pixelY = 0; pixelY < this.extrudedRectangle.getHeight(); ++pixelY) {
            for (int pixelX = 0; pixelX < this.extrudedRectangle.getWidth(); ++pixelX) {
                this.modelPart.setTextureOffset((int)this.extrudedRectangle.getX() + pixelX, (int)this.extrudedRectangle.getY() + pixelY);
                this.modelPart.addBox(minX + pixelX + 0.5f, minY + pixelY + 0.5f, minZ, 0.01f, 0.01f, 0.01f, 0.5f, mirror);
            }
        }
    }
    
    private void applyDepthMap() {
        final int size = this.modelPart.getBoxes().size();
        int boxIndex = 0;
        for (int index = this.extraBoxes; index < size; ++index) {
            final ModelBox modelBox = this.modelPart.getBoxes().get(index);
            for (int quadIndex = 0; quadIndex < modelBox.getQuads().length; ++quadIndex) {
                final ModelBoxQuad quad = modelBox.getQuads()[quadIndex];
                final Direction direction = quad.getDirection();
                if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                    quad.setVisible(false);
                }
                else {
                    final int x = (int)(boxIndex % this.extrudedRectangle.getWidth() + this.extrudedRectangle.getX());
                    final int y = (int)(boxIndex / this.extrudedRectangle.getWidth() + this.extrudedRectangle.getY());
                    quad.setVisible(this.depthMap == null || this.depthMap.shouldRenderFace(this.extrudedRectangle, x, y, quadIndex));
                }
            }
            ++boxIndex;
        }
    }
    
    private void hideSouthSide(final ModelBox box) {
        final ModelBoxQuad[] quads2;
        final ModelBoxQuad[] quads = quads2 = box.getQuads();
        for (final ModelBoxQuad quad : quads2) {
            if (quad.getDirection() == Direction.SOUTH) {
                quad.setVisible(false);
                break;
            }
        }
    }
}
