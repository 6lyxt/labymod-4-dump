// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget;

import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.scoreboard.ObjectiveRenderType;
import java.util.Collections;
import java.util.function.Function;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.util.Color;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import java.util.ArrayList;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.overlay.IngameOverlayElementRenderEvent;
import net.labymod.api.client.scoreboard.DisplaySlot;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.draw.RectangleRenderer;
import java.util.Iterator;
import net.labymod.api.client.render.RenderPipeline;
import java.util.List;
import net.labymod.api.client.util.VanillaParityUtil;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.Laby;
import java.util.Collection;
import net.labymod.api.client.component.format.numbers.NumberFormat;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.mouse.MutableMouse;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.util.bounds.DefaultRectangle;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import java.util.Comparator;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget;

@SpriteSlot(x = 2, y = 1)
public class ScoreboardHudWidget extends SimpleHudWidget<ScoreboardHudWidgetConfig>
{
    private static final boolean SORTED_SCORES;
    private static final Comparator<ScoreboardScore> SCORE_DISPLAY_ORDER;
    private static final float DEFAULT_FONT_HEIGHT = 9.0f;
    private static final int MAX_SCORES = 15;
    private static final float BODY_PADDING = 2.0f;
    private final Scoreboard dummyScoreboard;
    private final ScoreboardObjective dummyObjective;
    private final MutableRectangle bodyArea;
    
    public ScoreboardHudWidget() {
        super("scoreboard", ScoreboardHudWidgetConfig.class);
        this.dummyScoreboard = new DummyScoreboard();
        this.dummyObjective = new DummyObjective();
        this.bodyArea = new DefaultRectangle();
        this.bindCategory(HudWidgetCategory.INGAME);
        this.bindDropzones(NamedHudWidgetDropzones.SCOREBOARD_LEFT, NamedHudWidgetDropzones.SCOREBOARD_RIGHT);
    }
    
    @Override
    public void initializePreConfigured(final ScoreboardHudWidgetConfig config) {
        super.initializePreConfigured(config);
        config.setEnabled(true);
        config.setDropzoneId(NamedHudWidgetDropzones.SCOREBOARD_RIGHT.getId());
    }
    
    @Override
    public void render(@Nullable final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext, final HudSize size) {
        Scoreboard scoreboard = this.labyAPI.minecraft().getScoreboard();
        ScoreboardObjective objective = this.getObjective(scoreboard);
        if (scoreboard == null || objective == null || isEditorContext) {
            scoreboard = this.dummyScoreboard;
            objective = this.dummyObjective;
        }
        final NumberFormat numberFormat = objective.getNumberFormatOrDefault(NumberFormat.noStyle());
        final List<DisplayEntry> entries = this.getVisibleScores(scoreboard, objective, numberFormat);
        final boolean showScores = this.showScores(entries);
        final RenderPipeline renderPipeline = Laby.labyAPI().renderPipeline();
        final RenderableComponent title = RenderableComponent.of(objective.getTitle());
        int maxWidth;
        final int titleWidth = maxWidth = MathHelper.ceil(title.getWidth());
        final float colonWidth = renderPipeline.textRenderer().width(": ");
        for (final DisplayEntry entry : entries) {
            final boolean scoresVisible = showScores && entry.scoreWidth() > 0.0f;
            maxWidth = MathHelper.ceil(Math.max((float)maxWidth, entry.name().getWidth() + (scoresVisible ? (colonWidth + entry.scoreWidth()) : 0.0f)));
        }
        final RectangleRenderer rectangleRenderer = renderPipeline.rectangleRenderer();
        final ComponentRenderer componentRenderer = renderPipeline.componentRenderer();
        this.bodyArea.setPosition(0.0f, 0.0f);
        this.bodyArea.setSize(maxWidth + 2.0f, entries.size() * 9.0f + 2.0f);
        if (stack != null) {
            stack.push();
            stack.translate(0.0f, 0.0f, VanillaParityUtil.getScoreboardSidebarZLevel());
            this.renderSidebarBody(stack, numberFormat, entries, rectangleRenderer, componentRenderer, showScores);
            this.renderSidebarHeader(stack, rectangleRenderer, title, componentRenderer, titleWidth);
            stack.pop();
        }
        size.set((int)this.bodyArea.getWidth(), (int)(this.bodyArea.getHeight() + title.getHeight()));
    }
    
