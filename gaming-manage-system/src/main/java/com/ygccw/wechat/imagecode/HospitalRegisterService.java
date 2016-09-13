package com.ygccw.wechat.imagecode;

import com.ygccw.wechat.utils.CHttpClient;
import core.framework.util.JSONBinder;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author soldier
 */
@Service
public class HospitalRegisterService {
    @Inject
    CHttpClient cHttpClient;


    public void index(RegisterBean registerBean, HttpClientContext clientContext) throws Exception {
        HttpUriRequest request = RequestBuilder.get()
                .setUri("http://wechat.xmsmjk.com/zycapwxsehr/view/appointment/index.jsp?state=124&openid=" + registerBean.getOpenID())
                .setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9B176 MicroMessenger/4.3.2")
                .build();
        HttpResponse httpResponse = cHttpClient.execute(request, clientContext);
//        String responseStr = EntityUtils.toString(httpResponse.getEntity());
//        System.out.println(responseStr);
    }

    public Map<String, Object> login(RegisterBean registerBean, HttpClientContext clientContext) throws Exception {
        HttpUriRequest request = RequestBuilder.post()
                .setUri("http://wechat.xmsmjk.com/zycapwxsehr/ReservationLogController/toConfirm.do")
                .setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9B176 MicroMessenger/4.3.2")
                .addParameter("idno", registerBean.getIdNo())
                .addParameter("password", registerBean.getPassword())
                .build();
        HttpResponse httpResponse = cHttpClient.execute(request, clientContext);
        String responseStr = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(responseStr);
        return JSONBinder.fromJSON(Map.class, responseStr);
    }

    public String resLockNumber(RegisterBean registerBean, HttpClientContext clientContext) throws Exception {
        HttpUriRequest request = RequestBuilder.post()
                .setUri("http://wechat.xmsmjk.com/zycapwxsehr/HospitalNoteController/ResLockNumber.do")
                .setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9B176 MicroMessenger/4.3.2")
                .addParameter("orgId", registerBean.getOrgId())
                .addParameter("ssid", registerBean.getSsid())
                .addParameter("idCard", registerBean.getPatientID())
                .addParameter("numberId", registerBean.getNumberId())
                .addParameter("scheduleId", registerBean.getScheduleId()).build();
        HttpResponse httpResponse = cHttpClient.execute(request, clientContext);
        String responseStr = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(responseStr);
        return responseStr;
    }

    public String code(RegisterBean registerBean, HttpClientContext clientContext) throws Exception {
        HttpUriRequest request = RequestBuilder.post()
                .setUri("http://wechat.xmsmjk.com/zycapwxsehr/HospitalNoteController/Code.do")
                .addParameter("openid", registerBean.getOpenID())
                .build();
        HttpResponse httpResponse = cHttpClient.execute(request, clientContext);
        String responseStr = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(responseStr);
        return responseStr;
    }

    public String getSelectCode(RegisterBean registerBean, HttpClientContext clientContext) throws Exception {
        HttpUriRequest request = RequestBuilder.post()
                .setUri("http://wechat.xmsmjk.com/zycapwxsehr/HospitalNoteController/getSelectCode.do")
                .addParameter("openid", registerBean.getOpenID())
                .build();
        HttpResponse httpResponse = cHttpClient.execute(request, clientContext);
        String responseStr = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(responseStr);
        Map<String, Object> resultMap = JSONBinder.fromJSON(Map.class, responseStr);
        return resultMap.get("data").toString();
    }

    public InputStream drawImage(RegisterBean registerBean, HttpClientContext clientContext) throws Exception {
        HttpUriRequest request = RequestBuilder.get()
                .setUri("http://wechat.xmsmjk.com/zycapwxsehr/DrawImage?openid=" + registerBean.getOpenID())
                .build();
        HttpResponse httpResponse = cHttpClient.execute(request, clientContext);
        return httpResponse.getEntity().getContent();

    }

