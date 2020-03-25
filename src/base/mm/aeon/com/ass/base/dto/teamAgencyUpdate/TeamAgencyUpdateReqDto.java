/***********************************************************************
 * $Date: 2018-08-09 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.dto.teamAgencyUpdate;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

/**
 * 
 * TeamAgencyUpdateReqDto Class.
 * <p>
 * 
 * <pre>
 *      TeamAgencyUpdateReqDto Class.
 * </pre>
 * 
 * </p>
 */
@MyBatisMapper(namespace = "TeamAgency")
public class TeamAgencyUpdateReqDto implements IReqDto {
    /**
     * 
     */
    private static final long serialVersionUID = 7930856697424849917L;

    private int teamId;

    private int agencyId;
    
    private int isValid;
    
    private int nonValid;  
    
    private int oldTeamId;
    
    private Timestamp updatedDate;
    
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
    
    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }
    
    public int getNonValid() {
        return nonValid;
    }

    public void setNonValid(int nonValid) {
        this.nonValid = nonValid;
    }

    public int getOldTeamId() {
        return oldTeamId;
    }

    public void setOldTeamId(int oldTeamId) {
        this.oldTeamId = oldTeamId;
    }

    @Override
    public DaoType getDaoType() {

        return DaoType.UPDATE;
    }
}
