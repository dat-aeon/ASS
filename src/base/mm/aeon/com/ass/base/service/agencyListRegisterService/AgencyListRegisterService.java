/***********************************************************************
 * $Date: 2018-08-02 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.service.agencyListRegisterService;

import java.util.Arrays;
import java.util.List;

import mm.aeon.com.ass.base.dto.agencyIdRefer.AgencyIdReferReqDto;
import mm.aeon.com.ass.base.dto.agencyIdRefer.AgencyIdReferResDto;
import mm.aeon.com.ass.base.dto.agencyListRegister.AgencyListRegisterReqDto;
import mm.aeon.com.ass.base.dto.agencyRoleInsert.AgencyRoleInsertReqDto;
import mm.aeon.com.ass.base.dto.outletAgencyRegister.OutletAgencyRegisterReqDto;
import mm.aeon.com.ass.base.dto.outletRefer.OutletReferReqDto;
import mm.aeon.com.ass.base.dto.outletRefer.OutletReferResDto;
import mm.aeon.com.ass.base.dto.outletRegister.OutletRegisterReqDto;
import mm.aeon.com.ass.base.dto.teamAgencyRegister.TeamAgencyRegisterReqDto;
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

public class AgencyListRegisterService extends AbstractService
        implements IService<AgencyListRegisterServiceReqBean, AgencyListRegisterServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public AgencyListRegisterServiceResBean execute(AgencyListRegisterServiceReqBean reqBean) {

        AgencyListRegisterReqDto reqDto = new AgencyListRegisterReqDto();
        AgencyListRegisterServiceReqBean insertServiceReqBean = (AgencyListRegisterServiceReqBean) reqBean;
        AgencyListRegisterServiceResBean resBean = new AgencyListRegisterServiceResBean();

        reqDto.setAgencyName(insertServiceReqBean.getAgencyName());
        reqDto.setLocation(insertServiceReqBean.getLocation());
        reqDto.setAddress(insertServiceReqBean.getAddress());
        reqDto.setRemark(insertServiceReqBean.getRemark());
        reqDto.setIsValid(ASSCommon.IS_VALID);
        reqDto.setIsAeon(insertServiceReqBean.getIsAeon());
        reqDto.setCreatedBy(insertServiceReqBean.getCreatedBy());
        reqDto.setCreatedDate(insertServiceReqBean.getCreatedDate());
        reqDto.setUpdatedDate(insertServiceReqBean.getUpdatedDate());

        try {
            /*
             * Insert data to agency-info.
             */
            this.getDaoServiceInvoker().execute(reqDto);

            /**
             * Refer agency_id. for insert data in team-agency.
             */
            int agencyId = referAgencyId(insertServiceReqBean.getAgencyName());

            /*
             * insert data to team-agency.
             */
            if (agencyId != ASSCommon.NOT_VALID) {
                insertTeamAgency(agencyId, insertServiceReqBean);
            } else {
                resBean.setServiceStatus(ServiceStatusCode.SQL_ERROR);
                return resBean;
            }

            /*
             * insert data to team-agency.
             */
            insertAgencyRole(agencyId, insertServiceReqBean);

            /*
             * insert default head-office data to outlet.
             */
            insertHeadOfficeOutlet(agencyId, insertServiceReqBean);

            /*
             * insert default head-office data to outlet-agency.
             */
            insertHeadOfficeAgencyOutlet(agencyId, insertServiceReqBean);

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

    /**
     * insertTeamAgency Method.
     * <p>
     * 
     * <pre>
     *      Insert two time to agency-team table according to team-id
     *      team-id may be two : 
     *          1. mobile-team  
     *          2. nonmobile-team
     * 
     * </pre>
     * 
     * </p>
     * 
     * @param agencyId
     * @param insertServiceReqBean
     */
    private void insertTeamAgency(int agencyId, AgencyListRegisterServiceReqBean insertServiceReqBean)
            throws PrestoDBException {

        TeamAgencyRegisterReqDto reqDto = new TeamAgencyRegisterReqDto();
        reqDto.setAgencyId(agencyId);
        reqDto.setIsValid(ASSCommon.IS_VALID);

        /*
         * for mobile team.
         */
        reqDto.setTeamId(Integer.parseInt(insertServiceReqBean.getMobileTeam()));
        this.getDaoServiceInvoker().execute(reqDto);

        /*
         * for non-mobile team.
         */
        reqDto.setTeamId(Integer.parseInt(insertServiceReqBean.getNonMobileTeam()));
        this.getDaoServiceInvoker().execute(reqDto);

    }

    /**
     * referAgencyId Method.
     * <p>
     * 
     * <pre>
     *      only refer agency_id.
     * </pre>
     * 
     * </p>
     * 
     * @param agencyName
     * @return referID
     */
    private int referAgencyId(String agencyName) throws PrestoDBException {
        int referID = 0;
        AgencyIdReferReqDto referReqDto = new AgencyIdReferReqDto();
        AgencyIdReferResDto referResDto = new AgencyIdReferResDto();
        referReqDto.setAgencyName(agencyName);
        referResDto = (AgencyIdReferResDto) this.getDaoServiceInvoker().execute(referReqDto);
        referID = referResDto.getAgencyId();

        return referID;
    }

    /**
     * insertAgencyRole Method.
     * <p>
     * 
     * <pre>
     *      Insert to agency-role table for category role
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
     * @param insertServiceReqBean
     */
    private void insertAgencyRole(int agencyId, AgencyListRegisterServiceReqBean insertServiceReqBean)
            throws PrestoDBException {

        AgencyRoleInsertReqDto reqDto = null;
        List<String> roleList = Arrays.asList(insertServiceReqBean.getRole());

        for (int i = 1; i < 5; i++) {
            reqDto = new AgencyRoleInsertReqDto();
            reqDto.setAgencyId(agencyId);
            reqDto.setRoleId(i);
            reqDto.setUpdatedTime(CommonUtil.getCurrentTimeStamp());

            if (roleList.contains(Integer.toString(i))) {
                reqDto.setIsValid(ASSCommon.IS_VALID);
            } else {
                reqDto.setIsValid(ASSCommon.NOT_VALID);
            }

            this.getDaoServiceInvoker().execute(reqDto);
        }
    }

    /**
     * insertHeadOfficeOutlet Method.
     * <p>
     * 
     * <pre>
     *      Insert to outlet table for head-office
     * 
     * 
     * </pre>
     * 
     * </p>
     * 
     * @param agencyId
     * @param insertServiceReqBean
     */
    private void insertHeadOfficeOutlet(int agencyId, AgencyListRegisterServiceReqBean insertServiceReqBean)
            throws PrestoDBException {

        OutletRegisterReqDto reqDto = new OutletRegisterReqDto();
        reqDto.setOutletName(agencyId + ASSCommon.BLANK + ASSCommon.OUT_HEAD_OFFICE);
        reqDto.setAddress(insertServiceReqBean.getAddress());
        reqDto.setValidStatus(1);
        reqDto.setRemark(ASSCommon.AUTO_CREATED);
        reqDto.setCreatedBy(insertServiceReqBean.getCreatedBy());
        reqDto.setCreatedTime(insertServiceReqBean.getCreatedDate());

        this.getDaoServiceInvoker().execute(reqDto);

    }

    /**
     * insertHeadOfficeAgencyOutlet Method.
     * <p>
     * 
     * <pre>
     *      Insert to agency-outlet table for head-office.
     * 
     * 
     * </pre>
     * 
     * </p>
     * 
     * @param agencyId
     * @param insertServiceReqBean
     */
    private void insertHeadOfficeAgencyOutlet(int agencyId, AgencyListRegisterServiceReqBean insertServiceReqBean)
            throws PrestoDBException {

        OutletReferReqDto referReqDto = new OutletReferReqDto();
        referReqDto.setOutletName(agencyId + ASSCommon.BLANK + ASSCommon.OUT_HEAD_OFFICE);
        OutletReferResDto referResDto = (OutletReferResDto) this.getDaoServiceInvoker().execute(referReqDto);

        if (referResDto != null) {
            OutletAgencyRegisterReqDto reqDto = new OutletAgencyRegisterReqDto();
            reqDto.setAgencyId(agencyId);
            reqDto.setOutletId(referResDto.getOutletId());
            reqDto.setValidStatus(1);
            reqDto.setUpdatedTime(CommonUtil.getCurrentTimeStamp());

            this.getDaoServiceInvoker().execute(reqDto);
        }
    }
}
