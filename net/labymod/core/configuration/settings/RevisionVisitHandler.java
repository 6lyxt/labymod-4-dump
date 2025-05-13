// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.settings;

import net.labymod.api.event.Subscribe;
import net.labymod.api.revision.Revision;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.labymod.core.revision.PopupRevision;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.version.SemanticVersion;
import net.labymod.api.BuildData;
import net.labymod.core.configuration.labymod.main.laby.DefaultOtherConfig;
import net.labymod.api.event.labymod.config.ConfigurationLoadEvent;
import net.labymod.core.main.LabyMod;
import net.labymod.api.revision.RevisionRegistry;

public class RevisionVisitHandler
{
    private static final String KEY = "lastStartedVersion";
    private final RevisionRegistry revisionRegistry;
    
    public RevisionVisitHandler() {
        this.revisionRegistry = LabyMod.references().revisionRegistry();
    }
    
    @Subscribe
    public void load(final ConfigurationLoadEvent event) {
        if (event.clazz() != DefaultOtherConfig.class) {
            return;
        }
        final JsonObject object = event.jsonObject();
        final Version currentVersion = BuildData.version();
        if (object.has("lastStartedVersion")) {
            final JsonElement element = object.get("lastStartedVersion");
            final String lastStartedVersion = element.getAsString();
            if (SemanticVersion.isFormat(lastStartedVersion)) {
                final SemanticVersion version = new SemanticVersion(lastStartedVersion);
                final Revision lastRevision = this.revisionRegistry.getLastRevision("labymod");
                if (lastRevision != null && lastRevision.isRelevant() && version.isLowerThan((Version)lastRevision.version()) && lastRevision instanceof PopupRevision) {
                    final PopupRevision popupRevision = (PopupRevision)lastRevision;
                    final boolean isNewUser = lastStartedVersion.equals("0.0.0");
                    popupRevision.visit(isNewUser);
                }
            }
        }
        else {
            final Revision lastRevision2 = this.revisionRegistry.getLastRevision("labymod");
            if (lastRevision2 != null && lastRevision2.isRelevant() && lastRevision2 instanceof PopupRevision) {
                final PopupRevision popupRevision2 = (PopupRevision)lastRevision2;
                popupRevision2.visit(true);
            }
        }
        object.addProperty("lastStartedVersion", currentVersion.toString());
    }
}
