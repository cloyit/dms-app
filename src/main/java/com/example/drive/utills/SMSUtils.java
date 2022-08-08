package com.example.drive.utills;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

public class SMSUtils {


    public static void sendMessage(String signName, String templateCode, String phoneNumber, String param){
        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou",
                "LTAI5tKZxMiaFzVSgCRxUF8L",
                "fIuz2h2iN36KYnh0xbdhtbewObJwQe"
        );

        //创建用户客户端
        IAcsClient client = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();

        request.setSysRegionId("cn-hanghzou");
        request.setPhoneNumbers(phoneNumber);//接收短信的手机号码
        request.setSignName(signName);//短信签名名称
        request.setTemplateCode(templateCode);//短信模板CODE
        request.setTemplateParam("{\"code\":\"}" + param + "\"}");//短信模板变量对应的实际值

        //通过阿里云客户端对象，发送请求
        try{
            SendSmsResponse response = client.getAcsResponse(request);
        }catch (ClientException e){
            e.printStackTrace();
        }

    }


}
