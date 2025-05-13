// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.theme.controller;

import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.util.time.TimeUtil;
import java.util.concurrent.TimeUnit;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.gui.screen.theme.ThemeChangeEvent;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.activity.ActivityController;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlay;
import net.labymod.api.event.client.gui.screen.ActivityOpenEvent;
import net.labymod.api.event.Subscribe;
import java.util.Collection;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import net.labymod.api.client.gui.screen.activity.Activity;
import java.util.Map;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.ThemeEventListener;

public abstract class ActivityAnimationController implements ThemeEventListener
{
    protected final Theme theme;
    private final Map<Class<? extends Activity>, ActivityTransformer> activityClasses;
    private final Set<Class<? extends Activity>> currentActivityClasses;
    private Set<Class<? extends Activity>> previousActivityClasses;
    
    public ActivityAnimationController(final Theme theme) {
        this.currentActivityClasses = new HashSet<Class<? extends Activity>>();
        this.previousActivityClasses = new HashSet<Class<? extends Activity>>();
        this.theme = theme;
        this.activityClasses = new HashMap<Class<? extends Activity>, ActivityTransformer>();
        this.register();
    }
    
    protected ActivityTransformer register(final Class<? extends Activity> activityClass, final AnimationFunction animator) {
        final ActivityTransformer transformer = new ActivityTransformer(this, animator);
        this.activityClasses.put(activityClass, transformer);
        return transformer;
    }
    
    @Subscribe
    public void onScreenDisplay(final ScreenDisplayEvent event) {
        this.previousActivityClasses = new HashSet<Class<? extends Activity>>(this.currentActivityClasses);
        this.currentActivityClasses.clear();
    }
    
    @Subscribe
    public void onActivityOpenPre(final ActivityOpenEvent event) {
        final Activity activity = event.activity();
        if (activity instanceof ScreenOverlay || activity instanceof IngameOverlayActivity) {
            return;
        }
        final Class<? extends Activity> clazz = activity.getClass();
        this.currentActivityClasses.add(clazz);
        final Set<Class<? extends Activity>> closedActivities = new HashSet<Class<? extends Activity>>();
        final ActivityController controller = Laby.references().activityController();
        for (final Class<? extends Activity> currentActivityClass : this.currentActivityClasses) {
            if (controller.isActivityOpen(currentActivityClass)) {
                continue;
            }
            closedActivities.add(currentActivityClass);
        }
        if (!closedActivities.isEmpty()) {
            this.previousActivityClasses = closedActivities;
            this.currentActivityClasses.removeAll(closedActivities);
        }
        this.previousActivityClasses.removeAll(this.currentActivityClasses);
    }
    
    @Subscribe(64)
    public void onActivityOpenPost(final ActivityOpenEvent event) {
        final ActivityTransformer transformer = this.activityClasses.get(event.activity().getClass());
        if (transformer == null) {
            return;
        }
        if (!this.previousActivityClasses.contains(event.activity().getClass()) && this.isEnabled()) {
            transformer.setPreviousActivityClasses(this.previousActivityClasses);
            event.activity().setTransformer(transformer);
        }
    }
    
