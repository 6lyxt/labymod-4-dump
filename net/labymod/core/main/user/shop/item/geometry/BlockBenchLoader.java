// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry;

import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffectRegistry;
import net.labymod.core.main.user.DefaultGameUserService;
import net.labymod.api.util.math.Transformation;
import net.labymod.core.client.render.model.DefaultModelPart;
import net.labymod.api.client.render.model.box.ModelBoxVertex;
import net.labymod.api.util.math.Direction;
import net.labymod.api.client.render.model.box.ModelBoxQuad;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.util.math.vector.FloatVector3;
import com.google.gson.JsonElement;
import net.labymod.core.main.user.shop.item.geometry.model.bedrock.UVData;
import java.util.Locale;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.core.main.user.shop.item.geometry.model.blockbench.BlockBenchItem;
import com.google.gson.JsonArray;
import org.jetbrains.annotations.Nullable;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.InputStream;
import net.labymod.api.util.GsonUtil;
import org.jetbrains.annotations.NotNull;
import java.util.function.Consumer;
import net.labymod.api.client.render.model.ModelService;
import java.util.Iterator;
import net.labymod.api.Laby;
import net.labymod.api.client.render.model.ModelPartHolder;
import net.labymod.core.main.user.shop.item.geometry.model.blockbench.Group;
import net.labymod.core.client.render.model.DefaultModel;
import java.util.ArrayList;
import java.util.HashMap;
import net.labymod.api.client.render.model.Model;
import net.labymod.core.main.user.shop.item.geometry.model.blockbench.BlockBench;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;
import java.util.List;
import net.labymod.core.main.user.shop.item.geometry.model.blockbench.BlockBenchCube;
import java.util.Map;

public class BlockBenchLoader
{
    private static final BlockBenchMeta DEFAULT_BLOCK_BENCH_META;
    private final Map<String, BlockBenchCube> cubes;
    private final List<GeometryEffect> effects;
    private final BlockBench blockBench;
    private Model model;
    
    private BlockBenchLoader(final BlockBench blockBench) {
        this(blockBench, BlockBenchLoader.DEFAULT_BLOCK_BENCH_META);
    }
    
    private BlockBenchLoader(final BlockBench blockBench, final BlockBenchMeta blockBenchMeta) {
        this.cubes = new HashMap<String, BlockBenchCube>();
        this.effects = new ArrayList<GeometryEffect>();
        this.model = new DefaultModel();
        this.blockBench = blockBench;
        for (final BlockBenchCube cube : this.blockBench.elements) {
            this.cubes.put(cube.uuid, cube);
        }
        this.addGroup(this.blockBench.outliner, this.model, new Group());
        final ModelService modelService = Laby.references().modelService();
        final Model finalModel = this.model;
        if (blockBenchMeta.shouldGenerateBoneIds) {
            modelService.generateBoneIds(finalModel, finalModel.getParts().size());
        }
        this.model = finalModel;
    }
    
    @NotNull
    public static BlockBenchLoader create(final BlockBench blockBench, final Consumer<BlockBenchMeta> blockBechMetaConsumer) {
        final BlockBenchMeta blockBenchMeta = new BlockBenchMeta();
        blockBechMetaConsumer.accept(blockBenchMeta);
        return new BlockBenchLoader(blockBench, blockBenchMeta);
    }
    
    @NotNull
    public static BlockBenchLoader create(final String json) {
        return new BlockBenchLoader((BlockBench)GsonUtil.DEFAULT_GSON.fromJson(json, (Class)BlockBench.class));
    }
    
    @Nullable
    public static BlockBenchLoader create(final InputStream inputStream) {
        try (final InputStreamReader reader = new InputStreamReader(inputStream)) {
            return new BlockBenchLoader((BlockBench)GsonUtil.DEFAULT_GSON.fromJson((Reader)reader, (Class)BlockBench.class));
        }
        catch (final IOException exception) {
            return null;
        }
    }
    
