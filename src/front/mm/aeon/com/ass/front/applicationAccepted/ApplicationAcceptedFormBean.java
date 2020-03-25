/**************************************************************************
 * $Date: 2018-08-03$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.applicationAccepted;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;

/**
 * 
 * SearchFormBean Class.
 * <p>
 * 
 * <pre>
 *      SearchFormBean Class.
 * </pre>
 * 
 * </p>
 */
@Name("applicationAcceptedFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class ApplicationAcceptedFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 3584695935700863500L;

    private ApplicationAcceptedHeaderBean searchHeaderBean;

    private List<ApplicationAcceptedLineBean> refreshline;

    private List<ApplicationAcceptedLineBean> line;

    private List<ApplicationAcceptedTotalLineBean> totalLine;
    
    private boolean init = true;

    private boolean isUpdate = false;

    private boolean stop = false;

    private boolean showExport = true;

    private int count = 0;
    
    private int start = 0;

    private String showCount = "";
    
    String strDate;
    
    @Begin(nested = true)
    public String init() {
        this.getMessageContainer().clearAllMessages(true);
        searchHeaderBean = new ApplicationAcceptedHeaderBean();
        init = false;
        isUpdate = true;
        refreshDateTime();
        return LinkTarget.SEARCH;
    }

    public String prepareSearch() {
        stop = true;
        isUpdate = true;
        return LinkTarget.SEARCH;
    }

    @Action
    public String search() {
        
        if (isUpdate) {
            isUpdate = false;
        }
        /*
         * if(stop == true) stop = false;
         */
        refreshDateTime();
        return LinkTarget.OK;
    }

    public String goReceivingFile(){
        return LinkTarget.RECEIVING_FILE;
    }
    
    public void refreshDateTime() {
        SimpleDateFormat simple = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date now = new Date();
        strDate = simple.format(now);
    }
    
    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public ApplicationAcceptedHeaderBean getSearchHeaderBean() {
        return searchHeaderBean;
    }

    public void setSearchHeaderBean(ApplicationAcceptedHeaderBean searchHeaderBean) {
        this.searchHeaderBean = searchHeaderBean;
    }

    public List<ApplicationAcceptedLineBean> getLine() {
        return line;
    }

    public List<ApplicationAcceptedLineBean> getRefreshline() {
        return refreshline;
    }

    public void setRefreshline(List<ApplicationAcceptedLineBean> refreshline) {
        this.refreshline = refreshline;
    }

    public void setLine(List<ApplicationAcceptedLineBean> line) {
        this.line = line;
    }

    public boolean getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean getShowExport() {
        return showExport;
    }

    public void setShowExport(boolean showExport) {
        this.showExport = showExport;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getShowCount() {
        return showCount;
    }

    public void setShowCount(String showCount) {
        this.showCount = showCount;
    }

    public List<ApplicationAcceptedTotalLineBean> getTotalLine() {
        return totalLine;
    }

    public void setTotalLine(List<ApplicationAcceptedTotalLineBean> totalLine) {
        this.totalLine = totalLine;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    
}
