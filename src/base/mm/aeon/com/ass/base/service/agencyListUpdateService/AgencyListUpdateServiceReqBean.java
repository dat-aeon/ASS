/***********************************************************************
 * $Date: 2018-08-09 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.service.agencyListUpdateService;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class AgencyListUpdateServiceReqBean extends AbstractServiceReqBean {

    /**
     * 
     */
    private static final long serialVersionUID = -6473762488720461939L;

    private int agencyId;

    private String agencyName;

    private int location;

    private String address;

    private String mobileTeam;

    private int oldMobileId;

    private String nonMobileTeam;

    private int oldNonMobileId;

    private String[] role;

    private String[] oldRoleList;

    private String remark;

    private String updatetedBy;

    private Timestamp updatedDate;

    private Timestamp oldUpdatedDate;

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

    public String getUpdatetedBy() {
        return updatetedBy;
    }

    public void setUpdatetedBy(String updatetedBy) {
        this.updatetedBy = updatetedBy;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getOldMobileId() {
        return oldMobileId;
    }

    public void setOldMobileId(int oldMobileId) {
        this.oldMobileId = oldMobileId;
    }

    public int getOldNonMobileId() {
        return oldNonMobileId;
    }

    public void setOldNonMobileId(int oldNonMobileId) {
        this.oldNonMobileId = oldNonMobileId;
    }

    public Timestamp getOldUpdatedDate() {
        return oldUpdatedDate;
    }

    public void setOldUpdatedDate(Timestamp oldUpdatedDate) {
        this.oldUpdatedDate = oldUpdatedDate;
    }

    public String[] getRole() {
        return role;
    }

    public void setRole(String[] role) {
        this.role = role;
    }

    public String[] getOldRoleList() {
        return oldRoleList;
    }

    public void setOldRoleList(String[] oldRoleList) {
        this.oldRoleList = oldRoleList;
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
        return "AGENCYUPD";
    }

}
