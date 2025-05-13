// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mappings.io;

import net.labymod.api.mappings.ClassMapping;
import java.util.Iterator;
import java.util.Collection;
import net.labymod.api.mappings.MethodMapping;
import net.labymod.api.mappings.FieldMapping;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import net.labymod.api.mappings.MappingFile;
import java.io.InputStream;

public class SrgMappingReader implements MappingReader
{
    @Override
    public MappingFile read(final InputStream stream) {
        final Collection<String> lines = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).lines().filter(l -> !l.isEmpty()).toList();
        final MappingFile mappingFile = new MappingFile();
        if (lines.isEmpty()) {
            return mappingFile;
        }
        for (String line : lines) {
            if (line.startsWith("CL:")) {
                line = line.substring(4);
                final String[] split = line.split(" ", 2);
                mappingFile.getOrCreate(split[0], split[1]);
            }
            else if (line.startsWith("FD:")) {
                line = line.substring(4);
                final String[] split = line.split(" ", 2);
                final String left = split[0];
                final String right = split[1];
                final int leftLastIndex = left.lastIndexOf(47);
                final String leftClassName = left.substring(0, leftLastIndex);
                final String leftFieldName = left.substring(leftLastIndex + 1);
                final int rightLastIndex = right.lastIndexOf(47);
                final String rightClassName = right.substring(0, rightLastIndex);
                final String rightFieldName = right.substring(rightLastIndex + 1);
                final ClassMapping cls = mappingFile.getOrCreate(leftClassName, rightClassName);
                cls.addField(new FieldMapping(leftFieldName, rightFieldName));
            }
            else {
                if (!line.startsWith("MD:")) {
                    continue;
                }
                line = line.substring(4);
                final String[] split = line.split(" ", 4);
                final String left = split[0];
                final String right = split[2];
                final int leftLastIndex = left.lastIndexOf(47);
                final String leftClassName = left.substring(0, leftLastIndex);
                final String leftMethodName = left.substring(leftLastIndex + 1);
                final String leftMethodDescriptor = split[1];
                final int rightLastIndex2 = right.lastIndexOf(47);
                final String rightClassName2 = right.substring(0, rightLastIndex2);
                final String rightMethodName = right.substring(rightLastIndex2 + 1);
                final String rightMethodDescriptor = split[3];
                final ClassMapping cls2 = mappingFile.getOrCreate(leftClassName, rightClassName2);
                cls2.addMethod(new MethodMapping(leftMethodName, leftMethodDescriptor, rightMethodName, rightMethodDescriptor));
            }
        }
        return mappingFile;
    }
}
