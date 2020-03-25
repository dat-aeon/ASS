/**************************************************************************
 * $Date : 2018-08-17$
 * $Author : Thar Pyae $
 * $Rev : 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.judgementStatusList;

import java.util.ArrayList;

import mm.aeon.com.ass.base.dto.judgementStatusSelectList.JudgementStatusSelectListReqDto;
import mm.aeon.com.ass.base.dto.judgementStatusSelectList.JudgementStatusSelectListResDto;
import mm.aeon.com.ass.front.common.abstractController.AbstractASSController;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.exception.ASSBusinessException;
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

public class JudgementStatusListController extends AbstractASSController
        implements IControllerAccessor<JudgementStatusListFormBean, JudgementStatusListFormBean> {

    private ASSLogger logger = new ASSLogger();
    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public JudgementStatusListFormBean process(JudgementStatusListFormBean formBean) {

        applicationLogger.log("Judgement Status list Initialization Process started.", LogLevel.INFO);
        MessageBean msgBean;

        formBean.getMessageContainer().clearAllMessages(true);

        if (!(ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())
                || ASSCommon.ZERO.equals(CommonUtil.getLoginUserInfo().getUserTypeName()))) {
            logger.log("Invalid URL Access.[Judgement Status List]", new InvalidScreenTransitionException(),
                    LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        ArrayList<JudgementStatusListLineBean> allDataList = new ArrayList<JudgementStatusListLineBean>();
        JudgementStatusSelectListReqDto reqDto = new JudgementStatusSelectListReqDto();
        reqDto.setStatus(formBean.getHeaderBean().getStatus());
        reqDto.setFromDate(formBean.getHeaderBean().getFromDate());
        reqDto.setToDate(formBean.getHeaderBean().getToDate());
        reqDto.setIsUpload(false);

        ArrayList<JudgementStatusSelectListResDto> resSeachList;
        try {
            resSeachList =
                    (ArrayList<JudgementStatusSelectListResDto>) CommonUtil.getDaoServiceInvoker().execute(reqDto);

            for (JudgementStatusSelectListResDto resDto : resSeachList) {

                JudgementStatusListLineBean allDataBean = new JudgementStatusListLineBean();
                allDataBean.setUploadDate(CommonUtil.getChangeTimestampToString(resDto.getUploadDate()));
                allDataBean.setAgencyName(resDto.getAgencyName());
                if (resDto.getOutletName().contains(ASSCommon.OUT_HEAD_OFFICE)) {
                    allDataBean.setOutletName(ASSCommon.OUT_HEAD_OFFICE);
                } else {
                    allDataBean.setOutletName(resDto.getOutletName());
                }
                
                allDataBean.setUserName(resDto.getUserName());
                allDataBean.setApplicationNo(resDto.getApplicationNo());
                if (2 == resDto.getStatus()) {
                    allDataBean.setStatus(ASSCommon.JD_STATUS_APPROVE);
                    allDataBean.setDate(CommonUtil.getChangeTimestampToString(resDto.getApproveDate()));

                } else if (3 == resDto.getStatus()) {
                    allDataBean.setStatus(ASSCommon.JD_STATUS_REJECT);
                    allDataBean.setDate(CommonUtil.getChangeTimestampToString(resDto.getApplyDate()));

                } else if (4 == resDto.getStatus()) {
                    allDataBean.setStatus(ASSCommon.JD_STATUS_CANCEL);
                    allDataBean.setDate(CommonUtil.getChangeTimestampToString(resDto.getApplyDate()));

                }
                allDataList.add(allDataBean);
            }
            formBean.setLineBeanList(allDataList);
            formBean.getHeaderBean().setRecord(CommonUtil.showRecord(resSeachList.size()));

            msgBean = new MessageBean(MessageId.MI0007, Integer.toString(resSeachList.size()));
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            } else {
                throw new ASSBusinessException(e.getCause());
            }
        }

        //formBean.setLineBeanList(allDataList);
        applicationLogger.log("Judgement Status List Initialization Process finished.", LogLevel.INFO);
        return formBean;
    }

}
