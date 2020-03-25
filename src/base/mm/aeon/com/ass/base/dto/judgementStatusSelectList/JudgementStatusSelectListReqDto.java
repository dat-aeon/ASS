/**************************************************************************
 * $Date: 2018-08-17 $
 * $Author: Thar Pyae$
 * $Rev:  $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.judgementStatusSelectList;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "JudgementStatus")
public class JudgementStatusSelectListReqDto implements IReqServiceDto {

    private static final long serialVersionUID = 2672207836115099916L;

    private String applicationNo;

    private String customerName;
    
    private int status;

    private String fromDate;

    private String toDate;
    
    private boolean isUpload;

    public boolean getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(boolean isUpload) {
        this.isUpload = isUpload;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
