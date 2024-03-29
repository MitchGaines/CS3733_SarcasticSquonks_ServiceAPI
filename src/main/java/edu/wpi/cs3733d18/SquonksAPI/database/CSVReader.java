package edu.wpi.cs3733d18.SquonksAPI.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Parses a CSV file and writes to a table.
 * @author: Joseph Turcotte
 * @version %I%, %G%
 * Date: March 24, 2018
 * Sources: https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
 * https://examples.javacodegeeks.com/core-java/writeread-csv-files-in-java-example/
 *
 */
public class CSVReader {

    /**
     * Stores null for the buffered reader.
     */
    private BufferedReader buffered_reader = null; // reader for file
    /**
     * Stores the edu.wpi.cs3733d18.teamS.database.
     */
    private IDatabase database;

    /**
     * Stores storage class.
     */
    private Storage storage;

    /**
     * Constructor for a CSVReader.
     * @param database the edu.wpi.cs3733d18.teamS.database that holds the tables.
     */
    public CSVReader(IDatabase database) {
        this.database = database;
        storage = Storage.getInstance();
    }

    /**
     * Loads a CSV file and populates a JavaDB table at the start of the program.
     * @param csv_file   the name of the csv file to read from.
     * @param table_name the name of the table to create.
     */
    public void readCSVFile(String csv_file, String table_name) {

        // parsing variables
        String line; // modified by each line read in
        String csv_delimiter = ","; // delimiter for CSV files

        try {
            buffered_reader = new BufferedReader(new FileReader(csv_file));

            // skip first line (column names)
            line = buffered_reader.readLine();

            // check the table type, then loop through each line of the CSV file
            switch (table_name) {
                case "NODES":
                    while ((line = buffered_reader.readLine()) != null) {

                        // split line using comma delimiter
                        String[] values = line.split(csv_delimiter);

                        // add quotes so SQL won't complain
                        values[0] = database.addQuotes(values[0]);
                        values[3] = database.addQuotes(values[3]);
                        values[4] = database.addQuotes(values[4]);
                        values[5] = database.addQuotes(values[5]);
                        values[6] = database.addQuotes(values[6]);
                        values[7] = database.addQuotes(values[7]);
                        values[8] = database.addQuotes(values[8]);

                        // add values to table
                        database.insert(table_name, values);
                    }
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // close the buffered reader, if any
            if (buffered_reader != null) {
                try {
                    buffered_reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //System.out.println("File read successfully!");
    }
}

