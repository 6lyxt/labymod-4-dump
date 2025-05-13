// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.asm.primitive;

import java.util.Objects;
import org.objectweb.asm.Type;

public class Primitive
{
    private final String bytecodeName;
    private final Type type;
    private final String methodName;
    private final String methodDescriptor;
    private final int returnOpcode;
    private final int loadOpcode;
    private final int arrayLoadOpcode;
    private final int storeOpcode;
    private final int arrayStoreOpcode;
    
    @Deprecated(forRemoval = true, since = "4.1.3")
    public Primitive(final String bytecodeName, final String type, final String methodName, final String methodDescriptor, final int returnOpcode) {
        this(bytecodeName, Type.getType("L" + type), methodName, methodDescriptor, returnOpcode, 0, 0, 0, 0);
    }
    
    public Primitive(final String bytecodeName, final Type type, final String methodName, final String methodDescriptor, final int returnOpcode, final int loadOpcode, final int arrayLoadOpcode, final int storeOpcode, final int arrayStoreOpcode) {
        this.bytecodeName = bytecodeName;
        this.type = type;
        this.methodName = methodName;
        this.methodDescriptor = methodDescriptor;
        this.returnOpcode = returnOpcode;
        this.loadOpcode = loadOpcode;
        this.arrayLoadOpcode = arrayLoadOpcode;
        this.storeOpcode = storeOpcode;
        this.arrayStoreOpcode = arrayStoreOpcode;
    }
    
    public String getBytecodeName() {
        return this.bytecodeName;
    }
    
    @Deprecated(forRemoval = true, since = "4.1.3")
    public String getType() {
        return this.getInternalName();
    }
    
    public String getInternalName() {
        return this.type.getInternalName();
    }
    
    public Type getASMType() {
        return this.type;
    }
    
    public String getMethodName() {
        return this.methodName;
    }
    
    public String getMethodDescriptor() {
        return this.methodDescriptor;
    }
    
    @Deprecated(forRemoval = true, since = "4.1.3")
    public int getReturnType() {
        return this.getReturnOpcode();
    }
    
    public int getReturnOpcode() {
        return this.returnOpcode;
    }
    
    public int getLoadOpcode(final boolean array) {
        return array ? this.arrayLoadOpcode : this.loadOpcode;
    }
    
    public int getStoreOpcode(final boolean array) {
        return array ? this.arrayStoreOpcode : this.storeOpcode;
    }
    
    public boolean isPrimitive(final String name) {
        return this.getInternalName().equals(name) || this.getBytecodeName().equals(name);
    }
    
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        final Primitive primitive = (Primitive)object;
        return this.returnOpcode == primitive.returnOpcode && this.loadOpcode == primitive.loadOpcode && this.arrayLoadOpcode == primitive.arrayLoadOpcode && this.storeOpcode == primitive.storeOpcode && this.arrayStoreOpcode == primitive.arrayStoreOpcode && Objects.equals(this.bytecodeName, primitive.bytecodeName) && Objects.equals(this.type, primitive.type) && Objects.equals(this.methodName, primitive.methodName) && Objects.equals(this.methodDescriptor, primitive.methodDescriptor);
    }
    
    @Override
    public int hashCode() {
        int result = (this.bytecodeName != null) ? this.bytecodeName.hashCode() : 0;
        result = 31 * result + ((this.type != null) ? this.type.hashCode() : 0);
        result = 31 * result + ((this.methodName != null) ? this.methodName.hashCode() : 0);
        result = 31 * result + ((this.methodDescriptor != null) ? this.methodDescriptor.hashCode() : 0);
        result = 31 * result + this.returnOpcode;
        result = 31 * result + this.loadOpcode;
        result = 31 * result + this.arrayLoadOpcode;
        result = 31 * result + this.storeOpcode;
        result = 31 * result + this.arrayStoreOpcode;
        return result;
    }
}
