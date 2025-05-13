// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.screenshot;

import java.util.concurrent.Future;
import java.util.Iterator;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

public class ScreenshotSection
{
    private final long timestamp;
    private final int year;
    private final List<Screenshot> screenshots;
    private boolean dirty;
    private boolean beginningOfYear;
    
    public ScreenshotSection(final long timestamp) {
        this.screenshots = new ArrayList<Screenshot>();
        this.beginningOfYear = false;
        this.timestamp = timestamp;
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        this.year = calendar.get(1);
    }
    
    public void push(final Screenshot screenshot) {
        this.screenshots.add(screenshot);
        this.dirty = true;
    }
    
    public void remove(final Screenshot screenshot) {
        this.screenshots.remove(screenshot);
        this.dirty = true;
    }
    
    @Override
    public int hashCode() {
        return Long.hashCode(this.timestamp);
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof ScreenshotSection && ((ScreenshotSection)obj).timestamp == this.timestamp;
    }
    
    public Screenshot getScreenshot(final Path path) {
        for (final Screenshot screenshot : this.screenshots) {
            if (screenshot.getPath().equals(path)) {
                return screenshot;
            }
        }
        return null;
    }
    
    public List<Screenshot> getScreenshots() {
        return this.screenshots;
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    public int getYear() {
        return this.year;
    }
    
    public boolean isDirty() {
        return this.dirty;
    }
    
    public boolean isBeginningOfYear() {
        return this.beginningOfYear;
    }
    
    public void setDirty(final boolean dirty) {
        this.dirty = dirty;
    }
    
    public void setBeginningOfYear(final boolean beginningOfYear) {
        this.beginningOfYear = beginningOfYear;
    }
    
    public void setTask(final Future<?> task) {
    }
    
    public boolean isEmpty() {
        return this.screenshots.isEmpty();
    }
}
