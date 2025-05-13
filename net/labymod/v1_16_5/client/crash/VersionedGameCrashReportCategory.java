// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.crash;

import java.util.Objects;
import java.util.function.Supplier;
import net.labymod.api.client.crash.GameCrashReport;

record VersionedGameCrashReportCategory(m category) implements GameCrashReport.Category {
    @Override
    public void setDetail(final String name, final Supplier<String> detail) {
        final m category = this.category;
        Objects.requireNonNull(detail);
        category.a(name, detail::get);
    }
}
