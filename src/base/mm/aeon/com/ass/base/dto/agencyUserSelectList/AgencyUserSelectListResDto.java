/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Swe Hsu Mon $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.agencyUserSelectList;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class AgencyUserSelectListResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -8085767478555013466L;

    private int id;

    private String loginID;

    private String userName;

    private String agencyName;

    private String outletName;

    private String location;

    private int validFlag;

    private int no;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(int validFlag) {
        this.validFlag = validFlag;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

}
