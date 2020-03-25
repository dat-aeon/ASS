/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.groupRefer;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "GroupInfo")
public class GroupReferReqDto implements IReqServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 3537709060374191306L;

    private int agencyUserId;

    public int getAgencyUserId() {
        return agencyUserId;
    }

    public void setAgencyUserId(int agencyUserId) {
        this.agencyUserId = agencyUserId;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.REFER;
    }

}
