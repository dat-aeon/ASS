/**************************************************************************
 * $Date : 2018-09-03 $
 * $Author : Thar Pyae $
 * $Rev : 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.agencyListSelectForUpdate;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "AgencyInfo")
public class AgencyListSelectForUpdateReqDto implements IReqServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 5540745575591937916L;

    private Integer agencyId;
    
    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.SELECT_FOR_UPDATE;
    }

}
