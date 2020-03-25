/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.operatorUpdateService;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceResBean;

public class OperatorUpdateServiceResBean extends AbstractServiceResBean {

    /**
     * 
     */
    private static final long serialVersionUID = 7158226906356312955L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
