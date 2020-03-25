/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.operatorTeamUpdateService;

import mm.aeon.com.ass.base.dto.operatorTeamSelectForUpdate.OperatorTeamSelectForUpdateReqDto;
import mm.aeon.com.ass.base.dto.operatorTeamSelectForUpdate.OperatorTeamSelectForUpdateResDto;
import mm.aeon.com.ass.base.dto.operatorTeamUpdate.OperatorTeamUpdateReqDto;
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

public class OperatorTeamUpdateService extends AbstractService
        implements IService<OperatorTeamUpdateServiceReqBean, OperatorTeamUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public OperatorTeamUpdateServiceResBean execute(OperatorTeamUpdateServiceReqBean reqBean) {

        OperatorTeamUpdateServiceResBean resBean = new OperatorTeamUpdateServiceResBean();

        OperatorTeamSelectForUpdateReqDto selectForUpdateReqDto = new OperatorTeamSelectForUpdateReqDto();
        selectForUpdateReqDto.setTeamOperatorId(reqBean.getTeamOperatorId());
        try {

            OperatorTeamSelectForUpdateResDto selectForUpdateResDto =
                    (OperatorTeamSelectForUpdateResDto) this.getDaoServiceInvoker().execute(selectForUpdateReqDto);

            if (null == selectForUpdateResDto) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_NOT_FOUND_ERROR);
            } else {
                OperatorTeamUpdateReqDto reqDto = new OperatorTeamUpdateReqDto();
                reqDto.setId(reqBean.getTeamOperatorId());
                reqDto.setTeamId(reqBean.getTeamId());
                reqDto.setUserId(reqBean.getUserId());
                reqDto.setValidStatus(reqBean.getValidStatus());
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
