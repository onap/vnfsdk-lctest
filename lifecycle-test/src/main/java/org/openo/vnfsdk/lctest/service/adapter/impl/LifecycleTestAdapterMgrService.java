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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import org.openo.baseservice.util.impl.SystemEnvVariablesFactory;
import org.openo.vnfsdk.lctest.common.constant.Constant;
import org.openo.vnfsdk.lctest.common.constant.UrlConstant;
import org.openo.vnfsdk.lctest.common.util.RestfulUtil;
import org.openo.vnfsdk.lctest.service.adapter.inf.ILifecycleTestAdapter2MSBManager;
import org.openo.vnfsdk.lctest.service.adapter.inf.ILifecycleTestAdapterMgrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

/**
 * <br>
 * <p>
 * </p>
 * 
 * @author
 * @version NFVO 0.5 Sep 22, 2016
 */
@Service
public class LifecycleTestAdapterMgrService implements ILifecycleTestAdapterMgrService {

    private static final Logger LOG = LoggerFactory.getLogger(LifecycleTestAdapterMgrService.class);

    public static final String LIFECYCLETESTINFO = "lifecycletestinfo.json";

    @Override
    public void register() {
        // set MSB URL and mothedtype
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("url", UrlConstant.REST_MSB_REGISTER);
        paramsMap.put("methodType", RestfulUtil.TYPE_POST);

        // get LifecycleTest info and raise registration
        try {
            String lifecycleTest = readAdapterInfoFromJson();
            if(!"".equals(lifecycleTest)) {
                JSONObject adapterObject = JSONObject.fromObject(lifecycleTest);
                RegisterThread registerThread = new RegisterThread(paramsMap, adapterObject);
                Executors.newSingleThreadExecutor().submit(registerThread);
            } else {
                LOG.error("LifecycleTest info is null,please check!");
            }

        } catch(IOException e) {
            LOG.error("Failed to read LifecycleTest info! " + e.getMessage(), e);
        }

    }

    /**
     * Retrieve lifecycleTest information.
     * 
     * @return
     * @throws IOException
     */
    public static String readAdapterInfoFromJson() throws IOException {
        InputStream ins = null;
        BufferedInputStream bins = null;
        String fileContent = "";

        String fileName = SystemEnvVariablesFactory.getInstance().getAppRoot() + System.getProperty(Constant.SEPARATOR)
                + "etc" + System.getProperty(Constant.SEPARATOR) + "adapterInfo"
                + System.getProperty(Constant.SEPARATOR) + LIFECYCLETESTINFO;

        try {
            ins = new FileInputStream(fileName);
            bins = new BufferedInputStream(ins);

            byte[] contentByte = new byte[ins.available()];
            int num = bins.read(contentByte);

            if(num > 0) {
                fileContent = new String(contentByte);
            }
        } catch(FileNotFoundException e) {
            LOG.error(fileName + "is not found!", e);
        } finally {
            if(ins != null) {
                ins.close();
            }
            if(bins != null) {
                bins.close();
            }
        }

        return fileContent;
    }

    private static class RegisterThread implements Runnable {

        // Thread lock Object
        private final Object lockObject = new Object();

        private ILifecycleTestAdapter2MSBManager adapter2MSBMgr = new LifecycleTestAdapter2MSBManager();

        // url and mothedtype
        private Map<String, String> paramsMap;

        // driver body
        private JSONObject adapterInfo;

        public RegisterThread(Map<String, String> paramsMap, JSONObject adapterInfo) {
            this.paramsMap = paramsMap;
            this.adapterInfo = adapterInfo;
        }

        @Override
        public void run() {
            LOG.info("start register lifecycleTest", RegisterThread.class);

            if(paramsMap == null || adapterInfo == null) {
                LOG.error("parameter is null,please check!", RegisterThread.class);
                return;
            }

            // catch Runtime Exception
            try {
                sendRequest(paramsMap, adapterInfo);
            } catch(RuntimeException e) {
                LOG.error(e.getMessage(), e);
            }

        }

        private void sendRequest(Map<String, String> paramsMap, JSONObject driverInfo) {
            JSONObject resultObj = adapter2MSBMgr.registerLifecycleTest(paramsMap, driverInfo);

            if(Integer.valueOf(resultObj.get("retCode").toString()) == Constant.HTTP_CREATED) {
                LOG.info("LifecycleTest has now Successfully Registered to the Microservice BUS!");
            } else {
                LOG.error("LifecycleTest failed to  Register to the Microservice BUS! Reason:"
                        + resultObj.get("reason").toString() + " retCode:" + resultObj.get("retCode").toString());

                // if registration fails,wait one minute and try again
                try {
                    synchronized(lockObject) {
                        lockObject.wait(Constant.REPEAT_REG_TIME);
                    }
                } catch(InterruptedException e) {
                    LOG.error(e.getMessage(), e);
                }

                sendRequest(this.paramsMap, this.adapterInfo);
            }

        }

    }

    @Override
    public void unregister() {
        // unregister
    }

}
