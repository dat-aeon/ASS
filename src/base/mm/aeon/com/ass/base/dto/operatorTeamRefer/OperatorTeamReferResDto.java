/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.operatorTeamRefer;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class OperatorTeamReferResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 3605938302397860871L;

    private Integer teamId;

    private Integer isValid;

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

}
