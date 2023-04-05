package com.csvupload.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "csv_upload_log")
public class UserUploadLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uploadBy;
    private String spreedsheetName;
    private String spreedSheetFormat;
    private Date spreedSheetSubmitDate;
    private String processingStatus;
    private String processingNotes;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_log_id", referencedColumnName = "id")
    private List<User> userList = new ArrayList<>();

}
