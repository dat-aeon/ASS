/**************************************************************************
 * $Date: 2018-08-03$
 * $Author: Shoon Latt Winne$
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.leadTimeReport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mm.aeon.com.ass.base.dto.leadTimeReport.LeadTimeAverageReportReqDto;
import mm.aeon.com.ass.base.dto.leadTimeReport.LeadTimeAverageReportResDto;
import mm.aeon.com.ass.base.dto.leadTimeReport.LeadTimeReportReqDto;
import mm.aeon.com.ass.base.dto.leadTimeReport.LeadTimeReportResDto;
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

public class LeadTimeReportController extends AbstractController
        implements IControllerAccessor<LeadTimeReportFormBean, LeadTimeReportFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public LeadTimeReportFormBean process(LeadTimeReportFormBean formBean) {
        applicationLogger.log("Lead Time Report Searching Process started.", LogLevel.INFO);

        formBean.getMessageContainer().clearAllMessages(true);

        if (!(ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())
                || ASSCommon.ZERO.equals(CommonUtil.getLoginUserInfo().getUserTypeName()))) {
            logger.log("Invalid URL Access.[Lead Time Report]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        MessageBean msgBean;

        List<LeadTimeReportLineBean> lineBeans = new ArrayList<LeadTimeReportLineBean>();

        List<LeadTimeAverageReportLineBean> lineBeansAverage = new ArrayList<LeadTimeAverageReportLineBean>();

        if (formBean.getSearchHeaderBean().getFromDate() != null
                && formBean.getSearchHeaderBean().getToDate() != null) {
            if (formBean.getSearchHeaderBean().getFromDate()
                    .compareTo(formBean.getSearchHeaderBean().getToDate()) > 0) {
                msgBean = new MessageBean(MessageId.ME1018);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                formBean.setAvgline(lineBeansAverage);
                formBean.setLine(lineBeans);
                formBean.getSearchHeaderBean().setRecord("");
                return formBean;
            }
        }

        LeadTimeReportReqDto reqDto = new LeadTimeReportReqDto();
        reqDto.setFromDate(changeDateToString(formBean.getSearchHeaderBean().getFromDate(), "yyyy-MM-dd HH:mm"));
        reqDto.setToDate(changeDateToString(formBean.getSearchHeaderBean().getToDate(), "yyyy-MM-dd HH:mm"));
        reqDto.setCustomerName(formBean.getSearchHeaderBean().getCustomerName());
        try {
            List<LeadTimeReportResDto> resList =
                    (List<LeadTimeReportResDto>) this.getDaoServiceInvoker().execute(reqDto);

            for (LeadTimeReportResDto resDto : resList) {
                LeadTimeReportLineBean lineBean = new LeadTimeReportLineBean();

                lineBean.setCustomerName(resDto.getCustomerName());
                lineBean.setNrc(resDto.getNrc());
                lineBean.setType(resDto.getType());
                lineBean.setAgencyName(resDto.getAgencyName());
                lineBean.setOutletName(resDto.getOutletName());
                lineBean.setApplicationNo(resDto.getApplicationNo());
                lineBean.setApplyStarted(resDto.getApplyStarted().replaceAll("-", "/"));
                lineBean.setApplyFinished(resDto.getApplyFinished().replaceAll("-", "/"));
                lineBean.setSent(resDto.getSent().replaceAll("-", "/"));
                lineBean.setReceived(resDto.getReceived().replaceAll("-", "/"));
                lineBean.setRegistering(resDto.getRegistering().replaceAll("-", "/"));
                lineBean.setRegistered(resDto.getRegistered().replaceAll("-", "/"));

                lineBeans.add(lineBean);
            }
            formBean.setLine(lineBeans);

            LeadTimeAverageReportReqDto reqAverageDto = new LeadTimeAverageReportReqDto();
            reqAverageDto
                    .setFromDate(changeDateToString(formBean.getSearchHeaderBean().getFromDate(), "yyyy-MM-dd HH:mm"));
            reqAverageDto.setToDate(changeDateToString(formBean.getSearchHeaderBean().getToDate(), "yyyy-MM-dd HH:mm"));
            reqAverageDto.setCustomerName(formBean.getSearchHeaderBean().getCustomerName());

            List<LeadTimeAverageReportResDto> resAverageList =
                    (List<LeadTimeAverageReportResDto>) this.getDaoServiceInvoker().execute(reqAverageDto);

            LeadTimeAverageReportLineBean lineBean1 = new LeadTimeAverageReportLineBean();
            LeadTimeAverageReportLineBean lineBean2 = new LeadTimeAverageReportLineBean();
            LeadTimeAverageReportLineBean lineBean3 = new LeadTimeAverageReportLineBean();
            LeadTimeAverageReportLineBean lineBean4 = new LeadTimeAverageReportLineBean();
            LeadTimeAverageReportLineBean lineBean5 = new LeadTimeAverageReportLineBean();

            lineBean1.setOperation("Start > Finished");
            lineBean2.setOperation("Finished > Sent");
            lineBean3.setOperation("Received > Registering");
            lineBean4.setOperation("Registering > Registered");
            lineBean5.setOperation("Received > Registered");

            for (LeadTimeAverageReportResDto resDto : resAverageList) {

                if (resDto.getType().equals("1st")) {

                    lineBean1.setAverageAll(resDto.getStartToFinished());
                    lineBean2.setAverageAll(resDto.getFinishedToSent());
                    lineBean3.setAverageAll(resDto.getReceivedtoRegistering());
                    lineBean4.setAverageAll(resDto.getRegisteringToRegistered());
                    lineBean5.setAverageAll(resDto.getReceivedtoRegistering());

                    lineBean1.setAverage1st(convertSecond(resDto.getStartToFinished()));
                    lineBean2.setAverage1st(convertSecond(resDto.getFinishedToSent()));
                    lineBean3.setAverage1st(convertSecond(resDto.getReceivedtoRegistering()));
                    lineBean4.setAverage1st(convertSecond(resDto.getRegisteringToRegistered()));
                    lineBean5.setAverage1st(convertSecond(resDto.getReceivedtoRegistering()));
                } else {

                    lineBean1.setAverageAll(calculateAverage(lineBean1.getAverageAll(), resDto.getStartToFinished()));
                    lineBean2.setAverageAll(calculateAverage(lineBean2.getAverageAll(), resDto.getFinishedToSent()));
                    lineBean3.setAverageAll(
                            calculateAverage(lineBean3.getAverageAll(), resDto.getReceivedtoRegistering()));
                    lineBean4.setAverageAll(
                            calculateAverage(lineBean4.getAverageAll(), resDto.getRegisteringToRegistered()));
                    lineBean5.setAverageAll(
                            calculateAverage(lineBean5.getAverageAll(), resDto.getReceivedtoRegistering()));

                    lineBean1.setAverageFollowUp(convertSecond(resDto.getStartToFinished()));
                    lineBean2.setAverageFollowUp(convertSecond(resDto.getFinishedToSent()));
                    lineBean3.setAverageFollowUp(convertSecond(resDto.getReceivedtoRegistering()));
                    lineBean4.setAverageFollowUp(convertSecond(resDto.getRegisteringToRegistered()));
                    lineBean5.setAverageFollowUp(convertSecond(resDto.getReceivedtoRegistering()));
                }
            }

            if (lineBeans.size() == 0) {
                /*
                 * msgBean = new MessageBean(MessageId.MI0008); msgBean.setMessageType(MessageType.INFO);
                 */
                formBean.setShowExport(false);
                formBean.setAvgline(lineBeansAverage);
            } else {
                lineBean1.setAverageAll(convertSecond(lineBean1.getAverageAll()));
                lineBean2.setAverageAll(convertSecond(lineBean2.getAverageAll()));
                lineBean3.setAverageAll(convertSecond(lineBean3.getAverageAll()));
                lineBean4.setAverageAll(convertSecond(lineBean4.getAverageAll()));
                lineBean5.setAverageAll(convertSecond(lineBean5.getAverageAll()));

                lineBeansAverage.add(lineBean1);
                lineBeansAverage.add(lineBean2);
                lineBeansAverage.add(lineBean3);
                lineBeansAverage.add(lineBean4);
                lineBeansAverage.add(lineBean5);

                formBean.setAvgline(lineBeansAverage);

                /*
                 * msgBean = new MessageBean(MessageId.MI0007, Integer.toString(lineBeans.size()));
                 * msgBean.setMessageType(MessageType.INFO);
                 */
                formBean.setShowExport(true);
            }
            // formBean.getMessageContainer().addMessage(msgBean);
            // applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            formBean.getMessageContainer().clearAllMessages();
            formBean.getSearchHeaderBean().setRecord(CommonUtil.showRecord(lineBeans.size()));
            applicationLogger.log("Lead Time Report  Process finished.", LogLevel.INFO);
        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        return formBean;
    }

    private String calculateAverage(String v1, String v2) {

        int val1 = 0;
        int val2 = 0;

        if (v1 != null) {
            val1 = (int) Math.round(Double.parseDouble(v1));
        }

        if (v2 != null) {
            val2 = (int) Math.round(Double.parseDouble(v2));
        }

        int avg = (val1 + val2) / 2;
        /*
         * if(!v1.equals("")) {
         * 
         * }
         */
        return avg + "";
    }

    private String convertSecond(String seconds) {
        if (seconds != null && !seconds.equals("")) {
            int s = (int) Math.round(Double.parseDouble(seconds));
            int p1 = s % 60;
            int p2 = s / 60;
            int p3 = p2 % 60;

            p2 = p2 / 60;
            return p2 + " h : " + p3 + " m : " + p1 + " s";
        } else {
            return "0 h : " + "0 m : " + "0 s";
        }
    }

    public static String changeDateToString(Date date, String pattern) {

        if (date == null) {
            return null;
        } else {
            DateFormat format = new SimpleDateFormat(pattern);
            return format.format(date);
        }
    }

}
