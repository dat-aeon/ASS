/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.operatorRegisterService;

import mm.aeon.com.ass.base.dto.operatorRegister.OperatorRegisterReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class OperatorRegisterService extends AbstractService
        implements IService<OperatorRegisterServiceReqBean, OperatorRegisterServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public OperatorRegisterServiceResBean execute(OperatorRegisterServiceReqBean reqBean) {

        OperatorRegisterServiceResBean resBean = new OperatorRegisterServiceResBean();
        OperatorRegisterReqDto reqDto = new OperatorRegisterReqDto();

        reqDto.setCreatedBy(reqBean.getCreatedBy());
        reqDto.setCreatedTime(reqBean.getCreatedTime());
        reqDto.setPassword(reqBean.getPassword());
        reqDto.setRole(reqBean.getRole());
        reqDto.setUserLoginId(reqBean.getUserLoginId());
        reqDto.setUserName(reqBean.getUserName());
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
