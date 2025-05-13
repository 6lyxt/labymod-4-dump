// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.screenshot.command;

import java.nio.file.Path;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.screenshots.ScreenshotBrowserActivity;
import net.labymod.api.client.component.format.NamedTextColor;
import java.nio.file.Paths;
import net.labymod.api.client.chat.command.Command;

public class ScreenshotViewerCommand extends Command
{
    public ScreenshotViewerCommand() {
        super("screenshotViewer", new String[] { "screenshot-viewer" });
        this.translationKey("labymod.command.command.labynetScreenshotUpload");
    }
    
    @Override
    public boolean execute(final String prefix, final String[] arguments) {
        if (arguments.length == 0) {
            this.displaySyntax();
            return true;
        }
        final Path path = Paths.get(arguments[0], new String[0]);
        if (!path.toFile().exists()) {
            this.displayTranslatable("notFound", NamedTextColor.RED, new Object[] { path });
            return true;
        }
        ScreenshotBrowserActivity.openScreenshot(path);
        return true;
    }
}
