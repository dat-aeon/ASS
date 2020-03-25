/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.teamList;

import java.util.ArrayList;
import java.util.List;

import mm.aeon.com.ass.base.dto.teamDetailAgencySelectList.TeamDetailAgencySelectListReqDto;
import mm.aeon.com.ass.base.dto.teamDetailAgencySelectList.TeamDetailAgencySelectListResDto;
import mm.aeon.com.ass.base.dto.teamDetailOperatorSelectList.TeamDetailOperatorSelectListReqDto;
import mm.aeon.com.ass.base.dto.teamDetailOperatorSelectList.TeamDetailOperatorSelectListResDto;
import mm.aeon.com.ass.base.dto.teamDetailRefer.TeamDetailReferReqDto;
import mm.aeon.com.ass.base.dto.teamDetailRefer.TeamDetailReferResDto;
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

public class TeamDetailController extends AbstractController
        implements IControllerAccessor<TeamListFormBean, TeamListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public TeamListFormBean process(TeamListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Team Detail]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        applicationLogger.log("Team detail process stared.", LogLevel.INFO);
        MessageBean msgBean;

        TeamDetailReferReqDto detailReferReqDto = new TeamDetailReferReqDto();
        TeamDetailOperatorSelectListReqDto operatorSelectListReqDto = new TeamDetailOperatorSelectListReqDto();
        TeamDetailAgencySelectListReqDto agencySelectListReqDto = new TeamDetailAgencySelectListReqDto();

        detailReferReqDto.setTeamId(formBean.getDetailHeaderBean().getTeamId());
        operatorSelectListReqDto.setTeamId(formBean.getDetailHeaderBean().getTeamId());
        agencySelectListReqDto.setTeamId(formBean.getDetailHeaderBean().getTeamId());

        try {

            TeamDetailReferResDto detailReferResDto =
                    (TeamDetailReferResDto) this.getDaoServiceInvoker().execute(detailReferReqDto);

            if (null == detailReferResDto) {
                msgBean = new MessageBean(MessageId.ME1009);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
                applicationLogger.log("Team Detail Process unfinished.", LogLevel.ERROR);

                return formBean;

            }

            TeamDetailLineBean detailLineBean = new TeamDetailLineBean();

            detailLineBean.setCreatedBy(detailReferResDto.getCreatedBy());
            detailLineBean.setCreatedTime(detailReferResDto.getCreatedTime());
            detailLineBean.setNoOfAgent(detailReferResDto.getNoOfAgent());
            detailLineBean.setNoOfOperator(detailReferResDto.getNoOfOperator());
            detailLineBean.setTargetName(CommonUtil.getTargetMap().get(detailReferResDto.getTarget()));
            detailLineBean.setTeamId(detailReferResDto.getTeamId());
            detailLineBean.setTeamName(detailReferResDto.getTeamName());
            detailLineBean.setUpdatedBy(detailReferResDto.getUpdatedBy());
            detailLineBean.setUpdatedTime(detailLineBean.getUpdatedTime());

            List<TeamDetailOperatorSelectListResDto> operatorSelectListResDtoList =
                    (List<TeamDetailOperatorSelectListResDto>) this.getDaoServiceInvoker()
                            .execute(operatorSelectListReqDto);
            List<TeamDetailOperatorLineBean> operatorLineBeanList = new ArrayList<TeamDetailOperatorLineBean>();

            for (TeamDetailOperatorSelectListResDto opResDto : operatorSelectListResDtoList) {
                TeamDetailOperatorLineBean opLineBean = new TeamDetailOperatorLineBean();
                opLineBean.setAssignBy(opResDto.getAssignBy());
                opLineBean.setAssignTime(opResDto.getAssignTime());
                opLineBean.setOperatorName(opResDto.getOperatorName());
                opLineBean.setDisabledBy(opResDto.getDisabledBy());
                opLineBean.setValidStatus(opResDto.getValidStatus());

                operatorLineBeanList.add(opLineBean);
            }

            detailLineBean.setOperatorLineBeanList(operatorLineBeanList);

            List<TeamDetailAgencySelectListResDto> agencySelectListResDtoList =
                    (List<TeamDetailAgencySelectListResDto>) this.getDaoServiceInvoker()
                            .execute(agencySelectListReqDto);
            List<TeamDetailAgencyLineBean> agencyLineBeanList = new ArrayList<TeamDetailAgencyLineBean>();

            for (TeamDetailAgencySelectListResDto agResDto : agencySelectListResDtoList) {
                TeamDetailAgencyLineBean agLineBean = new TeamDetailAgencyLineBean();
                agLineBean.setAgencyName(agResDto.getAgencyName());
                agLineBean.setAssignBy(agResDto.getAssignBy());
                agLineBean.setAssignTime(agResDto.getAssignTime());
                agLineBean.setDisabledBy(agResDto.getDisabledBy());
                agLineBean.setValidStatus(agResDto.getValidStatus());

                agencyLineBeanList.add(agLineBean);
            }

            detailLineBean.setAgencyLineBeanList(agencyLineBeanList);

            formBean.setDetailLineBean(detailLineBean);
            applicationLogger.log("Team Detail Process finished.", LogLevel.ERROR);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
