// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.annotation.processing.util;

import com.google.gson.GsonBuilder;
import java.io.Writer;
import java.io.IOException;
import net.labymod.api.annotation.processing.exception.ProcessingException;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.annotation.processing.model.CustomServiceModel;
import java.util.ArrayList;
import java.util.Collection;
import javax.tools.FileObject;
import com.google.gson.Gson;

public class ServiceFileUtil
{
    private static final Gson GSON;
    
    public static void writeService(final FileObject fileObject, final double classVersion, final Collection<String> fileNames) {
        final List<CustomServiceModel> models = new ArrayList<CustomServiceModel>(fileNames.size());
        for (final String fileName : fileNames) {
            final CustomServiceModel model = new CustomServiceModel(fileName, classVersion);
            models.add(model);
        }
        writeService(fileObject, models);
    }
    
    public static void writeService(final FileObject fileObject, final Collection<CustomServiceModel> models) {
        if (models.isEmpty()) {
            return;
        }
        try (final Writer writer = fileObject.openWriter()) {
            writer.write(ServiceFileUtil.GSON.toJson((Object)models));
        }
        catch (final IOException exception) {
            throw new ProcessingException(exception);
        }
    }
    
    public static void writeService(final FileObject fileObject, final WriterConsumer consumer) {
        try (final Writer writer = fileObject.openWriter()) {
            consumer.accept(writer);
        }
        catch (final IOException exception) {
            throw new ProcessingException(exception);
        }
    }
    
    static {
        GSON = new GsonBuilder().create();
    }
    
    @FunctionalInterface
    public interface WriterConsumer
    {
        void accept(final Writer p0) throws IOException;
    }
}
