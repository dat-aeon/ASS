/**************************************************************************
 * $Date : 2018-08-17 $
 * $Author : Thar Pyae $
 * $Rev : 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.judgementStatusSelectList;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class JudgementStatusSelectListResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -8085767478555013466L;
    
    private Timestamp uploadDate;
    
    private Timestamp approveDate;
    
    private Timestamp applyDate;
    
    private String agencyName; 
    
    private String outletName;
    
    private String userName;
    
    private String applicationNo;
    
    private int status;
    
    private Timestamp firstApplyDate;

    private Timestamp judgementDate;

    private String customerName;
    
    private String nrc;


    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Timestamp approveDate) {
        this.approveDate = approveDate;
    }

    public Timestamp getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Timestamp applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getFirstApplyDate() {
        return firstApplyDate;
    }

    public void setFirstApplyDate(Timestamp firstApplyDate) {
        this.firstApplyDate = firstApplyDate;
    }

    public Timestamp getJudgementDate() {
        return judgementDate;
    }

    public void setJudgementDate(Timestamp judgementDate) {
        this.judgementDate = judgementDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }    
    
}