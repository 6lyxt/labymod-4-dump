// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.json;

import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonParseException;
import java.util.Map;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;
import net.labymod.core.main.user.shop.item.geometry.model.bedrock.UVData;
import net.labymod.api.util.gson.LabyGsonTypeAdapter;

public class UVDataTypeAdapter extends LabyGsonTypeAdapter<UVData>
{
    private static final Type INT_ARRAY_TYPE;
    private static final Type FACE_MAP_TYPE;
    
    public UVData deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        final UVData newData = new UVData();
        if (json.isJsonArray()) {
            newData.setUv((int[])context.deserialize(json, UVDataTypeAdapter.INT_ARRAY_TYPE));
        }
        else if (json.isJsonObject()) {
            newData.setFaces((Map<String, UVData.Face>)context.deserialize(json, UVDataTypeAdapter.FACE_MAP_TYPE));
        }
        return newData;
    }
    
    public JsonElement serialize(final UVData src, final Type typeOfSrc, final JsonSerializationContext context) {
        return null;
    }
    
    static {
        INT_ARRAY_TYPE = TypeToken.get((Class)int[].class).getType();
        FACE_MAP_TYPE = TypeToken.getParameterized((Type)Map.class, new Type[] { String.class, UVData.Face.class }).getType();
    }
}
