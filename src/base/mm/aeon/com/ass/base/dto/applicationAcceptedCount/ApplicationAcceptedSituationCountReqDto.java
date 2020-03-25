/**************************************************************************
 * $Date: 2018-08-20$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.applicationAcceptedCount;


import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "ApplicationAcceptedSituation")
public class ApplicationAcceptedSituationCountReqDto implements IReqServiceDto {

   
    private static final long serialVersionUID = 4841782200434711159L;

    private int limit;

    private int offset;
   
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.COUNT;
    }

}

