/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Swe Hsu Mon $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.adminGroupInsert;

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
@MyBatisMapper(namespace = "AdminGroup")
public class AdminGroupInsertReqDto implements IReqDto {
    /**
     * 
     */
    private static final long serialVersionUID = 7930856697424849917L;

    private int adminId;
    
    private int groupId;
    
    private int finishFlag;
    
    private Timestamp updatedTime;
    
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

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.INSERT;
    }



}
