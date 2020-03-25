/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.adminDeleteService;

import mm.aeon.com.ass.base.dto.adminDelete.AdminDeleteReqDto;
import mm.aeon.com.ass.base.dto.adminGroupDelete.AdminGroupDeleteReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PhysicalRecordLockedException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.LogLevel;

public class AdminDeleteService extends AbstractService
        implements IService<AdminDeleteServiceReqBean, AdminDeleteServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public AdminDeleteServiceResBean execute(AdminDeleteServiceReqBean serviceReqBean) {

        AdminDeleteReqDto reqDto = new AdminDeleteReqDto();
        AdminDeleteServiceResBean serviceResBean = new AdminDeleteServiceResBean();

        reqDto.setId(serviceReqBean.getId());
        try {
            /*
             * Delete admin group table first.
             */
            deleteAdminGroup(serviceReqBean.getId());

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

    /**
     * deleteAdminGroup Method.
     * <p>
     * 
     * <pre>
     *      Delete data according to adminId.
     * 
     * </pre>
     * 
     * </p>
     * 
     * @param agencyId
     */
    private void deleteAdminGroup(int adminId) throws PrestoDBException {

        AdminGroupDeleteReqDto reqDto = new AdminGroupDeleteReqDto();
        reqDto.setAdminId(adminId);
        this.getDaoServiceInvoker().execute(reqDto);

    }
}
