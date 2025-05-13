// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.gui.screen.widget.converter;

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
        registry.exclude(dsc.class, dql.class, dqa.class, dqh.class, dpt.class, dqx.class, dnq.class, dpr.class, dqc.class, dpi.class, dpy.class, dqs.class, dqn.class);
        registry.exclude(ExclusionStrategy.widget(doe.class, dlq.class));
        registry.register(new ButtonConverter(), dlj.class, dlr.class, dlt.class, dlw.class);
        registry.register(new SliderConverter(), dlg.class, dlz.class, dme.class);
        registry.register(new TextFieldConverter(), dlq.class);
    }
}
