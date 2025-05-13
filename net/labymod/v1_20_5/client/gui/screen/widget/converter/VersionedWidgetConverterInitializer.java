// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.client.gui.screen.widget.converter;

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
        registry.exclude(frn.class, fpd.class, fop.class, foy.class, foh.class, fpp.class, flu.class, fof.class, fos.class, fnt.class, fom.class, fpk.class, fpf.class);
        registry.exclude(ExclusionStrategy.widget(fmm.class, fho.class));
        registry.register(new ButtonConverter(), fhf.class, fhm.class, fhr.class, fhv.class);
        registry.register(new SliderConverter(), fhb.class, fgy.class, ffj.i.class);
        registry.register(new TextFieldConverter(), fho.class);
        registry.register(new TabLayoutConverter(), fjj.class);
        registry.register(new StringConverter(), fim.class);
    }
}
