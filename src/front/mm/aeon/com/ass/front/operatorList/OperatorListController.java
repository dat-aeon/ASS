/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.operatorList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mm.aeon.com.ass.base.dto.operatorSearch.OperatorSearchReqDto;
import mm.aeon.com.ass.base.dto.operatorSearch.OperatorSearchResDto;
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

public class OperatorListController extends AbstractController
        implements IControllerAccessor<OperatorListFormBean, OperatorListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public OperatorListFormBean process(OperatorListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Operator List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        applicationLogger.log("Operator searching process stared.", LogLevel.INFO);
        MessageBean messageBean;

        OperatorSearchReqDto reqDto = new OperatorSearchReqDto();
        reqDto.setRole(ASSCommon.OPERATOR_ROLE);
        if (null != formBean.getSearchHeaderBean().getUserValidStatus()) {
            reqDto.setUserValidStatus(CommonUtil.convertInteger(formBean.getSearchHeaderBean().getUserValidStatus()));
        }

        if (!InputChecker.isBlankOrNull(formBean.getSearchHeaderBean().getUserLoginId())) {
            reqDto.setUserLoginId(formBean.getSearchHeaderBean().getUserLoginId().toLowerCase());
        }
        if (!InputChecker.isBlankOrNull(formBean.getSearchHeaderBean().getUserName())) {
            reqDto.setUserName(formBean.getSearchHeaderBean().getUserName().toLowerCase());
        }

        try {
            List<OperatorSearchResDto> resDtoList =
                    (List<OperatorSearchResDto>) this.getDaoServiceInvoker().execute(reqDto);
            List<OperatorListLineBean> lineBeanList = new ArrayList<OperatorListLineBean>();

            Map targetMap = CommonUtil.getTargetMap();

            for (OperatorSearchResDto resDto : resDtoList) {
                OperatorListLineBean lineBean = new OperatorListLineBean();

                lineBean.setCreatedBy(resDto.getCreatedBy());
                lineBean.setCreatedTime(resDto.getCreatedTime());
                lineBean.setTeamId(resDto.getTeamId());
                lineBean.setTeamOperatorId(resDto.getTeamOperatorId());
                lineBean.setUpdatedBy(resDto.getUpdatedBy());
                lineBean.setUpdatedTime(resDto.getUpdatedTime());
                lineBean.setUserId(resDto.getUserId());
                lineBean.setUserLoginId(resDto.getUserLoginId());
                lineBean.setUserName(resDto.getUserName());
                lineBean.setUserValidStatus(resDto.getUserValidStatus());
                lineBean.setTeamOperatorUpdateTime(resDto.getTeamOperatorUpdateTime());

                if (null != resDto.getTeamTarget()) {
                    lineBean.setTeamName(resDto.getTeamName() + " (" + targetMap.get(resDto.getTeamTarget()) + ")");
                } else {
                    lineBean.setTeamName(resDto.getTeamName());
                }

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
