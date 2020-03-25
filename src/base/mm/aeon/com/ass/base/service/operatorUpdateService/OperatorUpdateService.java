/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.operatorUpdateService;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.operatorSelectForUpdate.OperatorSelectForUpdateReqDto;
import mm.aeon.com.ass.base.dto.operatorSelectForUpdate.OperatorSelectForUpdateResDto;
import mm.aeon.com.ass.base.dto.operatorUpdate.OperatorUpdateReqDto;
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

public class OperatorUpdateService extends AbstractService
        implements IService<OperatorUpdateServiceReqBean, OperatorUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public OperatorUpdateServiceResBean execute(OperatorUpdateServiceReqBean reqBean) {

        OperatorUpdateServiceResBean resBean = new OperatorUpdateServiceResBean();

        OperatorSelectForUpdateReqDto selectForUpdateReqDto = new OperatorSelectForUpdateReqDto();
        selectForUpdateReqDto.setUserId(reqBean.getUserId());

        try {
            OperatorSelectForUpdateResDto selectForUpdateResDto =
                    (OperatorSelectForUpdateResDto) this.getDaoServiceInvoker().execute(selectForUpdateReqDto);

            if (null == selectForUpdateResDto) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_NOT_FOUND_ERROR);
            } else if (null != selectForUpdateResDto.getUpdatedTime()
                    && !selectForUpdateResDto.getUpdatedTime().equals(reqBean.getUpdateTime())) {
                resBean.setServiceStatus(ASSServiceStatusCommon.RECORD_ALREADY_UPDATE);
            } else {
                OperatorUpdateReqDto reqDto = new OperatorUpdateReqDto();

                reqDto.setUserId(reqBean.getUserId());
                reqDto.setUpdatedBy(reqBean.getUpdatedBy());
                reqDto.setUpdatedTime(CommonUtil.getCurrentTimeStamp());

                if (!reqBean.isForToggleValid()) {
                    reqDto.setPassword(reqBean.getPassword());
                    reqDto.setUserLoginId(reqBean.getUserLoginId());
                    reqDto.setUserName(reqBean.getUserName());
                } else {
                    reqDto.setValidStatus(reqBean.getValidStatus());
                    reqDto.setDisabledBy(reqBean.getUpdatedBy());
                }

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
