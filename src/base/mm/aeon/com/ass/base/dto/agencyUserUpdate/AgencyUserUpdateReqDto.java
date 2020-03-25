/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Swe Hsu Mon $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.agencyUserUpdate;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

/**
 * 
 * AgencyUserInsertReqDto Class.
 * <p>
 * 
 * <pre>
 *      AgencyUserInsertReqDto Class.
 * </pre>
 * 
 * </p>
 */
@MyBatisMapper(namespace = "AgencyUser")
public class AgencyUserUpdateReqDto implements IReqDto {
    /**
     * 
     */
    private static final long serialVersionUID = 7930856697424849917L;

    private int id;
    
    private int agencyOutletId;
    
    private int agencyId;
    
    private String loginID;
    
    private String password;
    
    private String name;
    
    private Timestamp startDate;
    
    private Timestamp endDate;
    
    private String phoneNo;
    
    private String email;
    
    private String address;
    
    private String remark;
    
    private int isValid;
    
    private String updatedBy;
    
    private Timestamp updatedTime;
    
    private String disabledBy;
    
    private Timestamp disabledTime;
    
    private boolean isChangeValid;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgencyOutletId() {
        return agencyOutletId;
    }

    public void setAgencyOutletId(int agencyOutletId) {
        this.agencyOutletId = agencyOutletId;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getDisabledBy() {
        return disabledBy;
    }

    public void setDisabledBy(String disabledBy) {
        this.disabledBy = disabledBy;
    }

    public Timestamp getDisabledTime() {
        return disabledTime;
    }

    public void setDisabledTime(Timestamp disabledTime) {
        this.disabledTime = disabledTime;
    }

    public boolean getIsChangeValid() {
        return isChangeValid;
    }

    public void setIsChangeValid(boolean isChangeValid) {
        this.isChangeValid = isChangeValid;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.UPDATE;
    }

}
