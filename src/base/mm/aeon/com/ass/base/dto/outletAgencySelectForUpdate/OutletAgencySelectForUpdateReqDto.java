/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.outletAgencySelectForUpdate;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace="OutletAgency")
public class OutletAgencySelectForUpdateReqDto implements IReqServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 3937048666450717088L;

    private Integer agencyOutletId;

    @Override
    public DaoType getDaoType() {
        return DaoType.SELECT_FOR_UPDATE;
    }

    public Integer getAgencyOutletId() {
        return agencyOutletId;
    }

    public void setAgencyOutletId(Integer agencyOutletId) {
        this.agencyOutletId = agencyOutletId;
    }

}
