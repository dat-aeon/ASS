/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.outletAgencyRegisterService;

import mm.aeon.com.ass.base.dto.outletAgencyRegister.OutletAgencyRegisterReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class OutletAgencyRegisterService extends AbstractService
        implements IService<OutletAgencyRegisterServiceReqBean, OutletAgencyRegisterServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public OutletAgencyRegisterServiceResBean execute(OutletAgencyRegisterServiceReqBean reqBean) {

        OutletAgencyRegisterServiceResBean resBean = new OutletAgencyRegisterServiceResBean();
        OutletAgencyRegisterReqDto reqDto = new OutletAgencyRegisterReqDto();

        reqDto.setAgencyId(reqBean.getAgencyId());
        reqDto.setOutletId(reqBean.getOutletId());
        reqDto.setValidStatus(reqBean.getValidStatus());
        
        try {
            this.getDaoServiceInvoker().execute(reqDto);
            resBean.setServiceStatus(ServiceStatusCode.OK);
        } catch (PrestoDBException e) {
            if (e instanceof RecordDuplicatedException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
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
