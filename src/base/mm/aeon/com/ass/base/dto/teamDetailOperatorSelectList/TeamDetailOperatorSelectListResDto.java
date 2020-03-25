/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.teamDetailOperatorSelectList;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class TeamDetailOperatorSelectListResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 3416609229365609490L;

    private String operatorName;

    private String assignBy;

    private Timestamp assignTime;

    private Integer validStatus;

    private String disabledBy;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
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