    @Override
    public boolean isVisibleInGame() {
        final Scoreboard scoreboard = this.labyAPI.minecraft().getScoreboard();
        if (scoreboard == null) {
            return false;
        }
        final ScoreboardObjective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
        if (objective == null) {
            return false;
        }
        final NumberFormat numberFormat = objective.getNumberFormatOrDefault(NumberFormat.noStyle());
        final List<DisplayEntry> entries = this.getVisibleScores(scoreboard, objective, numberFormat);
        return !entries.isEmpty();
    }
    
    @Override
    public boolean renderInDebug() {
        return true;
    }
    
    @Subscribe
    public void onScoreboardRender(final IngameOverlayElementRenderEvent event) {
        if (this.isEnabled() && event.elementType() == IngameOverlayElementRenderEvent.OverlayElementType.SCOREBOARD && this.isVisibleInGame() && event.phase() == Phase.PRE) {
            event.setCancelled(true);
        }
    }
    
    private boolean showScores(final Collection<DisplayEntry> entries) {
        final ScoreboardHudWidgetConfig.ScoreVisibility scoreVisibility = ((ScoreboardHudWidgetConfig)this.config).scoreVisibility.get();
        if (scoreVisibility == ScoreboardHudWidgetConfig.ScoreVisibility.AUTO) {
            if (entries.size() <= 1) {
                return true;
            }
            int lastValue = -1;
            for (final DisplayEntry entry : entries) {
                final int currentValue = entry.scoreValue();
                if (lastValue != -1 && currentValue - lastValue != 1) {
                    return true;
                }
                lastValue = currentValue;
            }
        }
        return scoreVisibility == ScoreboardHudWidgetConfig.ScoreVisibility.VISIBLE;
    }
    
    private ScoreboardObjective getObjective(final Scoreboard scoreboard) {
        if (scoreboard == null) {
            return null;
        }
        return scoreboard.getObjective(DisplaySlot.SIDEBAR);
    }
    
    private List<DisplayEntry> getVisibleScores(final Scoreboard scoreboard, final ScoreboardObjective objective, final NumberFormat numberFormat) {
        final Collection<ScoreboardScore> scores = scoreboard.getScores(objective);
        final List<ScoreboardScore> toSort = new ArrayList<ScoreboardScore>();
        for (final ScoreboardScore s : scores) {
            if (s.isHidden()) {
                continue;
            }
            toSort.add(s);
        }
        if (ScoreboardHudWidget.SORTED_SCORES) {
            toSort.sort(ScoreboardHudWidget.SCORE_DISPLAY_ORDER);
        }
        final List<DisplayEntry> list = new ArrayList<DisplayEntry>();
        long limit = 15L;
        for (final ScoreboardScore score : toSort) {
            if (limit-- == 0L) {
                break;
            }
            final ScoreboardTeam scoreboardTeam = scoreboard.teamByEntry(score.getName());
            final Component ownerName = score.getOwnerName();
            final Component scoreName = (scoreboardTeam == null) ? ownerName.copy() : scoreboardTeam.formatDisplayName(ownerName);
            final RenderableComponent scoreComponent = RenderableComponent.of(score.formatValue(numberFormat));
            final DisplayEntry apply = new DisplayEntry(RenderableComponent.of(scoreName), scoreComponent, score.getValue(), scoreComponent.getWidth());
            list.add(apply);
        }
        return list;
    }
    
    private void renderSidebarHeader(@NotNull final Stack stack, final RectangleRenderer rectangleRenderer, final RenderableComponent title, final ComponentRenderer componentRenderer, final int titleWidth) {
        final float y = this.bodyArea.getY();
        rectangleRenderer.pos(this.bodyArea.getX(), y).size(this.bodyArea.getWidth(), 9.0f).color(((ScoreboardHudWidgetConfig)this.config).headerColor.get().get()).render(stack);
        componentRenderer.builder().text(title).pos(this.bodyArea.getX() + (this.bodyArea.getWidth() - titleWidth) / 2.0f, y + 1.0f).shadow(((ScoreboardHudWidgetConfig)this.config).headerTextShadow.get()).allowColors(true).useFloatingPointPosition(false).render(stack);
    }
    
