/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.operatorTeamRegisterService;

import mm.aeon.com.ass.base.dto.operatorTeamRegister.OperatorTeamRegisterReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class OperatorTeamRegisterService extends AbstractService
        implements IService<OperatorTeamRegisterServiceReqBean, OperatorTeamRegisterServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public OperatorTeamRegisterServiceResBean execute(OperatorTeamRegisterServiceReqBean reqBean) {

        OperatorTeamRegisterServiceResBean resBean = new OperatorTeamRegisterServiceResBean();
        OperatorTeamRegisterReqDto reqDto = new OperatorTeamRegisterReqDto();

        reqDto.setTeamId(reqBean.getTeamId());
        reqDto.setUserId(reqBean.getUserId());
        reqDto.setValidStatus(reqBean.getValidStatus());
        reqDto.setUpdatedTime(reqBean.getUpdatedTime());

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
