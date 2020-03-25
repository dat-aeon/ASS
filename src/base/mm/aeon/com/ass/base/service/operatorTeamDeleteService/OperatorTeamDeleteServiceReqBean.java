/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.operatorTeamDeleteService;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class OperatorTeamDeleteServiceReqBean extends AbstractServiceReqBean {

    /**
     * 
     */
    private static final long serialVersionUID = -5516833848892678435L;

    private Integer teamOperatorId;

    private Integer userId;

    @Override
    public String getServiceId() {
        return "OPERATORTEAMSD";
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
