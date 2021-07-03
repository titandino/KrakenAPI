package kraken.plugin.api;

import java.io.ByteArrayOutputStream;

/**
 * Text utilities.
 */
public class Text {

    private Text() { }

    /**
     * Filters special characters used by the RuneScape client.
     */
    public static byte[] filterSpecialChars(byte[] bin) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        for (int i = 0; i < bin.length; i++) {
            // jagex smoking that gooood shit bru
            if (bin[i] == -62) {
                bos.write(' ');
            } else if (bin[i] == -96) {
                // nothing
            } else {
                bos.write(bin[i]);
            }
        }
        return bos.toByteArray();
    }

}
