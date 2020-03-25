/***********************************************************************
 * $Date : 2018-08-13 $
 * $Author : Thar Pyae $
 * $Rev : 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
/***********************************************************************
 * $Modified Date : 2018-08-13 $
 * $Modified By : Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.service.agencyListDeleteService;

import java.util.ArrayList;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.agencyListDelete.AgencyListDeleteReqDto;
import mm.aeon.com.ass.base.dto.agencyOutletSelectList.AgencyOutletSelectListReqDto;
import mm.aeon.com.ass.base.dto.agencyOutletSelectList.AgencyOutletSelectListResDto;
import mm.aeon.com.ass.base.dto.agencyRoleDelete.AgencyRoleDeleteReqDto;
import mm.aeon.com.ass.base.dto.outletAgencyDelete.OutletAgencyDeleteReqDto;
import mm.aeon.com.ass.base.dto.outletDelete.OutletDeleteReqDto;
import mm.aeon.com.ass.base.dto.teamAgencyDelete.TeamAgencyDeleteReqDto;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.ForeignKeyConstraintException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.LogLevel;

public class AgencyListDeleteService extends AbstractService
        implements IService<AgencyListDeleteServiceReqBean, AgencyListDeleteServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public AgencyListDeleteServiceResBean execute(AgencyListDeleteServiceReqBean deleteServiceReqBean) {

        AgencyListDeleteReqDto reqDto = new AgencyListDeleteReqDto();
        AgencyListDeleteServiceResBean resBean = new AgencyListDeleteServiceResBean();

        reqDto.setAgencyId(deleteServiceReqBean.getAgencyId());
        try {

            AgencyOutletSelectListReqDto selectListReqDto = new AgencyOutletSelectListReqDto();
            selectListReqDto.setAgencyId(deleteServiceReqBean.getAgencyId());
            selectListReqDto.setIsValid(1);

            ArrayList<AgencyOutletSelectListResDto> resDtoList =
                    (ArrayList<AgencyOutletSelectListResDto>) this.getDaoServiceInvoker().execute(selectListReqDto);

            if (resDtoList != null && resDtoList.size() > 1) {
                resBean.setServiceStatus(ASSServiceStatusCommon.FOREIGN_KEY_CONSTRAINT_ERROR);
                return resBean;
            } else {
                if (resDtoList.get(0).getOutletName().contains(ASSCommon.OUT_HEAD_OFFICE)) {

                    /*
                     * Delete data in agency-outlet.
                     */
                    deleteHeadOfficeAgencyOutlet(resDtoList.get(0).getId());
                    
                    /*
                     * Delete data in outlet.
                     */
                    deleteHeadOfficeOutlet(resDtoList.get(0).getOutletId());

                } else {
                    resBean.setServiceStatus(ASSServiceStatusCommon.FOREIGN_KEY_CONSTRAINT_ERROR);
                    return resBean;
                }
            }

            /*
             * Delete data in team-agency.
             */
            deleteTeamAgency(deleteServiceReqBean.getAgencyId());

            /*
             * Delete data in agency-role.
             */
            deleteAgencyRole(deleteServiceReqBean.getAgencyId());

            /*
             * Delete data in agency-info.
             */
            this.getDaoServiceInvoker().execute(reqDto);

            resBean.setServiceStatus(ServiceStatusCode.OK);

        } catch (PrestoDBException e) {
            if (e instanceof ForeignKeyConstraintException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                resBean.setServiceStatus(ASSServiceStatusCommon.FOREIGN_KEY_CONSTRAINT_ERROR);
            } else {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return resBean;
    }

    /**
     * deleteTeamAgency Method.
     * <p>
     * 
     * <pre>
     *      Delete data according to agencyId.
     * 
     * </pre>
     * 
     * </p>
     * 
     * @param agencyId
     */
    private void deleteTeamAgency(int agencyId) throws PrestoDBException {

        AgencyRoleDeleteReqDto reqDto = new AgencyRoleDeleteReqDto();
        reqDto.setAgencyId(agencyId);
        this.getDaoServiceInvoker().execute(reqDto);

    }

    /**
     * deleteAgencyRole Method.
     * <p>
     * 
     * <pre>
     *      Delete data on AGENGY-ROLE table according to agencyId.
     * 
     * </pre>
     * 
     * </p>
     * 
     * @param agencyId
     */
    private void deleteAgencyRole(int agencyId) throws PrestoDBException {

        TeamAgencyDeleteReqDto reqDto = new TeamAgencyDeleteReqDto();
        reqDto.setAgencyId(agencyId);
        this.getDaoServiceInvoker().execute(reqDto);

    }

    /**
     * deleteHeadOfficeOutlet Method.
     * <p>
     * 
     * <pre>
     *      Delete data on OUTLET table according to agencyId for only head-office.
     * 
     * </pre>
     * 
     * </p>
     * 
     * @param outletId
     */
    private void deleteHeadOfficeOutlet(int outletId) throws PrestoDBException {

        OutletDeleteReqDto reqDto = new OutletDeleteReqDto();
        reqDto.setOutletId(outletId);
        this.getDaoServiceInvoker().execute(reqDto);

    }

    /**
     * deleteHeadOfficeAgencyOutlet Method.
     * <p>
     * 
     * <pre>
     *      Delete data on AGENGY-OUTLET table according to agencyId for only head-office.
     * 
     * </pre>
     * 
     * </p>
     * 
     * @param agencyId,
     *            outletId
     */
    private void deleteHeadOfficeAgencyOutlet(int agencyOutletId) throws PrestoDBException {

        OutletAgencyDeleteReqDto reqDto = new OutletAgencyDeleteReqDto();
        reqDto.setAgencyOutletId(agencyOutletId);
        this.getDaoServiceInvoker().execute(reqDto);
    }
}
