// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.gui.screen.key.mapper;

import net.labymod.core.client.gui.screen.key.mapper.DefaultKey;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;

@Singleton
@Implements(KeyMapper.class)
public class VersionedKeyMapper extends DefaultKeyMapper
{
    @Override
    protected DefaultKey createKey(final Key key, final int keyCode, final int legacyKeyCode) {
        return new VersionedKey(key, keyCode, legacyKeyCode);
    }
}
