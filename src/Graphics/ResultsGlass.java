import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ResultsGlass extends JPanel {

    private JFrame frame;

    private GlassPane glassPane;
    private PaneResults paneResults;
    private SubPaneResults subPaneResults;

    private ArrayList<int[][]> resultsList;

    public ResultsGlass(JFrame frame, ArrayList<int[][]> resultsList){
        this.frame = frame;
        this.resultsList = resultsList;
        setup();
    }

    public void setup(){

        setLayout(new BorderLayout());
        glassPane = new GlassPane(); //This panel acts as glass to contain the matrix the text and buttons
        add(glassPane, BorderLayout.CENTER);

        paneResults = new PaneResults(resultsList);
        subPaneResults = new SubPaneResults(frame);

        glassPane.addPanel(subPaneResults);
        glassPane.addPanel(paneResults);

    }
}

