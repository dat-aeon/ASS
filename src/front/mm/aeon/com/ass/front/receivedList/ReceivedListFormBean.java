/**************************************************************************
 * $Date : 2018-08-17$
 * $Author : Thar Pyae $
 * $Rev : 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.receivedList;

import java.io.Serializable;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.base.dto.receivedListSelectList.ReceivedListSelectListReqDto;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("receivedListFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class ReceivedListFormBean extends AbstractFormBean implements Serializable, IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 8509574334720669310L;

    private ReceivedListHeaderBean receivedListHeaderBean;

    private List<ReceivedListLineBean> lineBeanList;

    private LazyDataModel<ReceivedListLineBean> lazyModel;

    private ReceivedListSelectListReqDto selectListReqDto;

    private int totalCount;

    private boolean init = true;

    private boolean stop = false;

    private boolean isSearch;

    @Begin(nested = true)
    public String init() {

        init = false;
        receivedListHeaderBean = new ReceivedListHeaderBean();
        this.isSearch = true;
        return LinkTarget.INIT;
    }

    public String searchInit() {
        this.isSearch = true;
        this.stop = true;
        return LinkTarget.SEARCH;
    }

    @Action
    public String search() {
        this.lazyModel = null;
        if (isSearch == true) {
            this.isSearch = false;
        }
        if (stop == true) {
            this.stop = false;
        }
        if (!this.getMessageContainer().checkMessage(MessageType.ERROR) && this.totalCount != 0) {
            lazyModel = new ReceivedListPaginationController(this.totalCount, this.selectListReqDto);
        }
        return LinkTarget.OK;
    }

    public void clearErrorMessage() {
        getMessageContainer().clearAllMessages(true);

    }

    public ReceivedListHeaderBean getReceivedListHeaderBean() {
        return receivedListHeaderBean;
    }

    public void setReceivedListHeaderBean(ReceivedListHeaderBean receivedListHeaderBean) {
        this.receivedListHeaderBean = receivedListHeaderBean;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public List<ReceivedListLineBean> getLineBeanList() {
        return lineBeanList;
    }

    public void setLineBeanList(List<ReceivedListLineBean> lineBeanList) {
        this.lineBeanList = lineBeanList;
    }

    public boolean getIsSearch() {
        return isSearch;
    }

    public void SetIsSearch(boolean isSearch) {
        this.isSearch = isSearch;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public LazyDataModel<ReceivedListLineBean> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<ReceivedListLineBean> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public ReceivedListSelectListReqDto getSelectListReqDto() {
        return selectListReqDto;
    }

    public void setSelectListReqDto(ReceivedListSelectListReqDto selectListReqDto) {
        this.selectListReqDto = selectListReqDto;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
