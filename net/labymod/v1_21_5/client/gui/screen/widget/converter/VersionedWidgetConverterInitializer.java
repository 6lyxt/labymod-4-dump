// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.gui.screen.widget.converter;

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
        registry.exclude(gek.class, gbg.class, gat.class, gbb.class, gal.class, gbs.class, fym.class, gaj.class, gav.class, gaa.class, gaq.class, gbn.class, gbi.class);
        registry.exclude(ExclusionStrategy.widget(fzd.class, fuh.class));
        registry.register(new ButtonConverter(), fty.class, fuf.class, fuk.class, fuo.class);
        registry.register(new SliderConverter(), ftt.class, ftq.class, fqt.i.class);
        registry.register(new TextFieldConverter(), fuh.class);
        registry.register(new TabLayoutConverter(), fwd.class);
        registry.register(new StringConverter(), fvf.class);
    }
}
