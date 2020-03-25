/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.adminUpdateService;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.adminSelectForUpdate.AdminSelectForUpdateReqDto;
import mm.aeon.com.ass.base.dto.adminSelectForUpdate.AdminSelectForUpdateResDto;
import mm.aeon.com.ass.base.dto.adminUpdate.AdminUpdateReqDto;
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

public class AdminUpdateService extends AbstractService
        implements IService<AdminUpdateServiceReqBean, AdminUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public AdminUpdateServiceResBean execute(AdminUpdateServiceReqBean reqBean) {

        AdminUpdateServiceResBean resBean = new AdminUpdateServiceResBean();
        AdminSelectForUpdateReqDto selectForUpdateReqDto = new AdminSelectForUpdateReqDto();
        selectForUpdateReqDto.setId(reqBean.getId());

        try {
            AdminSelectForUpdateResDto selectForUpdateResDto =
                    (AdminSelectForUpdateResDto) this.getDaoServiceInvoker().execute(selectForUpdateReqDto);

            if (selectForUpdateResDto == null) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_NOT_FOUND_ERROR);
            } else if (null != selectForUpdateResDto.getUpdatedDate()
                    && !selectForUpdateResDto.getUpdatedDate().equals(reqBean.getUpdatedDate())) {
                resBean.setServiceStatus(ASSServiceStatusCommon.RECORD_ALREADY_UPDATE);
            } else {
                AdminUpdateReqDto updateReqDto = new AdminUpdateReqDto();
                updateReqDto.setDisabledBy(reqBean.getDisabledBy());
                updateReqDto.setDisabledDate(reqBean.getDisabledDate());
                updateReqDto.setId(reqBean.getId());
                updateReqDto.setLoginId(reqBean.getLoginId());
                updateReqDto.setName(reqBean.getName());
                updateReqDto.setPassword(reqBean.getPassword());
                updateReqDto.setRole(reqBean.getRole());
                updateReqDto.setUpdatedBy(reqBean.getUpdatedBy());
                updateReqDto.setUpdatedDate(CommonUtil.getCurrentTimeStamp());
                updateReqDto.setValidStatus(reqBean.getValidStatus());
                updateReqDto.setValidStatusToggle(reqBean.isValidStatusToggle());

                this.getDaoServiceInvoker().execute(updateReqDto);
                resBean.setServiceStatus(ServiceStatusCode.OK);
            }

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
