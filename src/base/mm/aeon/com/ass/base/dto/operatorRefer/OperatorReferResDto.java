/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.operatorRefer;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class OperatorReferResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -2446688021021106861L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
