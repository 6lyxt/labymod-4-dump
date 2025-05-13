// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry;

import java.lang.reflect.Type;
import net.labymod.core.main.user.shop.item.geometry.json.UVDataTypeAdapter;
import net.labymod.core.main.user.shop.item.geometry.model.bedrock.UVData;
import com.google.gson.GsonBuilder;
import java.util.Arrays;
import net.labymod.core.main.user.shop.item.geometry.model.blockbench.BlockBenchCube;
import java.util.List;
import java.util.Map;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.Iterator;
import com.google.gson.JsonElement;
import net.labymod.core.main.user.shop.item.geometry.model.bedrock.BedrockCube;
import net.labymod.core.main.user.shop.item.geometry.model.bedrock.Bone;
import net.labymod.core.main.user.shop.item.geometry.model.bedrock.BedrockModelGeometry;
import net.labymod.core.main.user.shop.item.geometry.model.blockbench.BlockBench;
import java.util.function.Consumer;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.util.io.IOUtil;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.InputStream;
import net.labymod.core.main.user.shop.item.geometry.model.bedrock.BedrockModel;
import com.google.gson.Gson;

public class GeometryLoader
{
    private static final Gson GEOMETRY_GSON;
    private final BedrockModel bedrockModel;
    
    public GeometryLoader(final BedrockModel bedrockModel) {
        this.bedrockModel = bedrockModel;
    }
    
    public GeometryLoader(final String json) {
        this((BedrockModel)GeometryLoader.GEOMETRY_GSON.fromJson(json, (Class)BedrockModel.class));
    }
    
    public GeometryLoader(final InputStream inputStream) throws IOException {
        this((BedrockModel)GeometryLoader.GEOMETRY_GSON.fromJson((Reader)new InputStreamReader(inputStream), (Class)BedrockModel.class));
        inputStream.close();
    }
    
    public GeometryLoader(final File file) throws IOException {
        this(IOUtil.newInputStream(file.toPath()));
    }
    
    public BedrockModel getGeometry() {
        return this.bedrockModel;
    }
    
    public Model toModel() throws Exception {
        return this.toBlockBenchLoader().getModel();
    }
    
    public Model toModel(final Consumer<BlockBenchLoader.BlockBenchMeta> blockBenchMetaConsumer) {
        return this.toBlockBenchLoader(blockBenchMetaConsumer).getModel();
    }
    
    public BlockBenchLoader toBlockBenchLoader() {
        return BlockBenchLoader.create(this.toBlockBench(), blockBenchMeta -> {});
    }
    
    public BlockBenchLoader toBlockBenchLoader(final Consumer<BlockBenchLoader.BlockBenchMeta> metaConsumer) {
        return BlockBenchLoader.create(this.toBlockBench(), metaConsumer);
    }
    
    public BlockBench toBlockBench() {
        final BlockBench blockBench = new BlockBench();
        if (this.bedrockModel.bedrockModelGeometry == null || this.bedrockModel.bedrockModelGeometry.isEmpty()) {
            return blockBench;
        }
        final BedrockModelGeometry geometry = this.bedrockModel.bedrockModelGeometry.get(0);
        blockBench.resolution.width = geometry.description.textureWidth;
        blockBench.resolution.height = geometry.description.textureHeight;
        for (Bone bone : geometry.bones) {
            for (int i = 0; i < (bone.cubes.isEmpty() ? 0 : bone.cubes.size()); ++i) {
                final BedrockCube cube = bone.cubes.get(i);
                blockBench.elements.add(this.toBlockBenchCube(cube, bone.name + "_" + i));
            }
        }
        for (final Bone bone : geometry.bones) {
            if (bone.parent == null) {
                blockBench.outliner.add((JsonElement)this.toBlockBenchChild(geometry, bone));
            }
        }
        return blockBench;
    }
    
    private JsonObject toBlockBenchChild(final BedrockModelGeometry geometry, final Bone bone) {
        final JsonObject child = new JsonObject();
        child.addProperty("name", bone.name);
        if (bone.pivot != null) {
            final JsonArray arrayOrigin = new JsonArray();
            arrayOrigin.add((JsonElement)new JsonPrimitive((Number)(-bone.pivot.get(0))));
            arrayOrigin.add((JsonElement)new JsonPrimitive((Number)bone.pivot.get(1)));
            arrayOrigin.add((JsonElement)new JsonPrimitive((Number)bone.pivot.get(2)));
            child.add("origin", (JsonElement)arrayOrigin);
        }
        if (!bone.rotation.isEmpty()) {
            final JsonArray arrayRotation = new JsonArray();
            arrayRotation.add((JsonElement)new JsonPrimitive((Number)(-bone.rotation.get(0))));
            arrayRotation.add((JsonElement)new JsonPrimitive((Number)(-bone.rotation.get(1))));
            arrayRotation.add((JsonElement)new JsonPrimitive((Number)bone.rotation.get(2)));
            child.add("rotation", (JsonElement)arrayRotation);
        }
        if (!bone.locators.isEmpty()) {
            final JsonObject locatorsObject = new JsonObject();
            for (final Map.Entry<String, List<Float>> entry : bone.locators.entrySet()) {
                final JsonArray array = new JsonArray();
                for (final Float coordinate : entry.getValue()) {
                    array.add((JsonElement)new JsonPrimitive((Number)coordinate));
                }
                locatorsObject.add((String)entry.getKey(), (JsonElement)array);
            }
            child.add("locators", (JsonElement)locatorsObject);
        }
        child.addProperty("uuid", bone.uuid);
        child.add("children", (JsonElement)this.findChildren(geometry, bone));
        return child;
    }
    
    private JsonArray findChildren(final BedrockModelGeometry geometry, final Bone targetBone) {
        final JsonArray array = new JsonArray();
        for (final Bone bone : geometry.bones) {
            if (bone.parent != null && bone.parent.equals(targetBone.name)) {
                array.add((JsonElement)this.toBlockBenchChild(geometry, bone));
            }
        }
        if (!targetBone.cubes.isEmpty()) {
            for (final BedrockCube cube : targetBone.cubes) {
                array.add((JsonElement)new JsonPrimitive(cube.uuid));
            }
        }
        return array;
    }
    
    private BlockBenchCube toBlockBenchCube(final BedrockCube cube, final String name) {
        final BlockBenchCube bbCube = new BlockBenchCube();
        bbCube.name = name;
        bbCube.from = Arrays.asList(-cube.origin.get(0) - cube.size.get(0), cube.origin.get(1), cube.origin.get(2));
        bbCube.to = Arrays.asList(-cube.origin.get(0), cube.origin.get(1) + cube.size.get(1), cube.origin.get(2) + cube.size.get(2));
        if (cube.rotation != null) {
            bbCube.rotation = Arrays.asList(-cube.rotation.get(0), -cube.rotation.get(1), cube.rotation.get(2));
        }
        if (cube.pivot != null) {
            bbCube.origin = Arrays.asList(-cube.pivot.get(0), cube.pivot.get(1), cube.pivot.get(2));
        }
        bbCube.inflate = cube.inflate;
        bbCube.uvOffset = cube.uv;
        bbCube.uuid = cube.uuid;
        bbCube.mirror = cube.mirror;
        return bbCube;
    }
    
    static {
        GEOMETRY_GSON = new GsonBuilder().registerTypeAdapter((Type)UVData.class, (Object)new UVDataTypeAdapter()).create();
    }
}
