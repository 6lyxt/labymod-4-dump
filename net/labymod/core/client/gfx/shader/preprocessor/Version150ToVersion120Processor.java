// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.shader.preprocessor;

import java.util.Iterator;
import net.labymod.api.client.gfx.shader.Shader;
import java.util.HashMap;
import java.util.Map;

public class Version150ToVersion120Processor implements Processor
{
    private final Map<String, String> mapped;
    
    public Version150ToVersion120Processor() {
        this.mapped = new HashMap<String, String>();
    }
    
    @Override
    public String process(String line, final Shader.Type type) {
        if (line.startsWith("#version")) {
            return "#version 120";
        }
        for (final Map.Entry<String, String> entry : this.mapped.entrySet()) {
            line = line.replace(entry.getKey(), entry.getValue());
        }
        if (line.startsWith("flat")) {
            return line.substring("flat ".length());
        }
        if (type == Shader.Type.VERTEX) {
            if (line.startsWith("in") && !line.startsWith("int")) {
                line = this.mapVec2("ivec2", "vec4", line);
                return "attribute" + line.substring("in".length());
            }
            if (line.startsWith("out")) {
                return "varying" + line.substring("out".length());
            }
        }
        else {
            if (line.startsWith("in") && !line.startsWith("int")) {
                return "varying" + line.substring("in".length());
            }
            if (line.startsWith("out")) {
                return null;
            }
        }
        if (line.contains("fragColor")) {
            return line.replace("fragColor", "gl_FragColor");
        }
        return line;
    }
    
    private String mapVec2(final String type, final String mappedType, String line) {
        if (!line.contains(type)) {
            return line;
        }
        final String prevLine = line;
        line = line.replace(type, mappedType);
        if (prevLine.equals(line)) {
            return line;
        }
        if (!line.startsWith("in")) {
            return line;
        }
        if (line.contains(" ")) {
            String name = line.substring(line.lastIndexOf(32) + 1);
            name = name.replace(";", "");
            this.mapped.put(name, type + "((" + name + ".r + " + name + ".g) * 240, (" + name + ".b + " + name + ".a) * 240)");
        }
        return line;
    }
    
    @Override
    public void finished() {
        this.mapped.clear();
    }
}
