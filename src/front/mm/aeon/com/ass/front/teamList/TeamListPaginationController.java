/**************************************************************************
 * $Date: 2018-09-05$
 * $Author: Arjun $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.teamList;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class TeamListPaginationController extends LazyDataModel<TeamListLineBean> {

    /**
     * 
     */
    private static final long serialVersionUID = -8785598350999473739L;

    private int rowCount;

    private List<TeamListLineBean> allDataList;

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public TeamListPaginationController(int rowCount, List<TeamListLineBean> allDataList) {
        this.rowCount = rowCount;
        this.allDataList = allDataList;
    }

    @Override
    public Object getRowKey(TeamListLineBean line) {
        return line.getTeamName();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<TeamListLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Team Search Pagination Process started.", LogLevel.INFO);

        List<TeamListLineBean> resultList = null;

        int endIndex = first + pageSize;

        if (endIndex > allDataList.size()) {
            endIndex = allDataList.size();
        }

        if (sortField != null) {
            Collections.sort(allDataList, new TeamListLazySorter(sortField, sortOrder));
        }

        resultList = allDataList.subList(first, endIndex);

        applicationLogger.log("Team Search Pagination Process finished.", LogLevel.INFO);
        return resultList;
    }

}
