/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Swe Hsu Mon $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.agencyUserUpdateService;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.agencyUserSelectForUpdate.AgencyUserSelectForUpdateReqDto;
import mm.aeon.com.ass.base.dto.agencyUserSelectForUpdate.AgencyUserSelectForUpdateResDto;
import mm.aeon.com.ass.base.dto.agencyUserUpdate.AgencyUserUpdateReqDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PhysicalRecordLockedException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class AgencyUserUpdateService extends AbstractService
        implements IService<AgencyUserUpdateServiceReqBean, AgencyUserUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public AgencyUserUpdateServiceResBean execute(AgencyUserUpdateServiceReqBean reqBean) {

        AgencyUserUpdateServiceResBean resBean = new AgencyUserUpdateServiceResBean();

        try {
            AgencyUserSelectForUpdateReqDto selectForUpdateReqDto = new AgencyUserSelectForUpdateReqDto();
            selectForUpdateReqDto.setId(reqBean.getId());

            AgencyUserSelectForUpdateResDto selectForUpdateResDto =
                    (AgencyUserSelectForUpdateResDto) this.getDaoServiceInvoker().execute(selectForUpdateReqDto);
            if (selectForUpdateResDto == null) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_NOT_FOUND_ERROR);
                return resBean;

            } else if (null != selectForUpdateResDto.getUpdatedTime()
                    && !reqBean.getUpdatedTime().equals(selectForUpdateResDto.getUpdatedTime())) {
                resBean.setServiceStatus(ASSServiceStatusCommon.RECORD_ALREADY_UPDATE);
                return resBean;
            }

            AgencyUserUpdateReqDto reqDto = new AgencyUserUpdateReqDto();
            reqDto.setId(reqBean.getId());

            if (reqBean.getIsUpdate()) {
                reqDto.setAgencyId(reqBean.getAgencyId());
                reqDto.setAgencyOutletId(reqBean.getAgencyOutletId());
                reqDto.setLoginID(reqBean.getLoginID());
                reqDto.setPassword(reqBean.getPassword());
                reqDto.setName(reqBean.getName());
                reqDto.setStartDate(reqBean.getStartDate());
                reqDto.setEndDate(reqBean.getEndDate());
                reqDto.setPhoneNo(reqBean.getPhoneNo());
                reqDto.setEmail(reqBean.getEmail());
                reqDto.setAddress(reqBean.getAddress());
                reqDto.setRemark(reqBean.getRemark());
                reqDto.setUpdatedBy(reqBean.getUpdatedBy());
                reqDto.setUpdatedTime(CommonUtil.getCurrentTimeStamp());
            } else {
                reqDto.setIsChangeValid(true);
                reqDto.setIsValid(reqBean.getIsValid());
                reqDto.setDisabledBy(reqBean.getDisabledBy());
                reqDto.setDisabledTime(CommonUtil.getCurrentTimeStamp());
            }
            this.getDaoServiceInvoker().execute(reqDto);
            resBean.setServiceStatus(ServiceStatusCode.OK);
        } catch (PrestoDBException e) {
            if (e instanceof RecordDuplicatedException) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_DUPLICATED_ERROR);

            } else if (e instanceof PhysicalRecordLockedException) {
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
