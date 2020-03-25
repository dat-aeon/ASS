/***********************************************************************
 * $Date: 2018-07-31 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
/***********************************************************************
 * $Date: 2018-08-31 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.front.agencyList;

import java.util.ArrayList;

import mm.aeon.com.ass.base.dto.agencyListSearch.AgencyListSearchReqDto;
import mm.aeon.com.ass.base.dto.agencyListSearch.AgencyListSearchResDto;
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

public class AgencyListSearchController extends AbstractASSController
        implements IControllerAccessor<AgencyListFormBean, AgencyListFormBean> {

    private ASSLogger logger = new ASSLogger();
    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public AgencyListFormBean process(AgencyListFormBean formBean) {

        applicationLogger.log("Agency List Searching Process started.", LogLevel.INFO);
        MessageBean msgBean;

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Agency List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        AgencyListSearchReqDto reqDto = new AgencyListSearchReqDto();
        reqDto.setAgencyName(formBean.getAgencyLisHeaderBean().getAgencyName());

        if (formBean.getAgencyLisHeaderBean().getStatus() != null) {
            reqDto.setIsValid(Integer.parseInt(formBean.getAgencyLisHeaderBean().getStatus()));
        } else {
            reqDto.setIsValid(ASSCommon.IS_VALID_FLAG);
        }

        try {
            ArrayList<AgencyListSearchResDto> resSeachList =
                    (ArrayList<AgencyListSearchResDto>) CommonUtil.getDaoServiceInvoker().execute(reqDto);

            ArrayList<AgencyListSearchLineBean> allDataList = new ArrayList<AgencyListSearchLineBean>();
            for (AgencyListSearchResDto resDto : resSeachList) {

                AgencyListSearchLineBean allDataBean = new AgencyListSearchLineBean();

                allDataBean.setAgencyId(resDto.getAgencyId());
                allDataBean.setAgencyName(resDto.getAgencyName());
                allDataBean.setLocation(CommonUtil.changeLocation(resDto.getLocation()));
                allDataBean.setAddress(resDto.getAddress());
                allDataBean.setRemark(resDto.getRemark());
                allDataBean.setIsAeon(resDto.getIsAeon());
                allDataBean.setMobileTeamId(resDto.getMobileTeamId());
                allDataBean.setMobileTeam(resDto.getMobileTeam());
                allDataBean.setNonMobileTeamId(resDto.getNonMobileTeamId());
                allDataBean.setNonMobileTeam(resDto.getNonMobileTeam());
                allDataBean.setIsValid(resDto.getIsValid());
                allDataBean.setCreatedBy(resDto.getCreatedBy());
                allDataBean.setCreatedDate(resDto.getCreatedDate());
                allDataBean.setUpdatedBy(resDto.getUpdatedBy());
                allDataBean.setAgencyUptDate(resDto.getAgencyUptDate());
                // allDataBean.setTeamAgencyUptDate(resDto.getTeamAgencyUptDate());
                allDataBean.setDisabledDate(resDto.getDisabledDate());

                allDataList.add(allDataBean);
            }
            formBean.setLineBeanList(allDataList);

            if (allDataList.size() == 0) {
                msgBean = new MessageBean(MessageId.MI0008);
            } else {
                msgBean = new MessageBean(MessageId.MI0007, String.valueOf(allDataList.size()));
            }
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);

            applicationLogger.log("Agency list Searching Process finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        return formBean;
    }
}
