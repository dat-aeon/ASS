/***********************************************************************
 * $Date: 2018-08-09 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.dto.agencyIdReferForUpdae;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

/**
 * 
 * AgencyIdReferForUpdateReqDto Class.
 * <p>
 * 
 * <pre>
 *      AgencyIdReferForUpdateReqDto Class.
 * </pre>
 * 
 * </p>
 */
@MyBatisMapper(namespace = "AgencyInfo")
public class AgencyIdReferForUpdateReqDto implements IReqDto {
    /**
     * 
     */
    private static final long serialVersionUID = 7930856697424849917L;

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

        return DaoType.REFER;
    }
}
