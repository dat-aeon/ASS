/***********************************************************************
 * $Date: 2018-14-15 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.dto.agencyListDetail;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "AgencyUserDetail")
public class AgencyListDetailUserReqDto implements IReqServiceDto {

    /**
     * Service Version ID.
     */
    private static final long serialVersionUID = -2392769361727867728L;

    private int agencyId;
    
    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.SELECT_LIST;
    }

}

