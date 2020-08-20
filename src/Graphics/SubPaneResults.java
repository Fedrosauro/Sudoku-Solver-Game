import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class SubPaneResults extends JPanel implements MouseMotionListener, MouseListener, ActionListener {

    private JFrame frame;

    private final int DELAY = 10;
    private Timer timer;

    private int WIDTH, HEIGHT;

    private Rectangle2D rect1;
    private int x1, y1;
    private int width1, height1;
    private boolean change1;

    private int line1N;
    private int line1S;
    private int line1W;
    private int line1E;

    private Rectangle2D rect2;
    private int x2;
    private int width2;
    private boolean change2;

    private int line2N;
    private int line2S;
    private int line2W;
    private int line2E;

    public SubPaneResults(JFrame frame){
        this.frame = frame;
        setup();
        initTimer();
    }

    public void setup(){

        addMouseListener(this);
        addMouseMotionListener(this);

        WIDTH = MainPage.WIDTH;
        HEIGHT = MainPage.HEIGHT;

        setBackground(new Color(0, 0, 0));
        setOpaque(true);
        setBounds(0,0, WIDTH, HEIGHT);

        width1 = 150;
        height1 = 40;
        x1 = MainPage.WIDTH - 525;
        y1 = MainPage.HEIGHT - 280;
        rect1= new Rectangle2D.Float(x1, y1, width1, height1);

        line1W = 0; line1E = 0; line1N = 0; line1S = 0;

        x2 = x1 + width1 + 40;
        width2 = 450 - 20 - width1;
        rect2= new Rectangle2D.Float(x2, y1, width2, height1);

        line2W = 0; line2E = 0; line2N = 0; line2S = 0;

        change1 = false;

        change2 = false;
    }

    public void initTimer(){
        timer = new Timer(DELAY, this);
        timer.start();
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

        if(change2) {
            if (line2N < width2) line2N += 10;
            if (line2S < width2) line2S += 10;
            if (line2W < height1) line2W += 5;
            if (line2E < height1) line2E += 5;
        } else {
            if (line2N > 0) line2N -= 10;
            if (line2S > 0) line2S -= 10;
            if (line2W > 0) line2W -= 5;
            if (line2E > 0) line2E -= 5;
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    public void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        drawButtons(g2d);

        drawLines(g2d);
    }

    public void drawButtons(Graphics2D g2d){
        g2d.setFont(SudokuGame.font35);
        int textWidth1 = g2d.getFontMetrics().stringWidth("MENU");
        int textWidth2 = g2d.getFontMetrics().stringWidth("VIEW SOLUTION");

        if(change1) {
            g2d.setColor(new Color(255, 255, 255));
            g2d.drawString("MENU", x1 + (width1 - textWidth1)/2, y1 + height1/2 + 8);
        } else{
            g2d.setColor(new Color(170, 170, 170));
            g2d.drawString("MENU", x1 + (width1 - textWidth1)/2, y1 + height1/2 + 8);
        }

        if(change2) {
            g2d.setColor(new Color(255, 255, 255));
            g2d.drawString("VIEW SOLUTION", x2 + (width2 - textWidth2)/2, y1 + height1/2 + 8);
        } else{
            g2d.setColor(new Color(170, 170, 170));
            g2d.drawString("VIEW SOLUTION", x2 + (width2 - textWidth2)/2, y1 + height1/2 + 8);
        }
    }

    public void drawLines(Graphics2D g2d){
        BasicStroke bs1 = new BasicStroke(5, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND, 1.0f, null, 1f);

        g2d.setStroke(bs1);
        //NORTH 1
        g2d.setColor(new Color(0,124,190));
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

        //NORTH 2
        g2d.setColor(new Color(0,124,190));
        g2d.drawLine(x2 + width2, y1, x2 + width2 - line2N, y1);
        //SOUTH 2
        g2d.setColor(new Color(249, 200, 14));
        g2d.drawLine(x2, y1 + height1, x2 + line2S, y1 + height1);
        //WEST 2
        g2d.setColor(new Color(238, 66, 102));
        g2d.drawLine(x2, y1, x2, y1 + line2W);
        //EAST 2
        g2d.setColor(new Color(0, 175, 84));
        g2d.drawLine(x2 + width2, y1 + height1, x2 + width2, y1 + height1 - line2E);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rect1.contains(x, y)) {
           MainPage mainPage = new MainPage(frame);
            timer.stop();
            frame.setContentPane(mainPage);
            frame.revalidate();
        }

        if (rect2.contains(x, y)) {
            Result result = new Result(frame);
            timer.stop();
            frame.setContentPane(result);
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

        if (rect2.contains(x, y)) {
            change2 = true;
        } else change2 = false;
    }

    ////////////////////////////////////////////////////
    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
    @Override public void mouseDragged(MouseEvent e) { }
    ////////////////////////////////////////////////////
}
