/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.outletAgencyUpdateService;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.outletAgencySelectForUpdate.OutletAgencySelectForUpdateReqDto;
import mm.aeon.com.ass.base.dto.outletAgencySelectForUpdate.OutletAgencySelectForUpdateResDto;
import mm.aeon.com.ass.base.dto.outletAgencyUpdate.OutletAgencyUpdateReqDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PhysicalRecordLockedException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class OutletAgencyUpdateService extends AbstractService
        implements IService<OutletAgencyUpdateServiceReqBean, OutletAgencyUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public OutletAgencyUpdateServiceResBean execute(OutletAgencyUpdateServiceReqBean reqBean) {

        OutletAgencyUpdateServiceResBean resBean = new OutletAgencyUpdateServiceResBean();

        OutletAgencySelectForUpdateReqDto selectForUpdateReqDto = new OutletAgencySelectForUpdateReqDto();
        selectForUpdateReqDto.setAgencyOutletId(reqBean.getAgencyOutletId());

        try {

            OutletAgencySelectForUpdateResDto selectForUpdateResDto =
                    (OutletAgencySelectForUpdateResDto) this.getDaoServiceInvoker().execute(selectForUpdateReqDto);

            if (null == selectForUpdateResDto) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_NOT_FOUND_ERROR);
            } else if (null != selectForUpdateResDto.getUpdatedTime()
                    && !selectForUpdateResDto.getUpdatedTime().equals(reqBean.getUpdatedTime())) {
                resBean.setServiceStatus(ASSServiceStatusCommon.RECORD_ALREADY_UPDATE);
            } else {

                OutletAgencyUpdateReqDto reqDto = new OutletAgencyUpdateReqDto();
                reqDto.setAgencyId(reqBean.getAgencyId());
                reqDto.setAgencyOutletId(reqBean.getAgencyOutletId());
                reqDto.setValidStatus(reqBean.getValidStatus());
                reqDto.setOutletId(reqBean.getOutletId());
                reqDto.setUpdatedTime(CommonUtil.getCurrentTimeStamp());

                this.getDaoServiceInvoker().execute(reqDto);
                resBean.setServiceStatus(ServiceStatusCode.OK);
            }

        } catch (PrestoDBException e) {
            if (e instanceof RecordDuplicatedException) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_DUPLICATED_ERROR);
            } else if (e instanceof PhysicalRecordLockedException) {
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
