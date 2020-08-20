import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class Result extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    private JFrame frame;

    private int WIDTHFAKE;

    private final int DELAY = 10;
    private Timer timer;

    private int offsetX;
    private int offsetY;
    private int miniOffset;
    private int L;

    private Rectangle2D rect1;
    private int x1, y1;
    private int width1, height1;
    private boolean change1;

    private int line1N;
    private int line1S;
    private int line1W;
    private int line1E;

    public Result(JFrame frame){
        this.frame = frame;
        setup();
        initTimer();
    }

    public void initTimer(){
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void setup(){

        WIDTHFAKE = 450;

        addMouseListener(this);
        addMouseMotionListener(this);

        setBackground(Color.black);
        setOpaque(true);
        setLayout(null);
        setBounds(0, 0, MainPage.WIDTH, MainPage.HEIGHT);

        width1 = 150;
        height1 = 40;
        x1 = MainPage.WIDTH/2 - 75;
        y1 = MainPage.HEIGHT - 120;
        rect1= new Rectangle2D.Float(x1, y1, width1, height1);

        line1W = 0; line1E = 0; line1N = 0; line1S = 0;

        change1 = false;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    public void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Stroke defaultStroke = g2d.getStroke();

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        drawMatrix(g2d);

        drawSudokuLines(g2d);

        g2d.setStroke(defaultStroke);

        drawButton(g2d);

        drawLines(g2d);
    }

    public void drawSudokuLines(Graphics2D g2d){
        BasicStroke bs1 = new BasicStroke(5, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND, 1.0f, null, 1f);

        g2d.setStroke(bs1);
        g2d.setColor(Color.white);
        int bigOffset = 15 + L * 3 + miniOffset * 2;
        g2d.drawLine((MainPage.WIDTH - WIDTHFAKE)/2 + 15, bigOffset + 12, (MainPage.WIDTH - WIDTHFAKE)/2 + WIDTHFAKE - 5, bigOffset + 12);
        g2d.drawLine((MainPage.WIDTH - WIDTHFAKE)/2 + 15, bigOffset * 2 + 20, (MainPage.WIDTH - WIDTHFAKE)/2 + WIDTHFAKE - 5, bigOffset * 2 + 20);
        g2d.drawLine((MainPage.WIDTH - WIDTHFAKE)/2 + bigOffset + 15, 30, (MainPage.WIDTH - WIDTHFAKE)/2 + bigOffset + 15, WIDTHFAKE - 20);
        g2d.drawLine((MainPage.WIDTH - WIDTHFAKE)/2 + bigOffset * 2 + 23, 30, (MainPage.WIDTH - WIDTHFAKE)/2 + bigOffset * 2 + 23, WIDTHFAKE - 20);

    }

    public void drawMatrix(Graphics2D g2d){
        L = 38;
        offsetY = 45;
        miniOffset = 6;
        g2d.setFont(SudokuGame.font35);
        for(int i = 0; i < 9; i++){ //shit to make a good visible grid
            offsetX = (MainPage.WIDTH - WIDTHFAKE)/2 + 30;
            if(i % 3 == 0 && i != 0) offsetY += 15 + L + miniOffset;
            else if(i != 0) offsetY += L + miniOffset;
            for(int j = 0; j < 9; j++){
                if(j % 3 == 0 && j != 0) offsetX += 15 + L + miniOffset;
                else if(j != 0) offsetX += L + miniOffset;
                if(PaneCreatingMatrix.getValues()[i][j] != 0){
                    g2d.setColor(new Color(0, 245, 3));
                    g2d.drawString(PaneCreatingMatrix.getValues()[i][j] + "", offsetX, offsetY);
                } else {
                    g2d.setColor(new Color(199, 199, 199));
                    g2d.drawString(PaneResults.getResultsList().get(PaneResults.getSolution() - 1)[i][j] + "", offsetX, offsetY);
                }
            }
        }
    }

    public void drawButton(Graphics2D g2d){
        g2d.setFont(SudokuGame.font35);
        int textWidth1 = g2d.getFontMetrics().stringWidth("GO BACK"); //to draw numbers centered
        if(change1) {
            g2d.setColor(new Color(255, 255, 255));
            g2d.drawString("GO BACK", x1 + 5 + (width1 - textWidth1)/2, y1 + height1/2 + 8);
        } else{
            g2d.setColor(new Color(170, 170, 170));
            g2d.drawString("GO BACK", x1 + 5 + (width1 - textWidth1)/2, y1 + height1/2 + 8);
        }
    }

    public void drawLines(Graphics2D g2d) {
        BasicStroke bs1 = new BasicStroke(5, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND, 1.0f, null, 1f);

        g2d.setStroke(bs1);
        //NORTH 1
        g2d.setColor(new Color(0, 124, 190));
        g2d.drawLine(x1 + width1, y1, x1 + width1 - line1N, y1);
        //SOUTH 1
        g2d.setColor(new Color(249, 200, 14));
        g2d.drawLine(x1, y1 + height1, x1 + line1S, y1 + height1);
        //WEST 1
        g2d.setColor(new Color(238, 66, 102));
        g2d.drawLine(x1, y1, x1, y1 + line1W);
        //EAST 1
        g2d.setColor(new Color(0, 175, 84));
        g2d.drawLine(x1 + width1, y1 + height1, x1 + width1, y1 + height1 - line1E);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();

        if(change1) {
            if (line1N < width1) line1N += 10;
            if (line1S < width1) line1S += 10;
            if (line1W < height1) line1W += 5;
            if (line1E < height1) line1E += 5;
        } else {
            if (line1N > 0) line1N -= 10;
            if (line1S > 0) line1S -= 10;
            if (line1W > 0) line1W -= 5;
            if (line1E > 0) line1E -= 5;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rect1.contains(x, y)) {
            ResultsGlass resultsGlass = new ResultsGlass(frame, PaneResults.getResultsList());
            timer.stop();
            frame.setContentPane(resultsGlass);
            frame.revalidate();
        } 
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rect1.contains(x, y)) {
            change1 = true;
        } else change1 = false;
    }

    ////////////////////////////////////////////////////
    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
    @Override public void mouseDragged(MouseEvent e) { }
    ////////////////////////////////////////////////////

}
