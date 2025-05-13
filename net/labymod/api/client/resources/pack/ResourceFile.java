// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.pack;

record ResourceFile(String baseDirectory, String fileName) {
    public static ResourceFile atlases(final String fileName) {
        return of("atlases", fileName);
    }
    
    public static ResourceFile blockStates(final String fileName) {
        return of("blockstates", fileName);
    }
    
    public static ResourceFile font(final String fileName) {
        return of("font", fileName);
    }
    
    public static ResourceFile models(final String fileName) {
        return of("models", fileName);
    }
    
    public static ResourceFile particles(final String fileName) {
        return of("particles", fileName);
    }
    
    public static ResourceFile shaders(final String fileName) {
        return of("shaders", fileName);
    }
    
    public static ResourceFile texts(final String fileName) {
        return of("texts", fileName);
    }
    
    public static ResourceFile textures(final String fileName) {
        return of("textures", fileName);
    }
    
    public static ResourceFile of(final String baseDirectory, final String fileName) {
        return new ResourceFile(baseDirectory, fileName);
    }
}
