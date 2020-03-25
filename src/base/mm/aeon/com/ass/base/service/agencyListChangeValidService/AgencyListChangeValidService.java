/***********************************************************************
 * $Date: 2018-08-10 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.service.agencyListChangeValidService;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.agencyListSelectForUpdate.AgencyListSelectForUpdateReqDto;
import mm.aeon.com.ass.base.dto.agencyListSelectForUpdate.AgencyListSelectForUpdateResDto;
import mm.aeon.com.ass.base.dto.agencyListUpdate.AgencyListUpdateReqDto;
import mm.aeon.com.ass.base.dto.teamAgencyUpdate.TeamAgencyUpdateReqDto;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PhysicalRecordLockedException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.LogLevel;

public class AgencyListChangeValidService extends AbstractService
        implements IService<AgencyListChangeValidServiceReqBean, AgencyListChangeValidServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public AgencyListChangeValidServiceResBean execute(AgencyListChangeValidServiceReqBean reqBean) {

        AgencyListChangeValidServiceReqBean changeValidServiceReqBean = (AgencyListChangeValidServiceReqBean) reqBean;
        AgencyListChangeValidServiceResBean resBean = new AgencyListChangeValidServiceResBean();

        /**
         * refer update time. it is already updated if not equal update time
         */
        AgencyListSelectForUpdateReqDto selectUpdateReqDto = new AgencyListSelectForUpdateReqDto();
        selectUpdateReqDto.setAgencyId(changeValidServiceReqBean.getAgencyId());

        try {

            AgencyListSelectForUpdateResDto selectUpdateResDto =
                    (AgencyListSelectForUpdateResDto) this.getDaoServiceInvoker().execute(selectUpdateReqDto);

            if (selectUpdateResDto == null) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_NOT_FOUND_ERROR);

            } else if (null != selectUpdateResDto.getDisabledTime()
                    && !selectUpdateResDto.getDisabledTime().equals(changeValidServiceReqBean.getOldDisableDate())) {
                resBean.setServiceStatus(ASSServiceStatusCommon.RECORD_ALREADY_UPDATE);

            } else {

                AgencyListUpdateReqDto reqDto = new AgencyListUpdateReqDto();

                reqDto.setAgencyId(changeValidServiceReqBean.getAgencyId());
                if (changeValidServiceReqBean.isEnable()) {
                    reqDto.setNonValid(ASSCommon.IS_VALID);
                    reqDto.setIsValid(ASSCommon.NOT_VALID);
                } else {
                    reqDto.setNonValid(ASSCommon.NOT_VALID);
                    reqDto.setIsValid(ASSCommon.IS_VALID);
                }

                reqDto.setLocation(ASSCommon.IS_VALID_FLAG);
                reqDto.setDisabledBy(changeValidServiceReqBean.getDisabledBy());
                reqDto.setDisabledDate(changeValidServiceReqBean.getDisableddDate());

                /*
                 * change valid data in agency-info.
                 */
                this.getDaoServiceInvoker().execute(reqDto);

                /*
                 * change valid data in team-agency.
                 */
                AgencyListChangeValidServiceResBean teamAgencyResBean = new AgencyListChangeValidServiceResBean();
                teamAgencyResBean = updateTeamAgency(changeValidServiceReqBean, resBean);

                if (!teamAgencyResBean.getServiceStatus().equals(ServiceStatusCode.OK)) {
                    resBean.setServiceStatus(teamAgencyResBean.getServiceStatus());
                    return resBean;
                } else {
                    resBean.setServiceStatus(ServiceStatusCode.OK);
                }
            }

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

    /**
     * updateTeamAgency Method.
     * <p>
     * 
     * <pre>
     *      Disable and Enable data according to agencyId.
     * 
     * </pre>
     * 
     * </p>
     * 
     * @param agencyId
     * @param updateServiceReqBean
     * @param resBean
     * @return resBean
     */
    private AgencyListChangeValidServiceResBean updateTeamAgency(
            AgencyListChangeValidServiceReqBean updateServiceReqBean, AgencyListChangeValidServiceResBean resBean)
            throws PrestoDBException {

        TeamAgencyUpdateReqDto reqDto = new TeamAgencyUpdateReqDto();
        reqDto.setAgencyId(updateServiceReqBean.getAgencyId());

        if (updateServiceReqBean.isEnable()) {
            reqDto.setNonValid(ASSCommon.IS_VALID);
            reqDto.setIsValid(ASSCommon.NOT_VALID);
        } else {
            reqDto.setNonValid(ASSCommon.NOT_VALID);
            reqDto.setIsValid(ASSCommon.IS_VALID);
        }
        reqDto.setUpdatedDate(updateServiceReqBean.getDisableddDate());
        this.getDaoServiceInvoker().execute(reqDto);
        resBean.setServiceStatus(ServiceStatusCode.OK);

        return resBean;
    }
}
