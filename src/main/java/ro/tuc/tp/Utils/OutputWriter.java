package ro.tuc.tp.Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OutputWriter {

    public static void writeOutput(List<String> output) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            for (String string : output) {
                writer.write(string);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
