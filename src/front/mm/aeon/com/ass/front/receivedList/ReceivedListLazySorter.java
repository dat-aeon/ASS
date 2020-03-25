/**************************************************************************
 * $Date : 2018-08-20$
 * $Author : Thar Pyae $
 * $Rev : 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.receivedList;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.util.ReflectionUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;
import mm.com.dat.presto.main.utils.exception.PrestoRuntimeException;

public class ReceivedListLazySorter implements Comparator<ReceivedListLineBean> {

    private SortOrder sortOrder;

    private String sortField;

    private ASSLogger logger = new ASSLogger();
    
    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public ReceivedListLazySorter(String sortField, SortOrder sortOrder) {
        this.sortOrder = sortOrder;
        this.sortField = sortField;
    }

    public int compare(ReceivedListLineBean member1, ReceivedListLineBean member2) {
        applicationLogger.log("Received List Sorter Process started.", LogLevel.INFO);
        try {
            Object value1 = ReflectionUtil.getValue(member1, sortField);
            Object value2 = ReflectionUtil.getValue(member2, sortField);
            if (value1 == null) {
                value1 = ASSCommon.SPACE;
            }
            if (value2 == null) {
                value2 = ASSCommon.SPACE;
            }
            int value = ((Comparable) value1).compareTo(value2);
            
            applicationLogger.log("Received List Sorter Process finished.", LogLevel.INFO);
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        } catch (Exception e) {
            logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
            throw new PrestoRuntimeException(e.getMessage());
        }
        
    }
}
