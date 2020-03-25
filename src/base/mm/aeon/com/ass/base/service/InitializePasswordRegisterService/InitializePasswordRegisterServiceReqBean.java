/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Swe Hsu Mon $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.InitializePasswordRegisterService;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class InitializePasswordRegisterServiceReqBean extends AbstractServiceReqBean {

    /**
     * 
     */
    private static final long serialVersionUID = -6473762488720461939L;

    private String password;
    
    private String createdBy;
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return "PASSWORDSI";
    }

}
