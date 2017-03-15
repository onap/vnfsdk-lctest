/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.vnfsdk.lctest.common.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.Restful;
import org.openo.baseservice.roa.util.restclient.RestfulFactory;
import org.openo.baseservice.roa.util.restclient.RestfulOptions;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * <br>
 * <p>
 * </p>
 * 
 * @author
 * @version NFVO 0.5 Mar 14, 2017
 */
public class RestfulUtil {

    public static final String TYPE_GET = "get";

    public static final String TYPE_PUT = "put";

    public static final String TYPE_POST = "post";

    public static final String TYPE_DEL = "delete";

    public static final String CONTENT_TYPE = "Content-type";

    public static final String APPLICATION = "application/json";

    private static final Logger LOGGER = LoggerFactory.getLogger(RestfulUtil.class);

    private static final Restful REST_CLIENT = RestfulFactory.getRestInstance(RestfulFactory.PROTO_HTTP);

    private RestfulUtil() {
    }

    /**
     * Get response object.<br>
     *
     * @param url
     * @param type
     * @return
     * @since NFVO 0.5
     */
    public static JSONObject getResponseObj(String url, String type) {
        return getResponseObj(url, new RestfulParametes(), type);
    }

    /**
     * Get response object.<br>
     *
     * @param url
     * @param parametes
     * @param type
     * @return
     * @since NFVO 0.5
     */
    public static JSONObject getResponseObj(String url, RestfulParametes parametes, String type) {
        try {
            String content = RestfulUtil.getResponseContent(url, parametes, null, type);
            LOGGER.error("function=getResponseObj, content : {}", content);
            if(StringUtils.isEmpty(content)) {
                return null;
            }
            return JSONObject.fromObject(content);
        } catch(JSONException e) {
            LOGGER.error("function=getResponseObj, exception : {}", e);
            return null;
        }
    }

    /**
     * Get response content.<br>
     *
     * @param url
     * @param restParametes
     * @param type
     * @return
     * @since NFVO 0.5
     */
    public static String getResponseContent(String url, RestfulParametes restParametes, String type) {
        return getResponseContent(url, restParametes, null, type);
    }

    /**
     * Get response content.<br>
     *
     * @param url
     * @param restParametes
     * @param opt
     * @param type
     * @return
     * @since NFVO 0.5
     */
    public static String getResponseContent(String url, RestfulParametes restParametes, RestfulOptions opt,
            String type) {
        String responseContent = null;
        RestfulResponse rsp = restfulResponse(url, restParametes, opt, type);
        if(rsp != null) {
            int httpStatus = rsp.getStatus();
            LOGGER.warn("function=getResponseContent, get response httpStatusCode : {} ", httpStatus);
            if(httpStatus < HttpServletResponse.SC_BAD_REQUEST && httpStatus > 0) {
                responseContent = rsp.getResponseContent();
                LOGGER.warn("function=getResponseContent, get response data success!responseContent={}",
                        responseContent);
            }
        }
        return responseContent;
    }

    /**
     * Get restful response.<br>
     *
     * @param url
     * @param restParametes
     * @param type
     * @return
     * @since NFVO 0.5
     */
    public static RestfulResponse getRestfulResponse(String url, RestfulParametes restParametes, String type) {
        return restfulResponse(url, restParametes, null, type);
    }

    private static RestfulResponse restfulResponse(String url, RestfulParametes restParametes, RestfulOptions opt,
            String type) {
        RestfulResponse rsp = new RestfulResponse();
        try {

            if(REST_CLIENT != null) {
                if(TYPE_GET.equals(type)) {
                    rsp = REST_CLIENT.get(url, restParametes, opt);
                } else if(TYPE_POST.equals(type)) {
                    rsp = REST_CLIENT.post(url, restParametes, opt);
                } else if(TYPE_PUT.equals(type)) {
                    rsp = REST_CLIENT.put(url, restParametes, opt);
                } else if(TYPE_DEL.equals(type)) {
                    rsp = REST_CLIENT.delete(url, restParametes, opt);
                }
            }
        } catch(ServiceException e) {
            LOGGER.error("function=restfulResponse, get restful response catch exception {} ", e);
        }
        LOGGER.warn("function=restfulResponse, response status is {} ", rsp.getStatus());
        return rsp;
    }

    /**
     * <br>
     *
     * @param paramsMap
     * @param params
     * @return
     * @since NFVO 0.5
     */
    public static String getRemoteResponseContent(Map<String, String> paramsMap, String params) {
        String responseContent = null;
        RestfulResponse rsp = getRemoteResponse(paramsMap, params);
        if(rsp != null) {
            int httpStatus = rsp.getStatus();
            LOGGER.warn("function=getResponseContent, get response httpStatusCode : {} ", httpStatus);
            if(httpStatus < HttpServletResponse.SC_BAD_REQUEST && httpStatus > 0) {
                responseContent = rsp.getResponseContent();
                LOGGER.warn("function=getResponseContent, get response data success!responseContent={}",
                        responseContent);
            }
        }
        return responseContent;
    }

    /**
     * <br>
     * 
     * @param paramsMap
     * @param params
     * @return
     * @since NFVO 0.5
     */
    public static RestfulResponse getRemoteResponse(Map<String, String> paramsMap, String params) {
        String url = paramsMap.get("url");
        String methodType = paramsMap.get("methodType");

        RestfulResponse rsp = null;
        Restful rest = RestfulFactory.getRestInstance(RestfulFactory.PROTO_HTTP);
        try {

            RestfulParametes restfulParametes = new RestfulParametes();
            Map<String, String> headerMap = new HashMap<>(3);
            headerMap.put(CONTENT_TYPE, APPLICATION);
            restfulParametes.setHeaderMap(headerMap);
            restfulParametes.setRawData(params);

            if(rest != null) {
                if(TYPE_GET.equalsIgnoreCase(methodType)) {
                    rsp = rest.get(url, restfulParametes);
                } else if(TYPE_POST.equalsIgnoreCase(methodType)) {
                    rsp = rest.post(url, restfulParametes);
                } else if(TYPE_PUT.equalsIgnoreCase(methodType)) {
                    rsp = rest.put(url, restfulParametes);
                } else if(TYPE_DEL.equalsIgnoreCase(methodType)) {
                    rsp = rest.delete(url, restfulParametes);
                }
            }
        } catch(ServiceException e) {
            LOGGER.error("function=getRemoteResponse, get restful response catch exception {}", e);
        }
        return rsp;
    }
}
