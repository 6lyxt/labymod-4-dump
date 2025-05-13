// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.object.lootbox.remote;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class IncentivesIndex
{
    @SerializedName("incentives")
    private final List<Incentive> incentives;
    
    public IncentivesIndex() {
        this.incentives = new ArrayList<Incentive>();
    }
    
    public List<Incentive> getIncentives() {
        return this.incentives;
    }
}
