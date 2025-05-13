// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.accesswidener;

import org.objectweb.asm.MethodVisitor;
import net.labymod.accesswidener.access.Access;
import net.labymod.accesswidener.AccessWidenerStorage;
import net.labymod.accesswidener.AccessSpecifier;
import org.objectweb.asm.FieldVisitor;
import net.labymod.accesswidener.AccessWidener;
import org.objectweb.asm.ClassVisitor;

public class AccessWidenerVisitor extends ClassVisitor
{
    protected final AccessWidener accessWidener;
    protected String className;
    private int classAccess;
    
    private AccessWidenerVisitor(final int api, final ClassVisitor classVisitor, final AccessWidener accessWidener) {
        super(api, classVisitor);
        this.accessWidener = accessWidener;
    }
    
    public static AccessWidenerVisitor of(final int api, final ClassVisitor visitor, final AccessWidener widener) {
        return new AccessWidenerVisitor(api, visitor, widener);
    }
    
    public void visit(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces) {
        this.className = name;
        this.classAccess = access;
        super.visit(version, this.accessWidener.getClassAccess(name).apply(access, name, this.classAccess), name, signature, superName, interfaces);
    }
    
    public void visitInnerClass(final String name, final String outerName, final String innerName, final int access) {
        int newAccess = this.accessWidener.getClassAccess(name).apply(access, name, this.classAccess);
        if ((newAccess & 0x1) != 0x0) {
            newAccess |= 0x8;
        }
        super.visitInnerClass(name, outerName, innerName, newAccess);
    }
    
    public FieldVisitor visitField(final int access, final String name, final String descriptor, final String signature, final Object value) {
        final AccessWidenerStorage methodAccess = this.accessWidener.getFieldAccess();
        Access newFieldAccess = methodAccess.getWildcardAccessFromOwner(this.className);
        if (newFieldAccess == null) {
            final AccessSpecifier specifier = AccessSpecifier.of(this.className, name, descriptor);
            newFieldAccess = this.accessWidener.getFieldAccess(specifier);
        }
        return super.visitField(newFieldAccess.apply(access, name, this.classAccess), name, descriptor, signature, value);
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String descriptor, final String signature, final String[] exceptions) {
        final AccessWidenerStorage methodAccess = this.accessWidener.getMethodAccess();
        Access newMethodAccess = methodAccess.getWildcardAccessFromOwner(this.className);
        if (newMethodAccess == null) {
            final AccessSpecifier specifier = AccessSpecifier.of(this.className, name, descriptor);
            newMethodAccess = this.accessWidener.getMethodAccess(specifier);
        }
        return new AccessWidenerMethodVisitor(this.api, super.visitMethod(newMethodAccess.apply(access, name, this.classAccess), name, descriptor, signature, exceptions), this, newMethodAccess);
    }
}