    @Subscribe
    public void onThemeChange(final ThemeChangeEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }
        this.activityClasses.clear();
    }
    
    public void transform(final Stack stack, final MutableMouse mouse, final float partialTicks, final Activity activity, final ActivityTransformer transformer) {
        final AnimationFunction function = transformer.animationFunction();
        transformer.setup(activity);
        function.update(transformer);
        final Bounds bounds = activity.bounds();
        stack.translate(bounds.getWidth() / 2.0f, bounds.getHeight() / 2.0f, 0.0f);
        final FloatVector3 scale = transformer.getScale();
        stack.scale(scale.getX(), scale.getY(), scale.getZ());
        final FloatVector3 translation = transformer.getTranslation();
        stack.translate(translation.getX(), translation.getY(), translation.getZ());
        final FloatVector3 rotation = transformer.getRotation();
        stack.rotate(rotation.getX(), 1.0f, 0.0f, 0.0f);
        stack.rotate(rotation.getY(), 0.0f, 1.0f, 0.0f);
        stack.rotate(rotation.getZ(), 0.0f, 0.0f, 1.0f);
        activity.document().opacity().set(transformer.getOpacity());
        stack.translate(-bounds.getWidth() / 2.0f, -bounds.getHeight() / 2.0f, 0.0f);
    }
    
    protected abstract void register();
    
    protected abstract boolean isEnabled();
    
    protected static float customFunc(final Transformer transformer, final float from, final float to, final int duration, final CubicBezier curve) {
        return from + (to - from) * transformer.getProgress(duration, TimeUnit.MILLISECONDS, curve);
    }
    
    public static class ActivityTransformer implements Transformer
    {
        private final ActivityAnimationController controller;
        private final AnimationFunction animationFunction;
        private float timePassed;
        private final FloatVector3 translation;
        private final FloatVector3 rotation;
        private final FloatVector3 scale;
        private float opacity;
        private Activity activity;
        private boolean active;
        private Set<Class<? extends Activity>> previousActivityClasses;
        
        public ActivityTransformer(final ActivityAnimationController controller, final AnimationFunction animationFunction) {
            this.previousActivityClasses = new HashSet<Class<? extends Activity>>();
            this.controller = controller;
            this.animationFunction = animationFunction;
            this.translation = new FloatVector3();
            this.rotation = new FloatVector3();
            this.scale = new FloatVector3(1.0f, 1.0f, 1.0f);
        }
        
        public void initialize() {
            this.timePassed = 0.0f;
            this.active = true;
        }
        
        public void render(final float tickDelta) {
            this.timePassed += TimeUtil.convertDeltaToMilliseconds(tickDelta);
        }
        
        @ApiStatus.Internal
        protected void setPreviousActivityClasses(final Set<Class<? extends Activity>> classes) {
            this.previousActivityClasses = classes;
        }
        
        protected void setup(final Activity activity) {
            this.activity = activity;
            this.reset();
        }
        
        public void dispose() {
            this.active = false;
            this.reset();
        }
        
        private void reset() {
            this.translation.set(0.0f, 0.0f, 0.0f);
            this.rotation.set(0.0f, 0.0f, 0.0f);
            this.scale.set(1.0f, 1.0f, 1.0f);
            this.opacity = 1.0f;
        }
        
        @Override
        public float getProgress(final int duration, final TimeUnit timeUnit) {
            final long millisDuration = timeUnit.toMillis(duration);
            if (millisDuration < 0L) {
                return 1.0f;
            }
            final float progress = this.timePassed / millisDuration;
            return (progress > 1.0f) ? 1.0f : progress;
        }
        
        @Override
        public Bounds getBounds() {
            return this.activity.bounds();
        }
        
        @Override
        public boolean isPreviously(final Class<? extends Activity> clazz) {
            return this.previousActivityClasses.contains(clazz);
        }
        
        @Override
        public boolean hasNoPreviouslyActivity() {
            return this.previousActivityClasses.isEmpty();
        }
        
        @Override
        public void scale(final float scale) {
            this.scale.setX(scale);
            this.scale.setY(scale);
            this.scale.setZ(scale);
        }
        
        @Override
        public void opacity(final float opacity) {
            this.opacity = opacity;
        }
        
        @Override
        public void translate(final float x, final float y) {
            this.translation.setX(x);
            this.translation.setY(y);
        }
        
        @Override
        public void rotateX(final float angle) {
            this.rotation.setX(angle);
        }
        
        @Override
        public void rotateY(final float angle) {
            this.rotation.setY(angle);
        }
        
        @Override
        public void rotateZ(final float angle) {
            this.rotation.setZ(angle);
        }
        
        public FloatVector3 getTranslation() {
            return this.translation;
        }
        
        public FloatVector3 getRotation() {
            return this.rotation;
        }
        
        public FloatVector3 getScale() {
            return this.scale;
        }
        
        public float getOpacity() {
            return this.opacity;
        }
        
        public ActivityAnimationController controller() {
            return this.controller;
        }
        
        public AnimationFunction animationFunction() {
            return this.animationFunction;
        }
        
        public boolean isActive() {
            return this.active;
        }
        
        public float getTimePassed() {
            return this.timePassed;
        }
        
        @Deprecated
        public long getTimeInitialized() {
            return 0L;
        }
    }
    
    public interface Transformer
    {
        float getProgress(final int p0, final TimeUnit p1);
        
        default float getProgress(final int duration, final TimeUnit timeUnit, final CubicBezier curve) {
            return (float)curve.curve(this.getProgress(duration, timeUnit));
        }
        
        Bounds getBounds();
        
        boolean isPreviously(final Class<? extends Activity> p0);
        
        boolean hasNoPreviouslyActivity();
        
        void scale(final float p0);
        
        void opacity(final float p0);
        
        void translate(final float p0, final float p1);
        
        void rotateX(final float p0);
        
        void rotateY(final float p0);
        
        void rotateZ(final float p0);
    }
    
    public interface AnimationFunction
    {
        void update(final Transformer p0);
    }
}
