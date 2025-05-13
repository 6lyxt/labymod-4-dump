// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet.data;

import java.util.Objects;
import java.util.Map;
import net.labymod.core.labymodnet.models.ChangeResponse;
import java.util.function.Consumer;
import net.labymod.core.labymodnet.models.Cosmetic;

public class SingleCosmeticRequestContent extends AbstractRequestContent
{
    private final Cosmetic cosmetic;
    private final String item;
    private final CosmeticRequestType type;
    private final String value;
    
    public SingleCosmeticRequestContent(final Cosmetic cosmetic, final String value, final CosmeticRequestType type, final Consumer<ChangeResponse> changeResponseConsumer) {
        super(type, changeResponseConsumer);
        this.cosmetic = cosmetic;
        this.item = String.valueOf(cosmetic.getId());
        this.type = type;
        this.value = value;
    }
    
    public Cosmetic getCosmetic() {
        return this.cosmetic;
    }
    
    public String getItem() {
        return this.item;
    }
    
    public String getValue() {
        return this.value;
    }
    
    @Override
    public void fill(final Map<String, String> body) {
        body.put("item", this.item);
        body.put("type", this.type.toString());
        body.put("value", this.value);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final SingleCosmeticRequestContent that = (SingleCosmeticRequestContent)o;
        return Objects.equals(this.item, that.item) && this.type == that.type;
    }
    
    @Override
    public int hashCode() {
        int result = (this.item != null) ? this.item.hashCode() : 0;
        result = 31 * result + ((this.type != null) ? this.type.hashCode() : 0);
        return result;
    }
}