    public String getRegister(RegisterBean registerBean, HttpClientContext clientContext) throws Exception {
        HttpUriRequest request = RequestBuilder.post()
                .setUri("http://wechat.xmsmjk.com/zycapwxsehr/HospitalNoteController/getRegister.do")
                .setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9B176 MicroMessenger/4.3.2")
//                .setHeader("Accept", "application/json, text/javascript, */*; q=0.01")
//                .setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .setHeader("Cookie", "JSESSIONID=42420FBE05B4709BCF49963F56106A2E")
                .setHeader("Host", "wechat.xmsmjk.com")
                .setHeader("Referer", "http://wechat.xmsmjk.com/zycapwxsehr/view/appointment/confirm.jsp")
                .addParameter("orgCode", registerBean.getOrgCode())
                .addParameter("deptCode", registerBean.getDeptCode())
                .addParameter("docCode", registerBean.getDocCode())
                .addParameter("sectionType", registerBean.getSectionType())
                .addParameter("startTime", registerBean.getStartTime())
                .addParameter("ssid", registerBean.getSsid())
                .addParameter("patientName", registerBean.getPatientName())
                .addParameter("patientID", registerBean.getPatientID())
                .addParameter("patientPhone", registerBean.getPatientPhone())
                .addParameter("patientSex", registerBean.getPatientSex())
                .addParameter("orgName", registerBean.getOrgName())
                .addParameter("openID", registerBean.getOpenID())
                .addParameter("deptName", registerBean.getDeptName())
                .addParameter("doctorName", registerBean.getDoctorName())
                .addParameter("seq", registerBean.getSeq())
                .addParameter("state", registerBean.getState())
                .addParameter("iptCode", registerBean.getIptCode())
                .build();
//创建参数列表
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("orgCode", registerBean.getOrgCode()));
        list.add(new BasicNameValuePair("deptCode", registerBean.getDeptCode()));
        list.add(new BasicNameValuePair("docCode", registerBean.getDocCode()));
        list.add(new BasicNameValuePair("sectionType", registerBean.getSectionType()));
        list.add(new BasicNameValuePair("startTime", registerBean.getStartTime()));
        list.add(new BasicNameValuePair("ssid", registerBean.getSsid()));
        list.add(new BasicNameValuePair("patientName", registerBean.getPatientName()));
        list.add(new BasicNameValuePair("patientID", registerBean.getPatientID()));
        list.add(new BasicNameValuePair("patientPhone", registerBean.getPatientPhone()));
        list.add(new BasicNameValuePair("patientSex", registerBean.getPatientSex()));
        list.add(new BasicNameValuePair("orgName", registerBean.getOrgName()));
        list.add(new BasicNameValuePair("openID", registerBean.getOpenID()));
        list.add(new BasicNameValuePair("deptName", registerBean.getDeptName()));
        list.add(new BasicNameValuePair("doctorName", registerBean.getDoctorName()));
        list.add(new BasicNameValuePair("seq", registerBean.getSeq()));
        list.add(new BasicNameValuePair("state", registerBean.getState()));
        list.add(new BasicNameValuePair("iptCode", registerBean.getIptCode()));
        //url格式编码
        UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list, "UTF-8");

        HttpResponse httpResponse = cHttpClient.execute(request, clientContext);
        String responseStr = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(responseStr);
        Map<String, Object> resultMap = JSONBinder.fromJSON(Map.class, responseStr);
        return resultMap.get("data").toString();
    }

    public List<Map<String, Object>> reservationList(RegisterBean registerBean, HttpClientContext clientContext) throws Exception {
        StringBuilder url = new StringBuilder("http://wechat.xmsmjk.com/zycapwxsehr/ReservationLogController/detailtData.do");
        url.append("?orgid=").append(registerBean.getOrgCode());
        url.append("&deptid=").append(registerBean.getDeptCode());
        url.append("&docid=").append(registerBean.getDocCode());
        url.append("&strStart=").append(registerBean.getStartDate());
        url.append("&strEnd=").append(registerBean.getEndDate());
        HttpUriRequest request = RequestBuilder.get()
                .setUri(url.toString())
                .setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9B176 MicroMessenger/4.3.2")
                .build();
        HttpResponse httpResponse = cHttpClient.execute(request, clientContext);
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

    public String doRegister(RegisterBean registerBean, HttpClientContext clientContext) throws Exception {
        String msg = "";
        resLockNumber(registerBean, clientContext);
        code(registerBean, clientContext);
        String wordArray = getSelectCode(registerBean, clientContext);
        InputStream inputStream = drawImage(registerBean, clientContext);
        ImageIO.write(ImageIO.read(inputStream), "png", new File("D:\\code\\" + System.currentTimeMillis() + ".png"));
        String iptCode = ImageCodeDemo.imageToWord(wordArray, registerBean.getIdNo(), "http://wechat.xmsmjk.com/zycapwxsehr/DrawImage?openid=" + registerBean.getOpenID());
        System.out.println(iptCode);
        registerBean.setIptCode(iptCode);
        msg = getRegister(registerBean, clientContext);
        System.out.println(msg);
        return msg;

    }

//    public String testGetRegister() throws Exception {
//        HttpUriRequest request = RequestBuilder.post()
//                .setUri("http://wechat.xmsmjk.com/zycapwxsehr/HospitalNoteController/getRegister.do")
//                .setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9B176 MicroMessenger/4.3.2")
////                .setHeader("Accept", "application/json, text/javascript, */*; q=0.01")
////                .setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
////                .setHeader("Cookie", "JSESSIONID=42420FBE05B4709BCF49963F56106A2E")
////                .setHeader("Host", "wechat.xmsmjk.com")
////                .setHeader("Referer", "http://wechat.xmsmjk.com/zycapwxsehr/view/appointment/confirm.jsp")
//
//                .addParameter("orgCode", "350211G1001")
//                .addParameter("deptCode", "320")
//                .addParameter("docCode", "040")
//                .addParameter("sectionType", "PM ")
//                .addParameter("startTime", "2016-09-18 14:36:00")
//                .addParameter("ssid", "600865500445500594400905502")
//                .addParameter("patientName", "")
//                .addParameter("patientID", registerBean.getPatientID())
//                .addParameter("patientPhone", registerBean.getPatientPhone())
//                .addParameter("patientSex", registerBean.getPatientSex())
//                .addParameter("orgName", registerBean.getOrgName())
//                .addParameter("openID", registerBean.getOpenID())
//                .addParameter("deptName", registerBean.getDeptName())
//                .addParameter("doctorName", registerBean.getDoctorName())
//                .addParameter("seq", registerBean.getSeq())
//                .addParameter("state", registerBean.getState())
//                .addParameter("iptCode", registerBean.getIptCode())
//                .build();
//        HttpResponse httpResponse = cHttpClient.execute(request, clientContext);
//        String responseStr = EntityUtils.toString(httpResponse.getEntity());
//        System.out.println(responseStr);
//        Map<String, Object> resultMap = JSONBinder.fromJSON(Map.class, responseStr);
//        return resultMap.get("data").toString();
//    }

    public void testDo() throws Exception {
        HttpClientContext clientContext = HttpClientContext.create();
        RegisterBean registerBean = new RegisterBean();
        registerBean.setOrgCode("350211G1001");
        registerBean.setDeptCode("320");
        registerBean.setDocCode("040");
        registerBean.setSectionType("PM ");
        registerBean.setStartTime("2016-09-18 14:36:00");
        registerBean.setSsid("600865500445500594400905502");
        registerBean.setPatientName("许少军");
        registerBean.setPatientID("500135400835500094400975500665400875500075400935500365");
        registerBean.setPatientPhone("400935500594400845500484500035505");
        registerBean.setPatientSex("男");
        registerBean.setOrgName("厦门市妇幼保健院");
        registerBean.setOpenID("oYNMFt49BHWpGRDCzqmtJg_vrfXg");
        registerBean.setDeptName("宫颈疾病门诊");
        registerBean.setDoctorName("吴冬梅");
        registerBean.setSeq("201609181436");
        registerBean.setState("124");
        registerBean.setIptCode("阳六话经");
        registerBean.setNumberId("704020160918PM1436");
        registerBean.setScheduleId("704020160918PM");
        registerBean.setIdNo("D86671124");
        registerBean.setOrgId("7");
        registerBean.setPassword("350521198809291558");
//        index(registerBean, clientContext);
//        Map<String, Object> resultMap = login(registerBean, clientContext);
//        registerBean.setSsid(resultMap.get("patientSsid").toString());
//        registerBean.setPatientID(resultMap.get("patientID").toString());
//        registerBean.setPatientPhone(resultMap.get("patientPhone").toString());
//        while (true) {
//            String msg = doRegister(registerBean, clientContext);
//            if (!"验证码错误,请重新输入".equals(msg)) {
//                break;
//            }
//
//        }
        getRegister(registerBean, clientContext);
    }
}
