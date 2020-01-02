public class DynamicMatrix2D {
    private String[][] matrix = new String[5][5];

    public void set(int x, int y, String value) {
        value = value.replace("\r","").replace("\n","");
        if (x >= matrix.length) {
            String[][] tmp = matrix;
            matrix = new String[x + 1][];
            System.arraycopy(tmp, 0, matrix, 0, tmp.length);
            for (int i = x; i < x + 1; i++) {
                matrix[i] = new String[y];
            }
        }

        if (y >= matrix[x].length) {
            String[] tmp = matrix[x];
            matrix[x] = new String[y + 1];
            System.arraycopy(tmp, 0, matrix[x], 0, tmp.length);
        }

        matrix[x][y] = value;
    }

    public String get(int x, int y) {
        return x >= matrix.length || y >= matrix[x].length ? "" : matrix[x][y];
    }

}