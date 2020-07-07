import javax.swing.*;

public class GlassPane extends JLayeredPane {

    private int i;

    public GlassPane(){
        i = 0;
        setOpaque(true);
    }

    public void addPanel(JPanel jpanel){
        add(jpanel, i, 0);
        i++;
    }
}