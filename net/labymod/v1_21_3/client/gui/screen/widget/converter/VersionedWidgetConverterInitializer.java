// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.gui.screen.widget.converter;

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
        registry.exclude(fyq.class, fvo.class, fvb.class, fvj.class, fut.class, fwa.class, fsu.class, fur.class, fvd.class, fui.class, fuy.class, fvv.class, fvq.class);
        registry.exclude(ExclusionStrategy.widget(ftl.class, foo.class));
        registry.register(new ButtonConverter(), fof.class, fom.class, fos.class, fow.class);
        registry.register(new SliderConverter(), fob.class, fny.class, fmj.i.class);
        registry.register(new TextFieldConverter(), foo.class);
        registry.register(new TabLayoutConverter(), fql.class);
        registry.register(new StringConverter(), fpn.class);
    }
}
