/***********************************************************************
 * $Date: 2018-08-09 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.service.agencyListUpdateService;

import java.util.Arrays;
import java.util.List;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.agencyListSelectForUpdate.AgencyListSelectForUpdateReqDto;
import mm.aeon.com.ass.base.dto.agencyListSelectForUpdate.AgencyListSelectForUpdateResDto;
import mm.aeon.com.ass.base.dto.agencyListUpdate.AgencyListUpdateReqDto;
import mm.aeon.com.ass.base.dto.agencyRoleUpdate.AgencyRoleUpdateReqDto;
import mm.aeon.com.ass.base.dto.teamAgencyUpdate.TeamAgencyUpdateReqDto;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
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

public class AgencyListUpdateService extends AbstractService
        implements IService<AgencyListUpdateServiceReqBean, AgencyListUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public AgencyListUpdateServiceResBean execute(AgencyListUpdateServiceReqBean reqBean) {
        AgencyListUpdateServiceResBean resBean = new AgencyListUpdateServiceResBean();
        AgencyListUpdateServiceReqBean updateServiceReqBean = (AgencyListUpdateServiceReqBean) reqBean;
        /**
         * refer update time. it is already updated if not equal update time
         */
        AgencyListSelectForUpdateReqDto selectUpdateReqDto = new AgencyListSelectForUpdateReqDto();
        selectUpdateReqDto.setAgencyId(updateServiceReqBean.getAgencyId());

        try {

            AgencyListSelectForUpdateResDto selectUpdateResDto =
                    (AgencyListSelectForUpdateResDto) this.getDaoServiceInvoker().execute(selectUpdateReqDto);

            if (selectUpdateResDto == null) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_NOT_FOUND_ERROR);
                return resBean;
            } else if (null != selectUpdateResDto.getUpdatedTime()
                    && !selectUpdateResDto.getUpdatedTime().equals(updateServiceReqBean.getOldUpdatedDate())) {
                resBean.setServiceStatus(ASSServiceStatusCommon.RECORD_ALREADY_UPDATE);
                return resBean;
            } else {

                AgencyListUpdateReqDto reqDto = new AgencyListUpdateReqDto();

                reqDto.setAgencyName(updateServiceReqBean.getAgencyName());
                reqDto.setLocation(updateServiceReqBean.getLocation());
                reqDto.setAddress(updateServiceReqBean.getAddress());
                reqDto.setRemark(updateServiceReqBean.getRemark());
                reqDto.setIsAeon(updateServiceReqBean.getIsAeon());
                reqDto.setIsValid(ASSCommon.IS_VALID_FLAG);
                reqDto.setNonValid(ASSCommon.IS_VALID_FLAG);
                reqDto.setUpdatedBy(updateServiceReqBean.getUpdatetedBy());
                reqDto.setUpdatedDate(updateServiceReqBean.getUpdatedDate());
                reqDto.setDisabledDate(null);

                int agencyId = updateServiceReqBean.getAgencyId();
                reqDto.setAgencyId(agencyId);

                /*
                 * update data in agency-info.
                 */
                this.getDaoServiceInvoker().execute(reqDto);

                /*
                 * update data in team-agency.
                 */
                updateTeamAgency(agencyId, updateServiceReqBean);

                /*
                 * update data in agency-role.
                 */
                updateAgencyRole(agencyId, updateServiceReqBean);

                resBean.setServiceStatus(ServiceStatusCode.OK);
            }

        } catch (PrestoDBException e) {
            if (e instanceof RecordDuplicatedException) {
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
     *      Update two time to agency-team table according to team-id
     *      team-id may be two : 
     *          1. mobile-team  
     *          2. nonmobile-team     *
     * </pre>
     * 
     * </p>
     * 
     * @param agencyId
     * @param updateServiceReqBean
     */
    private void updateTeamAgency(int agencyId, AgencyListUpdateServiceReqBean updateServiceReqBean)
            throws PrestoDBException {

        TeamAgencyUpdateReqDto reqDto = new TeamAgencyUpdateReqDto();
        reqDto.setAgencyId(agencyId);
        reqDto.setIsValid(ASSCommon.IS_VALID_FLAG);
        reqDto.setNonValid(ASSCommon.IS_VALID_FLAG);
        reqDto.setUpdatedDate(updateServiceReqBean.getUpdatedDate());

        /**
         * mobile team. 1.Update new team id.
         */
        reqDto.setTeamId(Integer.parseInt(updateServiceReqBean.getMobileTeam()));
        reqDto.setOldTeamId(updateServiceReqBean.getOldMobileId());

        this.getDaoServiceInvoker().execute(reqDto);

        /**
         * non-mobile team. 1.Update new team id.
         */
        reqDto.setTeamId(Integer.parseInt(updateServiceReqBean.getNonMobileTeam()));
        reqDto.setOldTeamId(updateServiceReqBean.getOldNonMobileId());
        this.getDaoServiceInvoker().execute(reqDto);

    }

    /**
     * updateAgencyRole Method.
     * <p>
     * 
     * <pre>
     *      Update to agency-role table for category role
     *      role-id may be four : 
     *          1. mobile  
     *          2. non-mobile
     *          3. personal-loan
     *          4. motorcycle-loan
     * 
     * </pre>
     * 
     * </p>
     * 
     * @param agencyId
     * @param updateServiceReqBean
     */
    private void updateAgencyRole(int agencyId, AgencyListUpdateServiceReqBean updateServiceReqBean)
            throws PrestoDBException {

        List<String> newRole = Arrays.asList(updateServiceReqBean.getRole());
        List<String> oldRole = Arrays.asList(updateServiceReqBean.getOldRoleList());

        for (int roleId = 1; roleId < 5; roleId++) {

            AgencyRoleUpdateReqDto reqDto = new AgencyRoleUpdateReqDto();
            reqDto.setAgencyId(agencyId);
            reqDto.setRoleId(roleId);
            reqDto.setUpdatedTime(CommonUtil.getCurrentTimeStamp());

            // if roleId doesn't contain in new roleList, change isValid from 1 to 0
            if (!newRole.contains(Integer.toString(roleId)) && oldRole.contains(Integer.toString(roleId))) {
                reqDto.setIsValid(ASSCommon.NOT_VALID);
                this.getDaoServiceInvoker().execute(reqDto);
            }
            // if roleId contains in new roleList, change isvalid from 0 to 1
            else if (newRole.contains(Integer.toString(roleId)) && !oldRole.contains(Integer.toString(roleId))) {
                reqDto.setIsValid(ASSCommon.IS_VALID);
                this.getDaoServiceInvoker().execute(reqDto);
            }
        }
    }
}
