// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.network.server.storage;

import com.google.gson.GsonBuilder;
import net.labymod.api.util.Color;
import net.labymod.api.client.network.server.ServerAddress;
import java.util.Comparator;
import java.util.Iterator;
import net.labymod.api.client.network.server.storage.MoveActionType;
import net.labymod.api.util.Debounce;
import net.labymod.api.util.GsonUtil;
import java.io.BufferedReader;
import java.util.Collection;
import java.lang.reflect.Type;
import java.io.Reader;
import com.google.gson.stream.JsonReader;
import java.nio.file.Files;
import net.labymod.api.Constants;
import net.labymod.api.util.io.IOUtil;
import net.labymod.core.loader.DefaultLabyModLoader;
import net.labymod.api.util.collection.Lists;
import net.labymod.api.client.network.server.storage.ServerFolder;
import java.io.File;
import net.labymod.api.client.network.server.storage.StorageServerData;
import java.util.List;
import net.labymod.api.util.logging.Logging;
import com.google.gson.Gson;
import net.labymod.api.client.network.server.storage.ServerList;

public abstract class DefaultServerList implements ServerList
{
    private static final Gson GSON;
    private static final Logging LOGGER;
    protected final List<StorageServerData> serverList;
    protected final File gameDirectory;
    protected final List<ServerFolder> folders;
    
    public DefaultServerList() {
        this.serverList = (List<StorageServerData>)Lists.newArrayList();
        this.gameDirectory = IOUtil.toFile(DefaultLabyModLoader.getInstance().getGameDirectory());
        this.folders = (List<ServerFolder>)Lists.newArrayList();
    }
    
    @Override
    public StorageServerData get(final int index) {
        return this.serverList.get(index);
    }
    
    @Override
    public void remove(final StorageServerData data) {
        this.serverList.remove(data);
        this.saveAsync();
    }
    
    @Override
    public void add(final StorageServerData data) {
        this.serverList.add(data);
        this.saveAsync();
    }
    
    @Override
    public void load() {
        if (IOUtil.exists(Constants.Files.SERVER_FOLDERS)) {
            try (final BufferedReader reader = Files.newBufferedReader(Constants.Files.SERVER_FOLDERS);
                 final JsonReader jsonReader = new JsonReader((Reader)reader)) {
                final ServerFolder[] folders = (ServerFolder[])DefaultServerList.GSON.fromJson(jsonReader, (Type)ServerFolder[].class);
                if (folders != null) {
                    this.folders.addAll(Lists.newArrayList(folders));
                }
            }
            catch (final Exception e) {
                DefaultServerList.LOGGER.error("Failed to load server folders", e);
            }
        }
    }
    
    @Override
    public void save() {
        try {
            if (this.folders.isEmpty()) {
                if (IOUtil.exists(Constants.Files.SERVER_FOLDERS)) {
                    IOUtil.delete(Constants.Files.SERVER_FOLDERS);
                }
            }
            else {
                GsonUtil.writeJson(DefaultServerList.GSON, Constants.Files.SERVER_FOLDERS, this.folders);
            }
        }
        catch (final Exception e) {
            DefaultServerList.LOGGER.error("Failed to save server folders", e);
        }
    }
    
    @Override
    public void saveAsync() {
        Debounce.of("server-list-save", 1000L, this::save);
    }
    
    @Override
    public int size() {
        return this.serverList.size();
    }
    
    @Override
    public void swap(final int index1, final int index2, final ServerFolder layer) {
        this.move(index1, index2, (index1 < index2) ? MoveActionType.INSERT_BELOW : MoveActionType.INSERT_ABOVE, layer);
    }
    
    @Override
    public boolean move(final int source, final int destination, final MoveActionType type, final ServerFolder layer) {
        boolean success = false;
        try {
            final ServerFolder sourceFolder = this.getFolder(source);
            final ServerFolder destinationFolder = this.getFolder(destination);
            final boolean isInAFolder = layer != null;
            final boolean sourceIsServer = isInAFolder || sourceFolder == null;
            final boolean destinationIsServer = isInAFolder || destinationFolder == null;
            final int destinationStart = destinationIsServer ? destination : destinationFolder.getStartIndex();
            final int destinationEnd = destinationIsServer ? destination : destinationFolder.getEndIndex();
            if (type == MoveActionType.ADD_TO_FOLDER) {
                if (sourceIsServer && !destinationIsServer) {
                    this.moveServer(source, destinationEnd + 1, true);
                    success = true;
                }
            }
            else if (type == MoveActionType.INSERT_ABOVE) {
                if (sourceIsServer) {
                    final boolean sameIndex = source + 1 == destination;
                    if (!sameIndex) {
                        this.moveServer(source, destinationStart, isInAFolder);
                        success = true;
                    }
                }
                else {
                    this.moveFolder(source, destinationStart);
                    success = true;
                }
            }
            else if (type == MoveActionType.INSERT_BELOW) {
                if (sourceIsServer) {
                    final boolean sameIndex = source == destinationEnd + 1;
                    if (!sameIndex) {
                        this.moveServer(source, destinationEnd + 1, isInAFolder);
                        success = true;
                    }
                }
                else {
                    this.moveFolder(source, destinationEnd + 1);
                    success = true;
                }
            }
            else if (type == MoveActionType.REMOVE_FROM_FOLDER && sourceFolder != null) {
                this.moveServer(source, (source == sourceFolder.getStartIndex()) ? (sourceFolder.getEndIndex() + 1) : sourceFolder.getEndIndex(), false);
                success = true;
            }
            this.saveAsync();
        }
        catch (final Exception e) {
            DefaultServerList.LOGGER.error("Failed to move server", e);
        }
        return success;
    }
    
