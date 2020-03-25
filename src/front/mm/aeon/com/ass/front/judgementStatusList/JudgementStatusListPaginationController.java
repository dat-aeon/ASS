/**************************************************************************
 * $Date: 2018-08-20 $
 * $Author: Thar Pyae $
 * $Rev:  $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.judgementStatusList;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class JudgementStatusListPaginationController extends LazyDataModel<JudgementStatusListLineBean> {

    /**
     * 
     */
    private static final long serialVersionUID = -8785598350999473739L;

    private int rowCount;  

    private List<JudgementStatusListLineBean> allDataList;

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public JudgementStatusListPaginationController(int rowCount,List<JudgementStatusListLineBean>allDataList ) {
        this.rowCount = rowCount;
        this.allDataList = allDataList;
    }
  
    @Override
    public Object getRowKey(JudgementStatusListLineBean line) {
        return line.getApplicationNo();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<JudgementStatusListLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Judgement Status Pagination Process started.", LogLevel.INFO);

        List<JudgementStatusListLineBean> resultList = null;

            int endIndex = first + pageSize;

            if (endIndex > allDataList.size()) {
                endIndex = allDataList.size();
            }

            if (sortField != null) {
                Collections.sort(allDataList, new JudgementStatusListLazySorter(sortField, sortOrder));
            }
           
            resultList = allDataList.subList(first, endIndex);

        applicationLogger.log("Judgement Status Pagination Process finished.", LogLevel.INFO);
        return resultList;
    }

}
