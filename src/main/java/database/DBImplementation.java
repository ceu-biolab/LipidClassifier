package database;

import lipids.LMLipid;
import lipids.LipidCategoryMapper;
import lipids.LipidType;
import lipids.PatternFinder;
import ceu.biolab.*;
import java.sql.*;
import java.util.*;

/**
 * This class is responsible for
 */
public class DBImplementation {


    /**
     * This method reads all the lipid types of the table compounds_view from CMM's dagtabase
     * @param conn connection to DB
     * @return the lipid types as a List
     */
    public static List<String> readDatabaseSQL(Connection conn) {
        Statement stmt = null;
        List<String> lipidTypes = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            String sql = "SELECT DISTINCT lipid_type FROM compounds_view WHERE lipid_type IS NOT NULL ORDER BY lipid_type ASC;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String lipidTypeDB = rs.getString("lipid_type");
                //LipidType.buildLipidTypeMap(file, lipidTypeDB);
                lipidTypes.add(lipidTypeDB);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lipidTypes;
    }


    /**
     * This method inserts all the lipids into the DB
     * @param connection connection to DB
     * @param lipid the lipid to be inserted as a LMLipid
     */
    public static void insertLipidsIntoDatabase(Connection connection, LMLipid lipid) throws IncorrectFormula, IncorrectAdduct, NotFoundElement {
        try {
            // Insert classification into lm_classification table and retrieve ID
            int lmClassificationId = Database.insertLmClassification(connection, lipid.getCategory(), lipid.getMainClass(), lipid.getSubClass(), lipid.getClassLevel4());

            // Get lipid's ID (lm_id) to find the compound_id
            String lipidID = lipid.getLmId();
            int compoundId = Database.getCompoundIDFromLMID(connection, lipidID);

            // Insert into compounds_lm_classification if compoundId is valid
            if (compoundId != -1) {
                Database.insertIntoCompoundsLMClassification(connection, compoundId, lmClassificationId);
            }else{
                compoundId = Database.getCompoundIdFromInchiKey(connection, lipid.getinchiKey());
                System.out.println("Lipid with ID not found: " + lipidID);
                if(compoundId == -1){
                    String casID = null;
                    String compound_name = lipid.getName();
                    String formulaString = lipid.getFormula();
                    String mass = lipid.getMass();
                    String inChI = lipid.getInChi();
                    String smiles = lipid.getSmiles();
                    int[] charges = PatternFinder.getChargeFromSmiles(smiles);
                    int chargeType = charges[0];
                    int numCharges = charges[1];
                    Formula formula = Formula.formulaFromStringHill(formulaString, null, null);
                    FormulaType formulaTypeEnum = formula.getType(); //formulaValidation
                    String formulaType = formulaTypeEnum.name();

                    int compoundType = 1; //lipids are always 1
                    int compoundStatus = 0;
                    String logP = null;

                    compoundId = Database.insertCompound(connection, casID, compound_name, formulaType, mass, chargeType, numCharges, formulaType, compoundType, compoundStatus, logP);
                    Database.insertIdentifiers(connection, compoundId, inChI, lipid.getinchiKey(), smiles);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method updates the chains in the DB
     * @param connection connection to the DB
     * @param lipids lipids with the chains to be updated as a List
     */
    public static void updateChainsDB(Connection connection, List<LMLipid> lipids) {
        int notFound = 0;
        try {
            for (LMLipid lipid : lipids) {
                // Get the compound ID based on the lipid's lmId
                int compoundId = Database.getCompoundIDFromLMID(connection, lipid.getLmId());
                if (compoundId == -1) {
                    // If compound ID is not found, skip this lipid
                    System.out.println("Lipid with id: " + lipid.getLmId() + " not found");
                    notFound++;
                    continue;
                }

                // Count occurrences of each chain type in this lipid
                Map<String, Integer> chainCountMap = new HashMap<>();
                for (String chain : lipid.getChains()) {
                    chainCountMap.put(chain, chainCountMap.getOrDefault(chain, 0) + 1);
                }

                // Iterate through each unique chain and its count
                for (Map.Entry<String, Integer> entry : chainCountMap.entrySet()) {
                    String chain = entry.getKey();
                    int numChains = entry.getValue();

                    int numberCarbons = PatternFinder.getNumberOfCarbons(chain);
                    int numberDoubleBonds = PatternFinder.getNumberOfDoubleBonds(chain);

                    // Check if the chain already exists in the database
                    int chainId = Database.getChainIdByChainComponents(connection, numberCarbons, numberDoubleBonds);

                    if (chainId != -1) {
                        // Chain exists, ensure the relation exists in compound_chain
                        if (!Database.isCompoundChainRelationExist(connection, compoundId, chainId)) {
                            // Insert relation if it does not exist
                            Database.insertIntoCompoundChain(connection, compoundId, chainId, numChains);
                        } else {
                            // Update the existing relationship's number_chains if necessary
                            Database.updateCompoundChain(connection, compoundId, chainId, numChains);
                        }
                    } else {
                        // Chain does not exist, so insert it
                        Database.insertIntoChains(connection, numberCarbons, numberDoubleBonds);
                        // Retrieve the newly created chain ID
                        chainId = Database.getChainIdByChainComponents(connection, numberCarbons, numberDoubleBonds);

                        // Insert into compound_chain with the new chain ID
                        Database.insertIntoCompoundChain(connection, compoundId, chainId, numChains);
                    }
                }
            }
            System.out.println("Number of compounds not found: " + notFound);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method updates the DB by inserting all the new Lipid Types RT and fragmentation
     * @param conn connection to DB
     * @param lipidTypeMap the Map containing the previous lipid type as key and LipidType (lipid type RT and fragmentation, and classification) as a Value
     */
    public static void updateLipidTypesWithHeadAndFragmentation(Connection conn, Map<String, LipidType> lipidTypeMap) {
        String updateSql = "UPDATE compounds_lipids_classification SET lipid_type_RT = ?, lipid_type_fragmentation = ? WHERE lipid_type = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            // Iterate through each lipid type in the map
            for (Map.Entry<String, LipidType> entry : lipidTypeMap.entrySet()) {
                String lipidTypeName = entry.getKey();
                LipidType lipidType = entry.getValue();

                // Set the parameters for the SQL update statement
                pstmt.setString(1, lipidType.getLipidTypeRT());            // head
                pstmt.setString(2, lipidType.getLipidTypeFragmentation()); // fragmentation (head-tail bonding)
                pstmt.setString(3, lipidTypeName);             // lipid_type

                // Execute the update statement
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Updated " + lipidTypeName + " successfully.");
                } else {
                    System.out.println("No record found for " + lipidTypeName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating lipid types in the database.");
        }
    }



}


