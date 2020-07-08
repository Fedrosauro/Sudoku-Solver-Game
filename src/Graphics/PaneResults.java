
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;

public class PaneResults extends JPanel {

    private int WIDTH, HEIGHT;

    private static InvisibleTextField solution;
    private PlainDocument docs;

    private static ArrayList<int[][]> resultsList;

    public PaneResults(ArrayList<int[][]> resultsList){
        this.resultsList = resultsList;
        setup();
    }

    public void setup(){
        setLayout(null);

        WIDTH = MainPage.WIDTH / 2 + 150;
        HEIGHT = MainPage.HEIGHT / 2 - 90;

        setBackground(Color.black);
        setOpaque(true);
        setBounds((MainPage.WIDTH - this.WIDTH)/2, (MainPage.HEIGHT - this.HEIGHT)/2 - 100, WIDTH, HEIGHT);

        solution = new InvisibleTextField();
        solution.setBounds(WIDTH/2 - 60, HEIGHT/2 + 45, 120, 38);
        add(solution);
        docs = (PlainDocument) solution.getDocument();
        docs.setDocumentFilter(new TextLengthDocFilter(countDigits()));
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    public void doDrawing(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        drawText(g2d);
    }

    public void drawText(Graphics2D g2d){
        g2d.setColor(Color.white);
        g2d.setFont(SudokuGame.font30);
        int textWidth1 = g2d.getFontMetrics().stringWidth("THE PROGRAM WAS ABLE TO FIND");
        g2d.drawString("THE PROGRAM WAS ABLE TO FIND", (WIDTH - textWidth1)/2, 20);
        int textWidth2 = g2d.getFontMetrics().stringWidth(resultsList.size() + " SOLUTIONS");
        g2d.drawString(resultsList.size() + " SOLUTIONS", (WIDTH - textWidth2)/2, 20 + 30);
        int textWidth3 = g2d.getFontMetrics().stringWidth("YOU CAN SEE ALL OF THEM");
        g2d.drawString("YOU CAN SEE ALL OF THEM", (WIDTH - textWidth3)/2, 20 + 60);
        int textWidth4 = g2d.getFontMetrics().stringWidth("BY INSERTING A NUMBER HERE");
        g2d.drawString("BY INSERTING A NUMBER HERE", (WIDTH - textWidth4)/2, 20 + 90);
    }

    public int countDigits(){ //count digits of the resultsList size
        return countDigitsR(0, resultsList.size());
    }

    private int countDigitsR(int i, int number){
        if(number == 0) return i;
        else return countDigitsR(i + 1, number/10);
    }

    public static ArrayList<int[][]> getResultsList() {
        return resultsList;
    }

    public static int getSolution() {
        return Integer.parseInt(solution.getText().isBlank() ? "1" : solution.getText());
        //if you don't enter any number by default you see the solution number 1
    }

    private class TextLengthDocFilter extends DocumentFilter {

        private int maxTextLength;

        public TextLengthDocFilter(int maxTextLength) {
            this.maxTextLength = maxTextLength;
        }

        private boolean verifyText(String text) { //setting the max value for each JTextField
            return text.length() <= maxTextLength;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string,
                                 AttributeSet attr) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.insert(offset, string);

            if (check(sb.toString()) && verifyText(sb.toString())) super.insertString(fb, offset, string, attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (check(sb.toString()) && verifyText(sb.toString())) super.replace(fb, offset, length, text, attrs);
        }

        @Override
        public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (check(sb.toString()) && verifyText(sb.toString())) super.remove(fb, offset, length);
        }

        private boolean check(String text) {
            if(text.isBlank()) return true; //if the field is blank is ok
            else {
                try {
                    Integer.parseInt(text); //but if it's not, it must be an int value (no char/string)
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
    }

}
