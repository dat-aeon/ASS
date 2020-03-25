/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Swe Hsu Mon $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.agencyRoleRefer;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

/**
 * 
 * AgencyRoleReferResDto Class.
 * <p>
 * 
 * <pre>
 *      AgencyRoleReferResDto Class.
 * </pre>
 * 
 * </p>
 */
public class AgencyRoleReferResDto implements IResServiceDto {
    /**
     * 
     */
    private static final long serialVersionUID = 7930856697424849917L;

    private String roleId;

    private String roleName;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public DaoType getDaoType() {
        return DaoType.REFER;
    }

}
