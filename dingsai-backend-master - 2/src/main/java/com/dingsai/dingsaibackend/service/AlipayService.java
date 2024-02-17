package com.dingsai.dingsaibackend.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.dingsai.dingsaibackend.model.entity.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class AlipayService {

    @Value("${alipay.gatewayUrl}")
    private String gatewayUrl;

    @Value("${alipay.appId}")
    private String appId;

    @Value("${alipay.merchantPrivateKey}")
    private String merchantPrivateKey;

    @Value("${alipay.alipayPublicKey}")
    private String alipayPublicKey;

    public String createOrder(Order order) {
        DefaultAlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId, merchantPrivateKey, AlipayConstants.FORMAT_JSON, AlipayConstants.CHARSET_UTF8, alipayPublicKey, AlipayConstants.SIGN_TYPE_RSA2);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 设置同步回调地址
        alipayRequest.setReturnUrl("http://www.example.com/return");
        // 设置异步通知地址
        alipayRequest.setNotifyUrl("http://www.example.com/notify");
        // 设置订单信息
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + order.getOutTradeNo() + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + order.getTotalAmount() + "," +
                "    \"subject\":\"" + order.getSubject()+"\"," +
                "    \"body\":\"" + order.getBody() + "\"" +
                "  }");

        try {
            AlipayTradePagePayResponse response = alipayClient.pageExecute(alipayRequest);
            if (response.isSuccess()) {
                return response.getBody();
            } else {
                return "生成支付链接失败";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "生成支付链接发生异常";
        }
    }

    public String handleNotify(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        // 从request中获取参数并转换为Map
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String key : requestParams.keySet()) {
            String[] values = requestParams.get(key);
            StringBuilder valueStr = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                valueStr.append((i == values.length - 1) ? values[i] : values[i] + ",");
            }
            params.put(key, valueStr.toString());
        }

        boolean verifyResult = false;
        try {
            verifyResult = AlipaySignature.rsaCheckV1(params, alipayPublicKey, "UTF-8", "RSA2"); // 使用SDK验证通知的合法性
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        if (verifyResult) {
            // 验证通过，处理订单状态等业务逻辑
            String outTradeNo = request.getParameter("out_trade_no");
            String tradeNo = request.getParameter("trade_no");
            String tradeStatus = request.getParameter("trade_status");
            // TODO: 根据业务需求更新订单状态等信息

            // 返回 success 告诉支付宝服务器收到通知
            return "success";
        } else {
            // 验证不通过，可以抛出异常或返回其他信息
            return "fail";
        }
    }

}

