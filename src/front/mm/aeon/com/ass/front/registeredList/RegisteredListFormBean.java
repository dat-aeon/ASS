/**************************************************************************
 * $Date : 2018-08-24$
 * $Author : Thar Pyae $
 * $Rev : 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.registeredList;

import java.io.Serializable;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.base.dto.registeredListSelectList.RegisteredListSelectListReqDto;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("registeredListFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class RegisteredListFormBean extends AbstractFormBean implements Serializable, IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 8509574334720669310L;

    private RegisteredListHeaderBean registeredListHeaderBean;

    private boolean isSearch;

    private List<RegisteredListLineBean> lineBeanList;

    private LazyDataModel<RegisteredListLineBean> lazyModel;

    private boolean init = true;

    private boolean stop;

    private int totalCount;

    private RegisteredListSelectListReqDto selectListReqDto;

    @Begin(nested = true)
    public String init() {

        init = false;
        this.isSearch = true;
        registeredListHeaderBean = new RegisteredListHeaderBean();
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
        if (!this.getMessageContainer().checkMessage(MessageType.ERROR) && totalCount != 0) {
            lazyModel = new RegisteredListPaginationController(this.totalCount, this.selectListReqDto);
        }
        return LinkTarget.OK;
    }

    public void clearErrorMessage() {
        getMessageContainer().clearAllMessages(true);

    }

    public RegisteredListHeaderBean getRegisteredListHeaderBean() {
        return registeredListHeaderBean;
    }

    public void setRegisteredListHeaderBean(RegisteredListHeaderBean registeredListHeaderBean) {
        this.registeredListHeaderBean = registeredListHeaderBean;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public List<RegisteredListLineBean> getLineBeanList() {
        return lineBeanList;
    }

    public void setLineBeanList(List<RegisteredListLineBean> lineBeanList) {
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

    public LazyDataModel<RegisteredListLineBean> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<RegisteredListLineBean> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public RegisteredListSelectListReqDto getSelectListReqDto() {
        return selectListReqDto;
    }

    public void setSelectListReqDto(RegisteredListSelectListReqDto selectListReqDto) {
        this.selectListReqDto = selectListReqDto;
    }

}
