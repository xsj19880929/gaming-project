package com.ygccw.wechat.imagecode;

import com.ygccw.wechat.utils.CHttpClient;
import core.framework.util.JSONBinder;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * @author soldier
 */
@Service
public class HospitalRegisterService {
    @Inject
    CHttpClient cHttpClient;


    public String login() throws Exception {
        HttpUriRequest request = RequestBuilder.post()
                .setUri("http://wechat.xmsmjk.com/zycapwxsehr/ReservationLogController/toConfirm.do")
                .setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9B176 MicroMessenger/4.3.2")
//                .setHeader("Cookie", "JSESSIONID=0B4C145570104CC9D904CDA7B0EE10B3")
//                .setHeader("Host", "wechat.xmsmjk.com")
//                .setHeader("Referer", "http://wechat.xmsmjk.com/zycapwxsehr/view/appointment/reservationLog.jsp")
                .addParameter("idno", "DA3923645")
                .addParameter("password", "350402197810210066")
                .build();
        HttpResponse httpResponse = cHttpClient.execute(request);
        String responseStr = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(responseStr);
        return responseStr;
    }

    public String resLockNumber() throws Exception {
        HttpUriRequest request = RequestBuilder.post()
                .setUri("http://wechat.xmsmjk.com/zycapwxsehr/HospitalNoteController/ResLockNumber.do")
//                .setHeader("Cookie", "JSESSIONID=8C4D17A46F2C7182251FD55A03195AFE")
                .addParameter("orgId", "7")
                .addParameter("ssid", "600856500175500015500425503")
                .addParameter("idCard", "500135400825400805400975500565400984500094400884500445")
                .addParameter("numberId", "704020160912PM1642")
                .addParameter("scheduleId", "704020160912PM").build();
        HttpResponse httpResponse = cHttpClient.execute(request);
        String responseStr = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(responseStr);
        return responseStr;
    }

    public String code() throws Exception {
        HttpUriRequest request = RequestBuilder.post()
                .setUri("http://wechat.xmsmjk.com/zycapwxsehr/HospitalNoteController/Code.do")
//                .setHeader("Cookie", "JSESSIONID=8C4D17A46F2C7182251FD55A03195AFE")
                .addParameter("openid", "oYNMFt49BHWpGRDCzqmtJg_vrfXg")
                .build();
        HttpResponse httpResponse = cHttpClient.execute(request);
        String responseStr = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(responseStr);
        return responseStr;
    }

    public String getSelectCode() throws Exception {
        HttpUriRequest request = RequestBuilder.post()
                .setUri("http://wechat.xmsmjk.com/zycapwxsehr/HospitalNoteController/getSelectCode.do")
//                .setHeader("Cookie", "JSESSIONID=8C4D17A46F2C7182251FD55A03195AFE")
                .addParameter("openid", "oYNMFt49BHWpGRDCzqmtJg_vrfXg")
                .build();
        HttpResponse httpResponse = cHttpClient.execute(request);
        String responseStr = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(responseStr);
        return responseStr;
    }

    public String drawImage() throws Exception {
        HttpUriRequest request = RequestBuilder.get()
                .setUri("http://wechat.xmsmjk.com/zycapwxsehr/DrawImage?openid=oYNMFt49BHWpGRDCzqmtJg_vrfXg&_=0.07276385626755655")
//                .setHeader("Cookie", "JSESSIONID=8C4D17A46F2C7182251FD55A03195AFE")
                .build();
        HttpResponse httpResponse = cHttpClient.execute(request);
        String responseStr = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(responseStr);
        return responseStr;
    }

    public String getRegister() throws Exception {
        HttpUriRequest request = RequestBuilder.post()
                .setUri("http://wechat.xmsmjk.com/zycapwxsehr/HospitalNoteController/getRegister.do")
//                .setHeader("Cookie", "JSESSIONID=FFCF61051A333E9569957F1E516AD3CD")
                .setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9B176 MicroMessenger/4.3.2")
                .addParameter("orgCode", "350211G1001")
                .addParameter("deptCode", "320")
                .addParameter("docCode", "040")
                .addParameter("sectionType", "PM")
                .addParameter("startTime", "2016-09-12 16:42:00")
                .addParameter("ssid", "600856500175500015500425503")
                .addParameter("patientName", "田莲珠")
                .addParameter("patientID", "500135400825400805400975500565400984500094400884500445")
                .addParameter("patientPhone", "400935400945400884500765500394505")
                .addParameter("patientSex", "女")
                .addParameter("orgName", "厦门市妇幼保健院")
                .addParameter("openID", "oYNMFt49BHWpGRDCzqmtJg_vrfXg")
                .addParameter("deptName", "宫颈疾病门诊")
                .addParameter("doctorName", "吴冬梅")
                .addParameter("seq", "201609121642")
                .addParameter("state", "124")
                .addParameter("iptCode", "几坐呢女")
                .build();
        HttpResponse httpResponse = cHttpClient.execute(request);
        String responseStr = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(responseStr);
        return responseStr;
    }

    public List<Map<String, Object>> reservationList() throws Exception {
        HttpUriRequest request = RequestBuilder.get()
                .setUri("http://wechat.xmsmjk.com/zycapwxsehr/ReservationLogController/detailtData.do?orgid=350211G1001&deptid=320&docid=040&strStart=2016-9-19&strEnd=2016-9-19")
//                .setHeader("Cookie", "JSESSIONID=8C4D17A46F2C7182251FD55A03195AFE")
                .setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9B176 MicroMessenger/4.3.2")
                .build();
        HttpResponse httpResponse = cHttpClient.execute(request);
        String responseStr = EntityUtils.toString(httpResponse.getEntity());
        Map<String, Object> map = JSONBinder.fromJSON(Map.class, responseStr);
        if (!"null".equals(map.get("data").toString())) {
            Map<String, Object> dataMap = JSONBinder.fromJSON(Map.class, map.get("data").toString());
            Map<String, Object> doctorMap = JSONBinder.fromJSON(Map.class, JSONBinder.toJSON(dataMap.get("doctor")));
            Map<String, Object> dateMap = JSONBinder.fromJSON(Map.class, JSONBinder.toJSON(doctorMap.get("date")));
            List<Map<String, Object>> sectionList = JSONBinder.fromJSON(List.class, JSONBinder.toJSON(dateMap.get("section")));
            System.out.println(JSONBinder.toJSON(sectionList));
            return sectionList;
        }
        return null;
    }
}
