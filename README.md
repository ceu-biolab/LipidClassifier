# Lipid Classification Project

## Description

This project reads lipids from structures.sdf (downloaded from LipidMaps) to classify lipids on 4 levels: category, main class, subclass, and class level 4. The classifications are updated in a MySQL database (compounds), along with additional characterizations and compound information.

## Requirements

Before executing the project, ensure you have the following:

- MySQL installed and configured.

- Database: compounds


Required Files:

- structures.sdf (from LipidMaps)

- queries.txt (contains SQL queries to execute)

- LipidClassification.csv (classification reference file)


Ensure that the following files are in the resources directory:

- queries.txt

- LipidClassification.csv


## Installation

* Clone the repository:

git clone <repository_url>
cd <project_directory>


* Configure MySQL Credentials:

- Update the database connection settings in the project to match your MySQL credentials.


## Execution

* Execute SQL Queries:

- Run the queries in queries.txt to finalize database updates.


* Run the code to classify lipids:

The script reads structures.sdf, extracts lipid types using regular expressions, and classifies them using LipidClassification.csv.

It updates the MySQL database with:

- Lipid classifications

- Lipid characterizations (lipid_type_RT and lipid_type_fragmentation)

- Complete information for existing compounds

- New compounds that are missing in the database


* Notes

Ensure MySQL is running and accessible.

Modify the database credentials in the script before running.

The LipidClassification.csv file serves as the reference for lipid classification.



## Contact

Alejandra O'Shea Fernández - alejandra.osheafernandez@usp.ceu.es Project link: https://github.com/ceu-biolab/LipidClassifier

