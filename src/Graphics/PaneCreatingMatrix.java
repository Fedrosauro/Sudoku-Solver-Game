import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class PaneCreatingMatrix extends JPanel { //JPanel for the matrix of numbers

    private int WIDTH;
    private int HEIGHT;

    private int offsetX;
    private int offsetY;
    private int miniOffset;
    private int L;

    private static InvisibleTextField[][] values;
    private PlainDocument[][] docs;

    public PaneCreatingMatrix(){
        setup();
    }

    public void setup(){
        WIDTH = MainPage.WIDTH - 150;
        HEIGHT = MainPage.HEIGHT - 150;

        setBackground(null);
        setOpaque(true);
        setLayout(null);
        setBounds((MainPage.WIDTH - this.WIDTH)/2, (MainPage.HEIGHT - this.HEIGHT)/2 - 50, WIDTH, HEIGHT);

        values = new InvisibleTextField[9][9]; //to store all the 81 values of the matrix
        docs = new PlainDocument[9][9];

        L = 38;
        offsetX = 0;
        offsetY = 15;
        miniOffset = 6;

        for(int i = 0; i < values.length; i++){ //shit to make a good visible grid
            offsetX = 15;
            if(i % 3 == 0 && i != 0) offsetY += 15 + L + miniOffset;
            else if(i != 0) offsetY += L + miniOffset;
            for(int j = 0; j < values[i].length; j++){
                values[i][j] = new InvisibleTextField();
                if(j % 3 == 0 && j != 0) offsetX += 15 + L + miniOffset;
                else if(j != 0) offsetX += L + miniOffset;
                values[i][j].setBounds(offsetX, offsetY, L, L);
                add(values[i][j]);
                docs[i][j] = (PlainDocument) values[i][j].getDocument();
                docs[i][j].setDocumentFilter(new TextLengthDocFilter(1));
            }
        }
    }

    public static int[][] getValues() {
        int[][] matrix = new int[9][9];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                if(values[i][j] == null || values[i][j].getText().equals("")) matrix[i][j] = 0; //if the value is null it means there is a 0 there
                else matrix[i][j] = Integer.parseInt(values[i][j].getText()); //otherwise i take the number of the JTextField
            }
        } return matrix;
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

        drawSudokuLines(g2d);

        g2d.setStroke(defaultStroke);
    }

    public void drawSudokuLines(Graphics2D g2d){
        BasicStroke bs1 = new BasicStroke(5, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND, 1.0f, null, 1f);

        g2d.setStroke(bs1);
        g2d.setColor(Color.white);
        int bigOffset = 15 + L * 3 + miniOffset * 2;
        g2d.drawLine(0, bigOffset + 10, WIDTH, bigOffset + 10);
        g2d.drawLine(0, bigOffset * 2 + 16, WIDTH, bigOffset * 2 + 16);
        g2d.drawLine(bigOffset + 10, 0, bigOffset + 10, HEIGHT);
        g2d.drawLine(bigOffset * 2 + 16, 0, bigOffset * 2 + 16, HEIGHT);
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
