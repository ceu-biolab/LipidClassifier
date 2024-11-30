package utilities;

import database.Database;

import java.sql.SQLException;
import java.sql.Statement;


public class Utilities {

    public static void executeNewIDUWithEx(String query) throws SQLException {
        Statement provStatement;
        provStatement = Database.getConnection().createStatement();
        provStatement.executeUpdate(query);
        provStatement.close();
    }


    public static String escapeSQL(String value) {
        value = value.replace("\\", "\\\\");
        value = value.replaceAll("\"", "\\'");
        value = value.replaceAll("'", "\\'");
        value = value.replace("\'", "\\'");

        return value;
    }


    /**
     *
     * @param inputChemAlphabet
     * @return the formula type as int according to the chemAlphabet or 4 (ALL) by default (No deuterium)
     */
    public static int getIntChemAlphabet(FormulaType formulaType) {
        int intChemAlphabet = MAPCHEMALPHABET.getOrDefault(formulaType, 4);
        return intChemAlphabet;
    }




}
