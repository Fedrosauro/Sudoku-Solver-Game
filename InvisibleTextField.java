import javax.swing.*;
import java.awt.*;

public class InvisibleTextField extends JTextField {

    public InvisibleTextField(){
        setBackground(new Color(62, 62, 62));
        setOpaque(true);
        setEditable(true);
        setFont(SudokuGame.font35);
        setForeground(Color.white);
        setHorizontalAlignment(this.CENTER);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
    }
}
