/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.teamList;

import java.util.ArrayList;
import java.util.List;

import mm.aeon.com.ass.base.dto.teamSearch.TeamSearchReqDto;
import mm.aeon.com.ass.base.dto.teamSearch.TeamSearchResDto;
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

public class TeamListController extends AbstractController
        implements IControllerAccessor<TeamListFormBean, TeamListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public TeamListFormBean process(TeamListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Team List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        applicationLogger.log("Team searching process stared.", LogLevel.INFO);
        MessageBean messageBean;

        TeamSearchReqDto reqDto = new TeamSearchReqDto();
        //reqDto.setValidStatus(ASSCommon.IS_VALID);

        if (!InputChecker.isBlankOrNull(formBean.getSearchHeaderBean().getTeamName())) {
            reqDto.setTeamName(formBean.getSearchHeaderBean().getTeamName().toLowerCase());
        }
        
        try {
            List<TeamSearchResDto> resDtoList = (List<TeamSearchResDto>) this.getDaoServiceInvoker().execute(reqDto);
            List<TeamListLineBean> lineBeanList = new ArrayList<TeamListLineBean>();

            for (TeamSearchResDto resDto : resDtoList) {
                TeamListLineBean lineBean = new TeamListLineBean();

                lineBean.setCreatedBy(resDto.getCreatedBy());
                lineBean.setCreatedTime(resDto.getCreatedTime());
                lineBean.setNoOfAgent(resDto.getNoOfAgent());
                lineBean.setNoOfOperator(resDto.getNoOfOperator());
                lineBean.setTarget(resDto.getTarget());
                lineBean.setTeamId(resDto.getTeamId());
                lineBean.setTeamName(resDto.getTeamName());
                lineBean.setUpdatedBy(resDto.getUpdatedBy());
                lineBean.setUpdatedTime(resDto.getUpdatedTime());

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
            applicationLogger.log("Team searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
