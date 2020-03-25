/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.operatorTeamSelectForUpdate;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "OperatorTeam")
public class OperatorTeamSelectForUpdateReqDto implements IReqServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -4886365913905360265L;

    private Integer teamOperatorId;

    public Integer getTeamOperatorId() {
        return teamOperatorId;
    }

    public void setTeamOperatorId(Integer teamOperatorId) {
        this.teamOperatorId = teamOperatorId;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.SELECT_FOR_UPDATE;
    }
}
