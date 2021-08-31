package kraken.plugin.api;

/**
 * Provides utilities for accessing dialogue widgets.
 */
public final class Dialogue {

    private Dialogue() { }

    private static final DialogueOption[] DIALOGUE_OPTIONS = {
            new DialogueOption(77856776, 1986068481),
            new DialogueOption(77856781, 16777217),
            new DialogueOption(77856786, 12058625),
            new DialogueOption(77856791, 16777217)
    };

    /**
     * Determines if the dialogue widget is open.
     *
     * @return If the dialogue widget is open.
     */
    public static boolean isOpen() {
        return Widgets.getGroupById(1188) != null;
    }

    /**
     * Retrieves all dialogue options.
     *
     * @return All dialogue options.
     */
    public static String[] getOptions() {
        WidgetGroup group = Widgets.getGroupById(1188);
        if (group == null) {
            return new String[0];
        }

        int highest = 0;
        for (Widget w : group.getWidgets()) {
            if (w.getType() == Widget.TEXT) {
                String s = w.getText();
                if (s != null && s.length() == 2 && s.charAt(1) == '.') {
                    Debug.log(s);
                    int num = s.charAt(0) - '0';
                    highest = Math.max(highest, num);
                }
            }
        }

        String[] options = new String[highest];
        Widget prev = null;
        for (Widget w : group.getWidgets()) {
            if (w.getType() == Widget.TEXT) {
                String s = w.getText();
                if (s != null && s.length() == 2 && s.charAt(1) == '.') {
                    int index = (s.charAt(0) - '0' - 1);
                    if (prev != null && prev.getType() == Widget.TEXT) {
                        options[index] = prev.getText();
                    }
                }
            }
            prev = w;
        }

        return options;
    }

    /**
     * Interacts with a dialogue option.
     */
    public static void interact(String name) {
        String[] options = getOptions();
        for (int i = 0; i < Math.min(DIALOGUE_OPTIONS.length, options.length); i++) {
            String s = options[i];
            if (name.equalsIgnoreCase(s)) {
                DialogueOption opt = DIALOGUE_OPTIONS[i];
                Actions.menu(Actions.MENU_EXECUTE_DIALOGUE, 0, -1, opt.dialogueId, opt.dialogueId2);
            }
        }
    }

    /**
     * Clicks to the next dialogue.
     */
    public static void next() {
        Actions.menu(Actions.MENU_EXECUTE_DIALOGUE, 0, -1, 77594639, -1576534015);

    }

    private static class DialogueOption {
        private final int dialogueId;
        private final int dialogueId2;

        public DialogueOption(int dialogueId, int dialogueId2) {
            this.dialogueId = dialogueId;
            this.dialogueId2 = dialogueId2;
        }
    }
}
