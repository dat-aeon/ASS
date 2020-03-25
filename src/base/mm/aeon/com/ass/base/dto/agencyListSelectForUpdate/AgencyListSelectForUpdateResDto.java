/**************************************************************************
 * $Date : 2018-09-03 $
 * $Author : Thar Pyae $
 * $Rev : 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.agencyListSelectForUpdate;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class AgencyListSelectForUpdateResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 6558009215317432659L;

    private Timestamp disabledTime;
    
    private Timestamp updatedTime;

    public Timestamp getDisabledTime() {
        return disabledTime;
    }

    public void setDisabledTime(Timestamp disabledTime) {
        this.disabledTime = disabledTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

}
