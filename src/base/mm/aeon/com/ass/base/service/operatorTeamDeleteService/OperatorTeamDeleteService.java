/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.operatorTeamDeleteService;

import mm.aeon.com.ass.base.dto.operatorTeamDelete.OperatorTeamDeleteReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PhysicalRecordLockedException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.LogLevel;

public class OperatorTeamDeleteService extends AbstractService
        implements IService<OperatorTeamDeleteServiceReqBean, OperatorTeamDeleteServiceResBean> {
    
    private ASSLogger logger = new ASSLogger();
    
    @Override
    public OperatorTeamDeleteServiceResBean execute(OperatorTeamDeleteServiceReqBean serviceReqBean) {

        OperatorTeamDeleteServiceResBean serviceResBean = new OperatorTeamDeleteServiceResBean();

        OperatorTeamDeleteReqDto reqDto = new OperatorTeamDeleteReqDto();
        reqDto.setUserId(serviceReqBean.getUserId());
        reqDto.setTeamOperatorId(serviceReqBean.getTeamOperatorId());
        
        try {
            this.getDaoServiceInvoker().execute(reqDto);
            serviceResBean.setServiceStatus(ServiceStatusCode.OK);
        } catch (PrestoDBException e) {
            if (e instanceof PhysicalRecordLockedException) {
                serviceResBean.setServiceStatus(ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR);
            } else if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                serviceResBean.setServiceStatus(ServiceStatusCode.SQL_ERROR);

            } else {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        return serviceResBean;
    }

}
