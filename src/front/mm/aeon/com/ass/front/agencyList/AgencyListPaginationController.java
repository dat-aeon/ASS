/***********************************************************************
 * $Date: 2018-07-31 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.front.agencyList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class AgencyListPaginationController extends LazyDataModel<AgencyListSearchLineBean> {

    /**
     * 
     */
    private static final long serialVersionUID = -8785598350999473739L;

    private int rowCount;

    private AgencyListHeaderBean headerBean;

    private List<AgencyListSearchLineBean> allDataList;

    // private ASSLogger logger = new ASSLogger();

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public AgencyListPaginationController(int rowCount, List<AgencyListSearchLineBean> lineBeanList) {
        this.rowCount = rowCount;
        this.allDataList = lineBeanList;
    }

    public AgencyListHeaderBean getHeaderBean() {
        return headerBean;
    }

    @Override
    public Object getRowKey(AgencyListSearchLineBean line) {
        return line.getAgencyName();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<AgencyListSearchLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Agency List Search Pagination Process started.", LogLevel.INFO);

        List<AgencyListSearchLineBean> resultList = new ArrayList<AgencyListSearchLineBean>();

        int endIndex = first + pageSize;

        if (endIndex > allDataList.size()) {
            endIndex = allDataList.size();
        }

        if (sortField != null) {
            Collections.sort(allDataList, new AgencyListLazySorter(sortField, sortOrder));
        }

        resultList = allDataList.subList(first, endIndex);

        applicationLogger.log("Agency List Search Pagination Process finished.", LogLevel.INFO);
        return resultList;
    }

}
