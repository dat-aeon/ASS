/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Swe Hsu Mon $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.agencyUserRegisterService;

import java.util.ArrayList;

import mm.aeon.com.ass.base.dto.adminGroupInsert.AdminGroupInsertReqDto;
import mm.aeon.com.ass.base.dto.adminSearch.AdminSearchReqDto;
import mm.aeon.com.ass.base.dto.adminSearch.AdminSearchResDto;
import mm.aeon.com.ass.base.dto.agencyUserIdRefer.AgencyUserIdReferReqDto;
import mm.aeon.com.ass.base.dto.agencyUserIdRefer.AgencyUserIdReferResDto;
import mm.aeon.com.ass.base.dto.agencyUserInsert.AgencyUserInsertReqDto;
import mm.aeon.com.ass.base.dto.groupInsert.GroupInsertReqDto;
import mm.aeon.com.ass.base.dto.groupRefer.GroupReferReqDto;
import mm.aeon.com.ass.base.dto.groupRefer.GroupReferResDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class AgencyUserRegisterService extends AbstractService
        implements IService<AgencyUserRegisterServiceReqBean, AgencyUserRegisterServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public AgencyUserRegisterServiceResBean execute(AgencyUserRegisterServiceReqBean reqBean) {

        AgencyUserInsertReqDto reqDto = new AgencyUserInsertReqDto();
        AgencyUserRegisterServiceResBean resBean = new AgencyUserRegisterServiceResBean();

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
        reqDto.setIsValid(reqBean.getIsValid());
        reqDto.setCreatedBy(reqBean.getCreatedBy());
        reqDto.setCreatedTime(CommonUtil.getCurrentTimeStamp());

        try {
            this.getDaoServiceInvoker().execute(reqDto);
            resBean.setServiceStatus(ServiceStatusCode.OK);
            /*
             * refer agencyUserId data.
             */
            int agencyUserId = referAgencyUserId(reqBean.getLoginID());

            /*
             * insert data to group-info.
             */
            insertGroupInfo(agencyUserId, reqBean);

            GroupReferReqDto referReqDto = new GroupReferReqDto();
            referReqDto.setAgencyUserId(agencyUserId);
            GroupReferResDto referResDto = (GroupReferResDto) CommonUtil.getDaoServiceInvoker().execute(referReqDto);

            AdminSearchReqDto selectListReqDto = new AdminSearchReqDto();
            selectListReqDto.setValidStatus(1);

            ArrayList<AdminSearchResDto> resDtoList =
                    (ArrayList<AdminSearchResDto>) CommonUtil.getDaoServiceInvoker().execute(selectListReqDto);
            for (AdminSearchResDto resDto : resDtoList) {
                AdminGroupInsertReqDto insertReqDto = new AdminGroupInsertReqDto();
                insertReqDto.setAdminId(resDto.getAdminId());
                insertReqDto.setFinishFlag(1);
                insertReqDto.setGroupId(referResDto.getId());
                insertReqDto.setUpdatedTime(CommonUtil.getCurrentTimeStamp());
                this.getDaoServiceInvoker().execute(insertReqDto);
            }

        } catch (PrestoDBException e) {
            if (e instanceof RecordDuplicatedException) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_DUPLICATED_ERROR);

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

    /**
     * referAgencyUserId Method.
     * <p>
     * 
     * <pre>
     *      only refer agency_user_id.
     * </pre>
     * 
     * </p>
     * 
     * @param loginId
     * @return referID
     */
    private int referAgencyUserId(String loginId) throws PrestoDBException {
        int referID = 0;
        AgencyUserIdReferReqDto referReqDto = new AgencyUserIdReferReqDto();
        AgencyUserIdReferResDto referResDto = new AgencyUserIdReferResDto();
        referReqDto.setLoginId(loginId);
        referResDto = (AgencyUserIdReferResDto) this.getDaoServiceInvoker().execute(referReqDto);
        referID = referResDto.getId();

        return referID;
    }

    /**
     * insertGroupInfo Method.
     * <p>
     * 
     * <pre>
     *      Insert to group-info table for messaging
     * 
     * </pre>
     * 
     * </p>
     * 
     * @param agencyId
     * @param insertServiceReqBean
     */
    private void insertGroupInfo(int agencyId, AgencyUserRegisterServiceReqBean insertServiceReqBean)
            throws PrestoDBException {

        GroupInsertReqDto reqDto = new GroupInsertReqDto();
        reqDto.setAgencyUserId(agencyId);
        reqDto.setFinishFlag(1);
        reqDto.setStartTime(CommonUtil.getCurrentTimeStamp());
        reqDto.setLastSendTime(CommonUtil.getCurrentTimeStamp());

        this.getDaoServiceInvoker().execute(reqDto);
    }
}
