// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.modifier;

public class ProcessedObject
{
    private final PostProcessor postProcessor;
    private final String rawValue;
    private final Object value;
    
    public ProcessedObject(final PostProcessor postProcessor, final String rawValue, final Object value) {
        this.postProcessor = postProcessor;
        this.rawValue = rawValue;
        this.value = value;
    }
    
    public static ProcessedObject makeObject(final PostProcessor postProcessor, final String rawValue, final Object value) {
        if (value instanceof final ProcessedObject processedObject) {
            return makeObject(postProcessor, processedObject.rawValue(), processedObject.value());
        }
        if (value instanceof final ProcessedObject[]values) {
            return makeObject(postProcessor, (values.length != 0) ? values[0].rawValue() : rawValue, (values.length != 0) ? values[0].value() : null);
        }
        return new ProcessedObject(postProcessor, rawValue, value);
    }
    
    public PostProcessor postProcessor() {
        return this.postProcessor;
    }
    
    public String rawValue() {
        return this.rawValue;
    }
    
    public Object value() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
