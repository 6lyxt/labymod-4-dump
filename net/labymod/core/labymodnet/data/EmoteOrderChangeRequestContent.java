// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet.data;

import java.util.Map;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import net.labymod.core.labymodnet.models.ChangeResponse;
import java.util.function.Consumer;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import com.google.gson.JsonObject;

public class EmoteOrderChangeRequestContent extends AbstractRequestContent
{
    private final JsonObject orderObject;
    
    public EmoteOrderChangeRequestContent(final Int2IntOpenHashMap newOrders, final Consumer<ChangeResponse> changeResponseConsumer) {
        super(CosmeticRequestType.MULTI, changeResponseConsumer);
        this.orderObject = new JsonObject();
        for (final Int2IntMap.Entry entry : newOrders.int2IntEntrySet()) {
            this.orderObject.addProperty(String.valueOf(entry.getIntKey()), (Number)entry.getIntValue());
        }
    }
    
    @Override
    public void fill(final Map<String, String> body) {
        body.put("type", "emotes");
        body.put("value", this.orderObject.toString());
    }
}
