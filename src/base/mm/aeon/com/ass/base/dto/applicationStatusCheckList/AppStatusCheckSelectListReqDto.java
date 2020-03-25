/**************************************************************************
 * $Date: 2018-08-27 $
 * $Author: Thar Pyae$
 * $Rev:  $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.applicationStatusCheckList;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "ApplicationStatus")
public class AppStatusCheckSelectListReqDto implements IReqServiceDto {

    private static final long serialVersionUID = 2672207836115099916L;
   
    private String agencyName;
    
    private String fromDate;
    
    private String toDate;
          
    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }
    
    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.SELECT_LIST;
    }
}
