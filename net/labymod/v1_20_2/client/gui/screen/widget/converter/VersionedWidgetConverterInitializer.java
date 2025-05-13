// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.gui.screen.widget.converter;

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
        registry.exclude(fcs.class, fah.class, ezu.class, fac.class, ezn.class, fat.class, exb.class, ezl.class, ezw.class, eyz.class, ezs.class, fao.class, faj.class);
        registry.exclude(ExclusionStrategy.widget(exs.class, esz.class));
        registry.register(new ButtonConverter(), esq.class, esx.class, etc.class, etg.class);
        registry.register(new SliderConverter(), esm.class, esj.class, eqy.i.class);
        registry.register(new TextFieldConverter(), esz.class);
        registry.register(new TabLayoutConverter(), eus.class);
        registry.register(new StringConverter(), etw.class);
    }
}
