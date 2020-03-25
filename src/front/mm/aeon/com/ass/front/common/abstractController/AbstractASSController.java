/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.common.abstractController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import mm.aeon.com.ass.base.dto.agencyListCount.AgencyListCountReqDto;
import mm.aeon.com.ass.base.dto.agencyNameSelectList.AgencyNameSelectListReqDto;
import mm.aeon.com.ass.base.dto.agencyNameSelectList.AgencyNameSelectListResDto;
import mm.aeon.com.ass.base.dto.agencyOutletSelectList.AgencyOutletSelectListReqDto;
import mm.aeon.com.ass.base.dto.agencyOutletSelectList.AgencyOutletSelectListResDto;
import mm.aeon.com.ass.base.dto.agencyRoleRefer.AgencyRoleReferReqDto;
import mm.aeon.com.ass.base.dto.agencyRoleRefer.AgencyRoleReferResDto;
import mm.aeon.com.ass.base.dto.agencyTeamSelectList.AgencyTeamSelectListReqDto;
import mm.aeon.com.ass.base.dto.agencyTeamSelectList.AgencyTeamSelectListResDto;
import mm.aeon.com.ass.base.dto.agencyUserCount.AgencyUserListCountReqDto;
import mm.aeon.com.ass.base.dto.outletNameRefer.OutletNameReferReqDto;
import mm.aeon.com.ass.base.dto.outletNameRefer.OutletNameReferResDto;
import mm.aeon.com.ass.base.dto.roleSelectList.RoleSelectListReqDto;
import mm.aeon.com.ass.base.dto.roleSelectList.RoleSelectListResDto;
import mm.aeon.com.ass.front.agencyListManagement.AgencyListManagementHeaderBean;
import mm.aeon.com.ass.front.agencyUserManagement.AgencyUserRegisterHeaderBean;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

/**
 * AbstractProjectController Class.
 * <p>
 * 
 * <pre>
 * 
 * </pre>
 * 
 * </p>
 */
public abstract class AbstractASSController extends AbstractController {

    private ASSLogger logger = new ASSLogger();

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    protected AgencyUserRegisterHeaderBean getAgencyUserRegisterInitData() {

        AgencyUserRegisterHeaderBean headerBean = new AgencyUserRegisterHeaderBean();
        headerBean.setAgencyNameList(this.getAgencyNameList(true));
        headerBean.setAllOutletNameList(this.getAllOutletNameList(true));
        headerBean.setOutletNameList(this.getEmptyList());
        headerBean.setPageTitle(DisplayItemBean.getDisplayItemName(DisplayItem.TITLE_AGENCY_USER_REGISTER));

        return headerBean;
    }

    protected AgencyListManagementHeaderBean getAgencyRegisterInitData() {

        AgencyListManagementHeaderBean headerBean = new AgencyListManagementHeaderBean();
        int mobile = ASSCommon.ZERO_INTEGER;
        int nonMobile = ASSCommon.ONE_INTEGER;
        headerBean.setMobileTeamList(this.getTeamList(mobile));
        headerBean.setNonMobileTeamList(this.getTeamList(nonMobile));
        headerBean.setRoleList(this.getRoleList());

        return headerBean;
    }

