package tests;

import ceu.biolab.*;
import lipids.PatternFinder;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

    /**
     *
     * @author alberto.gildelafuent
     */
    public class PatternFinderTest{

        /**
         * Test of searchPCID method, of class PatternFinder.
         */
        @Test
        public void testSearchPCID() {
            System.out.println("searchPCID");
            String pubchemLink = "http://pubchem.ncbi.nlm.nih.gov/compound/123";
            Integer expResult = 123;
            Integer result = PatternFinder.searchPCID(pubchemLink);
            assertEquals(expResult, result);
            pubchemLink = "https://pubchem.ncbi.nlm.nih.gov/compound/123";
            result = PatternFinder.searchPCID(pubchemLink);
            assertEquals(expResult, result);
        }

        /**
         * Test of getGenusSpecieFromGenusSpecieAndSubspecies method, of class PatternFinder.
         */
        @Test
        public void testGetSpeciesFromSpeciesAndCells() throws Exception {
            System.out.println("getSpeciesFromSpeciesAndCells");
            String species = "Aspergillus ochraceus";
            String expResult = "Aspergillus ochraceus";
            String result = PatternFinder.getGenusSpecieFromGenusSpecieAndSubspecies(species);
            assertEquals(expResult, result);
            species = "Aspergillus terreus NRRL 11156";
            expResult = "Aspergillus terreus";
            result = PatternFinder.getGenusSpecieFromGenusSpecieAndSubspecies(species);
            assertEquals(expResult, result);
        }

        /**
         * Test of getSubspecieFromGenusSpecieAndSubspecie method, of class PatternFinder.
         */
        @Test
        public void testGetCellsFromSpeciesAndCells() throws Exception {
            System.out.println("getCellsFromSpeciesAndCells");
            String species = "Aspergillus ochraceus";
            String expResult = null;
            String result = PatternFinder.getSubspecieFromGenusSpecieAndSubspecie(species);
            assertEquals(expResult, result);
            species = "Aspergillus terreus NRRL 11156";
            expResult = "NRRL 11156";
            result = PatternFinder.getSubspecieFromGenusSpecieAndSubspecie(species);
            assertEquals(expResult, result);
        }




        /**
         * Test of getLipidType method, of class PatternFinder.
         */
        @Test
        void getLipidType() {
            String name1 = "FA(20:5)";
            String expectedLipidType1 = "FA(";
            String actualLipidType1 = PatternFinder.getLipidType(name1);
            assertEquals(expectedLipidType1, actualLipidType1, "Lipid type extraction failed 1");

            String name2 = "PE(18:2)";
            String expectedLipidType2 = "PE(";
            String actualLipidType2 = PatternFinder.getLipidType(name2);
            assertEquals(expectedLipidType2, actualLipidType2, "Lipid type extraction failed 2");

            String name3 = "PC(16:0/18:1(9Z))";
            String expectedLipidType3 = "PC(";
            String actualLipidType3 = PatternFinder.getLipidType(name3);
            assertEquals(expectedLipidType3, actualLipidType3, "Lipid type extraction failed");

            String name4 = "FAHFA 16:1/32O(FA 32:1)";
            String expectedLipidType4 = "FAHFA(";
            String actualLipidType4 = PatternFinder.getLipidType(name4);
            assertEquals(expectedLipidType4, actualLipidType4, "Lipid type extraction failed");
        }

        /**
         * Test of getLipidTypeAbbreviation method, of class PatternFinder.
         */
        @Test
        void getLipidTypeAbbreviation() {
            String name1 = "PIP2[3',4'](17:0/20:4(5Z,8Z,11Z,14Z))";
            String expectedLipidType1 = "PIP2(";
            String actualLipidType1 = PatternFinder.getLipidTypeAbbreviation(name1);
            assertEquals(expectedLipidType1, actualLipidType1, "Lipid type extraction failed 1");

            String name2 = "Man-Cer(d16:0(15Me(3OH))/14:0(13Me))";
            String expectedLipidType2 = "Man-Cer(d";
            String actualLipidType2 = PatternFinder.getLipidTypeAbbreviation(name2);
            assertEquals(expectedLipidType2, actualLipidType2, "Lipid type extraction failed 2");


            String name3 = "MG(O-8:0)";
            String expectedLipidType3 = "MG(O-";
            String actualLipidType3 = PatternFinder.getLipidTypeAbbreviation(name3);
            assertEquals(expectedLipidType3, actualLipidType3, "Lipid type extraction failed 3");
        }




        /**
         * Test of getLipidType method, of class PatternFinder.
         */
        @Test
        void getNumberOfCarbons() {
            String name1 = "FA(20:5)";
            String expectedNCarbons1 = "20";
            String actualNCarbons1 = String.valueOf(PatternFinder.getNumberOfCarbons(name1));
            assertEquals(expectedNCarbons1, actualNCarbons1, "Number Carbons extraction failed");

            String name2 = "PE(18:2)";
            String expectedNCarbons2 = "18";
            String actualNCarbons2 = String.valueOf(PatternFinder.getNumberOfCarbons(name2));
            assertEquals(expectedNCarbons2, actualNCarbons2, "Number Carbons extraction failed");

            String name3 = "PC(16:0/18:1(9Z))";
            String expectedNCarbons3 = "34";
            String actualNCarbons3 = String.valueOf(PatternFinder.getNumberOfCarbons(name3));
            assertEquals(expectedNCarbons3, actualNCarbons3, "Number Carbons extraction failed");
        }


        /**
         * Test of getLipidType method, of class PatternFinder.
         */
        @Test
        void getNumberOfDoubleBonds() {
            String name1 = "FA(20:5)";
            String expectedNDoubleBonds1 = "5";
            String actualNDoubleBonds1 = String.valueOf(PatternFinder.getNumberOfDoubleBonds(name1));
            assertEquals(expectedNDoubleBonds1, actualNDoubleBonds1, "Number Double Bonds extraction failed");

            String name2 = "PE(18:2)";
            String expectedNDoubleBonds2 = "2";
            String actualNDoubleBonds2 = String.valueOf(PatternFinder.getNumberOfDoubleBonds(name2));
            assertEquals(expectedNDoubleBonds2, actualNDoubleBonds2, "Number Double Bonds extraction failed");

            String name3 = "PC(16:0/18:1(9Z))";
            String expectedNDoubleBonds3 = "1";
            String actualNDoubleBonds3 = String.valueOf(PatternFinder.getNumberOfDoubleBonds(name3));
            assertEquals(expectedNDoubleBonds3, actualNDoubleBonds3, "Number Double Bonds extraction failed");
        }


        /**
         * Test of getLipidType method, of class PatternFinder.
         */
        @Test
        void getListOfChains() {
            String input = "PE(P-16:0(OH)/20:4)";
            List<String> expectedChains = List.of("16:0(OH)", "20:4");
            List<String> actualChains = PatternFinder.getListOfChains(input);
            assertEquals(expectedChains, actualChains, "Chains should match for a single lipid");

            String input2 = "";
            List<String> actualChains2 = PatternFinder.getListOfChains(input2);
            assertTrue(actualChains2.isEmpty(), "Chains should be empty for an empty input");

            String input3 = "PE";
            List<String> actualChains3 = PatternFinder.getListOfChains(input3);
            assertTrue(actualChains3.isEmpty(), "Chains should be empty when no chain information is present");

            String input4 = "PC(16:0/18:1(OH))/PE(18:0/18:2)";
            List<String> expectedChains4 = List.of("16:0", "18:1(OH)", "18:0", "18:2");
            List<String> actualChains4 = PatternFinder.getListOfChains(input4);
            assertEquals(expectedChains4, actualChains4, "Chains should match for multiple lipids");


            String input5 = "PE(16:0/18:1(9Z))-15-isoLG hydroxylactam";
            List<String> expectedChains5 = List.of("16:0", "18:1");
            List<String> actualChains5 = PatternFinder.getListOfChains(input5);
            assertEquals(expectedChains5, actualChains5, "Chains should match for multiple lipids");
        }


        /**
         * Test of getLipidTypeFromAbbreviation method, of class PatternFinder.
         */
        @Test
        void getLipidTypeFromAbbreviation() {
            String abbreviation1 = "Hex(3)-HexNAc(2)-NeuAc-Cer 42:2;O2";
            String expectedLipidType1 = "Cer(";
            String actualLipidType1 = PatternFinder.getLipidTypeFromAbbreviation(abbreviation1);
            assertEquals(expectedLipidType1, actualLipidType1, "Lipid type extraction failed for abbreviation1");

            String abbreviation2 = "FA 20:5;O3";
            String expectedLipidType2 = "FA(";
            String actualLipidType2 = PatternFinder.getLipidTypeFromAbbreviation(abbreviation2);
            assertEquals(expectedLipidType2, actualLipidType2, "Lipid type extraction failed for abbreviation2");

            String abbreviation3 = "PE 18:2";
            String expectedLipidType3 = "PE(";
            String actualLipidType3 = PatternFinder.getLipidTypeFromAbbreviation(abbreviation3);
            assertEquals(expectedLipidType3, actualLipidType3, "Lipid type extraction failed for abbreviation3");

            String abbreviation4 = "PC 16:0/18:1(9Z)";
            String expectedLipidType4 = "PC(";
            String actualLipidType4 = PatternFinder.getLipidTypeFromAbbreviation(abbreviation4);
            assertEquals(expectedLipidType4, actualLipidType4, "Lipid type extraction failed for abbreviation4");

            String abbreviation5 = "MG O-8:0";
            String expectedLipidType5 = "MG(O-";
            String actualLipidType5 = PatternFinder.getLipidTypeFromAbbreviation(abbreviation5);
            assertEquals(expectedLipidType5, actualLipidType5, "Lipid type extraction failed for abbreviation5");
        }


        /**
         * Test of getNumberOfCarbonsFromAbbreviation method, of class PatternFinder.
         */
        @Test
        void getNumberOfCarbonsFromAbbreviation() {
            String abbreviation1 = "Hex(3)-HexNAc(2)-NeuAc-Cer 42:2;O2";
            String expectedNumCarbons1 = "42";
            String actualNumCarbons1 = String.valueOf(PatternFinder.getNumberOfCarbonsFromAbbreviation(abbreviation1));
            assertEquals(expectedNumCarbons1, actualNumCarbons1, "Number of carbons extraction failed for abbreviation1");

            String abbreviation2 = "FA 20:5;O3";
            String expectedNumCarbons2 = "20";
            String actualNumCarbons2 = String.valueOf(PatternFinder.getNumberOfCarbonsFromAbbreviation(abbreviation2));
            assertEquals(expectedNumCarbons2, actualNumCarbons2, "Number of carbons extraction failed for abbreviation2");

            String abbreviation3 = "PE 18:2";
            String expectedNumCarbons3 = "18";
            String actualNumCarbons3 = String.valueOf(PatternFinder.getNumberOfCarbonsFromAbbreviation(abbreviation3));
            assertEquals(expectedNumCarbons3, actualNumCarbons3, "Number of carbons extraction failed for abbreviation3");

            String abbreviation4 = "PC 16:0/18:1(9Z)";
            String expectedNumCarbons4 = "34";
            String actualNumCarbons4 = String.valueOf(PatternFinder.getNumberOfCarbonsFromAbbreviation(abbreviation4));
            assertEquals(expectedNumCarbons4, actualNumCarbons4, "Number of carbons extraction failed for abbreviation4");
        }


        /**
         * Test of getNumberOfDoubleBondsFromAbbreviation method, of class PatternFinder.
         */
        @Test
        void getNumberOfDoubleBondsFromAbbreviation() {
            String abbreviation1 = "Hex(3)-HexNAc(2)-NeuAc-Cer 42:2;O2";
            String expectedNumDoubleBonds1 = "2";
            String actualNumDoubleBonds1 = String.valueOf(PatternFinder.getNumberOfDoubleBondsFromAbbreviation(abbreviation1));
            assertEquals(expectedNumDoubleBonds1, actualNumDoubleBonds1, "Number of double bonds extraction failed for abbreviation1");

            String abbreviation2 = "FA 20:5;O3";
            String expectedNumDoubleBonds2 = "5";
            String actualNumDoubleBonds2 = String.valueOf(PatternFinder.getNumberOfDoubleBondsFromAbbreviation(abbreviation2));
            assertEquals(expectedNumDoubleBonds2, actualNumDoubleBonds2, "Number of double bonds extraction failed for abbreviation2");

            String abbreviation3 = "PE 18:2";
            String expectedNumDoubleBonds3 = "2";
            String actualNumDoubleBonds3 = String.valueOf(PatternFinder.getNumberOfDoubleBondsFromAbbreviation(abbreviation3));
            assertEquals(expectedNumDoubleBonds3, actualNumDoubleBonds3, "Number of double bonds extraction failed for abbreviation3");

            String abbreviation4 = "PC 16:0/18:1(9Z)";
            String expectedNumDoubleBonds4 = "1";
            String actualNumDoubleBonds4 = String.valueOf(PatternFinder.getNumberOfDoubleBondsFromAbbreviation(abbreviation4));
            assertEquals(expectedNumDoubleBonds4, actualNumDoubleBonds4, "Number of double bonds extraction failed for abbreviation4");
        }

        @Test
        void getLipidTypeFromSynonym() {
            String name1 = "TG(55:7); TG(13:0_20:2_22:5)";
            String expectedLipidType1 = "TG(";
            String actualLipidType1 = PatternFinder.getLipidTypeFromSynonym(name1);
            assertEquals(expectedLipidType1, actualLipidType1, "Lipid type extraction failed 1");

            String name2 = "TG(55:7)";
            String expectedLipidType2 = "TG(";
            String actualLipidType2 = PatternFinder.getLipidTypeFromSynonym(name2);
            assertEquals(expectedLipidType2, actualLipidType2, "Lipid type extraction failed 2");

            String input = "MGDG-O(12-oxo-18:2(11,15Z)-cyclo[9S,13S]/16:3(7Z,10Z,13Z))";
            String expectedLipidType = "MGDG-O(";
            String  actualLipidtype = PatternFinder.getLipidTypeFromSynonym(input);
            assertEquals(expectedLipidType, actualLipidtype, "No match");

            String input2 = "MG(O-8:0)";
            String expectedLipidType3 = "MG(O-";
            String  actualLipidtype3 = PatternFinder.getLipidTypeFromSynonym(input2);
            assertEquals(expectedLipidType3, actualLipidtype3, "No match");

            String input3 = "MG(P-8:0)";
            String expectedLipidType4 = "MG(P-";
            String  actualLipidtype4 = PatternFinder.getLipidTypeFromSynonym(input3);
            assertEquals(expectedLipidType4, actualLipidtype4, "No match");
        }


        @Test
        void getNumberCarbonsFromSynonym() {
            String synonym = "TG(48:2); TG(15:0_15:0_18:2)";
            String expectedNumCarbons1 = "48";
            String actualNumCarbons1 = String.valueOf(PatternFinder.getNumberCarbonsFromSynonym(synonym));
            assertEquals(expectedNumCarbons1, actualNumCarbons1, "Number of carbons extraction failed for synonym");
        }

        @Test
        void getNumberDoubleBondsFromSynonym() {
            String synonym = "TG(48:2); TG(15:0_15:0_18:2)";
            String expectedNumDoubleBonds1 = "2";
            String actualNumDoubleBonds1 = String.valueOf(PatternFinder.getNumberDoubleBondsFromSynonym(synonym));
            assertEquals(expectedNumDoubleBonds1, actualNumDoubleBonds1, "Number of carbons extraction failed for synonym");
        }

        @Test
        void getListChainsFromSynonym() {
            String input = "TG(55:7); TG(13:0_20:2_22:5)";
            List<String> expectedChains = List.of("13:0", "20:2", "22:5");
            List<String> actualChains = PatternFinder.getListChainsFromSynonym(input);
            assertEquals(expectedChains, actualChains, "Chains should match for a single lipid");

            String input2 = "TG(55:7)";
            List<String> expectedChains2 = List.of("55:7");
            List<String> actualChains2 = PatternFinder.getListChainsFromSynonym(input2);
            assertEquals(expectedChains2, actualChains2, "Chains should match for a single lipid");

            String input3 = "16:0MX18:2";
            List<String> expectedChains3 = List.of("16:0", "18:2");
            List<String> actualChains3 = PatternFinder.getListChainsFromSynonym(input3);
            assertEquals(expectedChains3, actualChains3, "Chains should match for a single lipid");
        }


        @Test
        void testGetListChainsFromNonStandardFormat() {
            String input3 = "16:0MX18:2";
            List<String> expectedChains3 = List.of("16:0", "18:2");
            List<String> actualChains3 = PatternFinder.getListChainsFromNonStandardFormat(input3);
            assertEquals(expectedChains3, actualChains3, "Chains should match for a single lipid");
        }





        @Test
        void getListChainsFromAbbrev() {
            String input = "TG 55:7";
            List<String> expectedChains = List.of("55:7");
            List<String> actualChains = PatternFinder.getListChainsFromAbbreviation(input);
            assertEquals(expectedChains, actualChains, "Chains should match for a single lipid");
        }


        @Test
        void getListChainsFromFAHFA() {
            String input = "FAHFA 16:1/32O(FA 32:1)";
            List<String> expectedChains = List.of("16:1", "FA 32:1");
            List<String> actualChains = PatternFinder.getListChainsFromFAHFA(input);
            assertEquals(expectedChains, actualChains, "Chains should match for a single lipid");
        }



        @Test
        void test() throws IncorrectAdduct, NotFoundElement, IncorrectFormula {
            String formulaString = "C20H44OAs";
            Formula formula = Formula.formulaFromStringHill(formulaString, null, null);
            FormulaType formulaType = formula.getType();
            String expected = formulaType.name();
            String actual = "ALL";
            assertEquals(expected, actual, "not match");
        }


    }



