/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.outletManagement;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import mm.aeon.com.ass.base.dto.outletAgencySelectList.OutletAgencySelectListReqDto;
import mm.aeon.com.ass.base.dto.outletAgencySelectList.OutletAgencySelectListResDto;
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

public class OutletManagementInitController extends AbstractController
        implements IControllerAccessor<OutletManagementFormBean, OutletManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public OutletManagementFormBean process(OutletManagementFormBean formBean) {

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Outlet Management]", new InvalidScreenTransitionException(),
                    LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        formBean.getMessageContainer().clearAllMessages(true);
        applicationLogger.log("Outlet Management-Init process started.", LogLevel.INFO);
        MessageBean messageBean;

        OutletAgencySelectListReqDto reqDto = new OutletAgencySelectListReqDto();
        // if register page, select valid agency name.
        if (formBean.getManagementHeaderBean() == null) {
            reqDto.setValidStatus(ASSCommon.IS_VALID);
        }

        try {
            List<OutletAgencySelectListResDto> resDtoList =
                    (List<OutletAgencySelectListResDto>) this.getDaoServiceInvoker().execute(reqDto);

            ArrayList<SelectItem> agencySelectItemList = new ArrayList<SelectItem>();

            agencySelectItemList.add(new SelectItem(0, ""));

            for (OutletAgencySelectListResDto resDto : resDtoList) {
                SelectItem selectItem = new SelectItem();
                selectItem.setValue(resDto.getAgencyId());
                selectItem.setLabel(resDto.getAgencyName());

                agencySelectItemList.add(selectItem);
            }

            formBean.setAgencySelectItemList(agencySelectItemList);

            if (agencySelectItemList.size() == 0) {
                messageBean = new MessageBean(MessageId.ME1006, "AGENCY");
                messageBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(messageBean);
                applicationLogger.log(messageBean.getMessage(), LogLevel.INFO);
            }

            applicationLogger.log("Outlet Management-Init process finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
