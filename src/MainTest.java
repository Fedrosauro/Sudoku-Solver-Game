public class MainTest {
    public static void main(String args[]){
        int[][] matrix = {{3,0,0,   0,0,0,   0,2,0},
                          {0,7,0,   0,1,0,   0,5,0},
                          {0,0,1,   6,0,2,   0,0,3},

                          {0,0,9,   4,0,0,   0,8,0},
                          {0,0,0,   0,9,0,   5,0,0},
                          {0,5,0,   0,0,7,   0,1,0},

                          {0,0,0,   0,2,6,   4,0,0},
                          {0,0,0,   0,3,0,   0,0,1},
                          {2,0,0,   1,0,0,   0,7,0}};

        //that is the matrix that we have to solve
        //if you put few numbers in this matrix, the program will crash
        Sudoku s1 = new Sudoku(matrix); //creating of a new sudoku with that matrix
        s1.resolve();
        System.out.println("\n" + s1.toStringL()); //print solutions

        /*   --------------------------
             |    MADE BY "Klio" :P   |
             --------------------------

             I'm still working on the graphical part...
         */
    }
}
