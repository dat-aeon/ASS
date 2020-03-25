/**************************************************************************
 * $Date: 2018-08-03$
 * $Author: Shoon Latt Winne$
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.applicationAccepted;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mm.aeon.com.ass.base.dto.applicationAccepted.ApplicationAcceptedSituationReqDto;
import mm.aeon.com.ass.base.dto.applicationAccepted.ApplicationAcceptedSituationResDto;
import mm.aeon.com.ass.base.dto.applicationAccepted.ApplicationAcceptedTotalReqDto;
import mm.aeon.com.ass.base.dto.applicationAccepted.ApplicationAcceptedTotalResDto;
import mm.aeon.com.ass.base.dto.applicationAcceptedCount.ApplicationAcceptedSituationCountReqDto;
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

public class ApplicationAcceptedController extends AbstractController
        implements IControllerAccessor<ApplicationAcceptedFormBean, ApplicationAcceptedFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public ApplicationAcceptedFormBean process(ApplicationAcceptedFormBean formBean) {

        if (!(ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())
                || ASSCommon.ZERO.equals(CommonUtil.getLoginUserInfo().getUserTypeName()))) {
            logger.log("Invalid URL Access.[Application Accepted]", new InvalidScreenTransitionException(),
                    LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        applicationLogger.log("Application Accepted Process started.", LogLevel.INFO);

        formBean.getMessageContainer().clearAllMessages(true);

        MessageBean msgBean;

        try {
            if (formBean.getCount() == 0 || formBean.getCount() <= formBean.getStart()) {
                ApplicationAcceptedSituationCountReqDto reqCountDto = new ApplicationAcceptedSituationCountReqDto();

                int count = (Integer) this.getDaoServiceInvoker().execute(reqCountDto);
                formBean.setStart(0);
                formBean.setCount(count);

                if (count == 0) {

                    msgBean = new MessageBean(MessageId.MI0008);
                    msgBean.setMessageType(MessageType.INFO);
                    formBean.getMessageContainer().addMessage(msgBean);
                    applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);

                    applicationLogger.log("Application Accepted Count Process finished.", LogLevel.INFO);
                    List<ApplicationAcceptedLineBean> lineBeans = new ArrayList<ApplicationAcceptedLineBean>();
                    List<ApplicationAcceptedTotalLineBean> totalLineBean =
                            new ArrayList<ApplicationAcceptedTotalLineBean>();
                    formBean.setLine(lineBeans);
                    formBean.setTotalLine(totalLineBean);
                    formBean.setShowCount("");
                } else {
                    ApplicationAcceptedSituationReqDto reqDto = new ApplicationAcceptedSituationReqDto();
                    reqDto.setOffset(formBean.getStart());
                    reqDto.setLimit(5);

                    List<ApplicationAcceptedSituationResDto> resList =
                            (List<ApplicationAcceptedSituationResDto>) this.getDaoServiceInvoker().execute(reqDto);
                    List<ApplicationAcceptedLineBean> lineBeans = new ArrayList<ApplicationAcceptedLineBean>();

                    for (ApplicationAcceptedSituationResDto resDto : resList) {
                        ApplicationAcceptedLineBean lineBean = new ApplicationAcceptedLineBean();
                        formBean.setStart(formBean.getStart() + 1);
                        lineBean.setNo(formBean.getStart());
                        lineBean.setAgencyName(resDto.getAgencyName());
                        lineBean.setReceived(resDto.getReceived());
                        lineBean.setRegistered(resDto.getRegistered());

                        lineBeans.add(lineBean);
                    }
                    formBean.setLine(lineBeans);

                    // TOTAL TABLE
                    String[] listType = { ASSCommon.ACCEPT_YGN, ASSCommon.ACCEPT_MDY, ASSCommon.ACCEPT_AEON_PL,
                            ASSCommon.ACCEPT_AEON_ML, ASSCommon.ACCEPT_TOTAL };

                    ApplicationAcceptedTotalReqDto totalReqDto = new ApplicationAcceptedTotalReqDto();
                    totalReqDto.setLocationYgn(ASSCommon.ACCEPT_YGN);
                    totalReqDto.setLocationMdy(ASSCommon.ACCEPT_MDY);
                    totalReqDto.setTypePLoan(ASSCommon.ACCEPT_AEON_PL);
                    totalReqDto.setTypeMLoan(ASSCommon.ACCEPT_AEON_ML);

                    List<ApplicationAcceptedTotalResDto> totalResList =
                            (List<ApplicationAcceptedTotalResDto>) this.getDaoServiceInvoker().execute(totalReqDto);

                    Map<String, ApplicationAcceptedTotalLineBean> totalLineBeanMap =
                            new LinkedHashMap<String, ApplicationAcceptedTotalLineBean>();

                    // prepare Map data for total 5 rows
                    for (String type : listType) {
                        totalLineBeanMap.put(type, null);
                    }
                    ApplicationAcceptedTotalLineBean totalLineBean = new ApplicationAcceptedTotalLineBean();

                    for (ApplicationAcceptedTotalResDto resDto : totalResList) {
                        ApplicationAcceptedTotalLineBean lineBean = new ApplicationAcceptedTotalLineBean();
                        lineBean.setLocation(resDto.getLocation());
                        lineBean.setReceived(resDto.getReceived());
                        lineBean.setRegistering(resDto.getRegistering());
                        lineBean.setRegistered(resDto.getRegistered());
                        lineBean.setNotFound(resDto.getNotFound());
                        lineBean.setTotal(resDto.getReceived() + resDto.getRegistering() + resDto.getRegistered()
                                + resDto.getNotFound());

                        totalLineBean.setLocation("Total");
                        totalLineBean.setReceived(totalLineBean.getReceived() + resDto.getReceived());
                        totalLineBean.setRegistering(totalLineBean.getRegistering() + resDto.getRegistering());
                        totalLineBean.setRegistered(totalLineBean.getRegistered() + resDto.getRegistered());
                        totalLineBean.setNotFound(totalLineBean.getNotFound() + resDto.getNotFound());
                        totalLineBean.setTotal(totalLineBean.getTotal() + resDto.getReceived() + resDto.getRegistering()
                                + resDto.getRegistered() + resDto.getNotFound());

                        // Add each line bean from Res to Map
                        totalLineBeanMap.put(resDto.getLocation(), lineBean);
                    }
                    totalLineBeanMap.put(ASSCommon.ACCEPT_TOTAL, totalLineBean);

                    List<ApplicationAcceptedTotalLineBean> totalLineBeans =
                            new ArrayList<ApplicationAcceptedTotalLineBean>();

                    // if location is null, add default 0 data
                    for (Map.Entry<String, ApplicationAcceptedTotalLineBean> entry : totalLineBeanMap.entrySet()) {
                        if (entry.getValue() == null) {
                            ApplicationAcceptedTotalLineBean lineBean = new ApplicationAcceptedTotalLineBean();
                            lineBean.setLocation(entry.getKey());
                            lineBean.setReceived(0);
                            lineBean.setRegistering(0);
                            lineBean.setRegistered(0);
                            lineBean.setNotFound(0);
                            lineBean.setTotal(0);
                            totalLineBeans.add(lineBean);
                        } else {
                            totalLineBeans.add(entry.getValue());
                        }
                    }

                    formBean.setTotalLine(totalLineBeans);

                    if (lineBeans.size() == 0) {
                        msgBean = new MessageBean(MessageId.MI0008);
                        msgBean.setMessageType(MessageType.INFO);
                        formBean.getMessageContainer().addMessage(msgBean);
                        formBean.setShowExport(false);
                        applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);

                        applicationLogger.log("Application Accepted Process finished.", LogLevel.INFO);
                    }
                    formBean.setShowExport(true);

                    formBean.setLine(lineBeans);
                    formBean.setShowCount(formBean.getCount() + " records found.");
                }
            } else {
                ApplicationAcceptedSituationReqDto reqDto = new ApplicationAcceptedSituationReqDto();
                reqDto.setOffset(formBean.getStart());
                reqDto.setLimit(5);

                List<ApplicationAcceptedSituationResDto> resList =
                        (List<ApplicationAcceptedSituationResDto>) this.getDaoServiceInvoker().execute(reqDto);
                List<ApplicationAcceptedLineBean> lineBeans = new ArrayList<ApplicationAcceptedLineBean>();

                for (ApplicationAcceptedSituationResDto resDto : resList) {
                    ApplicationAcceptedLineBean lineBean = new ApplicationAcceptedLineBean();
                    formBean.setStart(formBean.getStart() + 1);
                    lineBean.setNo(formBean.getStart());
                    lineBean.setAgencyName(resDto.getAgencyName());
                    lineBean.setReceived(resDto.getReceived());
                    lineBean.setRegistered(resDto.getRegistered());

                    lineBeans.add(lineBean);
                }
                formBean.setLine(lineBeans);

                if (lineBeans.size() == 0) {
                    msgBean = new MessageBean(MessageId.MI0008);
                    msgBean.setMessageType(MessageType.INFO);
                    formBean.getMessageContainer().addMessage(msgBean);
                    formBean.setShowExport(false);
                    applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);

                    applicationLogger.log("Application Accepted Process finished.", LogLevel.INFO);
                }
                formBean.setShowExport(true);

                formBean.setLine(lineBeans);
                formBean.setShowCount(formBean.getCount() + " records found.");
            }

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        return formBean;
    }
}
