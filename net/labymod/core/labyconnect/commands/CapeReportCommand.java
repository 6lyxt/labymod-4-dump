// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.commands;

import net.labymod.api.util.io.web.request.AbstractRequest;
import net.labymod.api.util.io.web.request.Response;
import java.util.Locale;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.StringRequest;
import net.labymod.api.util.io.LabyExecutors;
import java.io.IOException;
import net.labymod.api.client.component.Component;
import net.labymod.api.Laby;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.chat.command.Command;

public class CapeReportCommand extends Command
{
    private static final Logging LOGGER;
    private static final String URL_CAPE_REPORT = "https://next.api.labymod.net/cloak/report/v4";
    private static final String OUTPUT = "reporter=%s&owner=%s";
    private long nextReportTime;
    
    public CapeReportCommand() {
        super("capereport", new String[] { "reportcape" });
        this.nextReportTime = 0L;
        this.translationKey("labymod.command.command.capeReport");
    }
    
    @Override
    public boolean execute(final String prefix, final String[] arguments) {
        if (arguments.length == 0) {
            this.displaySyntax();
            return true;
        }
        final long currentTime = TimeUtil.getMillis();
        if (this.nextReportTime > currentTime) {
            this.displayTranslatable("cooldown", NamedTextColor.RED, new Object[] { (int)(this.nextReportTime - currentTime) / 1000 });
            return true;
        }
        final String playerName = Laby.labyAPI().minecraft().getClientPlayer().getName();
        LabyExecutors.executeBackgroundTask(() -> {
            int i = 0;
            for (int length = arguments.length; i < length; ++i) {
                final String argument = arguments[i];
                try {
                    this.nextReportTime = currentTime + 20000L;
                    this.report(playerName, argument);
                }
                catch (final IOException e) {
                    CapeReportCommand.LOGGER.error("Failed to report {}", argument, e);
                    this.displayTranslatable("error", NamedTextColor.RED, Component.text(argument, NamedTextColor.YELLOW), Component.text(e.getClass().getSimpleName() + ": " + e.getMessage(), NamedTextColor.DARK_RED));
                }
            }
            return;
        });
        return true;
    }
    
    private void report(final String playerName, final String reported) throws IOException {
        final Response<String> response = ((AbstractRequest<String, R>)((AbstractRequest<T, StringRequest>)((AbstractRequest<T, StringRequest>)((AbstractRequest<T, StringRequest>)Request.ofString()).url("https://next.api.labymod.net/cloak/report/v4", new Object[0])).method(Request.Method.POST)).write((Object)String.format(Locale.ROOT, "reporter=%s&owner=%s", playerName, reported))).executeSync();
        if (response.hasException()) {
            throw response.exception();
        }
        final String rawResponse = response.get();
        final ReportResponse reportResponse = ReportResponse.fromString(rawResponse.replace("\"", ""));
        if (reportResponse == null) {
            throw new IOException("No valid response (code: " + response.getStatusCode() + ", response: " + rawResponse);
        }
        switch (reportResponse.ordinal()) {
            case 3: {
                this.displayTranslatable("success", NamedTextColor.GREEN, Component.text(reported, NamedTextColor.YELLOW));
                break;
            }
            case 0: {
                this.displayTranslatable("userNotFound", NamedTextColor.RED, Component.text(reported, NamedTextColor.YELLOW));
                break;
            }
            case 2: {
                this.displayTranslatable("cloakNotFound", NamedTextColor.RED, Component.text(reported, NamedTextColor.YELLOW));
                break;
            }
            case 1: {
                this.displayTranslatable("selfReport", NamedTextColor.RED, new Component[0]);
                break;
            }
            case 4: {
                this.displayTranslatable("locked", NamedTextColor.RED, new Component[0]);
                break;
            }
        }
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
    
    private enum ReportResponse
    {
        USER_NOT_FOUND, 
        SELF_REPORT, 
        CLOAK_NOT_FOUND, 
        SUCCESS, 
        LOCKED;
        
        private static final ReportResponse[] VALUES;
        
        public static ReportResponse fromString(final String string) {
            for (final ReportResponse response : ReportResponse.VALUES) {
                if (response.name().toLowerCase(Locale.ROOT).equalsIgnoreCase(string)) {
                    return response;
                }
            }
            return null;
        }
        
        static {
            VALUES = values();
        }
    }
}
