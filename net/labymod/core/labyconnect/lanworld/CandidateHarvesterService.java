// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.lanworld;

import net.labymod.api.util.io.web.request.Response;
import org.ice4j.ice.harvest.UPNPHarvester;
import org.ice4j.ice.harvest.TurnCandidateHarvester;
import org.ice4j.security.LongTermCredential;
import org.ice4j.TransportAddress;
import org.ice4j.ice.harvest.StunCandidateHarvester;
import org.ice4j.Transport;
import java.util.Iterator;
import java.net.URI;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.TypeTokenGsonRequest;
import net.labymod.api.util.io.web.request.AbstractRequest;
import java.util.ArrayList;
import org.ice4j.ice.harvest.CandidateHarvester;
import java.util.function.Supplier;
import java.util.List;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class CandidateHarvesterService
{
    private static final Logging LOGGER;
    private static final String HARVESTERS_URL = "https://dl.labymod.net/labyconnect/lan/config.json";
    private final List<Supplier<CandidateHarvester>> harvesterSuppliers;
    
    public CandidateHarvesterService() {
        this.harvesterSuppliers = new ArrayList<Supplier<CandidateHarvester>>();
        Request.ofGsonList(String.class).url("https://dl.labymod.net/labyconnect/lan/config.json", new Object[0]).async().execute(harvesters -> {
            if (harvesters.hasException() || harvesters.isEmpty() || (harvesters.isPresent() && ((List)harvesters.get()).isEmpty())) {
                if (harvesters.hasException()) {
                    CandidateHarvesterService.LOGGER.warn("Failed to load ICE harvester uris, using UPnP as fallback", harvesters.exception());
                }
                else {
                    CandidateHarvesterService.LOGGER.warn("Failed to load ICE harvester uris, using UPnP as fallback", new Object[0]);
                }
                this.addHarvester(URI.create("upnp://labymod"));
            }
            else {
                ((List)harvesters.get()).iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final String harvester = iterator.next();
                    try {
                        this.addHarvester(URI.create(harvester));
                    }
                    catch (final IllegalArgumentException exception) {
                        CandidateHarvesterService.LOGGER.error("Failed to parse ICE harvester uri: {}", harvester, exception);
                    }
                }
            }
        });
    }
    
    public List<CandidateHarvester> buildHarvesters() {
        final List<CandidateHarvester> harvesters = new ArrayList<CandidateHarvester>(this.harvesterSuppliers.size());
        for (final Supplier<CandidateHarvester> supplier : this.harvesterSuppliers) {
            harvesters.add(supplier.get());
        }
        return harvesters;
    }
    
    public void addHarvester(final Supplier<CandidateHarvester> supplier) {
        this.harvesterSuppliers.add(supplier);
    }
    
    public void addHarvester(final URI uri) {
        final String scheme = uri.getScheme();
        switch (scheme) {
            case "stun": {
                Transport transport;
                try {
                    transport = Transport.parse(uri.getPath().replace("/", ""));
                }
                catch (final IllegalArgumentException exception) {
                    CandidateHarvesterService.LOGGER.error("Unknown transport for STUN ice harvester for uri {}", uri, exception);
                    break;
                }
                this.addHarvester(() -> {
                    new StunCandidateHarvester(new TransportAddress(uri.getHost(), uri.getPort(), transport));
                    return;
                });
                break;
            }
            case "turn": {
                Transport transport;
                try {
                    transport = Transport.parse(uri.getPath().replace("/", ""));
                }
                catch (final IllegalArgumentException exception) {
                    CandidateHarvesterService.LOGGER.error("Unknown transport for TURN ice harvester for uri {}", uri, exception);
                    break;
                }
                final String userInfo = uri.getUserInfo();
                LongTermCredential credential = null;
                if (userInfo != null) {
                    final String[] components = userInfo.split(":", 2);
                    if (components.length == 2) {
                        credential = new LongTermCredential(components[0], components[1]);
                    }
                }
                final LongTermCredential finalCredential = credential;
                this.addHarvester(() -> {
                    new TurnCandidateHarvester(new TransportAddress(uri.getHost(), uri.getPort(), transport), finalCredential);
                    return;
                });
                break;
            }
            case "upnp": {
                this.addHarvester((Supplier<CandidateHarvester>)UPNPHarvester::new);
                break;
            }
            default: {
                CandidateHarvesterService.LOGGER.error("Unknown ice harvester {} for uri {}", uri.getScheme(), uri);
                break;
            }
        }
    }
    
    static {
        LOGGER = Logging.create(CandidateHarvesterService.class);
    }
}
