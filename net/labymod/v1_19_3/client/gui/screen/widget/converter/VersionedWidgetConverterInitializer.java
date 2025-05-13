// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.gui.screen.widget.converter;

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
        registry.exclude(etc.class, eqx.class, eql.class, eqs.class, eqe.class, erj.class, env.class, eqc.class, eqn.class, epq.class, eqj.class, ere.class, eqz.class);
        registry.exclude(ExclusionStrategy.widget(eok.class, elh.class));
        registry.register(new ButtonConverter(), eky.class, elf.class, elk.class, elo.class);
        registry.register(new SliderConverter(), ekv.class, eks.class, eji.i.class);
        registry.register(new TextFieldConverter(), elh.class);
    }
}
