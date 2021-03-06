/***********************************************************************
 * $Date: 2018-08-09 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.dto.teamAgencyRegister;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

/**
 * 
 * TeamAgencyRegisterReqDto Class.
 * <p>
 * 
 * <pre>
 *      TeamAgencyRegisterReqDto Class.
 * </pre>
 * 
 * </p>
 */
@MyBatisMapper(namespace = "TeamAgency")
public class TeamAgencyRegisterReqDto implements IReqDto {
    /**
     * 
     */
    private static final long serialVersionUID = 7930856697424849917L;

    private int teamId;

    private int agencyId;
    
    private int isValid;
    
    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    @Override
    public DaoType getDaoType() {

        return DaoType.INSERT;
    }
}
