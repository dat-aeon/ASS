/**************************************************************************
 * $Date: 2018-08-06$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.outletNameRefer;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "OutletName")
public class OutletNameReferReqDto implements IReqServiceDto {

    private static final long serialVersionUID = 2672207836115099916L;

    private int agencyOutletId;

    public int getAgencyOutletId() {
        return agencyOutletId;
    }

    public void setAgencyOutletId(int agencyOutletId) {
        this.agencyOutletId = agencyOutletId;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.REFER;
    }
}
