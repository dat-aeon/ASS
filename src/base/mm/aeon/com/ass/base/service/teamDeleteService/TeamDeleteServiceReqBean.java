/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.teamDeleteService;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class TeamDeleteServiceReqBean extends AbstractServiceReqBean {

    /**
     * 
     */
    private static final long serialVersionUID = -139409642711196266L;

    private Integer teamId;

    @Override
    public String getServiceId() {
        return "TEAMSD";
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

}
