import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* README:
 *
 * Input files: Put input files in input folder and change names of txt files in main function
 * When writing \n to a text file in notepad it is recognized ignored (windows), you can open it in wordpad to see new line
 */

public class Main {

    /* Input: tab separated table
     * Output: table as a list of strings
     */
    public static String readFile(String filepath) throws IOException {
        BufferedReader br;
        String everything;
        try {
            br = new BufferedReader(new FileReader(filepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } finally {
            br.close();
        }
        return everything;
    }

    /* Input: File name in input directory
     * Output: 2D string array of the file
     */
    public static DynamicMatrix2D getTable(String filename) throws IOException{
        DynamicMatrix2D matrix = new DynamicMatrix2D();
        String table = readFile(filename);
        int i = 0, j = 0, k = 0;
        String val = "";

        while(k<table.length()) {
            val+=table.charAt(k);
            k++;

            if(table.charAt(k) == '\t'){
                matrix.set(i, j, val);
                val="";
                k++;
                j++;
            } else if(table.charAt(k) == '\n') {
                matrix.set(i, j, val);
                val="";
                k++;
                j = 0;
                i++;
            }
        }

        return matrix;
    }

    /* Input: original, conf1 and conf2 matrices
     * Output: new matrix from original according to conf1 and conf2
     */
    public static DynamicMatrix2D getOutput(DynamicMatrix2D original, DynamicMatrix2D conf1, DynamicMatrix2D conf2) {
        DynamicMatrix2D matrix = new DynamicMatrix2D();
        List<Integer> cols = new ArrayList<Integer>();
        int i=0, j=0;

        // modify matrix using conf1
        while(original.get(0, j) != null && conf1.get(i, 0) != null) {
            if(original.get(0, j).equals(conf1.get(i, 0))) {
                matrix.set(0, i, conf1.get(i, 1));
                cols.add(j);
                i++;
            }
            j++;
        }

        i=0;
        j=0;
        int k;
        // modify matrix using conf2
        while(original.get(j, 0) != null && conf2.get(i, 0) != null) {
            if(original.get(j, 0).equals(conf2.get(i, 0))) {
                matrix.set(i+1, 0, conf2.get(i, 1));
                for(k=0; k<cols.size(); k++) {
                    matrix.set(i+1, k, original.get(j, cols.get(k)));
                }
                i++;
            }
            j++;
        }
        return matrix;
    }

    public static void toFile(DynamicMatrix2D matrix) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));

        int i=0,j;
        while(matrix.get(i, 0) != null) {
            j=0;
            while(matrix.get(i, j) != null) {
                if(j>0) {
                    writer.append("\t");
                }
                writer.append(matrix.get(i, j));
                j++;
            }
            writer.append("\n");
            i++;
        }

        writer.close();
    }

    public static void main(String[] args) {
        DynamicMatrix2D original;
        DynamicMatrix2D conf1;
        DynamicMatrix2D conf2;
        DynamicMatrix2D output;

        try {
            original = getTable("input/input1.txt");
            conf1 = getTable("input/input2.txt");
            conf2 = getTable("input/input3.txt");

            output = getOutput(original,conf1,conf2);

            toFile(output);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}