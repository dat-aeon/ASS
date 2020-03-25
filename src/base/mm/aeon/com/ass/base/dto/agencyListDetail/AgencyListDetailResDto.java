/***********************************************************************
 * $Date: 2018-08-15 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.dto.agencyListDetail;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class AgencyListDetailResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 7737136798473893947L;

    private String agencyName;

    private int location;

    private String address;

    private String remark;

    private String createdBy;

    private Timestamp createdDate;

    private String updatedBy;

    private Timestamp updatedDate;

    private String mobileId;

    private String mobileTeam;

    private String nonMobileId;

    private String nonMobileTeam;

    private Boolean isAeon;

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getMobileId() {
        return mobileId;
    }

    public void setMobileId(String mobileId) {
        this.mobileId = mobileId;
    }

    public String getMobileTeam() {
        return mobileTeam;
    }

    public void setMobileTeam(String mobileteam) {
        this.mobileTeam = mobileteam;
    }

    public String getNonMobileId() {
        return nonMobileId;
    }

    public void setNonMobileId(String nonMobileId) {
        this.nonMobileId = nonMobileId;
    }

    public String getNonMobileTeam() {
        return nonMobileTeam;
    }

    public void setNonMobile_team(String nonMobileTeam) {
        this.nonMobileTeam = nonMobileTeam;
    }

    public Boolean getIsAeon() {
        return isAeon;
    }

    public void setIsAeon(Boolean isAeon) {
        this.isAeon = isAeon;
    }

}
