/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.outletAgencyRefer;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class OutletAgencyReferResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -7440745205934109889L;

    private Integer agencyId;

    private Integer validStatus;

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

}
