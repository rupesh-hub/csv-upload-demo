package com.csvupload.util;

import com.csvupload.entity.User;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.stream.Stream;

public class CsvUploadHelper {

    private CsvUploadHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static String TYPE = "text/csv";

    private static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }


    public static List<User> processUpload(MultipartFile file) {
        List<User> users = new ArrayList<>();
        try {

            if (CsvUploadHelper.isHeaderDuplicate(new InputStreamReader(file.getInputStream())))
                throw new Exception("duplicate headers exists");

            System.out.println(CsvUploadHelper.countMissingValuesByRow(new InputStreamReader(file.getInputStream())).size());

            List<String> noContents = CsvUploadHelper.checkFieldContent(new InputStreamReader(file.getInputStream()));
            if (noContents.size() > 0) //CHECK IS THERE DATA IN ANY FIELD OR NOT
                noContents.forEach(System.out::println);

            users.addAll(CsvUploadHelper.readCsv(new InputStreamReader(file.getInputStream())));//READ ALL CONTENT FROM CSV
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public static boolean isHeaderDuplicate(Reader reader) throws IOException, CsvValidationException {
        Set<String> uniqueHeaders = new HashSet<>();
        Set<String> duplicateHeaders = new HashSet<>();

        //Add duplicate headers to duplicateHeaders set
        Stream.of(CsvUploadHelper.readHeaders(reader))
                .filter(header -> !uniqueHeaders.add(header))
                .forEach(duplicateHeaders::add);

        if (!duplicateHeaders.isEmpty())
            return true;

        return false;
    }


    public static List<String> checkFieldContent(Reader reader) throws IOException {
        List<String> hasNoContents = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(reader)) {
            String[] row;
            while ((row = csvReader.readNext()) != null) {
                for (int i = 0; i < row.length; i++) {
                    if (row[i].isEmpty()) {
                        hasNoContents.add("Field content not found at row " + (csvReader.getLinesRead() + 1) + ", column " + (i + 1));
                    }
                }
            }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }

        return hasNoContents;
    }

    public static List<Integer> countMissingValuesByRow(Reader reader) throws IOException {
        List<Integer> rowsWithMissingValues = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(reader)) {
            String[] header = csvReader.readNext(); // assuming first row is header

            String[] row;
            int rowCount = 1; // start counting rows from 1
            while ((row = csvReader.readNext()) != null) {
                rowCount++;
                for (int i = 0; i < row.length; i++) {
                    if (row[i].isEmpty()) {
                        rowsWithMissingValues.add(rowCount);
                        break; // break out of loop if a missing value is found
                    }
                }
            }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }

        return rowsWithMissingValues;
    }

    private static List<User> readCsv(Reader reader) {
        List<User> users = new ArrayList<>();

        try {
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
            List<String[]> rows = csvReader.readAll();

            rows.forEach(row -> {
                User user = new User(row[1], row[2], row[3], row[4]);
                user.setAddress(new User.Address(row[5], row[6]));
                users.add(user);
            });

            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static String[] readHeaders(Reader reader) {
        try (CSVReader csvReader = new CSVReader(reader)) {
            return csvReader.readNext();
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
            return new String[]{e.getLocalizedMessage()};
        }
    }
}
