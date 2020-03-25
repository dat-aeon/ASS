/***********************************************************************
 * $Date: 2018-07-31 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.dto.agencyListSearch;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "AgencyInfo")
public class AgencyListSearchReqDto implements IReqServiceDto {

    /**
     * Service Version ID.
     */
    private static final long serialVersionUID = -2392769361727867728L;

    private String agencyName;
    
    private int isValid;
 
    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.SELECT_LIST;
    }

}

