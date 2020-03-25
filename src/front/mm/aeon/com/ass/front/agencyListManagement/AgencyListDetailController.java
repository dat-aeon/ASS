/***********************************************************************
 * $Date: 2018-08-14 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.front.agencyListManagement;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import mm.aeon.com.ass.base.dto.agencyListDetail.AgencyListDetailOutletReqDto;
import mm.aeon.com.ass.base.dto.agencyListDetail.AgencyListDetailOutletResDto;
import mm.aeon.com.ass.base.dto.agencyListDetail.AgencyListDetailReqDto;
import mm.aeon.com.ass.base.dto.agencyListDetail.AgencyListDetailResDto;
import mm.aeon.com.ass.base.dto.agencyListDetail.AgencyListDetailUserReqDto;
import mm.aeon.com.ass.base.dto.agencyListDetail.AgencyListDetailUserResDto;
import mm.aeon.com.ass.front.common.abstractController.AbstractASSController;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.exception.InvalidScreenTransitionException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

/**
 * 
 * AgencyListDetailController Class.
 * <p>
 * 
 * <pre>
 *      refer agency Detail data first.
 *          if data exist => (1) refer outlet data and
 *                           (2) refer user data.
 *          otherwise error message return.
 * </pre>
 * 
 * </p>
 */
public class AgencyListDetailController extends AbstractASSController
        implements IControllerAccessor<AgencyListManagementFormBean, AgencyListManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();
    MessageBean msgBean;

    @Override
    public AgencyListManagementFormBean process(AgencyListManagementFormBean formBean) {

        applicationLogger.log("Agency Detail Process started.", LogLevel.INFO);

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Agency Detail]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        AgencyListDetailResDto agencyDetailList = null;
        AgencyListDetailReqDto agencyListDetailReqDto = new AgencyListDetailReqDto();
        agencyListDetailReqDto.setAgencyId(formBean.getDetailParam().getAgencyId());

        try {
            /*
             * refer agencyDetail data
             */
            agencyDetailList = (AgencyListDetailResDto) this.getDaoServiceInvoker().execute(agencyListDetailReqDto);

            if (!agencyDetailList.equals(null)) {

                AgencyListManagementHeaderBean agencyListManagementHeaderBean = new AgencyListManagementHeaderBean();
                agencyListManagementHeaderBean.setAgencyName(agencyDetailList.getAgencyName());
                agencyListManagementHeaderBean.setLocation(CommonUtil.changeLocation(agencyDetailList.getLocation()));
                agencyListManagementHeaderBean.setAddress(agencyDetailList.getAddress());
                agencyListManagementHeaderBean.setRemark(agencyDetailList.getRemark());
                agencyListManagementHeaderBean.setIsAeon(agencyDetailList.getIsAeon());
                agencyListManagementHeaderBean.setCreatedBy(agencyDetailList.getCreatedBy());
                agencyListManagementHeaderBean
                        .setCreatedDateShow(changeDateToYMDSlashString(agencyDetailList.getCreatedDate()));
                agencyListManagementHeaderBean.setUpdatedBy(agencyDetailList.getUpdatedBy());
                agencyListManagementHeaderBean.setMobileTeamId(agencyDetailList.getMobileId());
                agencyListManagementHeaderBean.setMobileTeam(agencyDetailList.getMobileTeam());
                agencyListManagementHeaderBean.setNonMobileTeamId(agencyDetailList.getNonMobileId());
                agencyListManagementHeaderBean.setNonMobileTeam(agencyDetailList.getNonMobileTeam());
                String[] roleList = super.getSelectedRoleList(formBean.getDetailParam().getAgencyId(), true);

                agencyListManagementHeaderBean.setSelectedRole(roleList);

                if (agencyDetailList.getUpdatedBy() != null) {
                    agencyListManagementHeaderBean
                            .setUpdDateShow(changeDateToYMDSlashString(agencyDetailList.getUpdatedDate()));
                }
                formBean.setAgencyListManagementHeaderBean(agencyListManagementHeaderBean);

                List<AgencyOutletDetailLineBean> lineBeanList = new ArrayList<AgencyOutletDetailLineBean>();
                List<AgencyListDetailOutletResDto> resOutletNaemList;
                List<AgencyListDetailUserResDto> resUserNameList;

                AgencyListDetailOutletReqDto reqDto = new AgencyListDetailOutletReqDto();
                reqDto.setAgencyId(formBean.getDetailParam().getAgencyId());

                AgencyListDetailUserReqDto userReqDto = new AgencyListDetailUserReqDto();
                userReqDto.setAgencyId(formBean.getDetailParam().getAgencyId());

                AgencyListDetailReqDto teamReqDto = new AgencyListDetailReqDto();
                teamReqDto.setAgencyId(formBean.getDetailParam().getAgencyId());

                /*
                 * for outlet name.
                 */
                resOutletNaemList = (List<AgencyListDetailOutletResDto>) this.getDaoServiceInvoker().execute(reqDto);

                for (AgencyListDetailOutletResDto resDto : resOutletNaemList) {
                    AgencyOutletDetailLineBean lineBean = new AgencyOutletDetailLineBean();
                    if (resDto.getOutletName().contains(ASSCommon.OUT_HEAD_OFFICE)) {
                        lineBean.setOutletName(ASSCommon.OUT_HEAD_OFFICE);
                    } else {
                        lineBean.setOutletName(resDto.getOutletName());
                    }
                    lineBean.setOutletId(resDto.getOutletId());
                    lineBeanList.add(lineBean);
                }
                /*
                 * for user name.
                 */
                resUserNameList = (List<AgencyListDetailUserResDto>) this.getDaoServiceInvoker().execute(userReqDto);
                for (AgencyListDetailUserResDto resDto : resUserNameList) {
                    AgencyOutletDetailLineBean lineBean = new AgencyOutletDetailLineBean();
                    lineBean.setUserId(resDto.getUserId());
                    lineBean.setUserName(resDto.getUserName());
                    lineBeanList.add(lineBean);
                }

                formBean.setOutletNameLineBeanList(lineBeanList);

            } else {
                msgBean = new MessageBean(MessageId.MI0008);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                applicationLogger.log("Chang Valid  Process data is not found.", LogLevel.ERROR);
            }

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        applicationLogger.log("Outletdetail process finished.", LogLevel.INFO);
        return formBean;

    }

    /**
     * changeDateToYMDSlashString Method.
     * <p>
     * 
     * <pre>
     *      change format to "2010-08-31 01:30"
     * </pre>
     * 
     * </p>
     * 
     * @param createdDate
     * @return
     */
    private String changeDateToYMDSlashString(Timestamp createdDate) {

        if (createdDate == null) {
            return null;
        } else {
            DateFormat format = new SimpleDateFormat(ASSCommon.YYYYMMDD_HH_MM);
            return format.format(createdDate);
        }
    }
}
