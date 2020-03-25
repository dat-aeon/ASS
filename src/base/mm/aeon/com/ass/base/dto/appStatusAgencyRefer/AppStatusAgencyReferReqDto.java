/**************************************************************************
 * $Date: 2018-09-18$
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.appStatusAgencyRefer;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "ApplicationStatus")
public class AppStatusAgencyReferReqDto implements IReqServiceDto {

    private static final long serialVersionUID = 2672207836115099916L;
    
    private String loginID;

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.REFER;
    }

}
