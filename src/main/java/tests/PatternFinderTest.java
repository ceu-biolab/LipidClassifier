package tests;

import Lipids.PatternFinder;

import java.util.List;
import java.util.Set;
import Lipids.PatternFinder;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

    /**
     *
     * @author alberto.gildelafuent
     */
    public class PatternFinderTest {

        public PatternFinderTest() {
        }


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
}
