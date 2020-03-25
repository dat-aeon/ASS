/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.operatorTeamList;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class OperatorTeamSelectListResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 3605938302397860871L;

    private Integer teamId;

    private String teamName;

    private Integer target;

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

}
