/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.teamDetailAgencySelectList;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class TeamDetailAgencySelectListResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 7257299064220761563L;

    private String agencyName;

    private String assignBy;

    private Timestamp assignTime;

    private Integer validStatus;

    private String disabledBy;

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAssignBy() {
        return assignBy;
    }

    public void setAssignBy(String assignBy) {
        this.assignBy = assignBy;
    }

    public Timestamp getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Timestamp assignTime) {
        this.assignTime = assignTime;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public String getDisabledBy() {
        return disabledBy;
    }

    public void setDisabledBy(String disabledBy) {
        this.disabledBy = disabledBy;
    }

}
