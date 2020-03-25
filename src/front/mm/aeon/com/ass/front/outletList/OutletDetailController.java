/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.outletList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mm.aeon.com.ass.base.dto.outletDetailRefer.OutletDetailReferReqDto;
import mm.aeon.com.ass.base.dto.outletDetailRefer.OutletDetailReferResDto;
import mm.aeon.com.ass.base.dto.outletUserSelectList.OutletUserSelectListReqDto;
import mm.aeon.com.ass.base.dto.outletUserSelectList.OutletUserSelectListResDto;
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

public class OutletDetailController extends AbstractController
        implements IControllerAccessor<OutletListFormBean, OutletListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public OutletListFormBean process(OutletListFormBean formBean) {

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Outlet Detail]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        applicationLogger.log("Team detail process stared.", LogLevel.INFO);
        OutletDetailLineBean detailLineBean = null;
        MessageBean msgBean;

        try {
            OutletDetailReferReqDto referReqDto = new OutletDetailReferReqDto();
            referReqDto.setOutletId(formBean.getDetailHeaderBean().getOutletId());

            OutletDetailReferResDto referResDto =
                    (OutletDetailReferResDto) this.getDaoServiceInvoker().execute(referReqDto);

            if (null == referResDto) {
                msgBean = new MessageBean(MessageId.ME1009);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
                applicationLogger.log("Outlet Detail Process unfinished.", LogLevel.ERROR);

                return formBean;

            }

            detailLineBean = new OutletDetailLineBean();

            detailLineBean.setAddress(referResDto.getAddress());
            detailLineBean.setAgencyId(referResDto.getAgencyId());
            detailLineBean.setAgencyName(referResDto.getAgencyName());
            detailLineBean.setAgencyName(referResDto.getAgencyName());
            detailLineBean.setCreatedBy(referResDto.getCreatedBy());
            detailLineBean.setCreatedTime(referResDto.getCreatedTime());
            detailLineBean.setOutletId(referResDto.getOutletId());
            if (referResDto.getOutletName().contains(ASSCommon.OUT_HEAD_OFFICE)) {
                detailLineBean.setOutletName(ASSCommon.OUT_HEAD_OFFICE);
            } else {
                detailLineBean.setOutletName(referResDto.getOutletName());
            }
            detailLineBean.setRemark(referResDto.getRemark());
            detailLineBean.setPhoneNo(referResDto.getPhoneNo());
            detailLineBean.setImagePath(referResDto.getImagePath());
            detailLineBean.setLongitude(referResDto.getLongitude());
            detailLineBean.setLatitude(referResDto.getLatitude());
            detailLineBean.setUpdatedBy(referResDto.getUpdatedBy());
            detailLineBean.setUpdatedTime(referResDto.getUpdatedTime());

            OutletUserSelectListReqDto listReqDto = new OutletUserSelectListReqDto();
            listReqDto.setOutletId(formBean.getDetailHeaderBean().getOutletId());

            List<OutletUserSelectListResDto> listResDtoList =
                    (List<OutletUserSelectListResDto>) this.getDaoServiceInvoker().execute(listReqDto);

            Map<Integer, String> userMap = new HashMap<Integer, String>();

            for (OutletUserSelectListResDto resDto : listResDtoList) {
                userMap.put(resDto.getUserId(), resDto.getUserName());
            }

            detailLineBean.setOutletUserMap(userMap);

            formBean.setDetailLineBean(detailLineBean);

            applicationLogger.log("Outlet Detail Process finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
