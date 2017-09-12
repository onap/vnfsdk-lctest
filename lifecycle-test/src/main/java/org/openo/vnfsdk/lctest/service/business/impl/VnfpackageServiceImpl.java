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

package org.openo.vnfsdk.lctest.service.business.impl;

import java.util.HashMap;
import java.util.Map;

import org.openo.vnfsdk.lctest.common.constant.UrlConstant;
import org.openo.vnfsdk.lctest.common.util.RestConstant;
import org.openo.vnfsdk.lctest.common.util.RestfulClient;
import org.openo.vnfsdk.lctest.service.business.inf.VnfpackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**
 * <br>
 * <p>
 * </p>
 * 
 * @author
 * @version NFVO 0.5 Mar 14, 2017
 */
public class VnfpackageServiceImpl implements VnfpackageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VnfpackageServiceImpl.class);
    
    private static String MsbIpAddress = "127.0.0.1";
    private static  int  MsbPort = 80;

    /**
     * <br>
     * 
     * @param object
     * @return
     * @since NFVO 0.5
     */
    @Override
    public JSONObject onboarding(JSONObject object) {
        LOGGER.info("VnfpackageServiceImpl::onboarding:{}", object.toString());
        Map<String, String> paramsMap = new HashMap<>(3);
        paramsMap.put("url", UrlConstant.NSLCM_VNFPACKAGE_URL);
        paramsMap.put("methodType", RestConstant.MethodType.POST);
        String rsp = null; //RestfulUtil.getRemoteResponseContent(paramsMap, object.toString());
        if(null == rsp) {
            JSONObject resJson = new JSONObject();
            resJson.put("message", "NS LCM response Error.");
            return resJson;
        }
        LOGGER.info("VnfpackageServiceImpl::onboarding rsp:{}", rsp);
        return JSONObject.fromObject(rsp);
    }

    /**
     * <br>
     * 
     * @param csarId
     * @return
     * @since NFVO 0.5
     */
    @Override
    public JSONObject queryVnfpackage(String csarId) {
        String url = String.format(UrlConstant.QUERY_VNFPACKAGE_URL, csarId);
        JSONObject rsp = null;//RestfulClient.get(MsbIpAddress, MsbPort, url).getResult();
        	
        if(null == rsp) {
            JSONObject resJson = new JSONObject();
            resJson.put("message", "NS LCM response Error.");
            return resJson;
        }
        LOGGER.info("VnfpackageServiceImpl::queryVnfpackage rsp:{}", rsp);
        return rsp;
    }

    /**
     * <br>
     * 
     * @param object
     * @return
     * @since NFVO 0.5
     */
    @Override
    public JSONObject updatestatus(JSONObject object) {
        LOGGER.info("VnfpackageServiceImpl::updatestatus:{}", object.toString());
        Map<String, String> paramsMap = new HashMap<>(3);
        paramsMap.put("url", UrlConstant.MARKETPLACE_UPDATE_STATUS_URL);
        paramsMap.put("methodType", RestConstant.MethodType.POST);
        String rsp = null; //RestfulUtil.getRemoteResponseContent(paramsMap, object.toString());
        if(null == rsp) {
            JSONObject resJson = new JSONObject();
            resJson.put("message", "Marketplace response Error.");
            return resJson;
        }
        LOGGER.info("VnfpackageServiceImpl::updatestatus rsp:{}", rsp);
        return JSONObject.fromObject(rsp);
    }

}
