package shared.util;

import java.io.*;

public class FileHelper {

    public static String loadFile(String path) throws IOException {
        StringBuilder builder = new StringBuilder();
        File file = new File(path);
        String absPath = file.getAbsolutePath();
        System.out.println(absPath);
        try (FileReader fileReader = new FileReader(file)) {
            try (BufferedReader reader = new BufferedReader(fileReader)) {
                String line;
                while ((line=reader.readLine()) != null) {
                    builder.append(line);
                }
            }
        }
        return builder.toString();
    }

}
