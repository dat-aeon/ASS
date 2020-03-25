/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Swe Hsu Mon $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.roleSelectList;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class RoleSelectListResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -8085767478555013466L;

    private int id;

    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
