/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.outletUpdateService;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.outletSelectForUpdate.OutletSelectForUpdateReqDto;
import mm.aeon.com.ass.base.dto.outletSelectForUpdate.OutletSelectForUpdateResDto;
import mm.aeon.com.ass.base.dto.outletUpdate.OutletUpdateReqDto;
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

public class OutletUpdateService extends AbstractService
        implements IService<OutletUpdateServiceReqBean, OutletUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public OutletUpdateServiceResBean execute(OutletUpdateServiceReqBean reqBean) {
        OutletUpdateServiceResBean resBean = new OutletUpdateServiceResBean();

        OutletSelectForUpdateReqDto selectForUpdateReqDto = new OutletSelectForUpdateReqDto();
        selectForUpdateReqDto.setOutletId(reqBean.getOutletId());

        try {
            OutletSelectForUpdateResDto selectForUpdateResDto =
                    (OutletSelectForUpdateResDto) this.getDaoServiceInvoker().execute(selectForUpdateReqDto);

            if (null == selectForUpdateResDto) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_NOT_FOUND_ERROR);
            } else if (null != selectForUpdateResDto.getUpdatedTime()
                    && !selectForUpdateResDto.getUpdatedTime().equals(reqBean.getUpdatedTime())) {
                resBean.setServiceStatus(ASSServiceStatusCommon.RECORD_ALREADY_UPDATE);
            } else {
                OutletUpdateReqDto reqDto = new OutletUpdateReqDto();

                reqDto.setAddress(reqBean.getAddress());
                reqDto.setOutletId(reqBean.getOutletId());
                reqDto.setOutletName(reqBean.getOutletName());
                reqDto.setRemark(reqBean.getRemark());
                reqDto.setImagePath(reqBean.getImagePath());
                reqDto.setPhoneNo(reqBean.getPhoneNo());
                reqDto.setLongitude(reqBean.getLongitude());
                reqDto.setLatitude(reqBean.getLatitude());
                reqDto.setUpdatedBy(reqBean.getUpdatedBy());
                reqDto.setUpdatedTime(CommonUtil.getCurrentTimeStamp());
                reqDto.setValidStatus(reqBean.getValidStatus());

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
