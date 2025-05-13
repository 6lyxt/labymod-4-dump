// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources;

import java.util.Date;
import java.util.Calendar;
import net.labymod.api.util.io.web.request.Response;
import java.util.Locale;
import net.labymod.api.client.resources.ResourceLocation;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import net.labymod.api.Laby;
import java.util.Iterator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.labymod.api.Constants;
import net.labymod.api.util.io.web.request.Request;
import com.google.gson.JsonObject;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import java.util.HashMap;
import net.labymod.api.util.collection.Lists;
import java.util.Map;
import java.util.List;
import java.util.Random;
import net.labymod.api.util.logging.Logging;

public class SplashLoader
{
    public static final Logging LOGGER;
    public static final SplashLoader INSTANCE;
    private static final Random RANDOM;
    private static final String RESOURCE_LOCATION_PATH = "texts/splashes.txt";
    private final List<String> list;
    private final Map<SplashDate, String> splashDates;
    private String splashToday;
    
    public SplashLoader() {
        this.list = (List<String>)Lists.newArrayList();
        this.splashDates = new HashMap<SplashDate, String>();
        this.splashToday = null;
    }
    
    public void loadSplashDates() {
        this.registerSplashDate(12, 24, "Merry X-mas!");
        this.registerSplashDate(1, 1, "Happy new year!");
        this.registerSplashDate(10, 31, "OOoooOOOoooo! Spooky!");
        try {
            Request.ofGson(JsonObject.class).url(Constants.LegacyUrls.SPLASH_DATES, new Object[0]).execute(response -> {
                try {
                    final JsonObject obj = (JsonObject)response.get();
                    if (obj.has("splashDates")) {
                        final JsonArray array = obj.getAsJsonArray("splashDates");
                        array.iterator();
                        final Iterator iterator;
                        while (iterator.hasNext()) {
                            final JsonElement element = iterator.next();
                            final JsonObject entry = element.getAsJsonObject();
                            final String displayString = entry.get("displayString").getAsString();
                            final int month = entry.get("month").getAsInt();
                            final int day = entry.get("day").getAsInt();
                            final boolean isBirthday = entry.get("birthday").getAsBoolean();
                            this.registerSplashDate(month, day, isBirthday ? ("Happy birthday, " + displayString) : displayString);
                        }
                    }
                }
                catch (final Throwable t2) {
                    SplashLoader.LOGGER.error("Failed to fetch splash dates", t2);
                }
            });
        }
        catch (final Throwable t) {
            SplashLoader.LOGGER.error("Failed to fetch splash dates", t);
        }
    }
    
    public void loadSplashesResource() {
        final ResourceLocation resourceLocation = Laby.labyAPI().renderPipeline().resources().resourceLocationFactory().createMinecraft("texts/splashes.txt");
        this.list.clear();
        try (final BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(resourceLocation.openStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = bufferedreader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    this.list.add(line);
                }
            }
            this.splashToday = null;
        }
        catch (final IOException ex) {}
    }
    
    public void registerSplashDate(final int month, final int day, final String splashText) {
        synchronized (this.splashDates) {
            this.splashDates.put(new SplashDate(month, day), splashText);
        }
    }
    
    public String getSplashToday() {
        final SplashDate dateToday = SplashDate.today();
        synchronized (this.splashDates) {
            final String splashTextToday = this.splashDates.get(dateToday);
            if (splashTextToday != null) {
                return splashTextToday;
            }
        }
        if (this.list.isEmpty()) {
            this.loadSplashesResource();
        }
        return (this.splashToday == null) ? (this.splashToday = this.generateRandomSplash()) : this.splashToday;
    }
    
    public String generateRandomSplash() {
        final boolean isEmpty = this.list.isEmpty();
        final boolean isYou = !isEmpty && SplashLoader.RANDOM.nextInt(this.list.size()) == 42;
        if (isYou) {
            final String username = Laby.labyAPI().minecraft().sessionAccessor().getSession().getUsername();
            if (username != null) {
                return username.toUpperCase(Locale.ROOT) + " IS YOU";
            }
        }
        if (!isEmpty) {
            String splashText;
            do {
                splashText = this.list.get(SplashLoader.RANDOM.nextInt(this.list.size()));
            } while (splashText.hashCode() == 125780783);
            return splashText;
        }
        return "missingno";
    }
    
    static {
        LOGGER = Logging.getLogger();
        INSTANCE = new SplashLoader();
        RANDOM = new Random();
    }
    
    public static class SplashDate
    {
        private final int month;
        private final int day;
        
        public SplashDate(final int month, final int day) {
            this.month = month;
            this.day = day;
        }
        
        public SplashDate(final Calendar calendar) {
            this.month = calendar.get(2) + 1;
            this.day = calendar.get(5);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final SplashDate that = (SplashDate)o;
            return this.month == that.month && this.day == that.day;
        }
        
        @Override
        public int hashCode() {
            int result = this.month;
            result = 31 * result + this.day;
            return result;
        }
        
        public static SplashDate today() {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            return new SplashDate(calendar);
        }
    }
}