    private void renderSidebarBody(@NotNull final Stack stack, final NumberFormat numberFormat, final List<DisplayEntry> entries, final RectangleRenderer rectangleRenderer, final ComponentRenderer componentRenderer, final boolean showScores) {
        final boolean sortedScores = ScoreboardHudWidget.SORTED_SCORES;
        float yOffset = 9.0f;
        int lineIndex;
        final int size = lineIndex = entries.size();
        final int lastEntry = sortedScores ? (size - 1) : 0;
        for (int i = 0; i < size; ++i) {
            final DisplayEntry line = entries.get(i);
            final RenderableComponent scoreComponent = line.score();
            final RenderableComponent nameComponent = line.name();
            if (!sortedScores) {
                yOffset = lineIndex * 9.0f;
            }
            rectangleRenderer.pos(this.bodyArea.getX(), yOffset).size(this.bodyArea.getWidth(), 9.0f + ((i == lastEntry) ? 2.0f : 0.0f)).color(((ScoreboardHudWidgetConfig)this.config).bodyColor().get().get()).render(stack);
            componentRenderer.builder().text(nameComponent).pos(this.anchor.isRight() ? (2.0f + this.bodyArea.getX()) : (this.bodyArea.getWidth() - nameComponent.getWidth()), 2.0f + yOffset).shadow(((ScoreboardHudWidgetConfig)this.config).bodyTextShadow.get()).useFloatingPointPosition(false).allowColors(true).render(stack);
            if (showScores) {
                componentRenderer.builder().text(scoreComponent).pos(this.anchor.isRight() ? (this.bodyArea.getWidth() - scoreComponent.getWidth()) : (this.bodyArea.getX() + 2.0f), 2.0f + yOffset).color((numberFormat == NumberFormat.noStyle()) ? ((ScoreboardHudWidgetConfig)this.config).scoreColor.get() : -1).shadow(((ScoreboardHudWidgetConfig)this.config).bodyTextShadow.get()).useFloatingPointPosition(false).render(stack);
            }
            if (sortedScores) {
                yOffset += 9.0f;
            }
            else {
                --lineIndex;
            }
        }
    }
    
    static {
        SORTED_SCORES = MinecraftVersions.V23w46a.orNewer();
        SCORE_DISPLAY_ORDER = Comparator.comparing((Function<? super ScoreboardScore, ? extends Comparable>)ScoreboardScore::getValue).reversed().thenComparing((Function<? super ScoreboardScore, ?>)ScoreboardScore::getName, (Comparator<? super Object>)String.CASE_INSENSITIVE_ORDER);
    }
    
    private static class DummyScore implements ScoreboardScore
    {
        private final int index;
        
        private DummyScore(final int index) {
            this.index = index;
        }
        
        @Override
        public String getName() {
            return "Test" + this.index;
        }
        
        @Override
        public int getValue() {
            return this.index;
        }
        
        @Override
        public Component getOwnerName() {
            return Component.text(this.getName());
        }
        
        @Override
        public Component formatValue(final NumberFormat format) {
            return format.format(this.getValue());
        }
    }
    
    private static class DummyTeam implements ScoreboardTeam
    {
        @NotNull
        @Override
        public String getTeamName() {
            return "Test";
        }
        
        @NotNull
        @Override
        public Collection<String> getEntries() {
            return (Collection<String>)Collections.emptyList();
        }
        
        @Override
        public boolean hasEntry(@NotNull final String name) {
            return false;
        }
        
        @NotNull
        @Override
        public Component getPrefix() {
            return Component.empty();
        }
        
        @NotNull
        @Override
        public Component getSuffix() {
            return Component.empty();
        }
        
        @NotNull
        @Override
        public Component formatDisplayName(@NotNull final Component component) {
            return component;
        }
    }
    
    private static class DummyObjective implements ScoreboardObjective
    {
        private final ScoreboardScore score;
        
        private DummyObjective() {
            this.score = new DummyScore(0);
        }
        
