package com.github.yexiaoxiaogo.usercenter_reconstruction.common.sms;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.xiaokong.core.base.Result;
import com.xiaokong.core.contant.ResultCode;
import com.xiaokong.usercenter.common.constant.EnumMessageType;
import com.xiaokong.usercenter.common.constant.EnumTemplateCode;
import com.xiaokong.usercenter.common.utils.sendSmsUtil;

/**
 * 短信验证码管理
 * 
 * @author hervoo
 *
 */
@RestController
@RequestMapping("/sms")
public class SmsController {

    private static HashSet<String> messageTypeSet = new HashSet<>();

    static {
        messageTypeSet.add(EnumMessageType.RV.toString());
        messageTypeSet.add(EnumMessageType.CP.toString());
        messageTypeSet.add(EnumMessageType.CA.toString());
        messageTypeSet.add(EnumMessageType.P.toString());
        messageTypeSet.add(EnumMessageType.CAD.toString());
    }

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    // 发送短信验证码
    @RequestMapping(value = "/sendSms", method = RequestMethod.GET)
    public Result sendSms(@RequestParam("phoneNum") String phoneNum, @RequestParam("type") String type) {

        if (StringUtils.isBlank(type) || !messageTypeSet.contains(type)) {
            return new Result<>(ResultCode.INVALID_PARAMETER).setMessage("Invalid message type");
        }

        String templateCode = null;
        switch (EnumMessageType.valueOf(type)) {
            case RV:
                // 用户注册
                templateCode = EnumTemplateCode.REGISTER.getCode();
                break;
            case CP:
                // 修改密码
                templateCode = EnumTemplateCode.CHANGEPWD.getCode();
                break;
            case CA:
                // 修改信息
                templateCode = EnumTemplateCode.CHANGEINFO.getCode();
                break;
            case P:
                // 发送密码
                templateCode = EnumTemplateCode.PWD.getCode();
                break;
            case CAD:
                // 改绑管理员
                templateCode = EnumTemplateCode.CHANGEADMIN.getCode();
                break;
            default:
                break;
        }

        // 生成短信验证码
        String randomCode = RandomStringUtils.randomNumeric(6);

        // 保存短信验证码到redis,设置15分钟失效时间
        redisTemplate.opsForValue().set(phoneNum, randomCode, 900, TimeUnit.SECONDS);

        try {
            SendSmsResponse response = sendSmsUtil.sendSms(phoneNum, randomCode, templateCode);
            if (response != null && response.getCode().equals("OK")) {
                // 发送成功
                return new Result<>(ResultCode.SUCCESS).setData(phoneNum);
            } else {
                // 失败
               // throw new RuntimeException("短信发送失败，信息码" + response.getCode());
                return new Result<>(ResultCode.ERROR_SMS_LIMIT_CONTROL).setData(response.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }
}
