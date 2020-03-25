/**************************************************************************
 * $Date : 2018-08-27 $
 * $Author : Thar Pyae $
 * $Rev : 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.applicationStatusCheck;

import java.util.ArrayList;

import mm.aeon.com.ass.base.dto.appStatusAgencyRefer.AppStatusAgencyReferReqDto;
import mm.aeon.com.ass.base.dto.appStatusAgencyRefer.AppStatusAgencyReferResDto;
import mm.aeon.com.ass.base.dto.appStatusSummaryList.AppStatusSummaryListReqDto;
import mm.aeon.com.ass.base.dto.appStatusSummaryList.AppStatusSummaryListResDto;
import mm.aeon.com.ass.base.dto.applicationStatusCheckList.AppStatusCheckSelectListReqDto;
import mm.aeon.com.ass.base.dto.applicationStatusCheckList.AppStatusCheckSelectListResDto;
import mm.aeon.com.ass.front.common.abstractController.AbstractASSController;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.exception.ASSBusinessException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class ApplicationStatusCheckController extends AbstractASSController
        implements IControllerAccessor<ApplicationStatusCheckFormBean, ApplicationStatusCheckFormBean> {

    private ASSLogger logger = new ASSLogger();
    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public ApplicationStatusCheckFormBean process(ApplicationStatusCheckFormBean formBean) {

        applicationLogger.log("Application Status check list Initialization Process started.", LogLevel.INFO);
        formBean.getMessageContainer().clearAllMessages(true);

        /*
         * if (!(ASSCommon.TWO.equals(CommonUtil.getLoginUserInfo().getUserTypeName()) ||
         * ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName()))) {
         * logger.log("Invalid URL Access.[Application Status Check]", new InvalidScreenTransitionException(),
         * LogLevel.ERROR); throw new InvalidScreenTransitionException(); }
         */

        ApplicationStatusCheckHeaderBean listHeaderBean = formBean.getAppStatusCheckHeaderBean();
        AppStatusCheckSelectListReqDto reqDto = new AppStatusCheckSelectListReqDto();
        AppStatusSummaryListReqDto summaryReqDto = new AppStatusSummaryListReqDto();

        if (ASSCommon.TWO.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            if (formBean.getAppStatusCheckHeaderBean().getAgencyName() == null) {
                reqDto.setAgencyName(referAgencyName());
            } else {
                reqDto.setAgencyName(formBean.getAppStatusCheckHeaderBean().getAgencyName());
            }
            reqDto.setFromDate(
                    CommonUtil.changeDateToYMDSlashString(formBean.getAppStatusCheckHeaderBean().getFromDate()));
            reqDto.setToDate(CommonUtil.changeDateToYMDSlashString(formBean.getAppStatusCheckHeaderBean().getToDate()));

            // for summary table
            summaryReqDto.setAgencyName(reqDto.getAgencyName());
            summaryReqDto.setFromDate(
                    CommonUtil.changeDateToYMDSlashString(formBean.getAppStatusCheckHeaderBean().getFromDate()));
            summaryReqDto.setToDate(
                    CommonUtil.changeDateToYMDSlashString(formBean.getAppStatusCheckHeaderBean().getToDate()));
            listHeaderBean.setDisableFlg(true);
            listHeaderBean.setAgencyName(reqDto.getAgencyName());

        } else {
            reqDto.setAgencyName(formBean.getAppStatusCheckHeaderBean().getAgencyName());
            reqDto.setFromDate(
                    CommonUtil.changeDateToYMDSlashString(formBean.getAppStatusCheckHeaderBean().getFromDate()));
            reqDto.setToDate(CommonUtil.changeDateToYMDSlashString(formBean.getAppStatusCheckHeaderBean().getToDate()));
            summaryReqDto.setAgencyName(formBean.getAppStatusCheckHeaderBean().getAgencyName());
            summaryReqDto.setFromDate(
                    CommonUtil.changeDateToYMDSlashString(formBean.getAppStatusCheckHeaderBean().getFromDate()));
            summaryReqDto.setToDate(
                    CommonUtil.changeDateToYMDSlashString(formBean.getAppStatusCheckHeaderBean().getToDate()));
        }

        /**
         * For Apply List
         */
        ArrayList<AppStatusCheckSelectListResDto> resSeachList;
        try {
            resSeachList =
                    (ArrayList<AppStatusCheckSelectListResDto>) CommonUtil.getDaoServiceInvoker().execute(reqDto);

            ArrayList<ApplicationStatusCheckLineBean> resultList = new ArrayList<ApplicationStatusCheckLineBean>();
            for (AppStatusCheckSelectListResDto resDto : resSeachList) {

                ApplicationStatusCheckLineBean lineBean = new ApplicationStatusCheckLineBean();
                lineBean.setDate(CommonUtil.changeDateToYMDSlashString(resDto.getDate()));
                lineBean.setAgencyName(resDto.getAgencyName());
                if (resDto.getOutletName().contains(ASSCommon.OUT_HEAD_OFFICE)) {
                    lineBean.setOutletName(ASSCommon.OUT_HEAD_OFFICE);
                } else {
                    lineBean.setOutletName(resDto.getOutletName());
                }
                
                lineBean.setStaffName(resDto.getStaffName());
                lineBean.setCustomerName(resDto.getCustomerName());
                lineBean.setType(resDto.getType());
                resultList.add(lineBean);
            }

            formBean.setLineBeanList(resultList);
            listHeaderBean.setApplyRecords(CommonUtil.showRecord(resSeachList.size()));
            /**
             * for summary list.
             */
            ArrayList<AppStatusSummaryListResDto> summaryList =
                    (ArrayList<AppStatusSummaryListResDto>) CommonUtil.getDaoServiceInvoker().execute(summaryReqDto);
            ArrayList<ApplicationStatusCheckLineBean> summaryDataList = new ArrayList<ApplicationStatusCheckLineBean>();
            int sumNo = 1;
            for (AppStatusSummaryListResDto resDto : summaryList) {

                ApplicationStatusCheckLineBean allDataBean = new ApplicationStatusCheckLineBean();
                allDataBean.setSumNo(sumNo++);
                allDataBean.setSumAgentName(resDto.getSumAgentName());
                if (resDto.getSumOutletName().contains(ASSCommon.OUT_HEAD_OFFICE)) {
                    allDataBean.setSumOutletName(ASSCommon.OUT_HEAD_OFFICE);
                } else {
                    allDataBean.setSumOutletName(resDto.getSumOutletName());
                }
                
                allDataBean.setNoOfApp(resDto.getNoOfApp());
                allDataBean.setFirst(resDto.getFirst());
                allDataBean.setFollowUp(resDto.getFollowUp());
                summaryDataList.add(allDataBean);
            }

            formBean.setSumlineBeanList(summaryDataList);
            listHeaderBean.setSummaryRecords(CommonUtil.showRecord(summaryDataList.size()));

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            } else {
                throw new ASSBusinessException(e.getCause());
            }
        }

        formBean.setAppStatusCheckHeaderBean(listHeaderBean);
        applicationLogger.log("Application Status check List Initialization Process finished.", LogLevel.INFO);
        return formBean;
    }

    private String referAgencyName() {
        AppStatusAgencyReferReqDto referReqDto = new AppStatusAgencyReferReqDto();
        AppStatusAgencyReferResDto referResDto = null;
        String name = null;

        referReqDto.setLoginID(CommonUtil.getLoginUserInfo().getUserId());
        try {

            referResDto = (AppStatusAgencyReferResDto) CommonUtil.getDaoServiceInvoker().execute(referReqDto);
            name = referResDto.getAgencyName();
        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            } else {
                throw new ASSBusinessException(e.getCause());
            }
        }
        return name;
    }
}
