/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.operatorTeamDelete;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "OperatorTeam")
public class OperatorTeamDeleteReqDto implements IReqDto {

    /**
     * 
     */
    private static final long serialVersionUID = 3596742774808682551L;

    private Integer teamOperatorId;

    private Integer userId;

    @Override
    public DaoType getDaoType() {
        return DaoType.DELETE;
    }

    public Integer getTeamOperatorId() {
        return teamOperatorId;
    }

    public void setTeamOperatorId(Integer teamOperatorId) {
        this.teamOperatorId = teamOperatorId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
