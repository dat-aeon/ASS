/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.outletAgencySelectList;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class OutletAgencySelectListResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 7444069148134154385L;

    private Integer agencyId;

    private String agencyName;

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

}
