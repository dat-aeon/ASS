/**************************************************************************
 * $Date: 2018-08-06$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.smsMessageSelectList;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "SMSMessage")
public class SMSMessageSelectListReqDto implements IReqServiceDto {

    private static final long serialVersionUID = 2672207836115099916L;

    private String userName;
    
    private String agencyName;
    
    private String outletName;
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.SELECT_LIST;
    }
}
