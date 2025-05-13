// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.buffer.index;

import net.labymod.api.client.gfx.DataType;
import java.nio.ByteBuffer;

record IndexBuffer(int indices, ByteBuffer buffer, DataType type) {}
