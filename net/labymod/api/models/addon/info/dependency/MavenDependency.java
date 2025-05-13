// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.models.addon.info.dependency;

public class MavenDependency
{
    private static final String SEPARATOR = "/";
    private final String repo;
    private String group;
    private String name;
    private String version;
    private String classifier;
    
    public MavenDependency(final String repo, final String name) {
        this.repo = repo;
        final String[] split = name.split(":");
        if (split.length >= 3 && split.length <= 4) {
            this.group = split[0];
            this.name = split[1];
            this.version = split[2];
            if (split.length == 4) {
                this.classifier = split[3];
            }
        }
    }
    
    public MavenDependency(final String repo, final String group, final String name, final String version, final String classifier) {
        this.repo = repo;
        this.group = group;
        this.name = name;
        this.version = version;
        this.classifier = classifier;
    }
    
    public String buildURL() {
        if (this.repo == null || this.repo.isEmpty() || this.group == null || this.name == null || this.version == null) {
            return null;
        }
        final StringBuilder url = new StringBuilder(this.repo);
        if (!this.repo.endsWith("/")) {
            url.append('/');
        }
        this.appendFilePath(url);
        return url.toString();
    }
    
    private void appendFilePath(final StringBuilder builder) {
        this.appendFileDictionary(builder);
        this.appendFileName(builder);
    }
    
    private void appendFileName(final StringBuilder builder) {
        builder.append(this.name).append('-').append(this.version);
        if (this.classifier != null && !this.classifier.isEmpty()) {
            builder.append('-').append(this.classifier);
        }
        builder.append(".jar");
    }
    
    private void appendFileDictionary(final StringBuilder builder) {
        builder.append(this.group.replace(".", "/")).append("/");
        builder.append(this.name).append("/");
        builder.append(this.version).append("/");
    }
    
    public String buildFilePath() {
        final StringBuilder builder = new StringBuilder();
        this.appendFilePath(builder);
        return builder.toString();
    }
    
    public String buildFileDictionary() {
        final StringBuilder builder = new StringBuilder();
        this.appendFileDictionary(builder);
        return builder.toString();
    }
    
    public String buildFileName() {
        final StringBuilder builder = new StringBuilder();
        this.appendFileName(builder);
        return builder.toString();
    }
    
    public String getRepo() {
        return this.repo;
    }
    
    public String getGroup() {
        return this.group;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getVersion() {
        return this.version;
    }
    
    public String getClassifier() {
        return this.classifier;
    }
}
