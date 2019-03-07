package shared.util;

import com.google.gson.Gson;

import java.io.*;

public class FileHelper {

    public static String loadFile(String path) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(path);
        System.out.println(file.getAbsolutePath());
        try (FileReader fileReader = new FileReader(file)) {
            try (BufferedReader reader = new BufferedReader(fileReader)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line += "\n"; // important to preserve the newline character.
                    stringBuilder.append(line);
                }
            }
        }
        return stringBuilder.toString();
    }

    public static String inputStreamToString(InputStream input) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(String.format("%s\n", line));
        }
        br.close();
        return sb.toString();
    }

    public static Object readJsonFile(String path, Class klass) throws FileNotFoundException {
        return new Gson().fromJson(new FileReader(new File(path)), klass);
    }

}
