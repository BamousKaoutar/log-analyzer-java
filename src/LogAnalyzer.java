import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

public class LogAnalyzer {

    // Couleurs ANSI pour la console
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        log("Démarrage de l'analyseur de logs...");

        File folder = new File(".");
        File[] files = folder.listFiles();
        HashMap<String, Integer> extensionCounts = new HashMap<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String name = file.getName();
                    int dotIndex = name.lastIndexOf(".");
                    if (dotIndex != -1 && dotIndex < name.length() - 1) {
                        String ext = name.substring(dotIndex + 1).toLowerCase();
                        extensionCounts.put(ext, extensionCounts.getOrDefault(ext, 0) + 1);
                        log("Fichier analysé : " + CYAN + name + RESET);
                    }
                }
            }
        }

        try (FileWriter writer = new FileWriter("rapport.txt")) {
            System.out.println(GREEN + "Statistiques par extension :" + RESET);
            for (String ext : extensionCounts.keySet()) {
                int count = extensionCounts.get(ext);
                System.out.println(YELLOW + ext + RESET + " : " + count);
                writer.write(ext + " : " + count + "\n");
            }
            System.out.println(GREEN + "Résultats exportés dans rapport.txt" + RESET);
            log("Exportation réussie !");
        } catch (IOException e) {
            System.out.println(RED + "Erreur d'écriture : " + e.getMessage() + RESET);
            log("Erreur lors de l'écriture : " + e.getMessage());
        }

        log("Fin de l'analyse.");
    }

    public static void log(String message) {
        System.out.println(CYAN + "[LOG] " + LocalDateTime.now() + " - " + message + RESET);
    }
}