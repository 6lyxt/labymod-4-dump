// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.generator;

import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;
import net.labymod.api.volt.asm.util.ASMContext;
import java.util.List;
import net.labymod.api.volt.asm.util.ASMHelper;
import java.util.function.Consumer;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.ClassWriter;

public class ClassBytecodeComposer
{
    private final ClassWriter writer;
    private final int version;
    private final int access;
    private final String name;
    @Nullable
    private final String signature;
    @Nullable
    private final String superName;
    @Nullable
    private final String[] interfaces;
    
    private ClassBytecodeComposer(@NotNull final ClassWriter writer, final int version, final int access, @NotNull final String name, @Nullable final String signature, @Nullable final String superName, @Nullable final String[] interfaces) {
        Objects.requireNonNull(writer, "writer must not be null");
        Objects.requireNonNull(name, "name must not be null");
        this.writer = writer;
        this.version = version;
        this.access = access;
        this.name = name;
        this.signature = signature;
        this.superName = superName;
        this.interfaces = interfaces;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public void compose(final Consumer<ClassWriter> consumer) {
        this.writer.visit(this.version, this.access, this.name, this.signature, this.superName, this.interfaces);
        consumer.accept(this.writer);
        this.writer.visitEnd();
    }
    
    public byte[] getClassData() {
        final byte[] classData = this.writer.toByteArray();
        System.setProperty("net.labymod.debugging.asm", "true");
        ASMHelper.writeClassData("generated/" + this.name, classData);
        System.setProperty("net.labymod.debugging.asm", "false");
        return classData;
    }
    
    public static class Builder
    {
        private ClassWriter writer;
        private int version;
        private int access;
        private String name;
        @Nullable
        private String signature;
        private String superName;
        private final List<String> interfaces;
        
        public Builder() {
            this.writer = ASMHelper.newClassWriter();
            this.version = ASMContext.getClassVersion();
            this.access = 1;
            this.superName = "java/lang/Object";
            this.interfaces = new ArrayList<String>();
        }
        
        public Builder withWriter(final ClassWriter writer) {
            this.writer = writer;
            return this;
        }
        
        public Builder withVersion(final int version) {
            this.version = version;
            return this;
        }
        
        public Builder withAccess(final int access) {
            this.access = access;
            return this;
        }
        
        public Builder withName(final String name) {
            this.name = name;
            return this;
        }
        
        public Builder withSignature(@Nullable final String signature) {
            this.signature = signature;
            return this;
        }
        
        public Builder withSuperName(final String superName) {
            this.superName = superName;
            return this;
        }
        
        public Builder addInterface(final String name) {
            this.interfaces.add(name);
            return this;
        }
        
        public Builder addInterfaces(final String... names) {
            this.interfaces.addAll(Arrays.asList(names));
            return this;
        }
        
        public ClassBytecodeComposer build() {
            Objects.requireNonNull(this.writer, "writer must not be null");
            Objects.requireNonNull(this.name, "name must not be null");
            Objects.requireNonNull(this.superName, "superName must not be null");
            return new ClassBytecodeComposer(this.writer, this.version, this.access, this.name, this.signature, this.superName, (String[])(this.interfaces.isEmpty() ? null : ((String[])this.interfaces.toArray(new String[0]))));
        }
    }
}
