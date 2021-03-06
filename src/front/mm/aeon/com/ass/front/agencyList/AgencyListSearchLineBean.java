/***********************************************************************
 * $Date: 2018-07-31 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.front.agencyList;

import java.io.Serializable;
import java.sql.Timestamp;

public class AgencyListSearchLineBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7076362798229650598L;

    private int agencyId;

    private String agencyName;

    private String location;

    private String address;

    private String mobileTeamId;

    private String mobileTeam;

    private String nonMobileTeamId;

    private String nonMobileTeam;
    /*
     * to check disable or enable in view
     */
    private int isValid;

    private String remark;

    private String outletName;

    private String createdBy;

    private Timestamp createdDate;

    private String updatedBy;

    private Timestamp agencyUptDate;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
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
