/***********************************************************************
 * $Date: 2018-07-31 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.dto.agencyListSearch;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class AgencyListSearchResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 7737136798473893947L;

    private int agencyId;

    private String agencyName;

    private int location;

    private String address;

    private String remark;

    /*
     * to check disable or enable in view
     */
    private int isValid;

    private String mobileTeamId;

    private String mobileTeam;

    private String nonMobileTeamId;

    private String nonMobileTeam;

    private String createdBy;

    private Timestamp createdDate;

    private String updatedBy;

    private Timestamp agencyUptDate;

    private Timestamp teamAgencyUptDate;

    private Timestamp disabledDate;

    private Boolean isAeon;

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

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

    public String getMobileTeamId() {
        return mobileTeamId;
    }

    public void setMobileTeamId(String mobileTeamId) {
        this.mobileTeamId = mobileTeamId;
    }

    public String getNonMobileTeamId() {
        return nonMobileTeamId;
    }

    public void setNonMobileTeamId(String nonMobileTeamId) {
        this.nonMobileTeamId = nonMobileTeamId;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
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

    public Timestamp getAgencyUptDate() {
        return agencyUptDate;
    }

    public void setAgencyUptDate(Timestamp agencyUptDate) {
        this.agencyUptDate = agencyUptDate;
    }

    public Timestamp getTeamAgencyUptDate() {
        return teamAgencyUptDate;
    }

    public void setTeamAgencyUptDate(Timestamp teamAgencyUptDate) {
        this.teamAgencyUptDate = teamAgencyUptDate;
    }

    public Timestamp getDisabledDate() {
        return disabledDate;
    }

    public void setDisabledDate(Timestamp disabledDate) {
        this.disabledDate = disabledDate;
    }

    public String getMobileTeam() {
        return mobileTeam;
    }

    public void setMobileTeam(String mobileTeam) {
        this.mobileTeam = mobileTeam;
    }

    public String getNonMobileTeam() {
        return nonMobileTeam;
    }

    public void setNonMobileTeam(String nonMobileTeam) {
        this.nonMobileTeam = nonMobileTeam;
    }

    public Boolean getIsAeon() {
        return isAeon;
    }

    public void setIsAeon(Boolean isAeon) {
        this.isAeon = isAeon;
    }

}
