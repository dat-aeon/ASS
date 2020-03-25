/**************************************************************************
 * $Date: 2018-08-17 $
 * $Author: Thar Pyae$
 * $Rev:  $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.receivedListCount;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "ReceivedList")
public class ReceivedListCountReqDto implements IReqServiceDto {

    private static final long serialVersionUID = 2672207836115099916L;

    private String userName;
    
    private String agencyName;
           
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }
    
    @Override
    public DaoType getDaoType() {
        return DaoType.COUNT;
    }
}
