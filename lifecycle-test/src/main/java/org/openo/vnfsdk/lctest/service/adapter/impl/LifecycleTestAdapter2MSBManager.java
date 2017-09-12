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

package org.openo.vnfsdk.lctest.service.adapter.impl;

import java.util.Map;


import org.openo.vnfsdk.lctest.common.constant.Constant;
import org.openo.vnfsdk.lctest.common.util.RestResponse;
import org.openo.vnfsdk.lctest.service.adapter.inf.ILifecycleTestAdapter2MSBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**
 * <br>
 * <p>
 * </p>
 * 
 * @author
 * @version NFVO 0.5 Sep 22, 2016
 */
public class LifecycleTestAdapter2MSBManager implements ILifecycleTestAdapter2MSBManager {

    private static final Logger LOG = LoggerFactory.getLogger(LifecycleTestAdapter2MSBManager.class);

    @Override
    public JSONObject registerLifecycleTest(Map<String, String> paramsMap, JSONObject driverInfo) {
        JSONObject resultObj = new JSONObject();

        RestResponse rsp = new  RestResponse();// = RestfulUtil.getRemoteResponse(paramsMap, driverInfo.toString());
        if(null == rsp) {
            LOG.error("function=registerLifecycleTest,  RestfulResponse is null");
            resultObj.put(Constant.REASON, "RestfulResponse is null.");
            resultObj.put(Constant.RETCODE, Constant.ERROR_CODE);
            return resultObj;
        }
        LOG.warn("function=registerLifecycleTest, status={}, content={}.", rsp.getStatusCode(), rsp.getResult());
        String resultCreate = rsp.getResult();

        if(rsp.getStatusCode() == Constant.HTTP_CREATED) {
            LOG.warn("function=registerLifecycleTest, result={}.", resultCreate);
            resultObj = JSONObject.fromObject(resultCreate);
            resultObj.put("retCode", Constant.HTTP_CREATED);
            return resultObj;
        } else if(rsp.getStatusCode() == Constant.HTTP_INVALID_PARAMETERS) {
            LOG.error("function=registerLifecycleTest, msg=MSB return fail,invalid parameters,status={}, result={}.",
                    rsp.getStatusCode(), resultCreate);
            resultObj.put("reason", "MSB return fail,invalid parameters.");
        } else if(rsp.getStatusCode() == Constant.HTTP_INNERERROR_CODE) {
            LOG.error("function=registerLifecycleTest, msg=MSB return fail,internal system error,status={}, result={}.",
                    rsp.getStatusCode(), resultCreate);
            resultObj.put("reason", "MSB return fail,internal system error.");
        }
        resultObj.put("retCode", Constant.ERROR_CODE);
        return resultObj;
    }

    @Override
    public JSONObject unregisterLifecycleTest(Map<String, String> paramsMap) {
        return new JSONObject();
    }

}
