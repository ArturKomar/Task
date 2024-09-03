import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {

    private BufferedWriter intWriter;
    private BufferedWriter floatWriter;
    private BufferedWriter stringWriter;

    public FileManager(String intFilePath, String floatFilePath, String stringFilePath) throws IOException {
        this.intWriter = new BufferedWriter(new FileWriter(intFilePath));
        this.floatWriter = new BufferedWriter(new FileWriter(floatFilePath));
        this.stringWriter = new BufferedWriter(new FileWriter(stringFilePath));
    }

    public void sortFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        }

        for (String line : lines) {
            try {
                long longValue = Long.parseLong(line);
                intWriter.write(longValue + "\n");
            } catch (NumberFormatException e1) {
                try {
                    double doubleValue = Double.parseDouble(line);
                    floatWriter.write(doubleValue + "\n");
                } catch (NumberFormatException e2) {
                    stringWriter.write(line + "\n");
                }
            }
        }
    }
    public void closeStreams() throws IOException {
        intWriter.close();
        floatWriter.close();
        stringWriter.close();
    }
}