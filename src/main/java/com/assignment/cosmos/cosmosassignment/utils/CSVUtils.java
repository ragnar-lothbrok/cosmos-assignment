package com.assignment.cosmos.cosmosassignment.utils;

import com.assignment.cosmos.cosmosassignment.dtos.Product;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CSVUtils {

    public static List<Product> readDataLineByLine(String file)
    {
        List<Product> products = new ArrayList<>();
        Set<String> categories = new HashSet<>();
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                Product product = null;
                try {
                    products.add(new Product(nextRecord[0], nextRecord[1], nextRecord[2], Integer.parseInt(nextRecord[3]), Integer.parseInt(nextRecord[4]), nextRecord[5], nextRecord[6]));
                }catch(Exception e1) {
                    //Ignoring Exception
                }
            }
        }
        catch (Exception e) {
            //Ignoring Exception
        }
        return products;
    }
}
