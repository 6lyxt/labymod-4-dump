// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.client.gui.screen.widget.converter;

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
        registry.exclude(fsw.class, fpt.class, fpg.class, fpo.class, foy.class, fqf.class, fmz.class, fow.class, fpi.class, fon.class, fpd.class, fqa.class, fpv.class);
        registry.exclude(ExclusionStrategy.widget(fnq.class, fiv.class));
        registry.register(new ButtonConverter(), fim.class, fit.class, fiy.class, fjc.class);
        registry.register(new SliderConverter(), fii.class, fif.class, fgr.i.class);
        registry.register(new TextFieldConverter(), fiv.class);
        registry.register(new TabLayoutConverter(), fkq.class);
        registry.register(new StringConverter(), fjt.class);
    }
}
