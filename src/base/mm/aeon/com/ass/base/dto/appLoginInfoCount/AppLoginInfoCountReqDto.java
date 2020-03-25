/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Swe Hsu Mon $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.appLoginInfoCount;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "AppLoginInfo")
public class AppLoginInfoCountReqDto implements IReqServiceDto {

    private static final long serialVersionUID = 2672207836115099916L;

    @Override
    public DaoType getDaoType() {
        return DaoType.COUNT;
    }

}
