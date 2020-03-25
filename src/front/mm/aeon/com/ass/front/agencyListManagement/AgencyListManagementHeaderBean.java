/***********************************************************************
 * $Date: 2018-09-03 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.front.agencyListManagement;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Stack;

import javax.faces.model.SelectItem;

public class AgencyListManagementHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9179837623562515652L;

    private int agencyId;

    private String agencyName;

    private String location;

    private String address;

    private String mobileTeamId;

    private String mobileTeam;

    private String oldMobileId;

    private String nonMobileTeamId;

    private String nonMobileTeam;

    private String oldNonMobileId;

    private Boolean isAeon;

    private ArrayList<SelectItem> mobileTeamList = new ArrayList<SelectItem>();

    private ArrayList<SelectItem> nonMobileTeamList = new ArrayList<SelectItem>();

    private ArrayList<SelectItem> genderSelectItemList;

    private String remark;

    private boolean targetListFlag;

    private Timestamp agencyUptDate;

    private String createdBy;

    private Timestamp createdDate;

    private String updatedBy;

    private Timestamp updatedDate;

    private String updDateShow;

    private String createdDateShow;

    private Stack<String> priorLinkStack;

    // new item
    private ArrayList<SelectItem> roleList = new ArrayList<SelectItem>();

    private String[] selectedRole;

    // for update
    private String[] oldRoleList;

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

    public ArrayList<SelectItem> getMobileTeamList() {
        return mobileTeamList;
    }

    public void setMobileTeamList(ArrayList<SelectItem> mobileTeamList) {
        this.mobileTeamList = mobileTeamList;
    }

    public ArrayList<SelectItem> getNonMobileTeamList() {
        return nonMobileTeamList;
    }

    public void setNonMobileTeamList(ArrayList<SelectItem> nonMobileTeamList) {
        this.nonMobileTeamList = nonMobileTeamList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public boolean isTargetListFlag() {
        return targetListFlag;
    }

    public void setTargetListFlag(boolean targetListFlag) {
        this.targetListFlag = targetListFlag;
    }

    public String getOldMobileId() {
        return oldMobileId;
    }

    public void setOldMobileId(String oldMobileId) {
        this.oldMobileId = oldMobileId;
    }

    public String getOldNonMobileId() {
        return oldNonMobileId;
    }

    public void setOldNonMobileId(String oldNonMobileId) {
        this.oldNonMobileId = oldNonMobileId;
    }

    public Timestamp getAgencyUptDate() {
        return agencyUptDate;
    }

    public void setAgencyUptDate(Timestamp agencyUptDate) {
        this.agencyUptDate = agencyUptDate;
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

    public String getUpdDateShow() {
        return updDateShow;
    }

    public void setUpdDateShow(String updDateShow) {
        this.updDateShow = updDateShow;
    }

    public String getCreatedDateShow() {
        return createdDateShow;
    }

    public void setCreatedDateShow(String createdDateShow) {
        this.createdDateShow = createdDateShow;
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

    public Stack<String> getPriorLinkStack() {
        return priorLinkStack;
    }

    public void setPriorLinkStack(Stack<String> priorLinkStack) {
        this.priorLinkStack = priorLinkStack;
    }

    public ArrayList<SelectItem> getRoleList() {
        return roleList;
    }

    public void setRoleList(ArrayList<SelectItem> roleList) {
        this.roleList = roleList;
    }

    public String[] getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(String[] selectedRole) {
        this.selectedRole = selectedRole;
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

    public ArrayList<SelectItem> getGenderSelectItemList() {
        return genderSelectItemList;
    }

    public void setGenderSelectItemList(ArrayList<SelectItem> genderSelectItemList) {
        this.genderSelectItemList = genderSelectItemList;
    }

}
