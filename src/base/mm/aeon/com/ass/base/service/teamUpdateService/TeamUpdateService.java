/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.teamUpdateService;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.teamSelectForUpdate.TeamSelectForUpdateReqDto;
import mm.aeon.com.ass.base.dto.teamSelectForUpdate.TeamSelectForUpdateResDto;
import mm.aeon.com.ass.base.dto.teamUpdate.TeamUpdateReqDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class TeamUpdateService extends AbstractService
        implements IService<TeamUpdateServiceReqBean, TeamUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public TeamUpdateServiceResBean execute(TeamUpdateServiceReqBean reqBean) {

        TeamUpdateServiceResBean resBean = new TeamUpdateServiceResBean();

        TeamSelectForUpdateReqDto selectForUpdateReqDto = new TeamSelectForUpdateReqDto();
        selectForUpdateReqDto.setTeamId(reqBean.getTeamId());

        try {
            TeamSelectForUpdateResDto selectForUpdateResDto =
                    (TeamSelectForUpdateResDto) this.getDaoServiceInvoker().execute(selectForUpdateReqDto);

            if (null == selectForUpdateResDto) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_NOT_FOUND_ERROR);
            } else if (null != selectForUpdateResDto.getUpdatedTime()
                    && !selectForUpdateResDto.getUpdatedTime().equals(reqBean.getUpdatedTime())) {
                resBean.setServiceStatus(ASSServiceStatusCommon.RECORD_ALREADY_UPDATE);
            } else {
                TeamUpdateReqDto reqDto = new TeamUpdateReqDto();

                reqDto.setTeamId(reqBean.getTeamId());
                reqDto.setTarget(reqBean.getTarget());
                reqDto.setUpdatedBy(reqBean.getUpdatedBy());
                reqDto.setUpdatedTime(CommonUtil.getCurrentTimeStamp());

                this.getDaoServiceInvoker().execute(reqDto);
                resBean.setServiceStatus(ServiceStatusCode.OK);
            }

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
