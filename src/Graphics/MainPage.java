import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class MainPage extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    private JFrame frame;

    public static int WIDTH;
    public static int HEIGHT;

    private final int DELAY = 10;
    private Timer timer;

    private Rectangle2D rect1; //everything that we need for the first button
    private int x1, y1;
    private int width1, height1;
    private boolean change1;

    private int line1N;
    private int line1S;
    private int line1W;
    private int line1E;

    private Rectangle2D rect2;
    private int y2;
    private boolean change2;

    private int line2N;
    private int line2S;
    private int line2W;
    private int line2E;

    public MainPage(JFrame frame){
        this.frame = frame;
        setup();
        initTimer();
    }

    public void setup(){
        addMouseListener(this);
        addMouseMotionListener(this);

        WIDTH = 600;
        HEIGHT = 600;

        setBackground(Color.black);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        width1 = 210; //things for buttons
        height1 = 60;
        x1 = WIDTH/2 - 105;
        y1 = HEIGHT/2 - 40;
        rect1= new Rectangle2D.Float(x1, y1, width1, height1);

        change1 = false;

        line1W = 0; line1E = 0; line1N = 0; line1S = 0;

        y2 = y1 + height1 + 20;
        rect2= new Rectangle2D.Float(x1, y2, width1, height1);

        change2 = false;

        line2W = 0; line2E = 0; line2N = 0; line2S = 0;
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
            if (line2N < width1) line2N += 10;
            if (line2S < width1) line2S += 10;
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

    public void doDrawing(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        Stroke defaultStroke = g2d.getStroke();

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        drawTitle(g2d);

        drawButtons(g2d);
        
        drawLines(g2d);

        g2d.setStroke(defaultStroke);
    }

    public void drawTitle(Graphics2D g2d){
        g2d.setColor(Color.white);
        g2d.setFont(SudokuGame.font70);
        int textWidthTitle = g2d.getFontMetrics().stringWidth("S U D O K U");
        g2d.drawString("S U D O K U", (MainPage.WIDTH - textWidthTitle)/2, HEIGHT/4);
        g2d.drawString("S O L V E R", (MainPage.WIDTH - textWidthTitle)/2, HEIGHT/4 + 70);
    }

    public void drawButtons(Graphics2D g2d){
        g2d.setFont(SudokuGame.font35);
        int textWidth1 = g2d.getFontMetrics().stringWidth("EXIT"); //to draw numbers centered
        if(change1) {
            g2d.setColor(new Color(255, 255, 255));
            g2d.drawString("EXIT", x1 + 5 + (width1 - textWidth1)/2, y1 + height1/2 + 8);
        } else{
            g2d.setColor(new Color(170, 170, 170));
            g2d.drawString("EXIT", x1 + 5 + (width1 - textWidth1)/2, y1 + height1/2 + 8);
        }

        if(change2) {
            g2d.setColor(new Color(255, 255, 255));
            g2d.drawString("PLAY", x1 + 5 + (width1 - textWidth1)/2, y2 + height1/2 + 8);
        } else{
            g2d.setColor(new Color(170, 170, 170));
            g2d.drawString("PLAY", x1 + 5 + (width1 - textWidth1)/2, y2 + height1/2 + 8);
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
        g2d.drawLine(x1 + width1, y2, x1 + width1 - line2N, y2);
        //SOUTH 2
        g2d.setColor(new Color(249, 200, 14));
        g2d.drawLine(x1, y2 + height1, x1 + line2S, y2 + height1);
        //WEST 2
        g2d.setColor(new Color(238, 66, 102));
        g2d.drawLine(x1, y2, x1, y2 + line2W);
        //EAST 2
        g2d.setColor(new Color(0, 175, 84));
        g2d.drawLine(x1 + width1, y2 + height1, x1 + width1, y2 + height1 - line2E);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rect1.contains(x, y)) {
            timer.stop();
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)); //X with custom button
        }

        if (rect2.contains(x, y)) {
            PlayGlass playGlass = new PlayGlass(frame);
            timer.stop();
            frame.setContentPane(playGlass);
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
