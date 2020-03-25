/***********************************************************************
 * $Date: 2018-08-02 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.service.agencyListRegisterService;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class AgencyListRegisterServiceReqBean extends AbstractServiceReqBean {

    /**
     * 
     */
    private static final long serialVersionUID = -6473762488720461939L;

    private String agencyName;

    private int location;

    private String address;

    private String mobileTeam;

    private String nonMobileTeam;

    private String[] role;

    private String remark;

    private Boolean isAeon;

    private String createdBy;

    private Timestamp createdDate;

    private Timestamp updatedDate;

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

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String[] getRole() {
        return role;
    }

    public void setRole(String[] role) {
        this.role = role;
    }

    public Boolean getIsAeon() {
        return isAeon;
    }

    public void setIsAeon(Boolean isAeon) {
        this.isAeon = isAeon;
    }

    @Override
    public String getServiceId() {
        // TODO Auto-generated method stub
        return "AGENCYREG";
    }

}
