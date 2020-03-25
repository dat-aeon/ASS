/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.outletSelectForUpdate;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "Outlet")
public class OutletSelectForUpdateReqDto implements IReqServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -7466039987619550101L;

    private Integer outletId;

    public Integer getOutletId() {
        return outletId;
    }

    public void setOutletId(Integer outletId) {
        this.outletId = outletId;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.SELECT_FOR_UPDATE;
    }

}