    private void moveServer(final int source, final int destination, final boolean prioritizeResizeFolder) {
        if (source != destination) {
            final StorageServerData data = this.serverList.remove(source);
            final int shift = (source + 1 < destination) ? -1 : 0;
            this.serverList.add(Math.max(destination + shift, 0), data);
        }
        for (final ServerFolder folder : this.folders) {
            int start = folder.getStartIndex();
            final int end = folder.getEndIndex();
            if (prioritizeResizeFolder) {
                if (source >= start && source <= end) {
                    folder.shrink();
                }
                if (destination >= start && destination <= end + 1) {
                    folder.expand();
                }
            }
            else {
                if (source >= start && source <= end) {
                    folder.shrink();
                }
                if (destination > start && destination < end) {
                    folder.expand();
                }
            }
            start = folder.getStartIndex();
            int offset = 0;
            if (prioritizeResizeFolder) {
                if (source < start) {
                    --offset;
                }
                if (destination < start) {
                    ++offset;
                }
            }
            else {
                if (source < start) {
                    --offset;
                }
                if (destination <= start) {
                    ++offset;
                }
            }
            folder.shift(offset);
        }
        this.folders.removeIf(ServerFolder::isEmpty);
    }
    
    private void moveFolder(final int source, final int destination) {
        final ServerFolder folder = this.getFolder(source);
        if (folder == null) {
            return;
        }
        this.folders.remove(folder);
        final int length = folder.getLength();
        for (int i = 0; i < length; ++i) {
            final int sourceShift = (source < destination) ? 0 : i;
            final int destShift = (source + 1 < destination) ? (-i) : 0;
            this.moveServer(source + sourceShift, destination + i + destShift, false);
        }
        this.folders.add(folder);
        this.folders.sort(Comparator.comparingInt(ServerFolder::getStartIndex));
        folder.shift((source < destination) ? (destination - source - length) : (destination - source));
    }
    
    @Override
    public void replace(final int index, final StorageServerData data) {
        this.serverList.set(index, data);
    }
    
    @Override
    public void update(final StorageServerData serverData) {
        for (int i = 0; i < this.serverList.size(); ++i) {
            final StorageServerData currentData = this.serverList.get(i);
            if (currentData.equals(serverData)) {
                this.replace(i, serverData);
                break;
            }
        }
    }
    
    @Override
    public boolean has(final ServerAddress address) {
        StorageServerData serverData = null;
        for (final StorageServerData storageServerData : this.serverList) {
            if (address.equals(storageServerData.address())) {
                serverData = storageServerData;
                break;
            }
        }
        return serverData != null;
    }
    
    @Override
    public int index(final StorageServerData serverData) {
        return this.serverList.indexOf(serverData);
    }
    
    public ServerFolder getFolder(final int index) {
        for (final ServerFolder folder : this.folders) {
            if (index >= folder.getStartIndex() && index <= folder.getEndIndex()) {
                return folder;
            }
        }
        return null;
    }
    
    public ServerFolder getOrCreateFolder(final int index) {
        ServerFolder folder = this.getFolder(index);
        if (folder == null) {
            this.folders.add(folder = new ServerFolder("", index, 1, Color.WHITE));
            this.folders.sort(Comparator.comparingInt(ServerFolder::getStartIndex));
        }
        return folder;
    }
    
    public void removeFolder(final ServerFolder folder, final boolean removeServers) {
        if (removeServers) {
            final int start = folder.getStartIndex();
            for (int i = 0; i < folder.getLength(); ++i) {
                this.serverList.remove(start);
            }
        }
        this.folders.remove(folder);
    }
    
    public boolean hasFolder(final int index) {
        return this.getFolder(index) != null;
    }
    
    static {
        GSON = new GsonBuilder().create();
        LOGGER = Logging.getLogger();
    }
}
