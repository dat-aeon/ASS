/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.outletRegisterService;

import mm.aeon.com.ass.base.dto.outletRegister.OutletRegisterReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class OutletRegisterService extends AbstractService
        implements IService<OutletRegisterServiceReqBean, OutletRegisterServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public OutletRegisterServiceResBean execute(OutletRegisterServiceReqBean reqBean) {

        OutletRegisterServiceResBean resBean = new OutletRegisterServiceResBean();
        OutletRegisterReqDto reqDto = new OutletRegisterReqDto();

        reqDto.setAddress(reqBean.getAddress());
        reqDto.setCreatedBy(reqBean.getCreatedBy());
        reqDto.setCreatedTime(reqBean.getCreatedTime());
        reqDto.setOutletName(reqBean.getOutletName());
        reqDto.setRemark(reqBean.getRemark());
        reqDto.setImagePath(reqBean.getImagePath());
        reqDto.setPhoneNo(reqBean.getPhoneNo());
        reqDto.setLatitude(reqBean.getLatitude());
        reqDto.setLongitude(reqBean.getLongitude());
        reqDto.setValidStatus(reqBean.getValidStatus());

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
