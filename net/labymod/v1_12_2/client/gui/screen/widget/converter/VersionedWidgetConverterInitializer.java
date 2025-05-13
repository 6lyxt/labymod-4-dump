// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.gui.screen.widget.converter;

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
        registry.exclude(bkn.class, blb.class, bmx.class, bmp.class, bmh.class, bmn.class, bml.class, bmz.class);
        registry.exclude(ExclusionStrategy.widget(blb.class, bje.class));
        registry.register(new ButtonConverter(), bja.class, bjn.class, bji.class, bjl.class, blm.a.class);
        registry.register(new SliderConverter(), bjf.class, bjs.class, blo.class.getDeclaredClasses()[0]);
        registry.register(new TextFieldConverter(), bje.class);
    }
}
