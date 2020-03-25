/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Swe Hsu Mon $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.agencyUserDeleteService;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.adminGroupDelete.AdminGroupDeleteReqDto;
import mm.aeon.com.ass.base.dto.agencyUserDelete.AgencyUserDeleteReqDto;
import mm.aeon.com.ass.base.dto.groupDelete.GroupDeleteReqDto;
import mm.aeon.com.ass.base.dto.groupRefer.GroupReferReqDto;
import mm.aeon.com.ass.base.dto.groupRefer.GroupReferResDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.ForeignKeyConstraintException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.LogLevel;

public class AgencyUserDeleteService extends AbstractService
        implements IService<AgencyUserDeleteServiceReqBean, AgencyUserDeleteServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    public AgencyUserDeleteServiceResBean execute(AgencyUserDeleteServiceReqBean reqBean) {

        AgencyUserDeleteServiceResBean resBean = new AgencyUserDeleteServiceResBean();
        AgencyUserDeleteReqDto reqDto = new AgencyUserDeleteReqDto();
        reqDto.setId(reqBean.getId());

        try {
            /*
             * Delete data in admin-group.
             */
            int groupId = referGroupInfo(reqBean.getId());

            if (groupId != 0) {
                deleteAdminGroup(groupId);

                deleteGroupInfo(groupId);
            }

            this.getDaoServiceInvoker().execute(reqDto);
            resBean.setServiceStatus(ServiceStatusCode.OK);

        } catch (PrestoDBException e) {
            if (e instanceof ForeignKeyConstraintException) {
                resBean.setServiceStatus(ASSServiceStatusCommon.FOREIGN_KEY_CONSTRAINT_ERROR);

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
     * refer group info Method.
     * <p>
     * 
     * <pre>
     *      refer group data according to agencyId.
     * 
     * </pre>
     * 
     * </p>
     * 
     * @param agencyId
     */
    private int referGroupInfo(int agencyId) throws PrestoDBException {

        GroupReferReqDto referReqDto = new GroupReferReqDto();
        referReqDto.setAgencyUserId(agencyId);
        GroupReferResDto referResDto = (GroupReferResDto) CommonUtil.getDaoServiceInvoker().execute(referReqDto);

        if (referResDto != null) {
            return referResDto.getId();
        }

        return 0;
    }

    /**
     * deleteAdminGroup Method.
     * <p>
     * 
     * <pre>
     *      Delete data according to adminId.
     * 
     * </pre>
     * 
     * </p>
     * 
     * @param agencyId
     */
    private void deleteAdminGroup(int groupId) throws PrestoDBException {

        AdminGroupDeleteReqDto reqDto = new AdminGroupDeleteReqDto();
        reqDto.setGroupId(groupId);
        this.getDaoServiceInvoker().execute(reqDto);

    }

    /**
     * deleteGroupInfo Method.
     * <p>
     * 
     * <pre>
     *      Delete data according to groupId.
     * 
     * </pre>
     * 
     * </p>
     * 
     * @param groupId
     */
    private void deleteGroupInfo(int groupId) throws PrestoDBException {

        GroupDeleteReqDto reqDto = new GroupDeleteReqDto();
        reqDto.setGroupId(groupId);
        this.getDaoServiceInvoker().execute(reqDto);

    }
}
