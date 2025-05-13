// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes.rules.media;

import net.labymod.api.util.TextFormat;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;
import java.util.Iterator;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.lss.style.reader.StyleRule;
import java.util.List;

public class MediaRule
{
    private final List<Requirement> requirements;
    private final StyleRule styleRule;
    private final MediaIdentifier identifier;
    
    public MediaRule(final StyleRule styleRule, final List<Requirement> requirements, final MediaIdentifier identifier) {
        this.styleRule = styleRule;
        this.requirements = requirements;
        this.identifier = identifier;
    }
    
    public boolean matches() {
        final Rectangle rectangle = this.identifier.rectangle();
        boolean match = true;
        for (final Requirement requirement : this.requirements) {
            if (requirement.type == RequirementType.AND) {
                match = (match && requirement.matches(rectangle));
            }
            else {
                match = (match || requirement.matches(rectangle));
            }
        }
        return match;
    }
    
    public List<StyleBlock> getBlocks() {
        return this.styleRule.getBlocks();
    }
    
    public StyleRule getStyleRule() {
        return this.styleRule;
    }
    
    public List<Requirement> getRequirements() {
        return this.requirements;
    }
    
    public MediaIdentifier getIdentifier() {
        return this.identifier;
    }
    
    public void refresh() {
        for (final Requirement requirement : this.requirements) {
            requirement.processedValue = Float.MIN_VALUE;
        }
    }
    
    public enum Feature
    {
        HEIGHT, 
        WIDTH, 
        MIN_HEIGHT, 
        MIN_WIDTH, 
        MAX_HEIGHT, 
        MAX_WIDTH;
        
        private static final Feature[] VALUES;
        private String name;
        
        public static Feature of(final String string) {
            for (final Feature value : Feature.VALUES) {
                if (value.toString().equals(string)) {
                    return value;
                }
            }
            return null;
        }
        
        @Override
        public String toString() {
            if (this.name == null) {
                this.name = TextFormat.SNAKE_CASE.toDashCase(this.name());
            }
            return this.name;
        }
        
        static {
            VALUES = values();
        }
    }
    
    public enum RequirementType
    {
        AND(new String[] { "and", "&&", "" }), 
        OR(new String[] { "or", ",", "||" });
        
        private static final RequirementType[] VALUES;
        private final String[] aliases;
        
        private RequirementType(final String[] aliases) {
            this.aliases = aliases;
        }
        
        public static RequirementType of(final String string) {
            for (final RequirementType value : RequirementType.VALUES) {
                if (value.matches(string)) {
                    return value;
                }
            }
            return null;
        }
        
        public String[] getAliases() {
            return this.aliases;
        }
        
        public boolean matches(final String string) {
            for (final String alias : this.aliases) {
                if (alias.length() == 0) {
                    if (string.length() == 0) {
                        return true;
                    }
                }
                else if (alias.equalsIgnoreCase(string)) {
                    return true;
                }
            }
            return false;
        }
        
        static {
            VALUES = values();
        }
    }
    
    public static class Requirement
    {
        private final RequirementType type;
        private final Feature feature;
        private final String value;
        private float processedValue;
        
        public Requirement(final RequirementType type, final Feature feature, final String value) {
            this.processedValue = Float.MIN_VALUE;
            this.type = type;
            this.feature = feature;
            this.value = value;
        }
        
        public RequirementType type() {
            return this.type;
        }
        
        public Feature feature() {
            return this.feature;
        }
        
        public String value() {
            return this.value;
        }
        
        private float getProcessedValue(final Rectangle rectangle) {
            if (this.processedValue == Float.MIN_VALUE) {
                try {
                    this.processedValue = Float.parseFloat(this.value);
                }
                catch (final NumberFormatException e) {
                    this.processedValue = -1.0f;
                    throw new UnsupportedOperationException(this.value + " could not be processed as a float");
                }
            }
            return this.processedValue;
        }
        
        public boolean matches(final Rectangle rectangle) {
            switch (this.feature.ordinal()) {
                case 0: {
                    return rectangle.getHeight() == this.getProcessedValue(rectangle);
                }
                case 1: {
                    return rectangle.getWidth() == this.getProcessedValue(rectangle);
                }
                case 2: {
                    return rectangle.getHeight() >= this.getProcessedValue(rectangle);
                }
                case 3: {
                    return rectangle.getWidth() >= this.getProcessedValue(rectangle);
                }
                case 4: {
                    return rectangle.getHeight() <= this.getProcessedValue(rectangle);
                }
                case 5: {
                    return rectangle.getWidth() <= this.getProcessedValue(rectangle);
                }
                default: {
                    return false;
                }
            }
        }
    }
}
