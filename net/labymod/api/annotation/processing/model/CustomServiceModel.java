// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.annotation.processing.model;

import java.util.HashMap;
import java.util.Map;

record CustomServiceModel(String serviceClass, double classVersion, Map<String, Object> meta) {
    public CustomServiceModel(final String serviceClass, final double classVersion) {
        this(serviceClass, classVersion, new HashMap<String, Object>());
    }
}
