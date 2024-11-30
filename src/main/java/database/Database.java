package database;

import utilities.Utilities;

import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import static utilities.PeriodicTable.MAPELEMENTS;
//import static utilities.PeriodicTable.*;

public class Database {
        private static Connection connection = null;
        public static Connection getConnection() {
            return connection;
        }

        public Database() {
            try {
                String url = "jdbc:mysql://localhost:3306/compounds";
                String username = "root";
                String password = "Mysql24*";
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "An error occurred when establishing the database connection. Please try again.");
            }
        }


    /**
     * This method inserts a new classification into the table lm_classification of the CMM's database
     * @param connection connection to DB
     * @param category the category as a String
     * @param mainClass   the main class as a String
     * @param subclass the sub class as a String
     * @param classLevel4 the class level 4 as a String
     * @return the ID of this classification in the DB
     * @throws SQLException
     */
        public static int insertLmClassification(Connection connection, String category, String mainClass, String subclass, String classLevel4) throws SQLException {
            // insert data into lm_classification table
            String insertQuery = "INSERT INTO lm_classification (category, main_class, subclass, class_level4) VALUES (?, ?, ?, ?)";
            int lastID = 0;

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)){
                //PreparedStatement prepstmt = conn.prepareStatement(insertQuery, RESULT_GENERATED_KEYS);
                preparedStatement.setString(1, category);
                preparedStatement.setString(2, mainClass);
                preparedStatement.setString(3, subclass);
                preparedStatement.setString(4, classLevel4);
                preparedStatement.executeUpdate();


                // we retrieve the generated key (auto-increment ID) to use it in the table N to M
                try (Statement stmt = connection.createStatement()) {
                    ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID();");
                    if (rs.next()) {
                        lastID = rs.getInt(1);
                        return lastID;
                    }
                }

            }
            return lastID;
        }


    /**
     * This method inserts the ids of the classification and the compound into the M-N relationship table compounds_lm_classification
     * @param connection connection to DB
     * @param compoundId id of the compound as an int
     * @param lmClassificationId id of the classification as an int
     * @throws SQLException
     */
        public static void insertIntoCompoundsLMClassification(Connection connection, int compoundId, int lmClassificationId) throws SQLException {
            // Insert data or update if the combination already exists
            String insertQuery = "INSERT IGNORE INTO compounds_lm_classification (compound_id, lm_classification_id) VALUES (?, ?)";
            //** we added "IGNORE" in the query since we've noticed that there were 3 lipids with different lm_id but with the same compound_id
            //** and 2 of them had the exact same classification

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, compoundId);
                preparedStatement.setInt(2, lmClassificationId);
                preparedStatement.executeUpdate();

            }
        }


    /**
     * This method obtains the compound id from the lipid id of LIPID MAPS
     * @param connection connection to DB
     * @param lmID lipid ID as an int
     * @return compound ID as an int
     * @throws SQLException
     */
        public static int getCompoundIDFromLMID(Connection connection, String lmID) throws SQLException{
            String selectQuery = "SELECT compound_id FROM compounds_lm WHERE lm_id = ?";


            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, lmID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("compound_id");
                    }
                }
            }
            return -1; //return -1 if compound_id is not found
        }


    /**
     * This method obtains the old number of chains in the DB
     * @param connection connection to DB
     * @param compoundID id of the compound as an int
     * @return the number of chains previously registered in the DB
     * @throws SQLException
     */
    public static int getOldNumberChains(Connection connection, int compoundID) throws SQLException {
        String selectQuery = "SELECT SUM(number_chains) AS total_chains FROM compound_chain WHERE compound_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, compoundID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("total_chains");
                }
            }
        }
        return 0; // return 0 if no chains are found for the compound
    }


    /**
     * This method closes the connection
     */
    public void closeConnection() {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Database connection closed.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    /**
     * This method inserts the compounds chains in the table compound_chain in the DB
     * @param connection connection to DB
     * @param compoundId the id of the compound as an int
     * @param chainID the id of the chain as an int
     * @param numberChains the number of chains as an int
     */
        public static void insertIntoCompoundChain(Connection connection, int compoundId, int chainID, int numberChains) {
            String insertSQL = "INSERT INTO compound_chain (compound_id, chain_id, number_chains) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                pstmt.setInt(1, compoundId);
                pstmt.setInt(2, chainID);
                pstmt.setInt(3, numberChains);
                pstmt.executeUpdate();

                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    /**
     * This method inserts the chains into the table chains in the DB
     * @param connection the connection to DB
     * @param numCarbons the number of carbons of the chain as an int
     * @param numDoubleBonds number of double bonds of the chain as an int
     */
        public static void insertIntoChains(Connection connection, int numCarbons, int numDoubleBonds) {
            String insertSQL = "INSERT INTO chains (num_carbons,double_bonds) VALUES (?, ?)";

            try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                pstmt.setInt(1, numCarbons);
                pstmt.setInt(2, numDoubleBonds);
                pstmt.executeUpdate();
                pstmt.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    /**
     * This method obtains the ID of the chain having the number of carbons and double bonds
     * @param connection connection to DB
     * @param numCarbons number of carbons of the chain we want to obtain the id from as an int
     * @param numDoubleBonds number of double bonds of the chain we want to obtain the id from as an int
     * @return the chain id as an int
     * @throws SQLException
     */
        public static int getChainIdByChainComponents(Connection connection, int numCarbons, int numDoubleBonds) throws SQLException {
            String query = "SELECT chain_id FROM chains WHERE num_carbons = ? AND double_bonds = ? ";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, numCarbons);
                stmt.setInt(2, numDoubleBonds);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("chain_id");
                }
                rs.close();
                stmt.close();
            }
            return -1; // chain does not exist
        }


    /**
     * THis method updates the table compound_chain of the DB
     * @param connection connection to DB
     * @param compoundId the id of the compound as an int
     * @param chainId the id of the chain as an int
     * @param newNumChains the updated number of chains of the compound as an int
     */
    public static void updateCompoundChain(Connection connection, int compoundId, int chainId, int newNumChains) {
        String updateSQL = "UPDATE compound_chain SET number_chains = ? WHERE compound_id = ? AND chain_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setInt(1, newNumChains);
            pstmt.setInt(2, compoundId);
            pstmt.setInt(3, chainId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method verifies if the relationship between the compound and the chain already exists
     * @param connection connection to db
     * @param compoundId id of the compound as an int
     * @param chainId id of the chain as an int
     * @return boolean
     * @throws SQLException
     */
    public static boolean isCompoundChainRelationExist(Connection connection, int compoundId, int chainId) throws SQLException {
        String selectQuery = "SELECT 1 FROM compound_chain WHERE compound_id = ? AND chain_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, compoundId);
            preparedStatement.setInt(2, chainId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if a record exists
            }
        }
    }


    /**
     * This method deletes a row from table compound_chain
     * @param connection connection to DB
     * @param compoundId id of the compound as an int
     * @param numberChains number of chains as an int
     */
    public void deleteFromCompoundChain(Connection connection, int compoundId, int numberChains) {
            String deleteSQL = "DELETE FROM compound_chain WHERE compound_id = ? AND number_chains = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {

                pstmt.setInt(1, compoundId);
                pstmt.setInt(2, numberChains);

                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Deleted: " + rowsAffected + " row(s) from compound_chain.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }




    /**
     * Retrieve the compound Id from the inchikey
     * @param inchiKey of the compound to search
     * @return the compound id of the inchikey. 0 if the compound was not found
     */
    public static int getCompoundIdFromInchiKey(Connection connection, String inchiKey) {
        String query = "SELECT compound_id FROM compound_identifiers WHERE inchi_key = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, inchiKey);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("compound_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1; // chain does not exist
    }


    /**
     * Retrieve the inchikey from the compound ID
     * @param compoundId of the compound to search
     * @return the inchi key of the compound id. 0 if the compound was not found
     */
    public static int getInChIKeyFromCompound(Connection connection, int compoundId) {
        String query = "SELECT inchi_key FROM compound_identifiers WHERE compound_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, compoundId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("inchi_key");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1; // chain does not exist
    }


    /**
     * Insert the structures of the compound Id in the database
     *
     * @param compoundId
     * @param inChI
     * @param inChIKey
     * @param SMILES
     */
    public static void insertIdentifiers(Connection connection, int compoundId, String inChI, String inChIKey, String SMILES) {
        if (Database.getInChIKeyFromCompound(connection, compoundId) == -1){
            if (inChI != null && SMILES != null && inChIKey != null
                    && !inChI.equals("") && !inChIKey.equals("") && !SMILES.equals("")) {
                String insertion = "INSERT IGNORE INTO compound_identifiers(compound_id, inchi, inchi_key, smiles) VALUES(?,?,?,?);";
                try (PreparedStatement stmt = connection.prepareStatement(insertion)) {
                    inChI = Utilities.escapeSQL(inChI);
                    SMILES = Utilities.escapeSQL(SMILES);

                    stmt.setInt(1, compoundId);
                    stmt.setString(2, inChI);
                    stmt.setString(3, inChIKey);
                    stmt.setString(4, SMILES);

                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        Utilities.executeNewIDUWithEx(insertion);
                    }
                } catch (SQLException ex) {
                    System.out.println("Identifier not inserted -> compoundId: " + compoundId + " InCHI: " + inChI);
                }
            }
        }
    }




    /**
     * This method inserts a compound in the database
     * @param casId cas Id of the compound as a string. Can be null
     * @param name the name of the compound as a string
     * @param formula the formula of the compound as a string
     * @param mass the mass of the compound as a string
     * @param chargeType the type of charge as an Integer: 0 for neutral, 1 for positive, 2 for negative
     * @param numCharges the number of charges as an integer positive
     * @param formulaType the formula type as a string which can take the values: CHNOPS, CHNOPSCL or ALL
     * @param compoundType the type of compound as an integer which can take the values: 0 for metabolite, 1 for lipid, 2 for peptide
     * @param compoundStatus the status of the compound as an integer which can take the values: 0 for expected, 1 for detected, 2 for quantified and 3 for predicted
     * @param logP logP as a string
     * @return the generated id of the compound inserted in the DB
     */
    public static int insertCompound(Connection connection, String casId, String name, String formula, String mass,
                                     Integer chargeType, Integer numCharges, String formulaType,
                                     Integer compoundType, Integer compoundStatus, String logP) {
        if (casId == null || casId.equalsIgnoreCase("null") || casId.equals("")) {
            casId = "NULL";
        } else {
            casId = "'" + casId + "'";
        }
        if (name == null || name.equalsIgnoreCase("null") || name.equals("")) {
            name = "NULL";
        } else {
            name = "\"" + name + "\"";
        }
        if (formula == null || formula.equalsIgnoreCase("null") || formula.equals("")) {
            formula = "NULL";
        } else {
            formula = "'" + formula + "'";
        }
        if (mass == null || mass.equalsIgnoreCase("null") || mass.equals("")) {
            mass = "NULL";
        }
        if (formulaType.equalsIgnoreCase("null") || formulaType.equals("")) {
            formulaType = "ALL";
        }
        if (logP == null || logP.equalsIgnoreCase("null") || logP.equals("")) {
            logP = "NULL";
        }
        int formulaTypeInt = Utilities.getIntChemAlphabet(formulaType);
        formulaType = "'" + formulaType + "'";

        String insertion = "INSERT IGNORE INTO compounds(cas_id, compound_name, formula, mass, "
                + "charge_type, charge_number, formula_type, formula_type_int, "
                + "compound_type, compound_status, logP) VALUES(?,?,?,?,?,?,?,?,?,?,?);";



        try (PreparedStatement stmt = connection.prepareStatement(insertion)) {
            stmt.setString(1, casId);
            stmt.setString(2, name);
            stmt.setString(3, formula);
            stmt.setString(4, mass);
            stmt.setInt(5, chargeType);
            stmt.setInt(6, numCharges);
            stmt.setString(7, formulaType);
            stmt.setInt(8, formulaTypeInt);
            stmt.setInt(9, compoundType);
            stmt.setInt(10, compoundStatus);
            stmt.setString(11, logP);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return executeNewInsertion(stmt, insertion);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1; // chain does not exist
    }



    /**
     * Executes the insert and return the First generated Key
     * @param statement the statement as a prepared statement
     * @param update the query as a string (insert, delete or update)
     * @return the Id of the insertion
     */
    public static int executeNewInsertion(PreparedStatement statement, String update) { // insertar, borrar o actualizar
        int id = 0;
        try {
            statement.executeUpdate(update, Statement.RETURN_GENERATED_KEYS);
            try (ResultSet provRS = statement.getGeneratedKeys()) {
                if (provRS.next()) {
                    id = provRS.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("\n GENERATED KEY of " + update + " : " + id);
        return id;
    }


}
