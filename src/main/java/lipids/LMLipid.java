package lipids;

import java.util.ArrayList;
import java.util.List;

public class LMLipid {
    protected String lmId;
    protected String name;
    protected String abbreviation;
    protected String synonym;
    protected String category;
    protected String mainClass;
    protected String subClass;
    protected String classLevel4;
    protected String inChi;
    protected String inchiKey;
    protected String smiles;
    protected String formula;
    protected String mass;
    protected String lipidType;
    protected List<String> chains = new ArrayList<>();

    public LMLipid(String lmId, String name, String abbreviation, String synonym, String category, String mainClass, String subClass,
                   String classLevel4, String mass, String formula, String smiles, String inChi, String inchiKey, String lipidType, List<String> chains) {
        this.lmId = lmId;
        this.name = name;
        this.abbreviation = abbreviation;
        this.synonym = synonym;
        this.category = category;
        this.mainClass = mainClass;
        this.subClass = subClass;
        this.classLevel4 = classLevel4;
        this.mass = mass;
        this.formula = formula;
        this.smiles = smiles;
        this.inchiKey = inchiKey;
        this.inChi = inChi;
        this.lipidType = lipidType;
        this.chains = chains;

    }




    public String getLmId() {
        return this.lmId;
    }

    public void setLmId(String lmId) {
        this.lmId = lmId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getSynonym() {
        return this.synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMainClass() {
        return this.mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public String getSubClass() {
        return this.subClass;
    }

    public void setSubClass(String subClass) {
        this.subClass = subClass;
    }

    public String getClassLevel4() {
        return this.classLevel4;
    }

    public void setClassLevel4(String classLevel4) {
        this.classLevel4 = classLevel4;
    }


    public String getInChi() {
        return this.inChi;
    }

    public void setInChi(String inChi) {
        this.inChi = inChi;
    }

    public String getinchiKey() {
        return this.inchiKey;
    }

    public void setinchiKey(String inchiKey) {
        this.inchiKey = inchiKey;
    }


    public String getSmiles() {
        return this.smiles;
    }

    public void setSmiles(String smiles) {
        this.smiles = smiles;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getMass() {
        return this.mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getLipidType() {
        return this.lipidType;
    }

    public void setLipidType(String lipidType) {
        this.lipidType = lipidType;
    }

    public List<String> getChains() {
        return this.chains;
    }

    public void setChains(List<String> chains) {
        this.chains = chains;
    }


    @Override
    public String toString() {
        return "\nLipid ID: " + this.lmId + ", Name: " + this.name + ", Abbreviation: " + this.abbreviation +
                ", Category: " + this.category + ", Main Class: " + this.mainClass + ", Subclass: " + this.subClass
                + ", Class level 4: " + this.classLevel4 + ", Inchi Key: " + this.inchiKey + ", Chains: " + this.chains;
    }


}