        @NotNull
        @Override
        public String getName() {
            return "test";
        }
        
        @NotNull
        @Override
        public Component getTitle() {
            return Component.text("Title");
        }
        
        @NotNull
        @Override
        public ObjectiveRenderType getRenderType() {
            return ObjectiveRenderType.INTEGER;
        }
        
        @NotNull
        @Override
        public ScoreboardScore getOrCreateScore(@NotNull final String entry) {
            return this.score;
        }
        
        @Nullable
        @Override
        public ScoreboardScore getScore(@NotNull final String entry) {
            return this.score;
        }
        
        @Nullable
        @Override
        public NumberFormat getNumberFormat() {
            return NumberFormat.noStyle();
        }
    }
    
    public static class DummyScoreboard implements Scoreboard
    {
        private final List<ScoreboardTeam> teams;
        private final List<ScoreboardScore> scores;
        private final ScoreboardObjective objective;
        
        public DummyScoreboard() {
            this.teams = new ArrayList<ScoreboardTeam>();
            this.scores = new ArrayList<ScoreboardScore>(2);
            this.objective = new DummyObjective();
            this.scores.add(new DummyScore(0));
            this.scores.add(new DummyScore(1));
            this.teams.add(new DummyTeam());
        }
        
        @NotNull
        @Override
        public Collection<ScoreboardTeam> getTeams() {
            return this.teams;
        }
        
        @Override
        public ScoreboardTeam teamByEntry(@NotNull final String entry) {
            return this.teams.get(0);
        }
        
        @Nullable
        @Override
        public ScoreboardObjective getObjective(@NotNull final DisplaySlot slot) {
            return this.objective;
        }
        
        @Nullable
        @Override
        public ScoreboardObjective getObjective(@NotNull final String objective) {
            return this.objective;
        }
        
        @NotNull
        @Override
        public Collection<ScoreboardScore> getScores(final ScoreboardObjective objective) {
            return this.scores;
        }
        
        @NotNull
        @Override
        public Collection<String> getEntries(final ScoreboardObjective objective) {
            final List<String> objects = new ArrayList<String>();
            for (final ScoreboardScore score : this.scores) {
                objects.add(score.getName());
            }
            return objects;
        }
    }
    
    public static class ScoreboardHudWidgetConfig extends HudWidgetConfig
    {
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> bodyTextShadow;
        @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
        private final ConfigProperty<Color> bodyColor;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> headerTextShadow;
        @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
        private final ConfigProperty<Color> headerColor;
        @ColorPickerWidget.ColorPickerSetting(alpha = true)
        private final ConfigProperty<Integer> scoreColor;
        @DropdownWidget.DropdownSetting
        private final ConfigProperty<ScoreVisibility> scoreVisibility;
        
        public ScoreboardHudWidgetConfig() {
            this.bodyTextShadow = new ConfigProperty<Boolean>(false);
            this.bodyColor = new ConfigProperty<Color>(Color.of(1275068416));
            this.headerTextShadow = new ConfigProperty<Boolean>(false);
            this.headerColor = new ConfigProperty<Color>(Color.of(1711276032));
            this.scoreColor = new ConfigProperty<Integer>(-43691);
            this.scoreVisibility = new ConfigProperty<ScoreVisibility>(ScoreVisibility.VISIBLE);
        }
        
        public ConfigProperty<Boolean> bodyTextShadow() {
            return this.bodyTextShadow;
        }
        
        public ConfigProperty<Color> bodyColor() {
            return this.bodyColor;
        }
        
        public ConfigProperty<Boolean> headerTextShadow() {
            return this.headerTextShadow;
        }
        
        public ConfigProperty<Color> headerColor() {
            return this.headerColor;
        }
        
        public ConfigProperty<Integer> scoreColor() {
            return this.scoreColor;
        }
        
        public ConfigProperty<ScoreVisibility> scoreVisibility() {
            return this.scoreVisibility;
        }
        
        public enum ScoreVisibility
        {
            VISIBLE, 
            HIDDEN, 
            AUTO;
        }
    }
    
    record DisplayEntry(RenderableComponent name, RenderableComponent score, int scoreValue, float scoreWidth) {}
}
