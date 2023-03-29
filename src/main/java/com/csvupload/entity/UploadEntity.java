package com.csvupload.entity;

import java.util.Date;

public class UploadEntity {

    private String uploadBy;
    private String spreedsheetName,
    private String spreedSheetFormat;
    private Date spreedSheetSubmitDate;
    private String processingStatus;
    private String processingNotes;

    public String getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(String uploadBy) {
        this.uploadBy = uploadBy;
    }

    public String getSpreedsheetName() {
        return spreedsheetName;
    }

    public void setSpreedsheetName(String spreedsheetName) {
        this.spreedsheetName = spreedsheetName;
    }

    public String getSpreedSheetFormat() {
        return spreedSheetFormat;
    }

    public void setSpreedSheetFormat(String spreedSheetFormat) {
        this.spreedSheetFormat = spreedSheetFormat;
    }

    public Date getSpreedSheetSubmitDate() {
        return spreedSheetSubmitDate;
    }

    public void setSpreedSheetSubmitDate(Date spreedSheetSubmitDate) {
        this.spreedSheetSubmitDate = spreedSheetSubmitDate;
    }

    public String getProcessingStatus() {
        return processingStatus;
    }

    public void setProcessingStatus(String processingStatus) {
        this.processingStatus = processingStatus;
    }

    public String getProcessingNotes() {
        return processingNotes;
    }

    public void setProcessingNotes(String processingNotes) {
        this.processingNotes = processingNotes;
    }
}
