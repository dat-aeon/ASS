/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.teamDetailAgencySelectList;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "TeamAgent")
public class TeamDetailAgencySelectListReqDto implements IReqServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 2474380240428683674L;

    private Integer teamId;

    @Override
    public DaoType getDaoType() {
        return DaoType.SELECT_LIST;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

}
