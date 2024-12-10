package lipids;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PatternFinder {

    int start = 0;

    /**
     * Function searchWithReplacement
     *
     * @param content String of content
     * @param pattern pattern to search
     * @param stringToRemove stringToremove in content
     * @return a String with content which reaches the pattern
     */
    public static String searchWithReplacement(String content, String pattern, String stringToRemove) {
        Pattern p = Pattern.compile(pattern, Pattern.DOTALL);

        Matcher m = p.matcher(content);

        String response = "";

        while (m.find()) {
            response += m.group().replaceAll(stringToRemove, "") + "\n";
        }

        return response;
    }

    /**
     * Function searchWithoutReplacement
     *
     * @param content String of content
     * @param pattern pattern to search
     * @return a String with content which reaches the pattern
     */
    public static String searchWithoutReplacement(String content, String pattern) {
        Pattern p = Pattern.compile(pattern, Pattern.DOTALL);

        Matcher m = p.matcher(content);

        String respuesta = "";

        while (m.find()) {
            respuesta += m.group() + "\n";
        }

        return respuesta;
    }

    /**
     * Function searchFirstOcurrence
     *
     * @param content String of content
     * @param pattern pattern to search
     * @return a String with content which reaches the pattern
     */
    public static String searchFirstOcurrence(String content, String pattern) {
        Pattern p = Pattern.compile(pattern, Pattern.DOTALL);

        Matcher m = p.matcher(content);

        String respuesta = "";
        if (m.find()) {
            respuesta = m.group();
        }

        return respuesta;
    }

    /**
     * Function searchSecondOcurrence
     *
     * @param content String of content
     * @param pattern pattern to search
     * @return a String with content which reaches the pattern
     */
    public static String searchSecondOcurrence(String content, String pattern) {
        Pattern p = Pattern.compile(pattern, Pattern.DOTALL);

        Matcher m = p.matcher(content);

        String respuesta = "";
        if (m.find()) {
            if (m.find()) {
                respuesta = m.group();
            }
        }

        return respuesta;
    }

    /**
     * Function searchListWithReplacement. Find the pattern in content and
     * removes the String stringToRemove in the results
     *
     * @param content String of content
     * @param pattern pattern to search
     * @param stringToRemove stringToremove in content
     * @return arraylist with content which reaches the pattern
     */
    public static List<String> searchListWithReplacement(String content, String pattern, String stringToRemove) {
        Pattern p = Pattern.compile(pattern, Pattern.DOTALL);

        Matcher m = p.matcher(content);

        List<String> respuesta = new LinkedList<>();

        while (m.find()) {
            respuesta.add(m.group().replaceAll(stringToRemove, ""));

        }

        return respuesta;
    }

    /**
     * Function searchListWithoutReplacement Find the pattern in content.
     *
     * @param content String of content
     * @param pattern pattern to search
     * @return arraylist with content which reaches the pattern
     */
    public static List<String> searchListWithoutReplacement(String content, String pattern) {
        Pattern p = Pattern.compile(pattern, Pattern.DOTALL);

        Matcher m = p.matcher(content);

        List<String> respuesta = new LinkedList<>();

        while (m.find()) {
            String ocurrence = m.group();
            respuesta.add(ocurrence);
        }

        return respuesta;
    }

    /**
     * Function searchListWithoutReplacement Find the pattern in content.
     *
     * @param content String of content
     * @param pattern pattern to search
     * @return arraylist with content which reaches the pattern
     */
    public static Set<String> searchSetWithoutReplacement(String content, String pattern) {
        Pattern p = Pattern.compile(pattern, Pattern.DOTALL);

        Matcher m = p.matcher(content);

        Set<String> compiledSet = new TreeSet();

        while (m.find()) {
            compiledSet.add(m.group());
        }

        return compiledSet;
    }

    /**
     * Function searchInDifferentCalls
     *
     * @param content StringBuilder of content
     * @param pattern pattern to search
     * @param stringToRemove stringToremove in content
     * @param result StringBuilder to save the result
     * @return arraylist with content which reaches the pattern
     */
    // What does this function do exactly?
    // Why delete at the beginning all the results?
    // I think it is only to reuse the stringBuilder
    public boolean searchInDifferentCalls(StringBuilder content, String pattern, String stringToRemove, StringBuilder result) {
        result.delete(0, result.length());
        Pattern patternContextoDato = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher matcherContextoDato = patternContextoDato.matcher(content);

        boolean found = matcherContextoDato.find(start);
        if (found) {
            start = matcherContextoDato.end();

            String localResult = matcherContextoDato.group().replaceAll(stringToRemove, "");

            localResult = localResult.replaceAll("\"", "''");
            localResult = localResult.replaceAll("\n", "");
            result.append(localResult);
        }
        return found;
    }

    /**
     * Method that returns the first double in content. If there is no double in
     * content, the method returns 0
     * @param content
     * @return
     */
    public static double searchDouble(String content) {
        double result = 0.0d;
        Pattern p = Pattern.compile("(\\d+(?:\\.\\d+))");
        Matcher m = p.matcher(content);
        if (m.find()) {
            result = Double.parseDouble(m.group(1));
//            System.out.println(result);
        }

        return result;
    }

    /**
     * Method that returns pc_id from a pubchem link or null otherwise
     *
     * @param pubchemLink of the type:
     * http://pubchem.ncbi.nlm.nih.gov/compound/150816 or
     * https://pubchem.ncbi.nlm.nih.gov/compound/100005
     * https://pubchem.ncbi.nlm.nih.gov/compound/16067346#section=Top
     * @return
     */
    public static Integer searchPCID(String pubchemLink) {
        Integer result = null;
        String pattern1 = "http://pubchem.ncbi.nlm.nih.gov/compound/";
        String pattern2 = "https://pubchem.ncbi.nlm.nih.gov/compound/";
        String suffix = "#(.)*";
        pubchemLink = pubchemLink.replaceAll(suffix, "");
        if (pubchemLink.contains(pattern1)) {
            String pc_id_string = pubchemLink.replaceAll(pattern1, "");
            try {
                result = Integer.parseInt(pc_id_string);
            } catch (NumberFormatException nfe) {
                System.out.println("Trying to obtain pubchemlink of: " + pubchemLink + " and failed");
            }
        } else if (pubchemLink.contains(pattern2)) {
            String pc_id_string = pubchemLink.replaceAll(pattern2, "");
            try {
                result = Integer.parseInt(pc_id_string);
            } catch (NumberFormatException nfe) {
                System.out.println("Trying to obtain pubchemlink of: " + pubchemLink);
            }
        }

        return result;
    }

    public static String getGenusSpecieFromGenusSpecieAndSubspecies(String species) {

        String[] wordSpecies = species.split("\\s");

        if (wordSpecies.length < 3) {
            return species;
        } else {
            String finalSpecie = wordSpecies[0] + " " + wordSpecies[1];
            return finalSpecie;
        }
    }

    public static String getSubspecieFromGenusSpecieAndSubspecie(String species) {
        String[] wordSpecies = species.split("\\s");

        if (wordSpecies.length < 3) {
            return null;
        } else {
            String finalCells = "";
            finalCells = wordSpecies[2];
            for (int indexSpecie = 3; indexSpecie < wordSpecies.length; indexSpecie++) {
                finalCells = finalCells + " " + wordSpecies[indexSpecie];
            }
            return finalCells.substring(0, finalCells.length());
        }
    }

    public static String getGenusFromGenusAndSpecie(String genusAndSpecie) throws Exception {
        String[] wordSpecies = genusAndSpecie.split("\\s");

        if (wordSpecies.length > 2) {
            throw new Exception("GenusAndSpecie cannot have more than 1 space");
        } else if (wordSpecies.length == 2) {
            return wordSpecies[0];
        } else {
            return genusAndSpecie;
        }
    }

    public static String getSpecieFromGenusAndSpecie(String genusAndSpecie) throws Exception {

        String[] wordSpecies = genusAndSpecie.split("\\s");

        if (wordSpecies.length > 2) {
            throw new Exception("GenusAndSpecie cannot have more than 1 space");
        } else if (wordSpecies.length == 2) {
            return wordSpecies[1];
        } else {
            return "";
        }
    }

    /**
     * Read javadoc for getLipidType
     * @param common_name
     * @return
     */
    public static String getLipidTypeFromName(String common_name) {
        String lipidType = "";
        if (!common_name.equalsIgnoreCase("")) {
            lipidType = getLipidType(common_name);
        }
        return lipidType;
    }


    /**
     * Read javadoc for getNumberOfCarbons
     * @param common_name
     * @return
     */
    public static int getNumberOfCarbonsFromName(String common_name) {
        int numCarbons = 0;
        String lipidType = getLipidTypeFromName(common_name);
        if (!lipidType.equalsIgnoreCase("")) {
            numCarbons = getNumberOfCarbons(common_name);
        }
        return numCarbons;
    } //! test!!


    /**
     * Read javadoc for getNumberOfDoubleBonds
     *
     * @param common_name
     * @return
     */
    public static int getNumberOfDoubleBondsFromName(String common_name) {
        int numDoubleBonds = 0;
        String lipidType = getLipidTypeFromName(common_name);
        if (!lipidType.equalsIgnoreCase("")) {
            numDoubleBonds = getNumberOfDoubleBonds(common_name);
        }
        return numDoubleBonds;
    } //! test!!!

    /**
     *
     * @param content
     * @return
     */
    public static String getPrimaryHMDBIDFromHTML(String content) { //! javadoc?
        String hmdbId = "";
        String patternHMDBID = "HMDB[\\d]{7}";
        Pattern p = Pattern.compile(patternHMDBID);
        Matcher m = p.matcher(content);
        if (m.find()) {
            hmdbId = m.group();
        } else {
            System.out.println("HMDB PRIMARY ID NOT FOUND");
            return "NOT FOUND";
        }
        return hmdbId;
    } //! ??

    /**
     * Method for get the Lipid Type from Lipid Nomenclature as
     * PE(22:4(7Z,10Z,13Z,16Z)/22:6(4Z,7Z,10Z,13Z,16Z,19Z))
     * <LIPID_TYPE>([<CONNECTION_TYPE>-]<CARBONS>:<DOUBLE_BONDS>[(<DB_POSITIONs>)]/<CARBONS>:<DOUBLE_BONDS>[(<DB_POSITIONs>)])
     *
     * Where: Expressions within [] are optional
     * <LIPID_TYPE> is defined with two capital letters
     * <CONNECTION_TYPE> is defined with a LETTER and "-". It may appear or not.
     * <CARBONS> and <DOUBLE_BONDS> are integers and they are separated with ":"
     * <DB_POSITION> is between "(" and ")" and may appear or not. After
     * <DOUBLE_BONDS> or <DB_POSITIONS> (if it appears), a new expression with
     * same pattern may appear 0 or more times.
     *
     * @param content
     * @return
     */
    public static String getLipidType(String content) {
        String lipidType = "";

        // Adapt the pattern to allow for optional spaces and ensure it includes "("
        String patternLipidType = "[a-zA-Z]{2,5}([a-zA-Z0-9]*)\\s*\\(?";
        patternLipidType += "|[a-zA-Z]{2,5}([a-zA-Z0-9]*)\\s*[0-9]{1,2}:";
        Pattern p = Pattern.compile(patternLipidType);
        Matcher m = p.matcher(content);

        if (m.find()) {
            String lipidTypePartial = m.group();

            // Now extract the lipid type up to the first parenthesis
            String patternLipidTypeName = "[a-zA-Z]{2,5}([a-zA-Z0-9]*)\\s*";
            Pattern p2 = Pattern.compile(patternLipidTypeName);
            Matcher m2 = p2.matcher(lipidTypePartial);

            if (m2.find()) {
                lipidType = m2.group().trim();  // Trim to clean up any trailing spaces
            }
        }

        // Always append the "(" to the lipid type
        if (!lipidType.isEmpty()) {
            lipidType += "(";
        }

        return lipidType;
    }


    /**
     * Method for get the number of Carbons from Lipid Nomenclature as
     * PE(22:4(7Z,10Z,13Z,16Z)/22:6(4Z,7Z,10Z,13Z,16Z,19Z))
     * <LIPID_TYPE>([<CONNECTION_TYPE>-]<CARBONS>:<DOUBLE_BONDS>[(<DB_POSITIONs>)]/<CARBONS>:<DOUBLE_BONDS>[(<DB_POSITIONs>)])
     *
     * Where: Expressions within [] are optional
     * <LIPID_TYPE> is defined with two capital letters
     * <CONNECTION_TYPE> is defined with a LETTER and "-". It may appear or not.
     * <CARBONS> and <DOUBLE_BONDS> are integers and they are separated with ":"
     * <DB_POSITION> is between "(" and ")" and may appear or not. After
     * <DOUBLE_BONDS> or <DB_POSITIONS> (if it appears), a new expression with
     * same pattern may appear 0 or more times.
     *
     * @param content
     * @return
     */
    public static int getNumberOfCarbons(String content) {
        int numCarbons = 0;
        // System.out.println("PROCESSING NUMBER OF CARBONS OF: " + content);
        Pattern p = Pattern.compile("[1-9][0-9]*:");
        Matcher m = p.matcher(content);
        int i = 1;
        while (m.find()) {
            String carbons = m.group();
            carbons = carbons.substring(0, carbons.length() - 1);
            //System.out.println("PARTIAL NUMBER CARBONS: " + carbons);
            numCarbons += Integer.parseInt(carbons);
        }
        //System.out.println("TOTAL NUMBER CARBONS: " + numCarbons);
        return numCarbons;
    }

    /**
     * Method for get the number of Double Bonds from Lipid Nomenclature as
     * PE(22:4(7Z,10Z,13Z,16Z)/22:6(4Z,7Z,10Z,13Z,16Z,19Z))
     * <LIPID_TYPE>([<CONNECTION_TYPE>-]<CARBONS>:<DOUBLE_BONDS>[(<DB_POSITIONs>)]/<CARBONS>:<DOUBLE_BONDS>[(<DB_POSITIONs>)])
     *
     * Where: Expressions within [] are optional
     * <LIPID_TYPE> is defined with two capital letters
     * <CONNECTION_TYPE> is defined with a LETTER and "-". It may appear or not.
     * <CARBONS> and <DOUBLE_BONDS> are integers and they are separated with ":"
     * <DB_POSITION> is between "(" and ")" and may appear or not. After
     * <DOUBLE_BONDS> or <DB_POSITIONS> (if it appears), a new expression with
     * same pattern may appear 0 or more times.
     *
     * @param content
     * @return
     */
    public static int getNumberOfDoubleBonds(String content) {
        int numDoubleBonds = 0;
        // System.out.println("PROCESSING NUMBER OF DOUBLE BONDS OF: " + content);
        Pattern p = Pattern.compile(":[0-9]*");
        Matcher m = p.matcher(content);
        int i = 1;
        while (m.find()) {
            String doubleBonds = m.group();
            doubleBonds = doubleBonds.substring(1, doubleBonds.length());
            //System.out.println("PARTIAL NUMBER DOUBLE BONDS: " + doubleBonds);
            numDoubleBonds += Integer.parseInt(doubleBonds);
        }
        //System.out.println("TOTAL NUMBER DOUBLEBONDS: " + numDoubleBonds);
        return numDoubleBonds;
    }

    /**
     * Method for get the oxidation type from Lipid Abbreviature as
     * PE(22:4/22:6(OH))
     * <LIPID_TYPE>([<CONNECTION_TYPE>-]<CARBONS>:<DOUBLE_BONDS>[(OXIDATION)]/<CARBONS>:<DOUBLE_BONDS>[(OXIDATION)])
     *
     * Where: Expressions within [] are optional
     * <LIPID_TYPE> is defined with two capital letters
     * <CONNECTION_TYPE> is defined with a LETTER and "-". It may appear or not.
     * <CARBONS> and <DOUBLE_BONDS> are integers and they are separated with ":"
     * <OXIDATION> is between "(" and ")" and may appear or not. After
     * <DOUBLE_BONDS> or <OXIDATION> (if it appears), a new expression with same
     * pattern may appear 0 or more times.
     *
     * @param content the string that contains the abbreviation
     * @return the oxidation from the abbreviation
     */
    public static String getOxidationFromAbbrev(String content) {
        Pattern p = Pattern.compile("\\([a-zA-Z]+\\)");
        Matcher m = p.matcher(content);
        String oxidationType = "";
        if (m.find()) {
            oxidationType = m.group();
            oxidationType = oxidationType.substring(1, oxidationType.length() - 1);
        }
        if ("Ke".equals(oxidationType)) {
            //oxidationType="O";
        }
        if ("COH".equals(oxidationType)) {
            oxidationType = "CHO";
        }
        //System.out.println("TOTAL NUMBER DOUBLEBONDS: " + numDoubleBonds);
        return oxidationType;
    } //! test !!

    /**
     * Get the list of the chains from the abbreviated form of the lipids:
     * PE(P-16:0(OH)/20:4) -> 16:0(OH) and 20:4
     *
     * @param content
     * @return
     */
    public static List<String> getListOfChains(String content) {
        List<String> chains = new LinkedList<>();
        //System.out.println("PROCESSING NUMBER OF DOUBLE BONDS OF: " + content);
        String lipidType = getLipidTypeFromName(content);
        if (!lipidType.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("[1-9][0-9]*:[0-9](\\([a-zA-Z]+\\))*");
            Matcher m = p.matcher(content);
            while (m.find()) {
                String chain = m.group();
                chains.add(chain);
            }
        }
        //System.out.println("TOTAL NUMBER DOUBLEBONDS: " + numDoubleBonds);
        return chains;
    }

    public static String getCodeLMJSON(String sentence) {
        String code = "";
        //System.out.println("PROCESSING NUMBER OF DOUBLE BONDS OF: " + content);
        Pattern p = Pattern.compile("\\[[a-zA-Z0-9]*\\]");
        Matcher m = p.matcher(sentence);
        while (m.find()) {
            code = m.group();
            code = code.substring(1, code.length() - 1);
        }
        //System.out.println("TOTAL NUMBER DOUBLEBONDS: " + numDoubleBonds);
        return code;
    } //! test!!



    public static boolean debugFormula(String content) {
        boolean found = true;
        /*
        Mg2(Al,Fe)3Si4O10(OH)Na2O
        C19H15NaB(Na)
        (C12H16O15S2R2)n"
        C23H16O6.(C11H14ClN5)2"
        C9H8O4.C2H4NO2.CO3.Al.Mg.2OH
        C28H46N5O29P5R2(R1)(C5H8O6PR)n
        C11H16N5O8P(C5H8O6PR)n(C5H8O6PR)n
         */
        Pattern p;
        Matcher m;
        /*
        String pattern = "[(](.*?)[)]\\d*";
        List<String> splitContent = new ArrayList<>();
        p = Pattern.compile(pattern);
        m = p.matcher(content);
        while (m.find()) {
            String element = m.group();
            element = element.replaceAll("(", "");
            element = element.replaceAll(")", "");
            splitContent.add(element);
        }
         */

        p = Pattern.compile("[A-Z][a-z]?\\d*");
        String element;
        // DELETE all . in order to check every element
        String rest = content.replaceAll("\\.", "");

        System.out.println("\nrest: " + rest);
        m = p.matcher(rest);
        while (m.find()) {
            element = m.group();
            String r = searchFirstOcurrence(element, "[R]\\d*");
            String x = searchFirstOcurrence(element, "[X]\\d*");
            System.out.println("\nR: " + r + "  X: " + x);
            if (!r.equals("") || !x.equals("")) {
                System.out.println("\n FIND R or X: " + element);
                //return false;
            } else {
                System.out.println("element: " + element);
            }
            rest = rest.replaceFirst(element, "");
        }
        p = Pattern.compile("[a-z]");
        m = p.matcher(rest);
        System.out.println("\n REST: " + rest);
        while (m.find()) //if(m.find())
        {
            System.out.println("\n FIND: " + m.group());
            //return false;
        }
        return found;
    } //! test!!



    /**
     * Return the list of ancestors from the HTML. It follow the pattern of the
     * CHEMONTID:{7} numbers
     *
     * @param htmlString
     * @return
     */
    public static List<String> getNodesAncestorsCLASSYFIRE(String htmlString) {

        List<String> ancestors = new LinkedList<String>();
        Pattern p = Pattern.compile("CHEMONTID:[0-9]{7}");
        Matcher m = p.matcher(htmlString);
        while (m.find()) {
            String nodeId = m.group();
            ancestors.add(nodeId);
        }

        return ancestors;
    }

    /**
     * analyze the SMILES and return an array with two values which contains the
     * charge (array[0]) (neutral - 0 ,positive - 1 and negative -2 ) and the
     * number of charges (array[1]
     *
     * @param Smiles
     * @return
     */
    public static int[] getChargeFromSmiles(String Smiles) {
        int[] arrayCharges = new int[2];
        Pattern p = Pattern.compile("(\\+)([0-9]*)\\]");
        Matcher m = p.matcher(Smiles);
        int positiveCharges = 0;
        int negativeCharges = 0;
        while (m.find()) {
            String positiveCharge = m.group();
            // when length is 2 it means + or - ]]
            if (positiveCharge.length() == 2) {
                positiveCharges = positiveCharges + 1;
            } else {
                positiveCharges = positiveCharges
                        + Integer.parseInt(positiveCharge.substring(1, positiveCharge.length() - 1));
            }
        }

        p = Pattern.compile("(-)([0-9]*)\\]");
        m = p.matcher(Smiles);
        while (m.find()) {
            String negativeCharge = m.group();
            if (negativeCharge.length() == 2) {
                negativeCharges = negativeCharges + 1;
            } else {
                negativeCharges = negativeCharges
                        + Integer.parseInt(negativeCharge.substring(1, negativeCharge.length() - 1));
            }
        }

        int totalCharges = positiveCharges - negativeCharges;
        if (totalCharges == 0) {
            arrayCharges[0] = 0;
        } else if (totalCharges > 0) {
            arrayCharges[0] = 1;
        } else if (totalCharges < 0) {
            arrayCharges[0] = 2;
        }
        arrayCharges[1] = Math.abs(totalCharges);

        return arrayCharges;
    } //! test ????


    /**
     * Method to get the Lipid Type from Lipid Nomenclature as
     * PE(22:4(7Z,10Z,13Z,16Z)/22:6(4Z,7Z,10Z,13Z,16Z,19Z))
     * <LIPID_TYPE>([<CONNECTION_TYPE>-]<CARBONS>:<DOUBLE_BONDS>[(<DB_POSITIONs>)]/<CARBONS>:<DOUBLE_BONDS>[(<DB_POSITIONs>)])
     * Where: Expressions within [] are optional
     * <LIPID_TYPE> is defined with two capital letters
     * <CONNECTION_TYPE> is defined with a LETTER and "-". It may appear or not.
     * <CARBONS> and <DOUBLE_BONDS> are integers and they are separated with ":"
     * <DB_POSITION> is between "(" and ")" and may appear or not. After
     * <DOUBLE_BONDS> or <DB_POSITIONS> (if it appears), a new expression with
     * same pattern may appear 0 or more times.
     *
     * @param abbreviation the complete expression as a String
     * @return the lipid type as a String
     */
    public static String getLipidTypeAbbreviation(String abbreviation) {
        String lipidType = "";

        // Remove text inside square brackets as before
        abbreviation = abbreviation.replaceAll("\\[[^\\]]*\\]", "");

        // Regex pattern to match lipid type, allowing for optional prefixes like O- or P-
        String patternLipidType = "[a-zA-Z]{2,3}([a-zA-Z0-9])*(-([a-zA-Z0-9])+)?\\(?(O-|P-)?([a-zA-Z](-)?)?[0-9]+:";
        patternLipidType = patternLipidType + "|[a-zA-Z]{2,3}([a-zA-Z0-9])*(-([a-zA-Z0-9])+)?\\(?(O-|P-)?C[0-9]+";

        // Compile the pattern
        Pattern p = Pattern.compile(patternLipidType);
        Matcher m = p.matcher(abbreviation);

        if (m.find()) {
            String lipidTypePartial = m.group();

            // Update to also capture lipid type names with O- or P- prefixes
            String patternLipidTypeName = "[a-zA-Z0-9]{2,3}([a-zA-Z0-9])*(-([a-zA-Z0-9])+)?\\(?(O-|P-)?([a-zA-Z](-)?)?"; // Adapted for O- and P-
            Pattern p2 = Pattern.compile(patternLipidTypeName);
            Matcher m2 = p2.matcher(lipidTypePartial);

            if (m2.find()) {
                lipidType = m2.group();
            }
        }

        return lipidType;
    }



    //getLipidType, getChains, getCarbons y getDoubleBonds from Abbreviation:

    /**
     * Method to get the Lipid Type from Abbreviation as
     * > <ABBREVIATION>
     * TG 62:6
     * []<LIPID_TYPE> <CARBONS>:<DOUBLE_BONDS>[additional information] (it can be: O3; or -name)
     * Where: Expressions within [] are optional
     * <LIPID_TYPE> is defined with two capital letters or 3 letters (the first one always capital letter)
     * <CARBONS> and <DOUBLE_BONDS> are integers and they are separated with ":"
     * @param abbreviation the complete abbreviation obtained from the file structures.sdf as a String
     * @return Lipid Type as a String
     */
    public static String getLipidTypeFromAbbreviation(String abbreviation) {
        String lipidType = "";

        // Pattern to capture lipid type with optional prefixes like O- or P- and optional formats
        String patternLipidTypeAbbreviation = "\\b([a-zA-Z]+)(?:\\s+(O-|P-))?\\s*(\\d+)?:(\\d+)?";
        Pattern p = Pattern.compile(patternLipidTypeAbbreviation);
        Matcher m = p.matcher(abbreviation);

        if (m.find()) {
            // we extract main lipid type
            String mainLipidType = m.group(1);          // >> "Cer", "FA", "PE"
            //we may have a "prefix" which will be O- or P- so we need to include that in the lipidtype
            String optionalPrefix = m.group(2) != null ? m.group(2) : "";

            // format the lipid type with optional prefix
            lipidType = mainLipidType + "(" + optionalPrefix;
        }

        return lipidType;
    }



    /**
     * Method to get the Lipid Type from Abbreviation as
     * > <ABBREVIATION>
     * TG 62:6
     * []<LIPID_TYPE> <CARBONS>:<DOUBLE_BONDS>[additional information] (it can be: O3; or -name)
     * Where: Expressions within [] are optional
     * <LIPID_TYPE> is defined with two capital letters or 3 letters (the first one always capital letter)
     * <CARBONS> and <DOUBLE_BONDS> are integers and they are separated with ":"
     * @param abbreviation the complete abbreviation obtained from the file structures.sdf as a String
     * @return number of carbons as an int
     */
    public static int getNumberOfCarbonsFromAbbreviation(String abbreviation) {
        int numCarbons = 0;
        Pattern p = Pattern.compile("[1-9][0-9]*:");
        Matcher m = p.matcher(abbreviation);
        while (m.find()) {
            String carbons = m.group();
            carbons = carbons.substring(0, carbons.length() - 1);
            numCarbons += Integer.parseInt(carbons);
        }
        return numCarbons;
    }

    /**
     * Method for get the Lipid Type from Abbreviation as
     * > <ABBREVIATION>
     * TG 62:6
     * []<LIPID_TYPE> <CARBONS>:<DOUBLE_BONDS>[additional information] (it can be: O3; or -name)
     * Where: Expressions within [] are optional
     * <LIPID_TYPE> is defined with two capital letters or 3 letters (the first one always capital letter)
     * <CARBONS> and <DOUBLE_BONDS> are integers and they are separated with ":"
     * @param abbreviation the complete abbreviation obtained from the file structures.sdf as a String
     * @return number of double bonds as an int
     */
    public static int getNumberOfDoubleBondsFromAbbreviation(String abbreviation) {
        int numDoubleBonds = 0;
        Pattern p = Pattern.compile(":[0-9]*");
        Matcher m = p.matcher(abbreviation);
        while (m.find()) {
            String doubleBonds = m.group();
            doubleBonds = doubleBonds.substring(1, doubleBonds.length());
            numDoubleBonds += Integer.parseInt(doubleBonds);
        }
        return numDoubleBonds;
    }



    /**
     * This method reads the chains from the abbreviation
     * TG 16:0
     * @param abbreviation the abbreviation containing a chain
     * @return list with the chain obtained from the abbreviation
     */
    public static List<String> getListChainsFromAbbreviation(String abbreviation) {
        List<String> chainsFromAbbrev = new LinkedList<>();

        // Identify chains by pattern such as "16:0", "18:1", etc.
        Pattern p = Pattern.compile("\\b[1-9][0-9]*:[0-9]\\b");
        Matcher m = p.matcher(abbreviation);

        while (m.find()) {
            String chain = m.group();
            chainsFromAbbrev.add(chain);
        }

        return chainsFromAbbrev;
    }




    /**
     * Method for get the Lipid Type from synonym as
     * <SYNONYM>
     * TG(48:2); TG(15:0_15:0_18:2)
     * <LIPID_TYPE>(NUMBER_CARBONS:NUMBER_DOUBLE_BONDS); <LIPID_TYPE>(<FIRST_CHAIN_NUMBER_C>:<FIRST_NUMBER_DB>_<SECOND_CHAIN_NUMBER_C>:<SECOND_NUMBER_DB>_[]..)
     * @param synonyms the synonym read from the file: structures.sdf
     * @return Lipid Type as a String
     */
    public static String getLipidTypeFromSynonym(String synonyms) {
        String lipidType = "";

        // Pattern for lipid types with optional O- or other structural prefixes within parentheses
        String patternLipidTypeSyn = "^[a-zA-Z]{2,5}-?[a-zA-Z]?\\(?(O-|P-)?";
        Pattern p = Pattern.compile(patternLipidTypeSyn);
        Matcher m = p.matcher(synonyms);

        if (m.find()) {
            lipidType = m.group();
        }
        return lipidType;
    }




    /**
     * Method for get the number of carbons from synonym as
     * <SYNONYM>
     * TG(48:2); TG(15:0_15:0_18:2)
     * <LIPID_TYPE>(NUMBER_CARBONS:NUMBER_DOUBLE_BONDS); <LIPID_TYPE>(<FIRST_CHAIN_NUMBER_C>:<FIRST_NUMBER_DB>_<SECOND_CHAIN_NUMBER_C>:<SECOND_NUMBER_DB>_[]..)
     * @param synonyms the synonym read from the file: structures.sdf
     * @return number of carbons as an integer
     */
    public static Integer getNumberCarbonsFromSynonym(String synonyms) {
        int totalCarbons = 0;

        String[] lipidSegments = synonyms.split(";\\s*");
        String lipidType = getLipidTypeFromName(lipidSegments[1]);

        if (!lipidType.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("([1-9][0-9]*):[0-9](\\([a-zA-Z]+\\))*");
            Matcher m = p.matcher(lipidSegments[1]);

            while (m.find()) {
                String chain = m.group();
                int numCarbons = Integer.parseInt(chain.split(":")[0]);
                totalCarbons += numCarbons;
            }
        }
        return totalCarbons;
    }


    /**
     * Method for get the number of double bonds from synonym as
     * <SYNONYM>
     * TG(48:2); TG(15:0_15:0_18:2)
     * <LIPID_TYPE>(NUMBER_CARBONS:NUMBER_DOUBLE_BONDS); <LIPID_TYPE>(<FIRST_CHAIN_NUMBER_C>:<FIRST_NUMBER_DB>_<SECOND_CHAIN_NUMBER_C>:<SECOND_NUMBER_DB>_[]..)
     * @param synonyms the synonym read from the file: structures.sdf
     * @return number of double bonds  as an integer
     */
    public static Integer getNumberDoubleBondsFromSynonym(String synonyms) {
        int doubleBonds = 0;

        String[] lipidSegments = synonyms.split(";\\s*");
        String lipidType = getLipidTypeFromName(lipidSegments[1]);

        if (!lipidType.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("([1-9][0-9]*):[0-9](\\([a-zA-Z]+\\))*");
            Matcher m = p.matcher(lipidSegments[1]);

            while (m.find()) {
                String chain = m.group();
                doubleBonds = Integer.parseInt(chain.split(":")[1]);
            }
        }
        return doubleBonds;
    }


    /**
     * This method gets the different chains of a lipid from the synonym of the file structures.sdf
     * @param synonyms String that contains the lipid type and its chains
     * @return A list containing the chains of the lipid
     */
    public static List<String> getListChainsFromSynonym(String synonyms) {
        List<String> chainsFromSyn = new LinkedList<>();

        // Split the input by ';' to handle multiple segments if present
        String[] lipidSegments = synonyms.split(";\\s*");

        // Patterns to identify standard and non-standard formats
        Pattern standardPattern = Pattern.compile("[1-9][0-9]*:[0-9](\\([a-zA-Z]+\\))*");
        Pattern nonStandardPattern = Pattern.compile("[1-9][0-9]*:[0-9]+");

        // We will only add chains if they are found in segments with underscores
        boolean extractedFromUnderscore = false;

        // Iterate through each segment to extract chains
        for (String segment : lipidSegments) {
            // Check if the segment has an underscore, indicating multiple chains
            if (segment.contains("_")) {
                // Remove any prefixes (like "TG(") and split by underscores
                String cleanedSegment = segment.replaceAll(".*\\(", "").replaceAll("\\).*", "");
                String[] chains = cleanedSegment.split("_");

                // Add each chain to the list
                chainsFromSyn.addAll(Arrays.asList(chains));
                extractedFromUnderscore = true;
                break; // Stop after finding underscore-based chains
            }
        }

        // If we didn't extract from underscore-based format, handle the segments normally
        if (!extractedFromUnderscore) {
            for (String segment : lipidSegments) {
                Matcher standardMatcher = standardPattern.matcher(segment);

                while (standardMatcher.find()) {
                    String chain = standardMatcher.group();
                    chainsFromSyn.add(chain);
                }
            }
        }

        return chainsFromSyn;
    }


    public static List<String> getListChainsFromNonStandardFormat(String input) {
        List<String> chains = new LinkedList<>();

        // Adjusted regular expression to find chains in the format "number:number"
        // even if they are surrounded by non-standard characters.
        Pattern pattern = Pattern.compile("[1-9][0-9]*:[0-9]+");
        Matcher matcher = pattern.matcher(input);

        // Extract all matching patterns
        while (matcher.find()) {
            chains.add(matcher.group());
        }

        return chains;
    }



    /**
     * This method gets the different chains of a FAHFA from the synonym of the file structures.sdf
     * @param synonym  String that contains the FAHFA and its chains
     * @return the chains of the FAHFA as a List
     */
    public static List<String> getListChainsFromFAHFA(String synonym) {
        List<String> chainsFromSyn = new LinkedList<>();

        String[] lipidSegments = synonym.split("\\s+");

        if (lipidSegments.length > 1) {
            String firstPart = lipidSegments[1];
            String[] chains = firstPart.split("/");

            for (String chain : chains) {
                if (!chain.contains("(")) {
                    chainsFromSyn.add(chain);
                }
            }

            Pattern p = Pattern.compile("\\(([^)]+)\\)");
            Matcher m = p.matcher(synonym);

            if (m.find()) {
                String insideParentheses = m.group(1).trim();
                chainsFromSyn.add(insideParentheses);
            }
        }
        return chainsFromSyn;
    }


}



