package com.github.yexiaoxiaogo.usercenter_reconstruction.common.utils;



import org.apache.commons.lang.StringUtils;

import com.xiaokong.core.base.Result;
import com.xiaokong.core.contant.ResultCode;
import com.xiaokong.core.utils.RegExUtil;
import com.xiaokong.usercenter.common.bean.ReqChangeAdminBean;
import com.xiaokong.usercenter.common.constant.Constants;
import com.xiaokong.usercenter.landlord.bean.ReqAuthBean;
import com.xiaokong.usercenter.supplier.bean.ReqAuditBean;
import com.xiaokong.usercenter.supplier.bean.ReqShopBean;

/**
 * 自定义参数校验
 * 
 * @author hervoo
 *
 */
public class VerifyParamUtils {

    // 店铺(公司)参数校验
    public static Result checkCompanyAuth(ReqShopBean bean) {
        Result result = new Result<>(ResultCode.INVALID_PARAMETER);

//        if ((StringUtils.isBlank(bean.getUserId())) || (Constants.UuidLength != bean.getUserId().length())) {
//            return result.setMessage("Invalid userId");
//        }

        if ((StringUtils.isBlank(bean.getPhoneNum())) || (!RegExUtil.match(RegExUtil.PHONE, bean.getPhoneNum()))) {
            return result.setMessage("Invalid phone number");
        }

        if (StringUtils.isBlank(bean.getCompanyName())) {
            return result.setMessage("Invalid company name");
        }

        if (StringUtils.isBlank(bean.getAddress())) {
            return result.setMessage("Invalid company address");
        }

        if (StringUtils.isBlank(bean.getBusinessLicenseNum())) {
            return result.setMessage("Invalid business license number");
        }

//        if (bean.getTimeLimit()==null) {
//            return result.setMessage("Invalid business term");
//        }

//        if (StringUtils.isBlank(bean.getSiteOfRegistration())) {
//            return result.setMessage("Invalid registered place");
//        }

        if (StringUtils.isBlank(bean.getLegalRepresentative())) {
            return result.setMessage("Invalid legal representative");
        }

        if (StringUtils.isBlank(bean.getLegalRepresentativeId())) {
            return result.setMessage("Invalid legal representativeId");
        }

        if (StringUtils.isBlank(bean.getLegalRepresentativeIdPic())) {
            return result.setMessage("Invalid legal representativeIdPic");
        }
        
        if (StringUtils.isBlank(bean.getLegalRepresentativeIdPicTwo())) {
            return result.setMessage("Invalid legal representativeIdPic");
        }

//        if (bean.getEmployeesNum()==null) {
//            return result.setMessage("Number of employees is missed");
//        }
//
//        if (bean.getRegisteredCapital()==null) {
//            return result.setMessage("The registered capital is missed");
//        }

        if (StringUtils.isBlank(bean.getBusinessLicensePic())) {
            return result.setMessage("Invalid business license electronic edition");
        }

        if (StringUtils.isBlank(bean.getCompanyTel())) {
            return result.setMessage("Invalid company phone");
        }

        if (StringUtils.isBlank(bean.getCompanyEmail())) {
            return result.setMessage("Invalid company email");
        }

//        if (StringUtils.isBlank(bean.getEmergencyContact())) {
//            return result.setMessage("Company emergency contact is missed");
//        }
//
//        if (StringUtils.isBlank(bean.getEmergencyContactTel())) {
//            return result.setMessage("Invalid company emergency contact phone");
//        }


        if (StringUtils.isBlank(bean.getBankAccountName())) {
            return result.setMessage("Invalid bank account name");
        }

        if (StringUtils.isBlank(bean.getBankNum())) {
            return result.setMessage("The bank account is missed");
        }

//        if (StringUtils.isBlank(bean.getBankBranch())) {
//            return result.setMessage("The name of the bank account is missed");
//        }
//
//        if (StringUtils.isBlank(bean.getBankBranchArea())) {
//            return result.setMessage("The location of the bank is missed");
//        }

//        if (StringUtils.isBlank(bean.getAccountPic())) {
//            return result.setMessage("Electronic version of an opening license is missed");
//        }

        if (StringUtils.isBlank(bean.getIntroduction())) {
            return result.setMessage("Shop introduction is missed");
        }

        return new Result(ResultCode.SUCCESS);
    }

    // 个人认证参数校验
    public static Result checkPersonalAuth(ReqAuthBean bean) {
        Result result = new Result<>(ResultCode.INVALID_PARAMETER);

        if ((StringUtils.isBlank(bean.getPhoneNum())) || (!RegExUtil.match(RegExUtil.PHONE, bean.getPhoneNum()))) {
            return result.setMessage("Invalid phone number");
        }

        if (StringUtils.isBlank(bean.getUserName())) {
            return result.setMessage("The name is empty");
        }

//        if ((StringUtils.isBlank(bean.getEmail())) || (!RegExUtil.match(RegExUtil.EMAIL, bean.getEmail()))) {
//            return result.setMessage("Invalid email address");
//        }

        if (StringUtils.isBlank(bean.getIdCard())) {
            return result.setMessage("Invalid id number");
        }

        if (StringUtils.isBlank(bean.getApartPic())) {
            return result.setMessage("Invalid image address");
        }

        if (StringUtils.isBlank(bean.getAddress())) {
            return result.setMessage("Invalid shipping address");
        }

        if (StringUtils.isBlank(bean.getBankName())) {
            return result.setMessage("Invalid bank name");
        }

        if (StringUtils.isBlank(bean.getBankAccountName())) {
            return result.setMessage("Invalid bank account name");
        }

        if (StringUtils.isBlank(bean.getBankNum())) {
            return result.setMessage("Invalid bank card number");
        }

        return new Result<>(ResultCode.SUCCESS);
    }

    // 审核记录参数校验
    public static Result verifyAuditRec(ReqAuditBean bean) {
        Result result = new Result(ResultCode.INVALID_PARAMETER);

        if (StringUtils.isBlank(bean.getUserId())) {
            return result.setMessage("The reviewer id is missed");
        }

        if (null == bean.getAuditResult()) {
            return result.setMessage("Audit result is missed");
        }

        return new Result<>(ResultCode.SUCCESS);
    }

    // 改绑管理员参数校验
    public static Result checkChangeAdminParm(ReqChangeAdminBean bean) {
        Result result = new Result(ResultCode.INVALID_PARAMETER);

        if (StringUtils.isBlank(bean.getUserId()) || Constants.UuidLength != bean.getUserId().length()) {
            return result.setMessage("Invalid userId");
        }

//        if (StringUtils.isBlank(bean.getOperatorId()) || Constants.UuidLength != bean.getOperatorId().length()) {
//            return result.setMessage("Invalid operatorId");
//        }

        return new Result<>(ResultCode.SUCCESS);
    }
}
