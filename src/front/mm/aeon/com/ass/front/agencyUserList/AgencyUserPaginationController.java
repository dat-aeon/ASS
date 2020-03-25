/**************************************************************************
 * $Date: 2018-08-06$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.agencyUserList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.aeon.com.ass.base.dto.agencyUserSelectList.AgencyUserSelectListReqDto;
import mm.aeon.com.ass.base.dto.agencyUserSelectList.AgencyUserSelectListResDto;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.exception.ASSBusinessException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class AgencyUserPaginationController extends LazyDataModel<AgencyUserLineBean> {

    /**
     * 
     */
    private static final long serialVersionUID = -8785598350999473739L;

    private int rowCount;

    private AgencyUserListHeaderBean headerBean;

    private ArrayList<AgencyUserLineBean> resultList;

    private ArrayList<AgencyUserLineBean> allDataList;

    private ASSLogger logger = new ASSLogger();

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public AgencyUserPaginationController(AgencyUserListHeaderBean headerBean) {
        this.headerBean = headerBean;
    }

    public AgencyUserListHeaderBean getHeaderBean() {
        return headerBean;
    }

    public ArrayList<AgencyUserLineBean> getResultList() {
        return resultList;
    }

    public void setResultList(ArrayList<AgencyUserLineBean> resultList) {
        this.resultList = resultList;
    }

    @Override
    public Object getRowKey(AgencyUserLineBean line) {
        return line.getUserName();
    }

    @Override
    public int getRowCount() {
        rowCount = headerBean.getCount();
        return rowCount;
    }

    @Override
    public List<AgencyUserLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Project Search Pagination Process started.", LogLevel.INFO);

        ArrayList<AgencyUserLineBean> resultList = null;

        try {

            if (allDataList == null || sortField != null) {

                allDataList = new ArrayList<AgencyUserLineBean>();

                AgencyUserSelectListReqDto reqDto = new AgencyUserSelectListReqDto();
                reqDto.setUserName(headerBean.getUserName());
                reqDto.setAgencyName(headerBean.getAgencyName());
                reqDto.setOutletName(headerBean.getOutletName());

                ArrayList<AgencyUserSelectListResDto> resSeachList =
                        (ArrayList<AgencyUserSelectListResDto>) CommonUtil.getDaoServiceInvoker().execute(reqDto);

                for (AgencyUserSelectListResDto resDto : resSeachList) {

                    AgencyUserLineBean allDataBean = new AgencyUserLineBean();
                    allDataBean.setId(resDto.getId());
                    allDataBean.setLoginID(resDto.getLoginID());
                    allDataBean.setUserName(resDto.getUserName());
                    allDataBean.setAgencyName(resDto.getAgencyName());
                    allDataBean.setOutletName(resDto.getOutletName());
                    allDataBean.setLocation(resDto.getLocation());
                    allDataBean.setValidFlag(resDto.getValidFlag());
                    allDataList.add(allDataBean);
                }

            }
            int endIndex = first + pageSize;

            if (endIndex > allDataList.size()) {
                endIndex = allDataList.size();
            }
            resultList = new ArrayList<AgencyUserLineBean>();

            if (sortField != null) {
                Collections.sort(allDataList, new AgencyUserLazySorter(sortField, sortOrder));
            }

            for (int index = first; index < endIndex; index++) {

                AgencyUserLineBean lineBean = new AgencyUserLineBean();
                lineBean.setId(allDataList.get(index).getId());
                lineBean.setLoginID(allDataList.get(index).getLoginID());
                lineBean.setUserName(allDataList.get(index).getUserName());
                lineBean.setAgencyName(allDataList.get(index).getAgencyName());
                if (allDataList.get(index).getOutletName().contains(ASSCommon.OUT_HEAD_OFFICE)) {
                    lineBean.setOutletName(ASSCommon.OUT_HEAD_OFFICE);
                } else {
                    lineBean.setOutletName(allDataList.get(index).getOutletName());
                }
                lineBean.setLocation(allDataList.get(index).getLocation());
                lineBean.setValidFlag(allDataList.get(index).getValidFlag());

                if (ASSCommon.ZERO.equals(lineBean.getLocation())) {
                    lineBean.setLocation(ASSCommon.YANGON);
                } else {
                    lineBean.setLocation(ASSCommon.MANDALAY);
                }

                resultList.add(lineBean);
            }

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);

                throw new BaseException(e.getCause());
            } else {
                throw new ASSBusinessException(e.getCause());
            }
        }

        applicationLogger.log("Project Search Pagination Process finished.", LogLevel.INFO);
        return resultList;
    }

}
