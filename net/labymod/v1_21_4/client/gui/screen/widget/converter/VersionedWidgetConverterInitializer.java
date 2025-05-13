// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.gui.screen.widget.converter;

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
        registry.exclude(fze.class, fwc.class, fvp.class, fvx.class, fvh.class, fwo.class, fti.class, fvf.class, fvr.class, fuw.class, fvm.class, fwj.class, fwe.class);
        registry.exclude(ExclusionStrategy.widget(ftz.class, fpd.class));
        registry.register(new ButtonConverter(), fou.class, fpb.class, fpg.class, fpk.class);
        registry.register(new SliderConverter(), foo.class, fol.class, fln.i.class);
        registry.register(new TextFieldConverter(), fpd.class);
        registry.register(new TabLayoutConverter(), fqz.class);
        registry.register(new StringConverter(), fqb.class);
    }
}
