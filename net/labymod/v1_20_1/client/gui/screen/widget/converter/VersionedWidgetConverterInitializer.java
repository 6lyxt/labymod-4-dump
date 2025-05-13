// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.gui.screen.widget.converter;

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
        registry.exclude(eyu.class, ewo.class, ewb.class, ewj.class, evu.class, exa.class, eti.class, evs.class, ewd.class, evg.class, evz.class, ewv.class, ewq.class);
        registry.exclude(ExclusionStrategy.widget(etz.class, epr.class));
        registry.register(new ButtonConverter(), epi.class, epp.class, ept.class, epw.class);
        registry.register(new SliderConverter(), epd.class, epa.class, enq.i.class);
        registry.register(new TextFieldConverter(), epr.class);
        registry.register(new TabLayoutConverter(), erb.class);
        registry.register(new StringConverter(), eqk.class);
    }
}
