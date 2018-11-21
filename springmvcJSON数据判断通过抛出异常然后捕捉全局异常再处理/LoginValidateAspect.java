package com.hanshow.wise.portal.eshop.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanshow.wise.base.member.error.MemberError;
import com.hanshow.wise.base.member.model.query.LoginValidQUERY;
import com.hanshow.wise.base.member.util.RedisUtil;
import com.hanshow.wise.common.JO.BaseDTO;
import com.hanshow.wise.common.JO.BaseQUERY;
import com.hanshow.wise.common.error.BaseError;
import com.hanshow.wise.portal.eshop.exception.LoginValidateException;
import com.hanshow.wise.portal.eshop.interceptor.SecurityKeyInterceptor;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: LoginValidateAspect</p>
 * <p>Description: 校验securityKey</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: www.hanshow.com</p>
 *
 * @author guolin
 * @version 1.0
 * @date 2018-06-09 11:48
 */
public class LoginValidateAspect {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(LoginValidateAspect.class);

    private final static ObjectMapper mapper = new ObjectMapper();
    /**
     *
     * @param request
     * @param jsonObject
     * @throws LoginValidateException
     */
    public void before(HttpServletRequest request, JSONObject jsonObject) throws LoginValidateException {
        logger.info("请求参数:{}",jsonObject);
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        if (SecurityKeyInterceptor.excludeUrls.contains(url)) { //如果是不拦截的地址直接放行
            return;
        }
        BaseDTO baseDTO = null;
        BaseQUERY<LoginValidQUERY> baseQUERY = null;
        LoginValidQUERY query = null;
        JsonConfig jsonConfig = new JsonConfig();
        Map<String, Object> classMap = new HashMap<>();
        classMap.put("data", LoginValidQUERY.class);
        jsonConfig.setClassMap(classMap);
        baseQUERY = (BaseQUERY) JSONObject.toBean(jsonObject, new BaseQUERY(), jsonConfig);
        query = baseQUERY.getData();
        if (query == null) {
            baseDTO = BaseDTO.genErrBaseDTO(BaseError.ARG_IS_EMPTY);
            if (baseDTO.getRequestId() == null) {
                baseDTO.setRequestId(String.valueOf(new Date().getTime()));
            }
            throw new LoginValidateException(JSONObject.fromObject(baseDTO).toString());
        }
        if (query.getMerchantId() == null || "".equals(query.getMerchantId())) {
            logger.warn("请求参数merchantId不能为空");
            baseDTO = BaseDTO.genErrBaseDTO(MemberError.MEMBER_ARG_MERCHANTID_IS_NEED);
            if (baseDTO.getRequestId() == null) {
                baseDTO.setRequestId(String.valueOf(new Date().getTime()));
            }
            throw new LoginValidateException(JSONObject.fromObject(baseDTO).toString());
        }
        if (query.getMemberId() == null || "".equals(query.getMemberId())) {
            logger.warn("请求参数memberId不能为空");
            baseDTO = BaseDTO.genErrBaseDTO(MemberError.MEMBER_ARG_MEMBERID_IS_NEED);
            if (baseDTO.getRequestId() == null) {
                baseDTO.setRequestId(String.valueOf(new Date().getTime()));
            }
            throw new LoginValidateException(JSONObject.fromObject(baseDTO).toString());
        }
        if (query.getSecurityKey() == null || "".equals(query.getSecurityKey())) {
            logger.warn("请求参数merchantId不能为空");
            baseDTO = BaseDTO.genErrBaseDTO(MemberError.MEMBER_ARG_SECURITYKEY_IS_NEED);
            if (baseDTO.getRequestId() == null) {
                baseDTO.setRequestId(String.valueOf(new Date().getTime()));
            }
            throw new LoginValidateException(JSONObject.fromObject(baseDTO).toString());
        }
        logger.info("securityKey={},merchantId={},memberId={}", query.getSecurityKey(), query.getMerchantId(), query.getMemberId());
        String securityKey = RedisUtil.getSecurityKey(query.getMerchantId(), query.getMemberId());
        if (securityKey != null && securityKey.equals(query.getSecurityKey())) {

        } else {
            logger.warn("securityKey校验失败");
            baseDTO = BaseDTO.genErrBaseDTO(MemberError.MEMBER_ARG_SECURITYKEY_IS_NOT_RIGHT);
            if (baseDTO.getRequestId() == null) {
                baseDTO.setRequestId(String.valueOf(new Date().getTime()));
            }
            throw new LoginValidateException(JSONObject.fromObject(baseDTO).toString());
        }
    }

    public void after(HttpServletRequest request, JSONObject jsonObject) {
        logger.info("[#RequestUrl##]:{},[#RequestJson#]:{}", request.getRequestURI(), jsonObject);
    }

    public void afterReturning(HttpServletRequest request, Object response, JSONObject jsonObject)throws JsonProcessingException {
        String reponseStr = mapper.writeValueAsString(response);
        logger.info("[#RequestUrl##]:{},[#RequestJson#]:{},[#reponseStr#]:{}", request.getRequestURI(), jsonObject, reponseStr);
    }
}
