// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.listener;

import net.labymod.api.event.Subscribe;
import net.labymod.core.localization.InternationalizationReloader;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;

public class TranslationReloadListener
{
    @Subscribe
    public void resourceReload(final ResourceReloadEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }
        InternationalizationReloader.reload();
    }
}
