// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.lang.annotation.Annotation;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.SettingInfo;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.util.I18n;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.MouseButton;
import java.util.Arrays;
import java.util.List;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import java.nio.file.Path;
import net.labymod.api.client.gui.screen.widget.action.Selectable;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;

@AutoWidget
@SettingWidget
public class FileChooserWidget extends ButtonWidget
{
    private final Selectable<Path> selectable;
    private final String[] extensions;
    
    public FileChooserWidget(final Selectable<Path> selectable) {
        this(selectable, null);
    }
    
    public FileChooserWidget(final Selectable<Path> selectable, final String[] extensions) {
        super(Component.translatable("labymod.ui.fileChooser.select", new Component[0]), null);
        this.selectable = selectable;
        this.extensions = extensions;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (FileChooserWidget.FILE_DROP_COMPATIBILITY.isCompatible(this.labyAPI.labyModLoader().version())) {
            this.setHoverComponent(Component.translatable("labymod.ui.fileChooser.description", new Component[0]));
        }
    }
    
    @Override
    public boolean fileDropped(final MutableMouse mouse, final List<Path> paths) {
        if (!paths.isEmpty()) {
            final Path path = paths.get(0);
            if (this.extensions != null && this.extensions.length != 0) {
                final String fileName = path.getFileName().toString();
                if (Arrays.stream(this.extensions).noneMatch(ext -> fileName.endsWith("." + ext))) {
                    return super.fileDropped(mouse, paths);
                }
            }
            this.selectable.select(path);
            return true;
        }
        return super.fileDropped(mouse, paths);
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (mouseButton == MouseButton.LEFT) {
            this.openDialog();
        }
        return super.mouseClicked(mouse, mouseButton);
    }
    
    private void openDialog() {
        Laby.references().fileDialogs().open(I18n.translate("labymod.ui.fileChooser.title", new Object[0]), null, null, this.extensions, false, paths -> this.selectable.select((paths != null && paths.length != 0) ? paths[0] : null));
    }
    
    @Override
    public boolean isHoverComponentRendered() {
        return this.hasHoverComponent() ? super.isHoverComponentRendered() : this.isHovered();
    }
    
    @SettingFactory
    public static class Factory implements WidgetFactory<FileChooserSetting, FileChooserWidget>
    {
        @Override
        public FileChooserWidget[] create(final Setting setting, final FileChooserSetting annotation, final SettingInfo<?> info, final SettingAccessor accessor) {
            return new FileChooserWidget[] { new FileChooserWidget(value -> Laby.labyAPI().minecraft().executeOnRenderThread(() -> accessor.set(value.toAbsolutePath())), annotation.extensions()) };
        }
        
        @Override
        public Class<?>[] types() {
            return new Class[] { Path.class };
        }
    }
    
    @SettingElement
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FileChooserSetting {
        String[] extensions() default {};
    }
}
