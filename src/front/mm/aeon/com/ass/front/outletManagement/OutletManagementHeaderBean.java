/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.outletManagement;

import java.io.Serializable;
import java.sql.Timestamp;

public class OutletManagementHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -573584367570393877L;

    private Integer outletId;

    private String outletName;

    private Integer agencyId;

    private Integer oldAgencyId;

    private Integer agencyOutletId;

    private Timestamp agnecyOutletUpdatedTime;

    private String address;

    private String remark;

    private String updatedBy;

    private Timestamp updatedTime;

    private boolean forUpdate;

    private String phoneNo;

    private String longitude;

    private String latitude;

    private String imagePath;

    private String uploadedImageFilePath;

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

    public Integer getOldAgencyId() {
        return oldAgencyId;
    }

    public void setOldAgencyId(Integer oldAgencyId) {
        this.oldAgencyId = oldAgencyId;
    }

    public Integer getAgencyOutletId() {
        return agencyOutletId;
    }

    public void setAgencyOutletId(Integer agencyOutletId) {
        this.agencyOutletId = agencyOutletId;
    }

    public Timestamp getAgnecyOutletUpdatedTime() {
        return agnecyOutletUpdatedTime;
    }

    public void setAgnecyOutletUpdatedTime(Timestamp agnecyOutletUpdatedTime) {
        this.agnecyOutletUpdatedTime = agnecyOutletUpdatedTime;
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

    public boolean getForUpdate() {
        return forUpdate;
    }

    public void setForUpdate(boolean forUpdate) {
        this.forUpdate = forUpdate;
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

    public String getUploadedImageFilePath() {
        return uploadedImageFilePath;
    }

    public void setUploadedImageFilePath(String uploadedImageFilePath) {
        this.uploadedImageFilePath = uploadedImageFilePath;
    }

}
