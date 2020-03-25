/***********************************************************************
 * $Date: 2018-07-31 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.front.agencyListManagement;

import mm.aeon.com.ass.front.common.abstractController.AbstractASSController;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.exception.InvalidScreenTransitionException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class AgencyListManagementInitController extends AbstractASSController
        implements IControllerAccessor<AgencyListManagementFormBean, AgencyListManagementFormBean> {

    private ASSLogger logger = new ASSLogger();
    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public AgencyListManagementFormBean process(AgencyListManagementFormBean formBean) {

        applicationLogger.log("Agency User Register Initialization Process started.", LogLevel.INFO);
        MessageBean msgBean;

        formBean.getMessageContainer().clearAllMessages(true);

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Agency Management]", new InvalidScreenTransitionException(),
                    LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        AgencyListManagementHeaderBean headerBean = new AgencyListManagementHeaderBean();

        headerBean = super.getAgencyRegisterInitData();

        if (headerBean.getMobileTeamList().size() == ASSCommon.ZERO_INTEGER) {

            msgBean = new MessageBean(MessageId.ME1002, DisplayItemBean.getDisplayItemName(DisplayItem.MOBILE_TEAM));
            msgBean.addColumnId(DisplayItem.MOBILE_TEAM + ASSCommon.LABEL);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            headerBean.setTargetListFlag(true);
        }

        if (headerBean.getNonMobileTeamList().size() == ASSCommon.ZERO_INTEGER) {
            msgBean = new MessageBean(MessageId.ME1002, DisplayItemBean.getDisplayItemName(DisplayItem.NONMOBILE_TEAM));
            msgBean.addColumnId(DisplayItem.NONMOBILE_TEAM + ASSCommon.LABEL);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            headerBean.setTargetListFlag(true);
        }

        if (headerBean.getRoleList().size() == ASSCommon.ZERO_INTEGER) {
            msgBean = new MessageBean(MessageId.ME1002, DisplayItemBean.getDisplayItemName(DisplayItem.ROLE_LIST));
            msgBean.addColumnId(DisplayItem.NONMOBILE_TEAM + ASSCommon.LABEL);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            headerBean.setTargetListFlag(true);
        }
        formBean.setAgencyListManagementHeaderBean(headerBean);

        if (formBean.getUpdateParam() != null) {

            String[] selectRole = super.getSelectedRoleList(formBean.getUpdateParam().getAgencyId(),false);
            if (selectRole != null) {
                formBean.getUpdateParam().setSelectedRole(selectRole);
                formBean.getUpdateParam().setOldRoleList(selectRole);
            } else {
                selectRole = new String[4];
            }
            formBean.getUpdateParam().setSelectedRole(selectRole);
            formBean.getUpdateParam().setOldRoleList(selectRole);
            
            applicationLogger.log("Agency Update Initialization Process finished.", LogLevel.INFO);
            return formBean;
        }

        applicationLogger.log("Agency Register Initialization Process finished.", LogLevel.INFO);
        return formBean;
    }

}
