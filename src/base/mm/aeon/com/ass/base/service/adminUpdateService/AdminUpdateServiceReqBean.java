/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.adminUpdateService;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class AdminUpdateServiceReqBean extends AbstractServiceReqBean {

    /**
     * 
     */
    private static final long serialVersionUID = -3883054262804630617L;

    private int id;

    private String loginId;

    private String password;

    private String name;

    private int role;

    private int validStatus;

    private String disabledBy;

    private Timestamp disabledDate;

    private String updatedBy;

    private Timestamp updatedDate;

    private boolean isValidStatusToggle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(int validStatus) {
        this.validStatus = validStatus;
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

    public boolean isValidStatusToggle() {
        return isValidStatusToggle;
    }

    public void setValidStatusToggle(boolean isValidStatusToggle) {
        this.isValidStatusToggle = isValidStatusToggle;
    }

    @Override
    public String getServiceId() {
        return "ADMINSU";
    }
}
