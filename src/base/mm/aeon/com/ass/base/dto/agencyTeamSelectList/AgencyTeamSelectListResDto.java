/**************************************************************************
 * $Date: 2018-08-036$
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.agencyTeamSelectList;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class AgencyTeamSelectListResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -8085767478555013466L;

    private String teamName;
    
    private String teamNameId;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamNameId() {
        return teamNameId;
    }

    public void setTeamNameId(String teamNameId) {
        this.teamNameId = teamNameId;
    }

}
