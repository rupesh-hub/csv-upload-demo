package com.csvupload.controller;

import com.csvupload.entity.User;
import com.csvupload.entity.UserUploadLog;
import com.csvupload.service.CsvUploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    private final CsvUploadService userService;

    public UserController(CsvUploadService userService) {
        this.userService = userService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {

        UserUploadLog upload = new UserUploadLog();
        upload.setUploadBy("rupesh dulal");
        upload.setSpreedsheetName(file.getOriginalFilename());
        upload.setSpreedSheetFormat(file.getName());
        upload.setSpreedSheetSubmitDate(new Date());

        try {
            userService.save(file, upload);
            return ResponseEntity.status(HttpStatus.OK).body("Uploaded the file successfully: " + file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not upload the file: " + file.getOriginalFilename() + "!");
        }

    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllTutorials() {
        try {
            List<User> tutorials = userService.getAllTutorials();

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
