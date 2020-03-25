/**************************************************************************
 * $Date: 2018-11-26$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.smsMessageSend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mm.aeon.com.ass.base.dto.smsMessageSelectList.SMSMessageSelectListReqDto;
import mm.aeon.com.ass.base.dto.smsMessageSelectList.SMSMessageSelectListResDto;
import mm.aeon.com.ass.base.service.agencyUserSMSRegisterService.AgencyUserSMSRegisterServiceReqBean;
import mm.aeon.com.ass.base.service.agencyUserSMSRegisterService.AgencyUserSMSRegisterServiceResBean;
import mm.aeon.com.ass.front.common.abstractController.AbstractASSController;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.JsonHeaderBean;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;
import mm.com.dat.presto.main.utils.exception.PrestoRuntimeException;

public class SMSMessageSendRegisterController extends AbstractASSController
        implements IControllerAccessor<SMSMessageSendFormBean, SMSMessageSendFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();
    private ASSLogger logger = new ASSLogger();

    @Override
    public SMSMessageSendFormBean process(SMSMessageSendFormBean formBean) {

        applicationLogger.log("SMS Message Send Process started.", LogLevel.INFO);

        formBean.getMessageContainer().clearAllMessages(true);
        try {
            if (isValidData(formBean)) {
                int count = 0;
                SMSMessageSelectListReqDto reqDto = new SMSMessageSelectListReqDto();

                ArrayList<SMSMessageSelectListResDto> resDtoList =
                        (ArrayList<SMSMessageSelectListResDto>) CommonUtil.getDaoServiceInvoker().execute(reqDto);

                for (SMSMessageSelectListResDto resDto : resDtoList) {

                    // Send SMS
                    //JsonHeaderBean jsonHeaderBean = connectURL(formBean, resDto);

                   // if (jsonHeaderBean != null) {

                        AgencyUserSMSRegisterServiceReqBean reqBean = new AgencyUserSMSRegisterServiceReqBean();
                        reqBean.setAgencyUserId(resDto.getId());
                        reqBean.setMessage(formBean.getHeaderBean().getMessage());
                        reqBean.setSendFlag(1);
                        reqBean.setCreatedBy(CommonUtil.getLoginUserInfo().getUserId());

                        this.getServiceInvoker().addRequest(reqBean);
                        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
                        AgencyUserSMSRegisterServiceResBean resBean = responseMessage.getMessageBean(0);
                        if (ServiceStatusCode.OK.equals(resBean.getServiceStatus())) {
                            count++;
                        }
                    //}
                }

                formBean.getMessageContainer().clearAllMessages(true);
                MessageBean msgBean = new MessageBean(MessageId.MI0004, Integer.toString(count));
                msgBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(msgBean);
                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            }

       /* } catch (JSONException e) {
            logger.log(e.getCause().getMessage(), e, LogLevel.ERROR);
            throw new PrestoRuntimeException(e.getCause());

        } catch (IOException e) {
            logger.log(e.getCause().getMessage(), e, LogLevel.ERROR);
            throw new PrestoRuntimeException(e.getCause());
*/
        } catch (Exception e) {

            logger.log(e.getCause().getMessage(), e, LogLevel.ERROR);
            throw new PrestoRuntimeException(e.getCause());

        }
        applicationLogger.log("SMS Message Send Process finished.", LogLevel.INFO);
        return formBean;

    }

    private boolean isValidData(SMSMessageSendFormBean formBean) {
        boolean isValid = true;
        String message = formBean.getHeaderBean().getMessage();

        if (message == null) {
            MessageBean msgBean =
                    new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.MESSAGE));
            msgBean.addColumnId(DisplayItem.MESSAGE);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;

        }

        return isValid;
    }

    private JsonHeaderBean connectURL(SMSMessageSendFormBean formBean, SMSMessageSelectListResDto infoBean)
            throws IOException, JSONException {

        String url = ASSCommon.MAIN_URL + ASSCommon.PHONE_URL + ASSCommon.PHONE_95 + infoBean.getPhoneNo()
                + ASSCommon.USER_NAME_URL + ASSCommon.PASSWORD_URL + ASSCommon.MESSAGE_URL;

        String message = formBean.getHeaderBean().getMessage();

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url + URLEncoder.encode(message, "UTF-8"));

        // add request header
        HttpResponse response = client.execute(request);
        int status = response.getStatusLine().getStatusCode();
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";

        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();

        JsonHeaderBean jsonHeaderBean = null;

        if (status == 200) {

            jsonHeaderBean = new JsonHeaderBean();
            String jsonString = ASSCommon.OPEN_BRACKET + result.toString() + ASSCommon.CLOSE_BRACKET;
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                // get the JSON Object
                JSONObject obj = jsonArray.getJSONObject(i);
                jsonHeaderBean.setStatus(obj.getString("status"));
                jsonHeaderBean.setIdentifier(obj.getString("identifier"));
                jsonHeaderBean.setGateway(obj.getString("gateway"));
                jsonHeaderBean.setMessageId(obj.getString("messageId"));
            }
        }
        return jsonHeaderBean;
    }

}
