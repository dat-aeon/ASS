/**************************************************************************
 * $Date: 2018-08-24 $
 * $Author: Thar Pyae $
 * $Rev:  $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.registeredList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.aeon.com.ass.base.dto.registeredListSelectList.RegisteredListSelectListReqDto;
import mm.aeon.com.ass.base.dto.registeredListSelectList.RegisteredListSelectListResDto;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.exception.ASSBusinessException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class RegisteredListPaginationController extends LazyDataModel<RegisteredListLineBean> {

    /**
     * 
     */
    private static final long serialVersionUID = -8785598350999473739L;

    private int rowCount;

    private RegisteredListSelectListReqDto selectListReqDto;

    private ApplicationLogger applicationLogger = new ApplicationLogger();
    private ASSLogger logger = new ASSLogger();

    public RegisteredListPaginationController(int rowCount, RegisteredListSelectListReqDto selectListReqDto) {
        this.rowCount = rowCount;
        this.selectListReqDto = selectListReqDto;
    }

    @Override
    public Object getRowKey(RegisteredListLineBean line) {
        return line.getAgency();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<RegisteredListLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Registered List Search Pagination Process started.", LogLevel.INFO);

        ArrayList<RegisteredListLineBean> allDataList = new ArrayList<RegisteredListLineBean>();
        selectListReqDto.setLimit(pageSize);
        selectListReqDto.setOffset(first);
        selectListReqDto.setSortField(sortField);
        selectListReqDto.setSortOrder(sortOrder.toString());

        ArrayList<RegisteredListSelectListResDto> resSeachList;
        try {
            resSeachList = (ArrayList<RegisteredListSelectListResDto>) CommonUtil.getDaoServiceInvoker()
                    .execute(selectListReqDto);

            for (RegisteredListSelectListResDto resDto : resSeachList) {

                RegisteredListLineBean allDataBean = new RegisteredListLineBean();
                allDataBean.setRegistrationDate(CommonUtil.getChangeTimestampToString(resDto.getRegistrationDate()));
                allDataBean.setAgency(resDto.getAgency());
                if (resDto.getOutlet().contains(ASSCommon.OUT_HEAD_OFFICE)) {
                    allDataBean.setOutlet(ASSCommon.OUT_HEAD_OFFICE);
                } else {
                    allDataBean.setOutlet(resDto.getOutlet());
                }
                allDataBean.setUser(resDto.getUser());
                allDataBean.setFileName(resDto.getFileName());
                allDataBean.setStatus(resDto.getStatus());
                allDataList.add(allDataBean);
            }

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            } else {
                throw new ASSBusinessException(e.getCause());
            }
        }

        applicationLogger.log("Registered List Search Pagination Process finished.", LogLevel.INFO);
        return allDataList;
    }

}
