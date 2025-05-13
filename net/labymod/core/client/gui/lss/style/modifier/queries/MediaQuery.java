// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.queries;

import net.labymod.api.client.gui.screen.widget.attributes.rules.media.ScreenIdentifier;
import net.labymod.api.client.gui.screen.widget.attributes.rules.media.DocumentIdentifier;
import java.util.HashSet;
import java.util.Iterator;
import net.labymod.api.client.gui.lss.style.reader.StyleRule;
import java.util.regex.Matcher;
import net.labymod.api.util.collection.Lists;
import net.labymod.api.client.gui.screen.widget.attributes.rules.media.MediaRule;
import java.util.List;
import java.util.regex.Pattern;
import net.labymod.api.client.gui.screen.widget.attributes.rules.media.MediaIdentifier;
import java.util.Set;
import net.labymod.api.client.gui.lss.style.modifier.Query;

public class MediaQuery implements Query
{
    private static final Set<MediaIdentifier> IDENTIFIERS;
    private static final Pattern PATTERN;
    
    @Override
    public String identifier() {
        return "media";
    }
    
    @Override
    public boolean matches(final String key) {
        return key.equals(this.identifier());
    }
    
    private List<MediaRule.Requirement> getRequirements(final String arguments) throws UnsupportedOperationException {
        final List<MediaRule.Requirement> requirements = (List<MediaRule.Requirement>)Lists.newArrayList();
        final Matcher matcher = MediaQuery.PATTERN.matcher(arguments);
        while (matcher.find()) {
            final String typeString = matcher.group(1);
            final String featureString = matcher.group(2);
            final String value = matcher.group(3);
            final MediaRule.RequirementType type = MediaRule.RequirementType.of((typeString == null || typeString.isEmpty()) ? "" : typeString);
            if (type == null) {
                throw new UnsupportedOperationException("Could not find a valid type for \"" + typeString);
            }
            final MediaRule.Feature feature = MediaRule.Feature.of(featureString);
            if (feature == null) {
                throw new UnsupportedOperationException("Could not find a valid feature for \"" + featureString);
            }
            final MediaRule.Requirement requirement = new MediaRule.Requirement(type, feature, value);
            requirements.add(requirement);
        }
        return requirements;
    }
    
    @Override
    public MediaRule process(final StyleRule rule) {
        final MediaIdentifier identifier = this.getIdentifier(rule.getValue().split(" ")[0]);
        if (identifier == null) {
            throw new UnsupportedOperationException("Could not find a valid identifier for \"" + rule.getValue());
        }
        final List<MediaRule.Requirement> requirements = this.getRequirements(rule.getValue());
        return new MediaRule(rule, requirements, identifier);
    }
    
    private MediaIdentifier getIdentifier(final String identifierString) {
        MediaIdentifier mediaIdentifier = null;
        for (final MediaIdentifier identifier : MediaQuery.IDENTIFIERS) {
            if (identifier.identifier().equalsIgnoreCase(identifierString)) {
                mediaIdentifier = identifier;
                break;
            }
        }
        return mediaIdentifier;
    }
    
    static {
        IDENTIFIERS = new HashSet<MediaIdentifier>();
        PATTERN = Pattern.compile("([a-zA-Z&|_]*) *\\(*([a-zA-Z-]+): *([^)]+)\\)+");
        MediaQuery.IDENTIFIERS.add(new DocumentIdentifier());
        MediaQuery.IDENTIFIERS.add(new ScreenIdentifier());
    }
}
