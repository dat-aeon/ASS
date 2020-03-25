/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.outletAgencyDelete;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "OutletAgency")
public class OutletAgencyDeleteReqDto implements IReqDto {

    /**
     * 
     */
    private static final long serialVersionUID = -8587688212691562821L;

    private Integer agencyOutletId;

    private Integer outletId;
    
    private Integer agencyId;

    public Integer getAgencyOutletId() {
        return agencyOutletId;
    }

    public void setAgencyOutletId(Integer agencyOutletId) {
        this.agencyOutletId = agencyOutletId;
    }

    public Integer getOutletId() {
        return outletId;
    }

    public void setOutletId(Integer outletId) {
        this.outletId = outletId;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.DELETE;
    }

}
