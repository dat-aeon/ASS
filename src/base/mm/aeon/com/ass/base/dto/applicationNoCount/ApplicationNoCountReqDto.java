/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Swe Hsu Mon $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.applicationNoCount;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "JudgementStatus")
public class ApplicationNoCountReqDto implements IReqServiceDto {

    private static final long serialVersionUID = 2672207836115099916L;
    
    private String applicationNo;
    
    private String customerName;
    

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


    @Override
    public DaoType getDaoType() {
        return DaoType.COUNT;
    }

}
