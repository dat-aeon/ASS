/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.outletList;

import java.util.ArrayList;
import java.util.List;

import mm.aeon.com.ass.base.dto.outletSearch.OutletSearchReqDto;
import mm.aeon.com.ass.base.dto.outletSearch.OutletSearchResDto;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.exception.InvalidScreenTransitionException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;
import mm.com.dat.presto.utils.common.InputChecker;

public class OutletListController extends AbstractController
        implements IControllerAccessor<OutletListFormBean, OutletListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public OutletListFormBean process(OutletListFormBean formBean) {

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Outlet List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        applicationLogger.log("Operator searching process stared.", LogLevel.INFO);
        MessageBean messageBean;

        OutletSearchReqDto reqDto = new OutletSearchReqDto();
        if (null != formBean.getSearchHeaderBean().getValidStatus()) {
            reqDto.setValidStatus(CommonUtil.convertInteger(formBean.getSearchHeaderBean().getValidStatus()));
        }

        if (!InputChecker.isBlankOrNull(formBean.getSearchHeaderBean().getAgencyName())) {
            reqDto.setAgencyName(formBean.getSearchHeaderBean().getAgencyName().toLowerCase());
        }
        if (!InputChecker.isBlankOrNull(formBean.getSearchHeaderBean().getOutletName())) {
            reqDto.setOutletName(formBean.getSearchHeaderBean().getOutletName().toLowerCase());
        }

        try {
            List<OutletSearchResDto> resDtoList =
                    (List<OutletSearchResDto>) this.getDaoServiceInvoker().execute(reqDto);
            List<OutletListLineBean> lineBeanList = new ArrayList<OutletListLineBean>();

            for (OutletSearchResDto resDto : resDtoList) {
                OutletListLineBean lineBean = new OutletListLineBean();

                lineBean.setAddress(resDto.getAddress());
                lineBean.setAgencyId(resDto.getAgencyId());
                lineBean.setAgencyLocation(resDto.getAgencyLocation());
                lineBean.setAgencyOutletId(resDto.getAgencyOutletId());
                lineBean.setAgencyName(resDto.getAgencyName());
                lineBean.setAgnecyOutletUpdatedTime(resDto.getAgencyOutletUpdatedTime());
                lineBean.setCreatedBy(resDto.getCreatedBy());
                lineBean.setImagePath(resDto.getImagePath());
                lineBean.setCreatedTime(resDto.getCreatedTime());
                lineBean.setOutletId(resDto.getOutletId());
                if (resDto.getOutletName().contains(ASSCommon.OUT_HEAD_OFFICE)) {
                    lineBean.setOutletName(ASSCommon.OUT_HEAD_OFFICE);
                    lineBean.setIsHeadOffice(true);
                } else {
                    lineBean.setOutletName(resDto.getOutletName());
                    lineBean.setIsHeadOffice(false);
                }
                lineBean.setRemark(resDto.getRemark());
                lineBean.setPhoneNo(resDto.getPhoneNo());
                lineBean.setLongitude(resDto.getLongitude());
                lineBean.setLatitude(resDto.getLatitude());
                lineBean.setUpdatedBy(resDto.getUpdatedBy());
                lineBean.setUpdatedTime(resDto.getUpdatedTime());
                lineBean.setValidStatus(resDto.getValidStatus());
                lineBean.setMobileTeamName(resDto.getMobileTeamName());
                lineBean.setNonMobileTeamName(resDto.getNonMobileTeamName());

                lineBeanList.add(lineBean);
            }

            formBean.setLineBeanList(lineBeanList);

            if (lineBeanList.size() == 0) {
                messageBean = new MessageBean(MessageId.MI0008);
            } else {
                messageBean = new MessageBean(MessageId.MI0007, String.valueOf(lineBeanList.size()));
            }
            messageBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(messageBean);
            applicationLogger.log(messageBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Operator searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
