/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.outletAgencySelectList;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "OutletAgency")
public class OutletAgencySelectListReqDto implements IReqServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 421443968686026592L;

    private Integer validStatus;

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.SELECT_LIST;
    }

}
