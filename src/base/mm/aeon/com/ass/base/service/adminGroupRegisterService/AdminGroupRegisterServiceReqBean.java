/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Swe Hsu Mon $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.adminGroupRegisterService;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class AdminGroupRegisterServiceReqBean extends AbstractServiceReqBean {

    /**
     * 
     */
    private static final long serialVersionUID = -6473762488720461939L;

    private int adminId;
    
    private int groupId;
    
    private int finishFlag;
    
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getFinishFlag() {
        return finishFlag;
    }

    public void setFinishFlag(int finishFlag) {
        this.finishFlag = finishFlag;
    }

    @Override
    public String getServiceId() {
        // TODO Auto-generated method stub
        return "ADMINGROUPSI";
    }

}
