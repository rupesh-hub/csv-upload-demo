package com.csvupload.service;

import com.csvupload.entity.UploadEntity;
import com.csvupload.entity.User;
import com.csvupload.repository.UserRepository;
import com.csvupload.util.CsvUploadHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CsvUploadService {

    private final UserRepository userRepository;

    public CsvUploadService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(MultipartFile file, UploadEntity upload) {
        userRepository.saveAll(CsvUploadHelper.processUpload(file, upload));
    }

    public List<User> getAllTutorials() {
        return userRepository.findAll();
    }

}
