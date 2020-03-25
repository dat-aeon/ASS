/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.outletDeleteService;

import mm.aeon.com.ass.base.dto.outletDelete.OutletDeleteReqDto;
import mm.aeon.com.ass.base.dto.outletSelectForUpdate.OutletSelectForUpdateReqDto;
import mm.aeon.com.ass.base.dto.outletSelectForUpdate.OutletSelectForUpdateResDto;
import mm.aeon.com.ass.base.dto.outletUpdate.OutletUpdateReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PhysicalRecordLockedException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.LogLevel;

public class OutletDeleteService extends AbstractService
        implements IService<OutletDeleteServiceReqBean, OutletDeleteServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public OutletDeleteServiceResBean execute(OutletDeleteServiceReqBean reqBean) {

        OutletDeleteServiceResBean resBean = new OutletDeleteServiceResBean();
        OutletUpdateReqDto reqDto = new OutletUpdateReqDto();

        OutletSelectForUpdateReqDto selectForUpdateReqDto = new OutletSelectForUpdateReqDto();
        selectForUpdateReqDto.setOutletId(reqBean.getOutletId());
        
        try {
            OutletSelectForUpdateResDto selectForUpdateResDto =
                    (OutletSelectForUpdateResDto) this.getDaoServiceInvoker().execute(selectForUpdateReqDto);
            
            if(selectForUpdateResDto == null) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_NOT_FOUND_ERROR);
                return resBean;
            }
            reqDto.setOutletId(reqBean.getOutletId());
            reqDto.setDelFlag(1);
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
