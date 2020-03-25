/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Swe Hsu Mon $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.judgementStatusUpdateService;

import mm.aeon.com.ass.base.dto.judgementStatusUpdate.JudgementStatusUpdateReqDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PhysicalRecordLockedException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.LogLevel;

public class JudgementStatusUpdateService extends AbstractService
        implements IService<JudgementStatusUpdateServiceReqBean, JudgementStatusUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public JudgementStatusUpdateServiceResBean execute(JudgementStatusUpdateServiceReqBean reqBean) {

        JudgementStatusUpdateServiceResBean resBean = new JudgementStatusUpdateServiceResBean();
        try {

            JudgementStatusUpdateReqDto updateReqDto = new JudgementStatusUpdateReqDto();
            updateReqDto.setStatus(reqBean.getStatus());
            updateReqDto.setApplicationNo(reqBean.getApplicationNo());
            updateReqDto.setJudgementDate(reqBean.getJudgementDate());
            updateReqDto.setAgreementNo(reqBean.getAgreementNo());
            updateReqDto.setFinanceTerm(reqBean.getFinanceTerm());
            updateReqDto.setFinanceAmount(reqBean.getFinanceAmount());
            updateReqDto.setUpdatedBy(reqBean.getUpdatedBy());
            updateReqDto.setUpdatedTime(CommonUtil.getCurrentTimeStamp());

            this.getDaoServiceInvoker().execute(updateReqDto);
            resBean.setServiceStatus(ServiceStatusCode.OK);

        } catch (PrestoDBException e) {
            if (e instanceof PhysicalRecordLockedException) {
                resBean.setServiceStatus(ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR);

            } else if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                resBean.setServiceStatus(ServiceStatusCode.SQL_ERROR);

            } else {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return resBean;

    }

}
