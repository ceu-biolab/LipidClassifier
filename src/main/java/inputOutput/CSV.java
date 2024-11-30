package inputOutput;

import lipids.LipidCategoryMapper;
import lipids.LipidMapsClassification;
import lipids.LipidType;

import java.io.*;
import java.util.*;

import java.nio.charset.MalformedInputException;

/**
 * This class is responsible for working with .csv files
 */
public class CSV {

    /**
     * This method writes the organized lipid types to a .csv file
     * @param filePath             the path to the output csv file
     */
    public static void writeToCSV(String filePath, List<LipidType> lipidTypes, Map<String, String> lipidTypeEquivalents) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Lipid Type,Category,Main Class,Sub Class,Class Level 4");
            writer.newLine();

            for (LipidType lipidType1 : lipidTypes) {
                // standardize lipid type for consistent mapping
                String standardizedLipidType = lipidTypeEquivalents.getOrDefault(null, null);

                for(LipidMapsClassification lipidMapsClassification: lipidType1.getLmClassifications()){
                //writer.write(String.join(",", lipidType1.getLipidTypeDB(), lipidMapsClassification.getCategory(), lipidMapsClassification.getMainClass(), lipidMapsClassification.getSubClass(), lipidMapsClassification.getClassLevel4()));
                writer.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
