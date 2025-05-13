// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.client.gui.screen.widget.converter;

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
        registry.exclude(fro.class, fpe.class, foq.class, foz.class, foi.class, fpq.class, flv.class, fog.class, fot.class, fnu.class, fon.class, fpl.class, fpg.class);
        registry.exclude(ExclusionStrategy.widget(fmn.class, fhp.class));
        registry.register(new ButtonConverter(), fhg.class, fhn.class, fhs.class, fhw.class);
        registry.register(new SliderConverter(), fhc.class, fgz.class, ffk.i.class);
        registry.register(new TextFieldConverter(), fhp.class);
        registry.register(new TabLayoutConverter(), fjk.class);
        registry.register(new StringConverter(), fin.class);
    }
}
