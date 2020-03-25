/**************************************************************************
 * $Date: 2018-08-20$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.applicationAccepted;


import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "ApplicationAcceptedTotal")
public class ApplicationAcceptedTotalReqDto implements IReqServiceDto {

   
    private static final long serialVersionUID = 4841782200434711159L;

    private String locationYgn;
    
    private String locationMdy;
    
    private String typePLoan;
    
    private String typeMLoan;
    
    private String fromDate;

    private String toDate;
    
    private int status;
    
    private int offset;
    
    private int limit;
    
    public String getLocationYgn() {
        return locationYgn;
    }

    public void setLocationYgn(String locationYgn) {
        this.locationYgn = locationYgn;
    }

    public String getLocationMdy() {
        return locationMdy;
    }

    public void setLocationMdy(String locationMdy) {
        this.locationMdy = locationMdy;
    }

    public String getTypePLoan() {
        return typePLoan;
    }

    public void setTypePLoan(String typePLoan) {
        this.typePLoan = typePLoan;
    }

    public String getTypeMLoan() {
        return typeMLoan;
    }

    public void setTypeMLoan(String typeMLoan) {
        this.typeMLoan = typeMLoan;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.SELECT_LIST;
    }

}