    protected ArrayList<AgencyNameSelectListResDto> getAgencyNameMasterList(boolean isRegister) {

        applicationLogger.log("User Search Process started.", LogLevel.INFO);

        AgencyNameSelectListReqDto reqDto = new AgencyNameSelectListReqDto();
        reqDto.setIsValid(isRegister ? 1 : 0);
        ArrayList<AgencyNameSelectListResDto> resDtoList = new ArrayList<AgencyNameSelectListResDto>();
        try {
            resDtoList = (ArrayList<AgencyNameSelectListResDto>) CommonUtil.getDaoServiceInvoker().execute(reqDto);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return resDtoList;
    }

    protected ArrayList<SelectItem> getAgencyNameList(boolean isRegister) {

        applicationLogger.log("Agency name Search Process started.", LogLevel.INFO);

        ArrayList<SelectItem> agencyNameList = new ArrayList<SelectItem>();

        ArrayList<AgencyNameSelectListResDto> resDtoList = this.getAgencyNameMasterList(isRegister);

        SelectItem item = new SelectItem();
        item.setLabel(ASSCommon.SPACE);
        item.setValue(ASSCommon.BLANK);
        agencyNameList.add(item);

        for (AgencyNameSelectListResDto resDto : resDtoList) {
            item = new SelectItem();
            item.setLabel(resDto.getAgencyName());
            item.setValue(resDto.getId());

            agencyNameList.add(item);
        }

        return agencyNameList;
    }

    protected Map<String, ArrayList<AgencyOutletSelectListResDto>> getAllOutletNameList(boolean isRegister) {

        ArrayList<AgencyNameSelectListResDto> agencyList = this.getAgencyNameMasterList(isRegister);

        Map<String, ArrayList<AgencyOutletSelectListResDto>> outletMap = new HashMap<>();
        AgencyOutletSelectListReqDto reqDto = new AgencyOutletSelectListReqDto();
        reqDto.setIsValid(isRegister ? 1 : 0);

        try {
            ArrayList<AgencyOutletSelectListResDto> resDtoList =
                    (ArrayList<AgencyOutletSelectListResDto>) CommonUtil.getDaoServiceInvoker().execute(reqDto);

            for (AgencyNameSelectListResDto agencyResDto : agencyList) {

                ArrayList<AgencyOutletSelectListResDto> outletBeanList = new ArrayList<AgencyOutletSelectListResDto>();
                for (AgencyOutletSelectListResDto resDto : resDtoList) {
                    if (agencyResDto.getId().equals(resDto.getAgencyId())) {
                        outletBeanList.add(resDto);
                    }
                }
                outletMap.put(agencyResDto.getId(), outletBeanList);
            }

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return outletMap;
    }

    protected ArrayList<SelectItem> getEmptyList() {

        applicationLogger.log("User Search Process started.", LogLevel.INFO);

        ArrayList<SelectItem> emptyList = new ArrayList<SelectItem>();

        SelectItem item = new SelectItem();
        item.setLabel(ASSCommon.SPACE);
        item.setValue(null);
        emptyList.add(item);

        return emptyList;
    }

    protected int getAgencyUserCount(AgencyUserListCountReqDto reqDto) {
        int count = 0;
        try {
            count = (Integer) CommonUtil.getDaoServiceInvoker().execute(reqDto);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        return count;
    }

    protected ArrayList<SelectItem> getOutletNameListOnAgencyId(AgencyUserRegisterHeaderBean headerBean) {

        ArrayList<AgencyOutletSelectListResDto> outletList =
                headerBean.getAllOutletNameList().get(headerBean.getAgencyName());

        ArrayList<SelectItem> outletNameList = new ArrayList<SelectItem>();
        SelectItem item = new SelectItem();
        item.setLabel(ASSCommon.SPACE);
        item.setValue(null);
        outletNameList.add(item);

        for (AgencyOutletSelectListResDto resDto : outletList) {
            item = new SelectItem();
            if (resDto.getOutletName().contains(ASSCommon.OUT_HEAD_OFFICE)) {
                item.setLabel(ASSCommon.OUT_HEAD_OFFICE);
            } else {
                item.setLabel(resDto.getOutletName());
            }

            item.setValue(resDto.getOutletId());

            outletNameList.add(item);
        }
        return outletNameList;
    }

    protected String getOutletName(String id) {

        applicationLogger.log("Outlet Name Search Process started.", LogLevel.INFO);

        OutletNameReferReqDto reqDto = new OutletNameReferReqDto();
        reqDto.setAgencyOutletId(Integer.parseInt(id));
        String outletId = null;
        try {
            OutletNameReferResDto resDto = (OutletNameReferResDto) CommonUtil.getDaoServiceInvoker().execute(reqDto);
            outletId = Integer.toString(resDto.getId());
        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return outletId;
    }

    protected int getAgencyListCount(AgencyListCountReqDto reqDto) {
        int count = 0;
        try {
            count = (Integer) CommonUtil.getDaoServiceInvoker().execute(reqDto);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        return count;
    }

    protected ArrayList<SelectItem> getTeamList(int target) {

        applicationLogger.log("Mobile And NonMobile team refer list Process started.", LogLevel.INFO);

        ArrayList<SelectItem> teamList = new ArrayList<SelectItem>();

        ArrayList<AgencyTeamSelectListResDto> resDtoList = new ArrayList<AgencyTeamSelectListResDto>();
        AgencyTeamSelectListReqDto reqDto = new AgencyTeamSelectListReqDto();
        reqDto.setTarget(target);

        try {
            resDtoList = (ArrayList<AgencyTeamSelectListResDto>) CommonUtil.getDaoServiceInvoker().execute(reqDto);
        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        if (resDtoList.size() != 0) {

            SelectItem item = new SelectItem();
            item.setLabel(ASSCommon.SPACE);
            item.setValue(null);
            teamList.add(item);

            for (AgencyTeamSelectListResDto resDto : resDtoList) {
                item = new SelectItem();
                item.setLabel(resDto.getTeamName());
                item.setValue(resDto.getTeamNameId());

                teamList.add(item);
            }
        }
        return teamList;
    }

    protected ArrayList<SelectItem> getRoleList() {

        ArrayList<SelectItem> roleList = new ArrayList<SelectItem>();

        RoleSelectListReqDto reqDto = new RoleSelectListReqDto();
        List<RoleSelectListResDto> resDtoList = new ArrayList<RoleSelectListResDto>();
        try {
            resDtoList = (List<RoleSelectListResDto>) CommonUtil.getDaoServiceInvoker().execute(reqDto);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        if (resDtoList.size() != 0) {

            SelectItem item = new SelectItem();

            for (RoleSelectListResDto resDto : resDtoList) {
                item = new SelectItem();
                item.setLabel(resDto.getType());
                item.setValue(resDto.getId());

                roleList.add(item);
            }
        }
        return roleList;
    }

    protected String[] getSelectedRoleList(int agencyId, boolean isDetail) {

        String[] selectRole = null;
        try {
            AgencyRoleReferReqDto referReqDto = new AgencyRoleReferReqDto();
            referReqDto.setAgencyId(agencyId);

            AgencyRoleReferResDto resDto =
                    (AgencyRoleReferResDto) CommonUtil.getDaoServiceInvoker().execute(referReqDto);

            if (resDto != null) {
                if (isDetail) {
                    selectRole = resDto.getRoleName().split(ASSCommon.COMMA);
                } else {
                    selectRole = resDto.getRoleId().split(ASSCommon.COMMA);
                }
            }

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        return selectRole;
    }

}
