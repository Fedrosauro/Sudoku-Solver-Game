import javax.swing.*;
import java.awt.*;

public class SudokuGame extends JFrame {

    /*    --------------------------------------------
          |       "GAME" DEVELOPED BY "Klio" :3      |
          |       University of Trieste Student      |
          |               (First Year)               |
          --------------------------------------------

     */

    public static Font font70 = null;
    public static Font font35 = null;
    public static Font font30 = null;
    public static Font font23 = null;

    public SudokuGame(){
        initUI();
    }

    public void initUI(){
        MainPage mainPage = new MainPage(this);

        setContentPane(mainPage);

        //setting the window
        setTitle("Sudoku Game");
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                createFonts();
                SudokuGame game = new SudokuGame();
                game.setVisible(true);
            }

            public void createFonts(){
                try {
                    if (font70 == null) {
                        font70 = ArcadeFont.createFont(70);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (font35 == null) {
                        font35 = ArcadeFont.createFont(35);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (font30 == null) {
                        font30 = ArcadeFont.createFont(30);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (font23 == null) {
                        font23 = ArcadeFont.createFont(23);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
