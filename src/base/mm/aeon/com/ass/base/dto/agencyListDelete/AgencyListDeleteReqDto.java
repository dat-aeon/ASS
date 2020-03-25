/***********************************************************************
 * $Date: 2018-07-13 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.dto.agencyListDelete;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

/**
 * 
 * AgencyListDeleteReqDto Class.
 * <p>
 * 
 * <pre>
 *      AgencyListDeleteReqDto Class.
 * </pre>
 * 
 * </p>
 */
@MyBatisMapper(namespace = "AgencyInfo")
public class AgencyListDeleteReqDto implements IReqDto {
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
