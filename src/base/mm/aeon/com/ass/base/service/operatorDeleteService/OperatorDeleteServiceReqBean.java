/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.operatorDeleteService;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class OperatorDeleteServiceReqBean extends AbstractServiceReqBean {

    /**
     * 
     */
    private static final long serialVersionUID = 6758771888436673216L;

    private Integer userId;

    @Override
    public String getServiceId() {
        return "OPERATORSD";
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
