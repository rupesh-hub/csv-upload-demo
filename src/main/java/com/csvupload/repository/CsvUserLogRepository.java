package com.csvupload.repository;

import com.csvupload.entity.UserUploadLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsvUserLogRepository extends JpaRepository<UserUploadLog, Long> {
}
