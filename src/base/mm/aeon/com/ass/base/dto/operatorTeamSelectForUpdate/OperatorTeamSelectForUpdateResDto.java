/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.operatorTeamSelectForUpdate;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class OperatorTeamSelectForUpdateResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -7509145713090056738L;

    private Integer teamOperatorId;

    private Timestamp updatedTime;

    public Integer getTeamOperatorId() {
        return teamOperatorId;
    }

    public void setTeamOperatorId(Integer teamOperatorId) {
        this.teamOperatorId = teamOperatorId;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

}
