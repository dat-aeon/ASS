/**************************************************************************
 * $Date: 2018-09-05$
 * $Author: Arjun $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.outletList;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class OutletListPaginationController extends LazyDataModel<OutletListLineBean> {

    /**
     * 
     */
    private static final long serialVersionUID = -8785598350999473739L;

    private int rowCount;

    private List<OutletListLineBean> allDataList;

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public OutletListPaginationController(int rowCount, List<OutletListLineBean> allDataList) {
        this.rowCount = rowCount;
        this.allDataList = allDataList;
    }

    @Override
    public Object getRowKey(OutletListLineBean line) {
        return line.getOutletName();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<OutletListLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Outlet Search Pagination Process started.", LogLevel.INFO);

        List<OutletListLineBean> resultList = null;

        int endIndex = first + pageSize;

        if (endIndex > allDataList.size()) {
            endIndex = allDataList.size();
        }

        if (sortField != null) {
            Collections.sort(allDataList, new OutletListLazySorter(sortField, sortOrder));
        }

        resultList = allDataList.subList(first, endIndex);

        applicationLogger.log("Outlet Search Pagination Process finished.", LogLevel.INFO);
        return resultList;
    }

}
