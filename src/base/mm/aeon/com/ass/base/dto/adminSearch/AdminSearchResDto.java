/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.adminSearch;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class AdminSearchResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -7591551575539498312L;

    private int adminId;

    private String adminName;

    private String adminLoginId;

    private int validStatus;

    private Timestamp updatedDate;

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminLoginId() {
        return adminLoginId;
    }

    public void setAdminLoginId(String adminLoginId) {
        this.adminLoginId = adminLoginId;
    }

    public int getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(int validStatus) {
        this.validStatus = validStatus;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

}
