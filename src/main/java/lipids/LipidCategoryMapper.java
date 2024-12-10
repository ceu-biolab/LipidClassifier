package lipids;

import database.DBImplementation;
import database.Database;

import java.sql.*;
import java.util.*;

import inputOutput.CSV;
import inputOutput.ReadLMFile;



public class LipidCategoryMapper {
    protected static Map<String, String> lipidTypeEquivalents = new HashMap<>();
    protected static List<String> lipidTypesFromDB = new ArrayList<>();
    protected static List<LMLipid> allLipids = new ArrayList<>();
    protected static Map<String, LipidType> lipidTypeMap = new HashMap<>();



    public static void main(String[] args) {
        String file = "C:/Users/34611/Desktop/INGENIERIA_BIOMEDICA/CUARTO_CARRERA/PROYECTOSII/LipidClassifier/lipidTypeClassification.csv";


        try {
            //** conectar a la base de datos MySQL y leer los tipos de lípidos (lipid_type)
            Database db = new Database();
            Connection connection = db.getConnection();

            lipidTypeMap = LipidType.buildLipidTypeMap(file);


            System.out.println("Final Lipid Type Map Size: " + lipidTypeMap.size());
            for (Map.Entry<String, LipidType> entry : lipidTypeMap.entrySet()) {
                System.out.println("Lipid Type: " + entry.getKey());
                System.out.println("Lipid Head (RT): " + entry.getValue().getLipidTypeRT());
                System.out.println("Lipid Head-Tail Bounding (fragmentation): " + entry.getValue().getLipidTypeFragmentation());
                System.out.println("Classification: ");
                for(LipidMapsClassification lm : entry.getValue().getLmClassifications()) {
                    System.out.println("\t Category:" + lm.getCategory());
                    System.out.println("\t Main Class:" + lm.getMainClass());
                    System.out.println("\t Sub class:" + lm.getSubClass());
                    System.out.println("\t Class Level 4:" + lm.getClassLevel4());
                }
                System.out.println();
            }

            //we initialize the lipid equivalents such as Lyso lipids
            initializeLipidTypeEquivalents();

            allLipids = ReadLMFile.readLipidDetailsFromLMFile(connection, lipidTypeEquivalents);
            lipidTypesFromDB = DBImplementation.readDatabaseSQL(connection);


            DBImplementation.insertLipidsIntoDatabase(connection, allLipids);
            DBImplementation.updateChainsDB(connection, allLipids);


            //**DBImplementation.updateLipidTypesWithHeadAndFragmentation(connection, lipidTypeMap); //DONE



        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    /**
     * This method add elements to the maps
     * @param map The map where we want to add Categories or Main Classes or SubClasses
     * @param key The lipid types
     * @param value The category, the main class or the subclass corresponding to the lipidtype
     */
    public static void addToMap(Map<String, Set<String>> map, String key, String value) {
        if (key != null && value != null) {
            map.putIfAbsent(key, new HashSet<>());
            map.get(key).add(value);
        }
    }



    /**
     * This method initializes some manual equivalences in order to work with some lipid types
     */
    private static void initializeLipidTypeEquivalents() {
        // Map specific lipid types to a common key
        lipidTypeEquivalents.put("CDP-DG(a-", "CDP-DG(");
        lipidTypeEquivalents.put("CDP-DG(i-", "CDP-DG(");
        lipidTypeEquivalents.put("DG(a-", "DG(");
        lipidTypeEquivalents.put("DG(i-", "DG(");

        lipidTypeEquivalents.put("CL(a-", "CL(");
        lipidTypeEquivalents.put("CL(i-", "CL(");

        lipidTypeEquivalents.put("LPA(", "PA(");
        lipidTypeEquivalents.put("PA(a-", "PA(");
        lipidTypeEquivalents.put("PA(i-", "PA(");
        lipidTypeEquivalents.put("LysoPA(", "PA(");
        lipidTypeEquivalents.put("LysoPA(a-", "PA(");
        lipidTypeEquivalents.put("LysoPA(i-", "PA(");

        lipidTypeEquivalents.put("LPC(", "PC(");
        lipidTypeEquivalents.put("LPC(O-", "PC(O-");
        lipidTypeEquivalents.put("LPC(P-", "PC(P-");
        lipidTypeEquivalents.put("lysoPC(", "PC(");

        lipidTypeEquivalents.put("LPE(", "PE(");
        lipidTypeEquivalents.put("PE-Cer(d", "PE(");
        lipidTypeEquivalents.put("PE-Cer(t", "PE(");

        lipidTypeEquivalents.put("LPG(", "PG(");
        lipidTypeEquivalents.put("LPG(O-", "PG(O-");
        lipidTypeEquivalents.put("LPG(P-", "PG(P-");
        lipidTypeEquivalents.put("PG(a-", "PG(");
        lipidTypeEquivalents.put("PG(i-", "PG(");

        lipidTypeEquivalents.put("LPI(", "PI(");
        lipidTypeEquivalents.put("LPI(O-", "PI(O-");
        lipidTypeEquivalents.put("LPI(P-", "PI(P-");

        lipidTypeEquivalents.put("LPS(", "PS(");
        lipidTypeEquivalents.put("LPS(O-", "PS(O-");
        lipidTypeEquivalents.put("LPS(P-", "PS(P-");

        lipidTypeEquivalents.put("MG(a-", "MG(");
        lipidTypeEquivalents.put("MG(i-", "MG(");

        lipidTypeEquivalents.put("PGP(a-", "PGP(");
        lipidTypeEquivalents.put("PGP(i-", "PGP(");

        lipidTypeEquivalents.put("TG(a-", "TG(");
        lipidTypeEquivalents.put("TG(i-", "TG(");

        lipidTypeEquivalents.put("HexCer(d", "Cer(d");
        lipidTypeEquivalents.put("SulfoHexCer(d", "Cer(d");
        lipidTypeEquivalents.put("SulfoHexCer(t", "Cer(t");
        lipidTypeEquivalents.put("LacCer(", "Cer(t");

    }


    /**
     * This method checks if the lipid is to be excluded from counting.
     * @param lipidType The lipid type to check.
     * @return true if the lipid is MG, FA, or LP- lipids ; false otherwise.
     */
    private static boolean isExcluded(String lipidType) {
        return lipidType.startsWith("MG") || lipidType.startsWith("FA") || lipidType.startsWith("LP") || lipidType.contains("Lyso");
    }




}
