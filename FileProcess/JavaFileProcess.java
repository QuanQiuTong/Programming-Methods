import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class JavaFileProcess {
    public class TodoNotFoundException extends Exception {
        public TodoNotFoundException() {
            super("todo not found");
        }

        public TodoNotFoundException(String message) {
            super(message);
        }
    }

    /**
     * Finds comment with the format "//todo:".
     * If found, prints the absolute path of the file.
     * If not found, throws TodoNotFoundException.
     */
    public void processJavaFile(File file)
            throws FileNotFoundException, IOException, TodoNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            Pattern pattern = Pattern.compile("//.*\\btodo\\b:");

            for (String line; (line = br.readLine()) != null;) {
                if (pattern.matcher(line).find()) {
                    System.out.println(file.getAbsolutePath());
                    return;
                }
            }

            // If we reach here, it means we didn't find any todo.
            throw new TodoNotFoundException();
        }
    }

    public void findJavaFiles(String path) {
        File file = Paths.get(path).toFile();

        // System.out.println("Processing directory: " + file.getAbsolutePath());

        File[] files = file.listFiles();
        if (files == null) {
            System.out.println("*ERROR*" +
                    "This abstract pathname does not denote a directory: "
                    + file.getAbsolutePath());
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                findJavaFiles(f.getAbsolutePath());
            } else if (f.isFile() && f.getName().endsWith(".java")) {
                try {
                    processJavaFile(f);
                } catch (TodoNotFoundException e) {
                    System.out.println(e.getMessage());
                } catch (FileNotFoundException e) {
                    System.out.println("File not found or cannot be opened: " + f.getAbsolutePath());
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("Error reading file: " + f.getAbsolutePath());
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        JavaFileProcess jfp = new JavaFileProcess();
        jfp.findJavaFiles(Paths.get(".").toAbsolutePath().toString());
    }
}