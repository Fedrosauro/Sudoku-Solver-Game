import javax.swing.*;
import java.awt.*;

public class PlayGlass extends JPanel {

    private JFrame frame;

    private GlassPane glassPane;
    private SubPaneCreatingMatrix subPaneMatrix;
    private PaneCreatingMatrix matrixPane;

    public PlayGlass(JFrame frame){
        this.frame = frame;
        setup();
    }

    public void setup(){
        setBackground(Color.black);

        setLayout(new BorderLayout());
        glassPane = new GlassPane(); //This panel acts as glass to contain the matrix the text and buttons
        add(glassPane, BorderLayout.CENTER);

        subPaneMatrix = new SubPaneCreatingMatrix(frame);
        matrixPane = new PaneCreatingMatrix();

        glassPane.addPanel(subPaneMatrix);
        glassPane.addPanel(matrixPane);
    }
}

