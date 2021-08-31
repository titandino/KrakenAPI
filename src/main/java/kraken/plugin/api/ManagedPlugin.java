package kraken.plugin.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides a more detailed way of managing plugins at the Java level.
 */
public class ManagedPlugin<K extends Enum<K>> extends Plugin {

    /**
     * If this option is set, none of the plugin functions will be invoked while the cache is loading data.
     */
    public static final int OPTION_WAIT_CACHE_LOAD = (1 << 0);

    /**
     * All options to use for deciding plugin behavior.
     */
    private int options = 0;
    /**
     * The default amount of milliseconds to wait in onLoop when the managed version is not called.
     */
    private int defaultLoopDelay = 1000;

    /**
     * The current state.
     */
    private K activeState;

    /**
     * A map of actions to execute.
     */
    private final Map<K, PluginAction> actionMap = new HashMap<>();

    /**
     * Adds an option to this plugin.
     */
    public void addOption(int option) {
        options |= option;
    }

    /**
     * Removes an option from this plugin.
     */
    public void removeOption(int option) {
        options &= ~option;
    }

    /**
     * Determines if this plugin has an option set.
     */
    public boolean hasOption(int option) {
        return (options & option) != 0;
    }

    /**
     * Changes the default amount of milliseconds to wait in onLoop when the managed version is not called.
     */
    public void setDefaultLoopDelay(int defaultLoopDelay) {
        this.defaultLoopDelay = defaultLoopDelay;
    }

    /**
     * Adds an action to the action map.
     */
    public void addAction(K key, PluginAction action) {
        actionMap.put(key, action);
    }

    /**
     * Changes the state.
     */
    public void changeState(K state) {
        activeState = state;
    }

    /**
     * Determines if this plugin is busy waiting on the cache to finish loading.
     */
    private boolean waitingOnCache() {
        return hasOption(OPTION_WAIT_CACHE_LOAD) && Cache.loading();
    }

    @Override
    public boolean onLoaded(PluginContext pluginContext) {
        return super.onLoaded(pluginContext);
    }

    protected int onLoopManaged() {
        if (activeState == null) {
            Debug.log("State is not set");
            return defaultLoopDelay;
        }

        PluginAction action = actionMap.get(activeState);
        if (action == null) {
            Debug.log("Plugin action does not exist state state'" + activeState + "'");
            return defaultLoopDelay;
        }

        return action.onLoop();
    }

    protected void onPaintManaged() {

    }

    protected void onPaintOverlayManaged() {

    }

    protected void onConVarChangedManaged(ConVar conv, int oldValue, int newValue) {

    }

    protected void onWidgetVisibilityChangedManaged(int id, boolean visible) {

    }

    protected void onLocalPlayerChangedManaged(Player player) {

    }

    protected void onInventoryItemChangedManaged(WidgetItem prev, WidgetItem next) {

    }

    @Override
    public final int onLoop() {
        if (waitingOnCache()) {
            return defaultLoopDelay;
        }

        return onLoopManaged();
    }

    @Override
    public final void onPaint() {
        if (waitingOnCache()) {
            return;
        }

        onPaintManaged();
    }

    @Override
    public final void onPaintOverlay() {
        if (waitingOnCache()) {
            return;
        }

        onPaintOverlayManaged();
    }

    @Override
    public final void onConVarChanged(ConVar conv, int oldValue, int newValue) {
        if (waitingOnCache()) {
            return;
        }

        onConVarChangedManaged(conv, oldValue, newValue);
    }

    @Override
    public final void onWidgetVisibilityChanged(int id, boolean visible) {
        if (waitingOnCache()) {
            return;
        }

        onWidgetVisibilityChangedManaged(id, visible);
    }

    @Override
    public final void onLocalPlayerChanged(Player self) {
        if (waitingOnCache()) {
            return;
        }

        onLocalPlayerChangedManaged(self);
    }

    @Override
    public final void onInventoryItemChanged(WidgetItem prev, WidgetItem next) {
        if (waitingOnCache()) {
            return;
        }

        onInventoryItemChangedManaged(prev, next);
    }

    public static interface PluginAction {
        int onLoop();
    }
}
