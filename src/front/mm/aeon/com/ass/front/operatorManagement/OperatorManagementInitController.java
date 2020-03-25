/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.operatorManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import mm.aeon.com.ass.base.dto.operatorTeamList.OperatorTeamSelectListReqDto;
import mm.aeon.com.ass.base.dto.operatorTeamList.OperatorTeamSelectListResDto;
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

public class OperatorManagementInitController extends AbstractController
        implements IControllerAccessor<OperatorManagementFormBean, OperatorManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public OperatorManagementFormBean process(OperatorManagementFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Operator List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        applicationLogger.log("Operator Register-Init process started.", LogLevel.INFO);
        MessageBean messageBean;
        OperatorTeamSelectListReqDto reqDto = new OperatorTeamSelectListReqDto();
        reqDto.setValidStatus(ASSCommon.IS_VALID);

        try {

            List<OperatorTeamSelectListResDto> resDtoList =
                    (List<OperatorTeamSelectListResDto>) this.getDaoServiceInvoker().execute(reqDto);

            Map targetMap = CommonUtil.getTargetMap();

            ArrayList<SelectItem> teamSelectItemList = new ArrayList<SelectItem>();
            teamSelectItemList.add(new SelectItem(null, ASSCommon.SPACE));

            for (OperatorTeamSelectListResDto resDto : resDtoList) {
                SelectItem selectItem = new SelectItem();
                selectItem.setValue(resDto.getTeamId());
                if(null != resDto.getTarget()) {
                    selectItem.setLabel(resDto.getTeamName() + " (" + targetMap.get(resDto.getTarget()) + ")");
                }else {
                    selectItem.setLabel(resDto.getTeamName());
                }

                teamSelectItemList.add(selectItem);
            }
            formBean.setTeamSelectItemList(teamSelectItemList);

            if (teamSelectItemList.size() <= 1) {
                messageBean = new MessageBean(MessageId.ME1006, "TEAM_TABLE");
                messageBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(messageBean);
                applicationLogger.log(messageBean.getMessage(), LogLevel.INFO);
            }

            applicationLogger.log("Operator Register-Init process finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
