// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy;
import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterRegistry;
import net.labymod.api.service.annotation.AutoService;
import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer;

@AutoService(value = WidgetConverterInitializer.class, versionSpecific = true)
public class VersionedWidgetConverterInitializer implements WidgetConverterInitializer
{
    @Override
    public void initialize(final WidgetConverterRegistry registry) {
        registry.exclude(ehm.class, efq.class, eff.class, efm.class, eey.class, egc.class, ecs.class, eew.class, efh.class, eel.class, efd.class, efx.class, efs.class);
        registry.exclude(ExclusionStrategy.widget(edg.class, eam.class));
        registry.register(new ButtonConverter(), eae.class, eak.class, ean.class, eap.class);
        registry.register(new SliderConverter(), eav.class, eab.class, eaz.class);
        registry.register(new TextFieldConverter(), eam.class);
    }
}
