/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.outletSearch;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class OutletSearchResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -5906074760546281468L;

    private Integer outletId;

    private String outletName;

    private Integer agencyId;

    private Integer agencyOutletId;

    private String agencyName;

    private Integer agencyLocation;

    private Timestamp agencyOutletUpdatedTime;

    private String address;

    private String remark;

    private Integer validStatus;

    private String createdBy;

    private Timestamp createdTime;

    private String updatedBy;

    private Timestamp updatedTime;

    private String mobileTeamName;

    private String nonMobileTeamName;

    private String phoneNo;

    private String longitude;

    private String latitude;

    private String imagePath;

    public Integer getOutletId() {
        return outletId;
    }

    public void setOutletId(Integer outletId) {
        this.outletId = outletId;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public Integer getAgencyOutletId() {
        return agencyOutletId;
    }

    public void setAgencyOutletId(Integer agencyOutletId) {
        this.agencyOutletId = agencyOutletId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public Integer getAgencyLocation() {
        return agencyLocation;
    }

    public void setAgencyLocation(Integer agencyLocation) {
        this.agencyLocation = agencyLocation;
    }

    public Timestamp getAgencyOutletUpdatedTime() {
        return agencyOutletUpdatedTime;
    }

    public void setAgencyOutletUpdatedTime(Timestamp agencyOutletUpdatedTime) {
        this.agencyOutletUpdatedTime = agencyOutletUpdatedTime;
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

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getMobileTeamName() {
        return mobileTeamName;
    }

    public void setMobileTeamName(String mobileTeamName) {
        this.mobileTeamName = mobileTeamName;
    }

    public String getNonMobileTeamName() {
        return nonMobileTeamName;
    }

    public void setNonMobileTeamName(String nonMobileTeamName) {
        this.nonMobileTeamName = nonMobileTeamName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
