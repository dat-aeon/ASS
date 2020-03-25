/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Swe Hsu Mon $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.agencyUserSMSRegisterService;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class AgencyUserSMSRegisterServiceReqBean extends AbstractServiceReqBean {

    /**
     * 
     */
    private static final long serialVersionUID = -6473762488720461939L;

    private int agencyUserId;

    private String message;
    
    private int sendFlag;
    
    private String createdBy;
    
    public int getAgencyUserId() {
        return agencyUserId;
    }

    public void setAgencyUserId(int agencyUserId) {
        this.agencyUserId = agencyUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(int sendFlag) {
        this.sendFlag = sendFlag;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String getServiceId() {
        // TODO Auto-generated method stub
        return "AGENCYUSERSMSSI";
    }

}
