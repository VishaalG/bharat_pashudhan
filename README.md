# bharat_pashudhan
Automate Bharat Pashudhan portal using java selenium

## Steps to validate before running any flow
1. Change the credentials in the DataProvider.java class for right login credentials.
2. Change the file path in the DataProvider.java class for right file path to run. The file can contain numeric or string animal id in the first column. Make sure the numeric value should not be in scientific notation E.g. 1.0E+2212
3. Change the village name or any other specific inputs for the script to run.
4. Now uncomment the flow which you are trying to run in BharatPasudhan.java main method and comment all other flows. Commenting the flow will grey out the line.
5. Finally, change the driver to your preferred browser chrome or firefox.


### Steps to pull latest code in windows machine
1. Go to project repository in file explorer. You will see src, bin, target folders here. 
2. Right click on an open space here. Click Git bash here.
3. Now do the following commands. ```git stash``` - This will stash the local changes and commits.
4. Nod do ```git pull``` - This will pull the latest code changes.
5. Refresh the project in eclipse and run. 

