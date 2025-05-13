// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.options;

import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.client.component.TextComponent;
import net.labymod.core.flint.downloader.FlintDownloadTask;
import net.labymod.api.util.io.web.result.Result;
import java.util.Collection;
import net.labymod.core.flint.FlintController;
import net.labymod.api.addon.AddonService;
import net.labymod.core.flint.downloader.DownloadState;
import net.labymod.core.flint.marketplace.FlintModification;
import net.labymod.api.models.addon.info.AddonMeta;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.labymod.core.main.LabyMod;
import java.util.Comparator;
import net.labymod.api.configuration.converter.addon.LegacyAddon;
import java.util.HashMap;
import java.util.Iterator;
import net.labymod.api.client.component.TranslatableComponent;
import java.util.stream.Stream;
import net.labymod.api.client.gui.screen.activity.activities.ConfirmActivity;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.util.ComponentUtil;
import net.labymod.api.client.component.Component;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import net.labymod.api.configuration.converter.LegacyConverter;
import java.util.function.Consumer;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ActivityOpenEvent;
import net.labymod.api.Laby;
import net.labymod.api.configuration.converter.LegacyConfigConverter;
import net.labymod.api.LabyAPI;

public class LegacyConfigConvertListener
{
    private final LabyAPI labyAPI;
    private final LegacyConfigConverter converter;
    private boolean conversionAsked;
    
    public LegacyConfigConvertListener() {
        this.labyAPI = Laby.labyAPI();
        this.converter = Laby.references().legacyConfigConverter();
    }
    
    @Subscribe
    public void onActivityOpen(final ActivityOpenEvent event) {
        if (this.conversionAsked) {
            return;
        }
        this.conversionAsked = true;
        this.labyAPI.minecraft().executeNextTick(() -> {
            final boolean labyModConverted = this.converter.wasConversionAsked("labymod");
            this.convertLabyModAndAddons(this.converter, confirmed -> {
                if (!labyModConverted) {
                    this.labyAPI.minecraft().executeNextTick(() -> this.convertLegacyAddons(this.converter, addonsConfirmed -> this.converter.setConversionAsked("labymod")));
                }
            });
        });
    }
    
    private void convertLabyModAndAddons(final LegacyConfigConverter converter, final Consumer<Boolean> callback) {
        final Stream<Object> filter = converter.getConverters().stream().map((Function<? super LegacyConverter<?>, ?>)LegacyConverter::getNamespace).distinct().filter(namespace -> !converter.wasConversionAsked(namespace));
        Objects.requireNonNull(converter);
        final List<String> namespaces = filter.filter((Predicate<? super Object>)converter::hasStuffToConvert).sorted().collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
        if (namespaces.isEmpty()) {
            callback.accept(null);
            return;
        }
        final Component joined = ComponentUtil.join((List<Component>)namespaces.stream().map(namespace -> Component.text(Laby.labyAPI().addonService().getAddon(namespace).map(addon -> addon.info().getDisplayName()).orElse(namespace.equals("labymod") ? "LabyMod" : namespace))).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        final TranslatableComponent title = Component.translatable("labymod.legacyconverter.convertSettings", joined, Component.translatable("labymod.legacyconverter.convertWarning", NamedTextColor.RED, joined.copy()));
        ConfirmActivity.confirm(title, Component.translatable("labymod.legacyconverter.yes", new Component[0]), Component.translatable("labymod.legacyconverter.no", new Component[0]), confirmed -> {
            if (confirmed != null && confirmed) {
                namespaces.iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final String namespace2 = iterator.next();
                    converter.convert(namespace2);
                }
            }
            namespaces.iterator();
            final Iterator iterator2;
            while (iterator2.hasNext()) {
                final String addonNamespace = iterator2.next();
                converter.setConversionAsked(addonNamespace);
            }
            callback.accept(confirmed);
        });
    }
    
    private void convertLegacyAddons(final LegacyConfigConverter converter, final Consumer<Boolean> callback) {
        final Map<String, LegacyAddon> addons = new HashMap<String, LegacyAddon>();
        final AddonService addonService = this.labyAPI.addonService();
        final Iterator<LegacyAddon> iterator = converter.discoverLegacyAddons().iterator();
        LegacyAddon addon = null;
        while (iterator.hasNext()) {
            addon = iterator.next();
            final String namespace = addon.getNamespace();
            if (namespace != null && !addonService.getAddon(namespace).isPresent()) {
                addons.put(namespace, addon);
            }
        }
        if (addons.isEmpty()) {
            callback.accept(null);
            return;
        }
        final Component addonsComponent = ComponentUtil.join((List<Component>)addons.values().stream().sorted(Comparator.comparing((Function<? super LegacyAddon, ? extends Comparable>)LegacyAddon::getName)).map(addon -> Component.text(addon.getName() + " (" + addon.getVersion().getVersion())).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        ConfirmActivity.confirm(Component.translatable("labymod.legacyconverter.convertAddons", addonsComponent), Component.translatable("labymod.legacyconverter.yes", new Component[0]), Component.translatable("labymod.legacyconverter.no", new Component[0]), confirmed -> {
            callback.accept(confirmed);
            if (confirmed != null && confirmed) {
                final FlintController flintController = LabyMod.references().flintController();
                final AtomicInteger downloadCount = new AtomicInteger();
                final Collection<FlintModification> installed = (Collection<FlintModification>)ConcurrentHashMap.newKeySet();
                final Runnable completed = () -> {
                    addons.entrySet().iterator();
                    final Iterator iterator3;
                    while (iterator3.hasNext()) {
                        final Map.Entry<String, LegacyAddon> addon2 = iterator3.next();
                        converter.useVersion(addon2.getKey(), addon2.getValue().getVersion());
                    }
                    final boolean restart = installed.stream().anyMatch(mod -> mod.hasMeta(AddonMeta.RESTART_REQUIRED));
                    this.convertLabyModAndAddons(converter, c -> {
                        if (restart) {
                            ConfirmActivity.confirm(Component.translatable("labymod.legacyconverter.restartRequired", new Component[0]), restartNow -> {
                                if (restartNow != null && restartNow) {
                                    Laby.labyAPI().minecraft().shutdownGame();
                                }
                            });
                        }
                    });
                    return;
                };
                addons.keySet().iterator();
                final Iterator iterator2;
                while (iterator2.hasNext()) {
                    final String addonNamespace = iterator2.next();
                    flintController.getModification(addonNamespace, result -> {
                        if (!result.isPresent()) {
                            if (downloadCount.incrementAndGet() == addons.size()) {
                                completed.run();
                            }
                        }
                        else {
                            final FlintModification mod2 = (FlintModification)result.get();
                            flintController.downloadModification(mod2, task -> {
                                if (task.state() != DownloadState.FAILED) {
                                    installed.add(mod);
                                }
                                if (downloadCount.incrementAndGet() == addons.size()) {
                                    completed.run();
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}
