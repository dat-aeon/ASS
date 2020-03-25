/***********************************************************************
 * $Date: 2018-07-09 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.dto.agencyListUpdate;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

/**
 * 
 * AgencyListUpdateReqDto Class.
 * <p>
 * 
 * <pre>
 *      AgencyListUpdateReqDto Class.
 * </pre>
 * 
 * </p>
 */
@MyBatisMapper(namespace = "AgencyInfo")
public class AgencyListUpdateReqDto implements IReqDto {
    /**
     * 
     */
    private static final long serialVersionUID = 7930856697424849917L;

    private int agencyId;

    private String agencyName;

    private int location;

    private String address;

    private String remark;

    private int isValid;

    private int nonValid;

    private String updatedBy;

    private Timestamp updatedDate;

    private String disabledBy;

    private Timestamp disabledDate;

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

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
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

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public int getNonValid() {
        return nonValid;
    }

    public void setNonValid(int nonValid) {
        this.nonValid = nonValid;
    }

    public String getDisabledBy() {
        return disabledBy;
    }

    public void setDisabledBy(String disabledBy) {
        this.disabledBy = disabledBy;
    }

    public Timestamp getDisabledDate() {
        return disabledDate;
    }

    public void setDisabledDate(Timestamp disabledDate) {
        this.disabledDate = disabledDate;
    }

    public Boolean getIsAeon() {
        return isAeon;
    }

    public void setIsAeon(Boolean isAeon) {
        this.isAeon = isAeon;
    }

    @Override
    public DaoType getDaoType() {

        return DaoType.UPDATE;
    }
}
