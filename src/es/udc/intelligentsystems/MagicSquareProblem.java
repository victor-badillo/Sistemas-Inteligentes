package es.udc.intelligentsystems;

import java.util.ArrayList;

public class MagicSquareProblem extends SearchProblem{

    public static class MagicSquareState extends State {

        int n;
        private int [][] square;

        public MagicSquareState(int[][] square, int n){
            this.n = n;
            this.square = square;
        }

        public int getN(){
            return n;
        }

        public int[][] getSquare(){
            return square;
        }

        @Override
        public String toString() {
            StringBuilder str = new StringBuilder();
            str.append("{");
            for(int i=0;i < n;i++) {
                str.append("{");
                for (int j = 0; j < n; j++) {
                    str.append(square[i][j]).append(",");
                }
                str.append("}");
            }
            str.append("}");
            return str.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MagicSquareState someSquare = (MagicSquareState) o;
            int row = someSquare.square.length;
            int column = someSquare.square[0].length;

            for(int c=1; c< row; c++) {
                if(someSquare.square[c].length != column){
                    return false;
                }
            }

            if (this.square.length != row || this.square[0].length != column) {
                return false;
            }

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < row; j++) {
                    if (this.square[i][j] != someSquare.square[i][j])
                        return false;

                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            int result = 17; // arbitrary prime number
            //result = 31 * result + this.square.length;
            //result = 31 * result + this.square[0].length;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    result = 31 * result + square[i][j];
                }
            }

            return result;
        }
    }

    public static class MagicSquareAction extends Action {



        int x;
        int y;
        int num;


        public MagicSquareAction(int x, int y, int num) {
            this.x = x;
            this.y = y;
            this.num = num;
        }

        @Override
        public String toString() {
            return "(" + "(" + x + "," + y + ")" + num + ")";
        }

        @Override
        public boolean isApplicable(State st) {
            MagicSquareState esC = (MagicSquareState) applyTo(st);

            if (((MagicSquareState)st).square[x][y] != 0) return false;

            int num = esC.getN();
            int maxN = (num*((num*num)+1))/2;

            int sumd1 = 0,sumd2=0;
            int rowSum = 0, colSum = 0;
            for (int i = 0; i < esC.n; i++) {
                if (maxN < (sumd1 += esC.square[i][i])) return false;
                if (maxN < (sumd2 += esC.square[i][esC.n-1-i])) return false;
                if (maxN < (rowSum += esC.square[x][i])) return false;
                if (maxN < (colSum += esC.square[i][y])) return false;
            }
            return true;
        }

        @Override
        public State applyTo(State st) {
            MagicSquareState esC = ((MagicSquareState) st);
            int[][] matriz = new int[esC.n][esC.n];

            for(int i = 0 ; i < esC.n ; i++)
                System.arraycopy(esC.square[i], 0, matriz[i], 0, esC.n);

            matriz[x][y] = num;

            return new MagicSquareState( matriz,esC.n);
        }


    }

    private Action[] actionList;

    public MagicSquareProblem(State initialState) {
        super(initialState);
    }

    @Override
    public boolean isGoal(State st) {
        MagicSquareState esC = (MagicSquareState) st;

        int sumd1 = 0,sumd2=0;
        for (int i = 0; i < esC.n; i++) {
            sumd1 += esC.square[i][i];
            sumd2 += esC.square[i][esC.n-1-i];
        }
        //compara que las diagonales sumen lo mismo
        if(sumd1!=sumd2)
            return false;

        for (int i = 0; i < esC.n; i++) {
            int rowSum = 0, colSum = 0;
            for (int j = 0; j < esC.n; j++) {
                rowSum += esC.square[i][j];
                colSum += esC.square[j][i];
            }
            //compara que las filas y columnas sumen lo mismo
            if (rowSum != colSum || colSum != sumd1)
                return false;
        }
        return true;
    }

    @Override
    public Action[] actions(State st) {
        MagicSquareState esC = (MagicSquareState) st;
        ArrayList<Action> listaAcciones = new ArrayList<>();
        ArrayList<Integer> listA = new ArrayList<>();
        ArrayList<Integer> listB = new ArrayList<>();

        // mete todos los valores de la matriz en una lista
        for (int i = 0; i < esC.n; i++) {
            for (int j = 0; j < esC.n; j++) {
                listA.add(esC.square[i][j]);
            }
        }
        // hace una lista con los valores que no están en la matriz
        for(int i=1;i<= (esC.n * esC.n);i++){
            if(!listA.contains(i))
                listB.add(i);
        }

        // crea las posibles acciones para cada casilla vacía
        for (int i = 0; i < esC.n; i++) {
            for (int j = 0; j < esC.n; j++) {
                if(esC.square[i][j] == 0){
                    for(Integer item : listB){
                        Action a = new MagicSquareAction(i,j,item);
                        if (a.isApplicable(esC))
                            listaAcciones.add(a);
                    }
                }
            }
        }

        return listaAcciones.toArray(new Action[0]);
    }


}
