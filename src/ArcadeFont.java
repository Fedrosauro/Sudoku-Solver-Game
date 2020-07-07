import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class ArcadeFont {

    private static Font ttfBase = null;
    private static Font arcadeFont = null;
    private static InputStream myStream = null;
    private static final String FONT_PATH_ARCADE = "src/Arcadepix Plus.ttf";

    public static Font createFont(int size) {

        try {
            myStream = new BufferedInputStream(
                    new FileInputStream(FONT_PATH_ARCADE));
            ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
            arcadeFont = ttfBase.deriveFont(Font.PLAIN, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Font not loaded.");
        }
        return arcadeFont;
    }
}