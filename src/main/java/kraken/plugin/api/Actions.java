package kraken.plugin.api;

/**
 * A provider of actions.
 */
public class Actions {

    private Actions() { }

    public static final int MENU_EXECUTE_WALK = 0;
    public static final int MENU_EXECUTE_PLAYER_REQ_ASSIST = 1;
    public static final int MENU_EXECUTE_PLAYER_EXAMINE = 2;
    public static final int MENU_EXECUTE_PLAYER = 3;
    public static final int MENU_EXECUTE_PLAYER_TRADE = 4;
    public static final int MENU_EXECUTE_NPC1 = 5;
    public static final int MENU_EXECUTE_NPC2 = 6;
    public static final int MENU_EXECUTE_NPC3 = 7;
    public static final int MENU_EXECUTE_NPC4 = 8;
    public static final int MENU_EXECUTE_NPC5 = 9;
    public static final int MENU_EXECUTE_NPC6 = 10;
    public static final int MENU_EXECUTE_WIDGET_ITEM = 11;
    public static final int MENU_EXECUTE_OBJECT2 = 12;
    public static final int MENU_EXECUTE_OBJECT1 = 13;
    public static final int MENU_EXECUTE_WIDGET = 14;
    public static final int MENU_EXECUTE_GROUND_ITEM = 15;
    public static final int MENU_EXECUTE_DIALOGUE = 16;
    public static final int MENU_EXECUTE_SELECTABLE = 17;
    public static final int MENU_EXECUTE_SELECT = 18;
    public static final int MENU_EXECUTE_OBJECT3 = 19;
    public static final int MENU_EXECUTE_OBJECT4 = 20;
    public static final int MENU_EXECUTE_OBJECT5 = 21;
    public static final int MENU_EXECUTE_OBJECT6 = 22;

    /**
     * Executes a synthetic menu action.
     */
    public static native void menu(int type, int param1, int param2, int param3, int param4);

    // Entity utilities.

    public static void entity(SceneObject object, int type) {
        Vector3i pos = object.getGlobalPosition();
        Actions.menu(type, object.getId(), pos.getX(), pos.getY(), 1);
    }

    public static void entity(SceneObject object, int type, int xOff, int yOff) {
        Vector3i pos = object.getGlobalPosition();
        Actions.menu(type, object.getId(), pos.getX() + xOff, pos.getY() + yOff, 1);
    }

    public static void entity(Npc npc, int type) {
        Actions.menu(type, npc.getServerIndex(), 0, 0, 1);
    }
}
