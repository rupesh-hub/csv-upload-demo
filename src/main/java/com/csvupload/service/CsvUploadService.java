package com.csvupload.service;

import com.csvupload.entity.User;
import com.csvupload.entity.UserUploadLog;
import com.csvupload.repository.CsvUserLogRepository;
import com.csvupload.repository.UserRepository;
import com.csvupload.util.CsvUploadHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvUploadService {

    private final UserRepository userRepository;
    private final CsvUserLogRepository userLogRepository;

    public void save(MultipartFile file, UserUploadLog upload) {
        final List<User> users = CsvUploadHelper.processUpload(file);
        upload.setUserList(users);

        userLogRepository.save(upload);
    }

    public List<User> getAllTutorials() {
        return userRepository.findAll();
    }

}
