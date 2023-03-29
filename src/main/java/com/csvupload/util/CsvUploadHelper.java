package com.csvupload.util;

import com.csvupload.entity.UploadEntity;
import com.csvupload.entity.User;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CsvUploadHelper {

    private CsvUploadHelper(){
        throw new IllegalStateException("Utility class");
    }

    public static String TYPE = "text/csv";

    private static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }


    public static List<User> processUpload(MultipartFile file, UploadEntity uploadEntity) {
        List<User> users = new ArrayList<>();

        try {
            InputStreamReader reader = new InputStreamReader(file.getInputStream());
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
            List<String[]> rows = csvReader.readAll();

            rows.forEach(row -> {
                    users.add(new User(row[1], row[2], row[3], row[4]));
            });

            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
