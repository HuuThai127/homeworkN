package Controllers;



import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TextEditorController {
    public static List<String> loadFromFile(String fileName) {
        List<String> content = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            content = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static void saveToFile(String fileName, List<String> content) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            content.forEach(writer::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> listFilesRecursive(String directoryPath) {
        return listFilesRecursiveHelper(new File(directoryPath))
                .collect(Collectors.toList());
    }

    private static java.util.stream.Stream<String> listFilesRecursiveHelper(File directory) {
        return java.util.stream.Stream.of(directory.listFiles())
                .flatMap(file -> file.isDirectory() ? listFilesRecursiveHelper(file) : java.util.stream.Stream.of(file.getAbsolutePath()));
    }
    public static void saveToFile(String fileName, String content) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println(content);
        }
    }
}