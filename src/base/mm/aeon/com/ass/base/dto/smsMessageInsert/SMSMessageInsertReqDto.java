/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Swe Hsu Mon $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.smsMessageInsert;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

/**
 * 
 * AgencyUserInsertReqDto Class.
 * <p>
 * 
 * <pre>
 *      AgencyUserInsertReqDto Class.
 * </pre>
 * 
 * </p>
 */
@MyBatisMapper(namespace = "SMSMessage")
public class SMSMessageInsertReqDto implements IReqDto {
    /**
     * 
     */
    private static final long serialVersionUID = 7930856697424849917L;

    private int agencyUserId;
    
    private int sendFlag;
    
    private String message;
    
    private String createdBy;
    
    private Timestamp createdTime;
    
    public int getAgencyUserId() {
        return agencyUserId;
    }

    public void setAgencyUserId(int adminId) {
        this.agencyUserId = adminId;
    }

    public int getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(int sendFlag) {
        this.sendFlag = sendFlag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.INSERT;
    }



}