    private void addGroup(final JsonArray outliner, final ModelPartHolder parent, final Group group) {
        for (int i = 0; i < outliner.size(); ++i) {
            final JsonElement element = outliner.get(i);
            if (element.isJsonObject()) {
                final Group child = (Group)GsonUtil.DEFAULT_GSON.fromJson(element, (Class)Group.class);
                final JsonArray children = child.children;
                if (children.size() != 0) {
                    final ModelPart model = this.addModel(parent, child, group);
                    this.addGroup(children, model, child);
                }
            }
            else {
                final BlockBenchCube cube = this.cubes.get(element.getAsString());
                final float originX = group.origin.get(0).floatValue();
                final float originY = group.origin.get(1).floatValue();
                final float originZ = group.origin.get(2).floatValue();
                final float fromX = cube.from.get(0).floatValue();
                final float fromY = cube.from.get(1).floatValue();
                final float fromZ = cube.from.get(2).floatValue();
                final float toX = cube.to.get(0).floatValue();
                final float toY = cube.to.get(1).floatValue();
                final float toZ = cube.to.get(2).floatValue();
                final float sizeX = Math.abs(fromX - toX);
                final float sizeY = Math.abs(fromY - toY);
                final float sizeZ = Math.abs(fromZ - toZ);
                float x = originX - toX;
                float y = -fromY - sizeY + originY;
                float z = fromZ - originZ;
                final UVData uvData = cube.uvOffset;
                final Integer textureWidth = this.blockBench.resolution.width;
                final Integer textureHeight = this.blockBench.resolution.height;
                final boolean isCubeRotationEmpty = cube.rotation.isEmpty();
                final ModelPart target = (ModelPart)((isCubeRotationEmpty && parent instanceof ModelPart) ? parent : this.addModel(parent, cube, group));
                if (!isCubeRotationEmpty) {
                    final FloatVector3 translation = target.getModelPartTransform().getTranslation();
                    x -= translation.getX();
                    y -= translation.getY();
                    z -= translation.getZ();
                }
                if (uvData.isPerUVFace()) {
                    final ModelBox newBox = target.createAndAddBox(x, y, z, sizeX, sizeY, sizeZ, cube.inflate, cube.mirror);
                    final float uScale = 1.0f / textureWidth;
                    final float vScale = 1.0f / textureHeight;
                    final ModelBoxQuad[] quads2;
                    final ModelBoxQuad[] quads = quads2 = newBox.getQuads();
                    for (final ModelBoxQuad quad : quads2) {
                        final Direction direction = this.flipDirection(quad.getDirection());
                        final String name = direction.name().toLowerCase(Locale.ROOT);
                        final UVData.Face face = uvData.getFaces().get(name);
                        final ModelBoxVertex[] vertices = quad.getVertices();
                        final float uOffset = face.getUv()[0] * uScale;
                        final float vOffset = face.getUv()[1] * vScale;
                        final float uSize = face.getSizes()[0] * uScale;
                        final int size = face.getSizes()[1];
                        final float vSize = size * vScale;
                        vertices[3] = vertices[3].remap(uOffset, vOffset);
                        vertices[0] = vertices[0].remap(uOffset, vOffset + vSize);
                        vertices[1] = vertices[1].remap(uOffset + uSize, vOffset + vSize);
                        vertices[2] = vertices[2].remap(uOffset + uSize, vOffset);
                    }
                }
                else {
                    final int textureOffsetX = uvData.getUVCoordinate(0);
                    final int textureOffsetZ = uvData.getUVCoordinate(1);
                    target.setTextureOffset(textureOffsetX, textureOffsetZ);
                    target.setTextureSize(textureWidth, textureHeight);
                    target.addBox(x, y, z, sizeX, sizeY, sizeZ, cube.inflate, cube.mirror);
                }
            }
        }
    }
    
    private ModelPart addModel(final ModelPartHolder parentModel, final BlockBenchItem blockBenchItem, final BlockBenchItem group) {
        final ModelPart model = new DefaultModelPart();
        this.rotateGroup(model, blockBenchItem, group);
        parentModel.addChild(blockBenchItem.name, model);
        this.addMapping(model, blockBenchItem);
        return model;
    }
    
    private void rotateGroup(final ModelPart model, final BlockBenchItem child, final BlockBenchItem group) {
        final boolean isChildOriginEmpty = child.origin.isEmpty();
        float originX = isChildOriginEmpty ? 0.0f : child.origin.get(0).floatValue();
        float originY = isChildOriginEmpty ? 0.0f : child.origin.get(1).floatValue();
        float originZ = isChildOriginEmpty ? 0.0f : child.origin.get(2).floatValue();
        if (!group.origin.isEmpty()) {
            originX -= group.origin.get(0).floatValue();
            originY -= group.origin.get(1).floatValue();
            originZ -= group.origin.get(2).floatValue();
        }
        final Transformation transformation = model.getModelPartTransform();
        transformation.setTranslation(-originX, -originY, originZ);
        child.setAbsoluteOrigin(-originX, -originY, originZ);
        if (!child.rotation.isEmpty()) {
            final float rotationX = child.rotation.get(0).floatValue();
            final float rotationY = child.rotation.get(1).floatValue();
            final float rotationZ = child.rotation.get(2).floatValue();
            transformation.setRotation((float)Math.toRadians(-rotationX), (float)Math.toRadians(-rotationY), (float)Math.toRadians(rotationZ));
        }
    }
    
    private void addMapping(final ModelPart model, final BlockBenchItem blockBenchItem) {
        final String name = blockBenchItem.name;
        this.model.addPart(blockBenchItem.name, model);
        try {
            final GeometryEffectRegistry effectService = ((DefaultGameUserService)Laby.references().gameUserService()).itemService().effectService();
            final GeometryEffect geometryEffect = effectService.create(name, model);
            if (geometryEffect != null) {
                this.effects.add(geometryEffect);
            }
        }
        catch (final Exception exception) {
            exception.printStackTrace();
        }
    }
    
    public Model getModel() {
        return this.model;
    }
    
    public List<GeometryEffect> getEffects() {
        return this.effects;
    }
    
    private Direction flipDirection(final Direction direction) {
        return switch (direction) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            default -> direction;
        };
    }
    
    static {
        DEFAULT_BLOCK_BENCH_META = new BlockBenchMeta();
    }
    
    public static class BlockBenchMeta
    {
        private boolean shouldGenerateBoneIds;
        private boolean shouldSplitModel;
        
        public BlockBenchMeta shouldGenerateBoneIds(final boolean shouldGenerateBoneIds) {
            this.shouldGenerateBoneIds = shouldGenerateBoneIds;
            return this;
        }
        
        public BlockBenchMeta shouldSplitModel(final boolean shouldSplitModel) {
            this.shouldSplitModel = shouldSplitModel;
            return this;
        }
    }
}
