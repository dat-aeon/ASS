/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.teamRegisterService;

import mm.aeon.com.ass.base.dto.teamRegister.TeamRegisterReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class TeamRegisterService extends AbstractService
        implements IService<TeamRegisterServiceReqBean, TeamRegisterServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public TeamRegisterServiceResBean execute(TeamRegisterServiceReqBean reqBean) {

        TeamRegisterServiceResBean resBean = new TeamRegisterServiceResBean();
        TeamRegisterReqDto reqDto = new TeamRegisterReqDto();

        reqDto.setCreatedBy(reqBean.getCreatedBy());
        reqDto.setCreatedTime(reqBean.getCreatedTime());
        reqDto.setTarget(reqBean.getTarget());
        reqDto.setTeamName(reqBean.getTeamName());
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
