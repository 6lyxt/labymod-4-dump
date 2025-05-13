// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.ice4j;

import net.labymod.core.util.Ice4jSocketFix;
import java.net.Socket;
import java.io.InputStream;
import org.objectweb.asm.tree.MethodNode;
import java.util.Collection;
import net.labymod.api.volt.asm.tree.InsnListBuilder;
import org.objectweb.asm.tree.ClassNode;
import net.labymod.api.volt.asm.util.ASMHelper;
import org.objectweb.asm.Type;
import net.minecraft.launchwrapper.IClassTransformer;

public class SocketTransformer implements IClassTransformer
{
    private static final String NAME = "org.ice4j.pseudotcp.PseudoTcpSocket";
    private static final Type PSEUDO_TCP_SOCKET;
    private static final Type PSEUDO_TCP_SOCKET_IMPL;
    private static final Type INPUT_STREAM;
    private static final Type GETTER_INPUT_STREAM;
    private static final Type SOCKET;
    private static final Type ICE4J_SOCKET_FIX;
    private static final Type GETTER_FIX_INPUT_STREAM;
    
    public byte[] transform(final String name, final String s1, final byte... classData) {
        if (classData == null) {
            return null;
        }
        if ("org.ice4j.pseudotcp.PseudoTcpSocket".equals(name)) {
            return ASMHelper.transformClassData(classData, this::patch);
        }
        return classData;
    }
    
    private void patch(final ClassNode classNode) {
        classNode.methods.add(ASMHelper.createMethod(1, "getInputStream", SocketTransformer.GETTER_INPUT_STREAM.getDescriptor(), null, null, methodNode -> {
            final InsnListBuilder builder = InsnListBuilder.create();
            builder.addVar(25, 0);
            builder.addVar(25, 0);
            builder.addField(180, SocketTransformer.PSEUDO_TCP_SOCKET.getInternalName(), "socketImpl", SocketTransformer.PSEUDO_TCP_SOCKET_IMPL.getDescriptor());
            builder.addMethod(182, SocketTransformer.PSEUDO_TCP_SOCKET_IMPL.getInternalName(), methodNode.name, SocketTransformer.GETTER_INPUT_STREAM.getDescriptor());
            builder.addMethod(184, SocketTransformer.ICE4J_SOCKET_FIX.getInternalName(), methodNode.name, SocketTransformer.GETTER_FIX_INPUT_STREAM.getDescriptor());
            builder.addInstruction(176);
            methodNode.instructions.add(builder.build());
        }));
    }
    
    static {
        PSEUDO_TCP_SOCKET = Type.getType("Lorg/ice4j/pseudotcp/PseudoTcpSocket;");
        PSEUDO_TCP_SOCKET_IMPL = Type.getType("Lorg/ice4j/pseudotcp/PseudoTcpSocketImpl;");
        INPUT_STREAM = Type.getType((Class)InputStream.class);
        GETTER_INPUT_STREAM = Type.getMethodType(SocketTransformer.INPUT_STREAM, new Type[0]);
        SOCKET = Type.getType((Class)Socket.class);
        ICE4J_SOCKET_FIX = Type.getType((Class)Ice4jSocketFix.class);
        GETTER_FIX_INPUT_STREAM = Type.getMethodType(SocketTransformer.INPUT_STREAM, new Type[] { SocketTransformer.SOCKET, SocketTransformer.INPUT_STREAM });
    }
}
