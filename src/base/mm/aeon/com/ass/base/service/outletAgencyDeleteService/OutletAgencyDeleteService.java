/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.outletAgencyDeleteService;

import mm.aeon.com.ass.base.dto.outletAgencyDelete.OutletAgencyDeleteReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PhysicalRecordLockedException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.LogLevel;

public class OutletAgencyDeleteService extends AbstractService
        implements IService<OutletAgencyDeleteServiceReqBean, OutletAgencyDeleteServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public OutletAgencyDeleteServiceResBean execute(OutletAgencyDeleteServiceReqBean reqBean) {

        OutletAgencyDeleteServiceResBean resBean = new OutletAgencyDeleteServiceResBean();
        OutletAgencyDeleteReqDto reqDto = new OutletAgencyDeleteReqDto();

        reqDto.setAgencyOutletId(reqBean.getAgencyOutletId());
        reqDto.setOutletId(reqBean.getOutletId());

        try {
            this.getDaoServiceInvoker().execute(reqDto);
            resBean.setServiceStatus(ServiceStatusCode.OK);
        } catch (PrestoDBException e) {
            if (e instanceof PhysicalRecordLockedException) {
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
