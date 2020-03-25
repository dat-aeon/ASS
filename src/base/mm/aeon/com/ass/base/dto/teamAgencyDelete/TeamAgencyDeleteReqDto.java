/***********************************************************************
 * $Date: 2018-08-13 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.dto.teamAgencyDelete;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

/**
 * 
 * TeamAgencyDeleteReqDto Class.
 * <p>
 * 
 * <pre>
 *      TeamAgencyDeleteReqDto Class.
 * </pre>
 * 
 * </p>
 */
@MyBatisMapper(namespace = "TeamAgency")
public class TeamAgencyDeleteReqDto implements IReqDto {
    /**
     * 
     */
    private static final long serialVersionUID = 7930856697424849917L;

    private int agencyId;

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }
    
    @Override
    public DaoType getDaoType() {

        return DaoType.DELETE;
    }
}
