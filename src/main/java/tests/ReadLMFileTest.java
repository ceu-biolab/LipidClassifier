package tests;

import inputOutput.ReadLMFile;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReadLMFileTest {
    /*@BeforeEach
    void setUp() {
    }
     */

    private String createTempFile(String content) throws IOException {
        String filePath = "test-file.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
        return filePath;
    }




}