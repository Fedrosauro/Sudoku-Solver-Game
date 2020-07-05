import java.util.ArrayList;

public class Sudoku {

    private int[][] matrix;
    private int N;
    public ArrayList<int[][]> resultsList; //this will contain all the matrices

    public Sudoku(int[][] matrix){
        this.matrix = matrix;
        resultsList = new ArrayList<>();
        this.N = 9;
    }

    public void resolve(){
        resolveR(0, matrix, 1); //Firstly I need this to put something inside the list
        System.out.println(resultsList.size() + " possible matrices for number : 1");
        for(int k = 2; k < 10; k++) {
            ArrayList<int[][]> matriciAggiornate = (ArrayList<int[][]>) resultsList.clone();
            resultsList.clear();
            for (int i = 0; i < matriciAggiornate.size(); i++) {
                resolveR(0, matriciAggiornate.get(i), k);
            }
            System.out.println(resultsList.size() + " possible matrices for number : " + k);
        }
    }

    public void resolveR(int col, int[][] matrix, int number){
        if(col == N) resultsList.add(matrix);
        else {
            if (numberInColumn(col, number, matrix)) { //it means that the number is already in the column ...
                resolveR(col + 1, matrix, number); //... therefore let's move on
            } else {
                for (int i = 0; i < N; i++) { //put the number in rows that can have the number
                    if (matrix[i][col] == 0 && numberIsSafe(i, col, number, matrix)) {
                        matrix[i][col] = number; //put the number ...
                        resolveR(col + 1, copyMatrix(matrix), number); //recursive call with the changed matrix
                        matrix[i][col] = 0; //... remove the number
                    }
                }
            }
        }
    }

    private boolean numberInColumn(int col, int number, int[][] matrix){
        boolean trovato = false;
        for(int i = 0; i < N && !trovato; i++){
            trovato = matrix[i][col] == number;
        } return trovato;
    }

    private boolean numberIsSafe(int row, int col, int number, int[][] matrix){
        //check if the number is already in the row chosen
        boolean safe = true;
        for(int j = 0; j < N && safe; j++){
            safe = matrix[row][j] != number;
        }
        if(safe){
            //check if the number is inside one of the nine "squares" of the sudoku
            return checkSquares(row, col, number, matrix);
        } else return false;
    }

    private boolean checkSquares(int row, int col, int number, int[][] matrix){
        if(row >= 0 && row <= 2 && col >= 0 && col <= 2) return checkSingleSquare(0, 0, number, matrix);
        if(row >= 0 && row <= 2 && col >= 3 && col <= 5) return checkSingleSquare(0, 3, number, matrix);
        if(row >= 0 && row <= 2 && col >= 6 && col <= 8) return checkSingleSquare(0, 6, number, matrix);

        if(row >= 3 && row <= 5 && col >= 0 && col <= 2) return checkSingleSquare(3, 0, number, matrix);
        if(row >= 3 && row <= 5 && col >= 3 && col <= 5) return checkSingleSquare(3, 3, number, matrix);
        if(row >= 3 && row <= 5 && col >= 6 && col <= 8) return checkSingleSquare(3, 6, number, matrix);

        if(row >= 6 && row <= 8 && col >= 0 && col <= 2) return checkSingleSquare(6, 0, number, matrix);
        if(row >= 6 && row <= 8 && col >= 3 && col <= 5) return checkSingleSquare(6, 3, number, matrix);
        else return checkSingleSquare(6, 6, number, matrix);
    }

    public boolean checkSingleSquare(int row, int col, int number, int[][] matrix){
        boolean safe = true;
        for(int i = row; i < N/3 + row && safe; i++){
            for(int j = col; j < N/3 + col && safe; j++){
                safe = matrix[i][j] != number;
            }
        } return safe;
    }

    private int[][] copyMatrix(int[][] oldMatrix){
        int[][] newMatrix = new int[N][N];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                newMatrix[i][j] = oldMatrix[i][j];
            }
        } return newMatrix;
    }

    public String toStringM(int[][] m){
        String s = "";
        for(int i = 0; i < m.length; i++){
            for(int j = 0; j < m[i].length; j++){
                s += m[i][j] + " ";
            } s += "\n";
        } return s;
    }

    public String toStringL(){
        String s = "";
        for(int i = 0; i < resultsList.size(); i++){
            if(resultsList.get(i) != null) {
                s += toStringM(resultsList.get(i)) + "\n";
            }
        } return s;
    }
}
