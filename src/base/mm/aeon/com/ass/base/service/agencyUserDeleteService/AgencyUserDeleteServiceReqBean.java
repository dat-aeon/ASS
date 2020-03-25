/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Swe Hsu Mon $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.agencyUserDeleteService;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class AgencyUserDeleteServiceReqBean extends AbstractServiceReqBean {

    /**
     * Serial Version ID.
     */
    private static final long serialVersionUID = 1466513684939487482L;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getServiceId() {
        return "AGENCYUSERSD";
    }

}
