package lipids;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class LipidType {
    protected String lipidTypeRT;
    protected String lipidTypeFragmentation;
    protected Set<LipidMapsClassification> lmClassifications = new HashSet<>();


    public LipidType(String lipidTypeRT, String lipidTypeFragmentation, Set<LipidMapsClassification> lmClassifications) {
        this.lipidTypeRT = lipidTypeRT;
        this.lipidTypeFragmentation = lipidTypeFragmentation;
        this.lmClassifications = lmClassifications;
    }


    public LipidType(String lipidTypeRT, String lipidTypeFragmentation) {
        this.lipidTypeRT = lipidTypeRT;
        this.lipidTypeFragmentation = lipidTypeFragmentation;
    }

    public LipidType() {
        this.lipidTypeRT = "";
        this.lipidTypeFragmentation = "";
    }


    public String getLipidTypeRT() {
        return this.lipidTypeRT;
    }

    public void setLipidTypeRT(String lipidTypeRT) {
        this.lipidTypeRT = lipidTypeRT;
    }

    public String getLipidTypeFragmentation() {
        return this.lipidTypeFragmentation;
    }

    public void setLipidTypeFragmentation(String lipidTypeFragmentation) {
        this.lipidTypeFragmentation = lipidTypeFragmentation;
    }

    public Set<LipidMapsClassification> getLmClassifications() {
        return this.lmClassifications;
    }

    public void setLmClassifications(Set<LipidMapsClassification> lmClassifications) {
        this.lmClassifications = lmClassifications;
    }

    public void addClassification(LipidMapsClassification classification) {
        this.lmClassifications.add(classification);
    }


    /**
     * This method builds the Lipid Type Map having as key the lipid type of CMM's database and as value a Lipid Type (cpmposed of the
     * RT lipid type, fragmentation lipid type and a LMClassification (category, main class, subclass, class level 4)
     * @param file file .csv containing the lipid types: database, RT, fragmentation; and classification
     * @return the map as a String and Lipid Type
     */
    public static Map<String, LipidType> buildLipidTypeMap(String file){
        Map<String, LipidType> lipidTypeMap = new HashMap<>();
        //System.out.println("Lipid Types from DB: " + lipidTypesFromDB);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            // Skip header
            reader.readLine();

            // Iterate through CSV file
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length < 7) {
                    continue;
                }

                String head = data[0].trim();
                String headTailBonding = data[1].trim();
                String lipidTypeName = data[2].trim();
                String category = data[3].trim();
                String mainClass = data[4].trim();
                String subClass = data[5].trim();
                String classLevel4 = data[6].trim();


                LipidMapsClassification lmClassification = new LipidMapsClassification(category, mainClass, subClass, classLevel4);

                // Check if lipidTypeMap already contains the lipid type
                LipidType lipidType = lipidTypeMap.get(lipidTypeName);
                if (lipidType == null) {
                    lipidType = new LipidType(head, headTailBonding, new HashSet<>());
                }

                // Add the LMClassification to the lipidType's set
                lipidType.getLmClassifications().add(lmClassification);

                // Update the map
                lipidTypeMap.put(lipidTypeName, lipidType);
            }

            reader.close();
            return lipidTypeMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lipidTypeMap;
    }
}
