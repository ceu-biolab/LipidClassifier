package inputOutput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;

import database.DBImplementation;
import lipids.*;


/**
 * This class works with the file structures.sdf
 */
public class ReadLMFile {

    protected static String filePath = "C:/Users/34611/Desktop/INGENIERIA_BIOMEDICA/CUARTO_CARRERA/PROYECTOSII/LMSD.sdf/structures.txt"; //path to the DB file


    /**
     * This method reads all the information of the lipids of the file structures (LIPID MAPS DB) that follow a pattern for the extraction of their lipid type,
     * number of carbons and double bonds, and chains*
     * @param lipidTypeEquivalents
     */
    public static  List<LMLipid> readLipidDetailsFromLMFile(Connection connection, Map<String, String> lipidTypeEquivalents) {
        List<LMLipid> allLipids  = new ArrayList<>();
        int lmIDCount =0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String lipidID = null;
            String name = null;
            String category = null;
            String mainClass = null;
            String subClass = null;
            String classLevel4 = null;
            String abbreviation = null;
            String synonym = null;
            String mass = null;
            String formula = null;
            String inChi = null;
            String inchiKey = null;
            String smiles = null;
            List<String> chainsLipids = new ArrayList<>();


            while ((line = br.readLine()) != null) {
                if (line.trim().equals("> <LM_ID>")) {
                    lipidID = br.readLine().trim();
                } else if (line.trim().equals("> <NAME>")) {
                    name = br.readLine().trim();
                } else if (line.trim().equals("> <CATEGORY>")) {
                    category = br.readLine().trim();
                } else if (line.trim().equals("> <MAIN_CLASS>")) {
                    mainClass = br.readLine().trim();
                } else if (line.trim().equals("> <SUB_CLASS>")) {
                    subClass = br.readLine().trim();
                } else if (line.trim().equals("> <CLASS_LEVEL4>")) {
                    classLevel4 = br.readLine().trim();
                } else if (line.trim().equals("> <ABBREVIATION>")) {
                    abbreviation = br.readLine().trim();
                } else if (line.trim().equals("> <SYNONYMS>")) {
                    synonym = br.readLine().trim();
                } else if (line.trim().equals("> <EXACT_MASS>")) {
                    mass = br.readLine().trim();
                } else if (line.trim().equals("> <FORMULA>")) {
                    formula = br.readLine().trim();
                } else if (line.trim().equals("> <INCHI_KEY>")) {
                    inchiKey = br.readLine().trim();
                } else if (line.trim().equals("> <INCHI>")) {
                    String result = br.readLine().trim();
                    inchiKey =  result.substring(6); // Removes "InChI=" (6 characters)
                }  else if (line.trim().equals("> <SMILES>")) {
                    smiles = br.readLine().trim();
                } else if (line.trim().equals("$$$$")) {
                    if (name != null) {
                        String lipidType = PatternFinder.getLipidTypeAbbreviation(name);
                        lipidType = lipidTypeEquivalents.getOrDefault(lipidType, lipidType);

                        if (lipidType.isEmpty() && abbreviation != null) {
                            lipidType = PatternFinder.getLipidTypeFromAbbreviation(abbreviation);
                        }

                        if (lipidType.isEmpty() && synonym != null) {
                            lipidType = PatternFinder.getLipidTypeFromSynonym(synonym);
                        }

                        if (lipidType.equals("FAHFA")) {
                            chainsLipids = PatternFinder.getListChainsFromFAHFA(abbreviation != null ? abbreviation : synonym);
                        } else {
                            if (name != null && !name.isEmpty()) {
                                chainsLipids = PatternFinder.getListOfChains(name);
                            }

                            if (chainsLipids.isEmpty() && synonym != null && !synonym.isEmpty()) {
                                chainsLipids = PatternFinder.getListChainsFromSynonym(synonym);
                            }

                            if (chainsLipids.isEmpty() && abbreviation != null && !abbreviation.isEmpty()) {
                                chainsLipids = PatternFinder.getListChainsFromAbbreviation(abbreviation);
                            }
                        }

                        LMLipid lipid = new LMLipid(lipidID, name, abbreviation, synonym, category, mainClass, subClass, classLevel4, mass, formula, smiles, inChi, inchiKey, lipidType, chainsLipids);
                        //DBImplementation.insertLipidsIntoDatabase(connection, lipid);
                        //DBImplementation.updateChainsDB(connection, allLipids);
                        allLipids.add(lipid);
                        lmIDCount++;

                    }

                    name = null;
                    category = null;
                    mainClass = null;
                    subClass = null;
                    classLevel4 = null;
                    inchiKey = null;
                    abbreviation = null;
                    synonym = null;
                    chainsLipids = new ArrayList<>();
                }

            }

            // verifications:
            System.out.println("Total > <LM_ID> entries found: " + lmIDCount);

            return allLipids;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return allLipids;
    }








}
