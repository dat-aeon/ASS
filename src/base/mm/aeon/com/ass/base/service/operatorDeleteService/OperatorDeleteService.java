/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.operatorDeleteService;

import mm.aeon.com.ass.base.dto.adminGroupDelete.AdminGroupDeleteReqDto;
import mm.aeon.com.ass.base.dto.operatorDelete.OperatorDeleteReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PhysicalRecordLockedException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.LogLevel;

public class OperatorDeleteService extends AbstractService
        implements IService<OperatorDeleteServiceReqBean, OperatorDeleteServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public OperatorDeleteServiceResBean execute(OperatorDeleteServiceReqBean serviceReqBean) {

        OperatorDeleteServiceResBean serviceResBean = new OperatorDeleteServiceResBean();
        OperatorDeleteReqDto reqDto = new OperatorDeleteReqDto();

        reqDto.setUserId(serviceReqBean.getUserId());

        try {
            deleteAdminGroup(serviceReqBean.getUserId());
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

    private void deleteAdminGroup(int adminId) throws PrestoDBException {

        AdminGroupDeleteReqDto reqDto = new AdminGroupDeleteReqDto();
        reqDto.setAdminId(adminId);
        this.getDaoServiceInvoker().execute(reqDto);

    }

}
