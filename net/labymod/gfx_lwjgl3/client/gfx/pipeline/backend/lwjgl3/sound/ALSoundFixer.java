// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.sound;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.AL;
import java.nio.IntBuffer;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.LWJGLException;

public final class ALSoundFixer
{
    private static long contextPointer;
    private static long devicePointer;
    private static boolean created;
    
    private ALSoundFixer() {
    }
    
    public static void create() throws LWJGLException {
        create(null, 44100, 60, false);
    }
    
    public static void create(final String arguments, final int frequency, final int refresh, final boolean synchronize) throws LWJGLException {
        create(arguments, frequency, refresh, synchronize, true);
    }
    
    public static void create(final String arguments, final int frequency, final int refresh, final boolean synchronize, final boolean openDevice) throws LWJGLException {
        if (ALSoundFixer.created) {
            throw new IllegalStateException("One OpenAL context may be instantiated at any one time.");
        }
        createContext(arguments, frequency, refresh, synchronize, openDevice);
        ALSoundFixer.created = true;
    }
    
    private static void createContext(final String arguments, final int frequency, final int refresh, final boolean synchronize, final boolean openDevice) throws LWJGLException {
        if (!openDevice) {
            return;
        }
        ALSoundFixer.devicePointer = ALC10.alcOpenDevice((CharSequence)arguments);
        if (ALSoundFixer.devicePointer == 0L) {
            destroy();
            throw new LWJGLException("Could not open ALC device");
        }
        final ALCCapabilities capabilities = ALC.createCapabilities(ALSoundFixer.devicePointer);
        if (frequency == -1L) {
            ALSoundFixer.contextPointer = ALC10.alcCreateContext(ALSoundFixer.devicePointer, (IntBuffer)null);
        }
        else {
            ALSoundFixer.contextPointer = ALC10.alcCreateContext(ALSoundFixer.devicePointer, createAndFillAttributeList(frequency, refresh, (int)(synchronize ? 1 : 0)));
        }
        ALC10.alcMakeContextCurrent(ALSoundFixer.contextPointer);
        AL.createCapabilities(capabilities);
    }
    
    public static void destroy() {
        if (ALSoundFixer.contextPointer != 0L) {
            ALC10.alcMakeContextCurrent(0L);
            ALC10.alcDestroyContext(ALSoundFixer.contextPointer);
            ALSoundFixer.contextPointer = 0L;
        }
        if (ALSoundFixer.devicePointer != 0L) {
            ALC10.alcCloseDevice(ALSoundFixer.devicePointer);
            ALSoundFixer.devicePointer = 0L;
        }
        ALSoundFixer.created = false;
    }
    
    private static IntBuffer createAndFillAttributeList(final int frequency, final int refresh, final int synchronize) {
        try (final MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer buffer = stack.mallocInt(7);
            buffer.put(0, 4103);
            buffer.put(1, frequency);
            buffer.put(2, 4104);
            buffer.put(3, refresh);
            buffer.put(4, 4105);
            buffer.put(5, synchronize);
            buffer.put(6, 0);
            return buffer;
        }
    }
}
