// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.gui.screen.widget.converter;

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
        registry.exclude(awv.class, azc.class, ayu.class, aym.class, aye.class, ayq.class);
        registry.exclude(ExclusionStrategy.widget(axk.class, avw.class));
        registry.register(new ButtonConverter(), avs.class, awe.class, avz.class, awc.class, axx.a.class);
        registry.register(new SliderConverter(), avx.class, awj.class, axz.class.getDeclaredClasses()[0]);
        registry.register(new TextFieldConverter(), avw.class);
    }
}
