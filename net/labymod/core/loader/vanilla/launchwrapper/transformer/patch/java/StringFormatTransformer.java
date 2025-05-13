// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.java;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ClassVisitor;
import net.labymod.api.volt.asm.util.ASMHelper;
import org.objectweb.asm.ClassReader;
import net.minecraft.launchwrapper.IClassTransformer;

public class StringFormatTransformer implements IClassTransformer
{
    public byte[] transform(final String name, final String transformedName, final byte... classData) {
        if (classData == null) {
            return null;
        }
        if (name.startsWith("net.minecraft.") || !name.contains(".")) {
            return this.replaceFormat(classData);
        }
        return classData;
    }
    
    private byte[] replaceFormat(final byte[] classData) {
        final ClassReader classReader = new ClassReader(classData);
        final ClassWriter classWriter = ASMHelper.newClassWriter(classReader, false);
        final ClassVisitor classVisitor = new ClassVisitor(this, 589824, classWriter) {
            public MethodVisitor visitMethod(final int access, final String name, final String descriptor, final String signature, final String[] exceptions) {
                final MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new StringFormatMethodVisitor(this.api, methodVisitor);
            }
        };
        classReader.accept(classVisitor, 8);
        return classWriter.toByteArray();
    }
    
    private static class StringFormatMethodVisitor extends MethodVisitor
    {
        private static final String STRING_NAME = "java/lang/String";
        private static final String FORMAT_NAME = "format";
        private static final String FORMAT_DESC = "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;";
        
        protected StringFormatMethodVisitor(final int api, final MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }
        
        public void visitMethodInsn(final int opcode, final String owner, final String name, final String descriptor, final boolean isInterface) {
            if (opcode == 184 && owner.equals("java/lang/String") && name.equals("format") && descriptor.equals("(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;")) {
                super.visitMethodInsn(184, "net/labymod/api/util/StringUtil", name, descriptor, false);
            }
            else {
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }
        }
    }
}
