import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class ProcessSolvePane extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    private JFrame frame;

    private int WIDTH, HEIGHT;

    private final int DELAY = 10;
    private Timer timer;

    private MyThread thread;

    private boolean appear;
    private Rectangle2D rect1;
    private int x1, y1;
    private int width1, height1;
    private boolean change1;

    private int line1N;
    private int line1S;
    private int line1W;
    private int line1E;

    private int magicTimer;
    private int animateTimer;

    public ProcessSolvePane(JFrame frame){
        this.frame = frame;
        setup();
        initTimer();
    }

    public void setup(){
        addMouseListener(this);
        addMouseMotionListener(this);

        WIDTH = MainPage.WIDTH;
        HEIGHT = MainPage.HEIGHT;

        setBackground(Color.black);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        thread = new MyThread();
        thread.start();

        appear = false;

        width1 = 250;
        height1 = 60;
        x1 = WIDTH/2 - 125;
        y1 = HEIGHT/2;
        rect1= new Rectangle2D.Float(x1, y1, width1, height1);

        line1W = 0; line1E = 0; line1N = 0; line1S = 0;

        change1 = false;

        magicTimer = 0;
        animateTimer = 100;
    }

    public void initTimer(){
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();

        if(thread.isAlive() || magicTimer < 300) {
            magicTimer++;
            if (animateTimer > 100) animateTimer = 0;
            else animateTimer++;
        }

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

        bipBopMethod(g2d);

        g2d.setStroke(defaultStroke);
    }

    public void bipBopMethod(Graphics2D g2d){
        g2d.setFont(SudokuGame.font30);
        int textWidth1 = g2d.getFontMetrics().stringWidth("SOLVING");
        g2d.setColor(Color.white);
        if(thread.isAlive() || magicTimer < 300){
            if(animateTimer >= 0 && animateTimer < 25)
                g2d.drawString("SOLVING", (WIDTH - textWidth1)/2, HEIGHT/2 - 30);
            else if(animateTimer >= 25 && animateTimer < 50)
                g2d.drawString("SOLVING.", (WIDTH - textWidth1)/2, HEIGHT/2 - 30);
            else if(animateTimer >= 50 && animateTimer < 75)
                g2d.drawString("SOLVING..", (WIDTH - textWidth1)/2, HEIGHT/2 - 30);
            else if(animateTimer >= 75 && animateTimer < 100)
                g2d.drawString("SOLVING...", (WIDTH - textWidth1)/2, HEIGHT/2 - 30);
        } else{
            g2d.drawString("SOLVED", WIDTH/2 - 50, HEIGHT/2 - 30);
            drawButtons(g2d);
            drawLines(g2d);
        }
    }

    public void drawButtons(Graphics2D g2d){
        g2d.setFont(SudokuGame.font35);
        int textWidthRes = g2d.getFontMetrics().stringWidth("VIEW  RESULTS");
        if(change1) {
            g2d.setColor(new Color(255, 255, 255));
            g2d.drawString("VIEW  RESULTS", (WIDTH - textWidthRes)/2, y1 + height1/2 + 8);
        } else{
            g2d.setColor(new Color(170, 170, 170));
            g2d.drawString("VIEW  RESULTS", (WIDTH - textWidthRes)/2, y1 + height1/2 + 8);
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
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rect1.contains(x, y)) {
            ResultsGlass resultsGlass = new ResultsGlass(frame, thread.getSudokuResults());
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

    private class MyThread extends Thread{

        private Sudoku sudoku;

        public MyThread(){
            sudoku = new Sudoku(PaneCreatingMatrix.getValues());
        }

        @Override
        public void run(){
            sudoku.resolve();
        }

        public ArrayList<int[][]> getSudokuResults() {
            return sudoku.getResultsList();
        }
    }

    ////////////////////////////////////////////////////
    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
    @Override public void mouseDragged(MouseEvent e) { }
    ////////////////////////////////////////////////////
}
