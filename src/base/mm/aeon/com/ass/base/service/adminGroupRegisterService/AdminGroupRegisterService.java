/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Swe Hsu Mon $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.adminGroupRegisterService;

import mm.aeon.com.ass.base.dto.adminGroupInsert.AdminGroupInsertReqDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class AdminGroupRegisterService extends AbstractService
        implements IService<AdminGroupRegisterServiceReqBean, AdminGroupRegisterServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public AdminGroupRegisterServiceResBean execute(AdminGroupRegisterServiceReqBean reqBean) {

        AdminGroupInsertReqDto reqDto = new AdminGroupInsertReqDto();
        AdminGroupRegisterServiceResBean resBean = new AdminGroupRegisterServiceResBean();
        
        reqDto.setAdminId(reqBean.getAdminId());
        reqDto.setGroupId(reqBean.getGroupId());
        reqDto.setFinishFlag(reqBean.getFinishFlag());
        reqDto.setUpdatedTime(CommonUtil.getCurrentTimeStamp());
        
        try {
            this.getDaoServiceInvoker().execute(reqDto);
            resBean.setServiceStatus(ServiceStatusCode.OK);
            
        } catch (PrestoDBException e) {
            if (e instanceof RecordDuplicatedException) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_DUPLICATED_ERROR);
                
            } else if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                resBean.setServiceStatus(ServiceStatusCode.SQL_ERROR);
                
            } else {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return resBean;

    }

}
