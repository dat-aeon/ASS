/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Swe Hsu Mon $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.InitializePasswordUpdateService;

import mm.aeon.com.ass.base.dto.appLoginInfoUpdate.AppLoginInfoUpdateReqDto;
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

public class InitializePasswordUpdateService extends AbstractService
        implements IService<InitializePasswordUpdateServiceReqBean, InitializePasswordUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public InitializePasswordUpdateServiceResBean execute(InitializePasswordUpdateServiceReqBean reqBean) {

        InitializePasswordUpdateServiceResBean resBean = new InitializePasswordUpdateServiceResBean();
        AppLoginInfoUpdateReqDto reqDto = new AppLoginInfoUpdateReqDto();
        reqDto.setPassword(reqBean.getPassword());
        reqDto.setUpdatedBy(reqBean.getUpdatedBy());
        reqDto.setUpdatedTime(CommonUtil.getCurrentTimeStamp());
        
        try {
            this.getDaoServiceInvoker().execute(reqDto);
            resBean.setServiceStatus(ServiceStatusCode.OK);
            
        } catch (PrestoDBException e) {
            if (e instanceof RecordDuplicatedException) {
                resBean.setServiceStatus(ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR);
                
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
