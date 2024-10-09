package InputOutput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadLMFile {
    public static String readName(String filePath) {
        String name = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().equals("> <NAME>")) { //leemos la siguiente línea que contiene el nombre
                    name = br.readLine().trim();
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return name;
    }

    public static String readAbbreviation(String filePath) {
        String abbreviation = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().equals("> <ABBREVIATION>")) {
                    abbreviation = br.readLine().trim();
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return abbreviation;
    }

    public static void main(String[] args) {
        String filePath = "C:/Users/34611/Desktop/INGENIERIA_BIOMEDICA/CUARTO_CARRERA/PROYECTOSII/LMSD.txt";

        String name = readName(filePath);
        String abbreviation = readAbbreviation(filePath);

        if (name != null) {
            System.out.println("Nombre: " + name);
        } else {
            System.out.println("No se encontró el nombre.");
        }

        if (abbreviation != null) {
            System.out.println("Abreviación: " + abbreviation);
        } else {
            System.out.println("No se encontró la abreviación.");
        }
    }
}
