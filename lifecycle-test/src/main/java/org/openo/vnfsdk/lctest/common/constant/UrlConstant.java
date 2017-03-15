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

package org.openo.vnfsdk.lctest.common.constant;

/**
 * <br>
 * <p>
 * </p>
 * 
 * @author
 * @version NFVO 0.5 Mar 14, 2017
 */
public class UrlConstant {

    private UrlConstant() {
        // private constructor
    }

    /**
     * MSB register url.
     */
    public static final String REST_MSB_REGISTER = "/openoapi/microservices/v1/services";

    /**
     * NSLCM VNF package onboarding url.
     */
    public static final String NSLCM_VNFPACKAGE_URL = "/openoapi/nslcm/v1/vnfpackage";

    public static final String QUERY_VNFPACKAGE_URL = "/openoapi/nslcm/v1/vnfpackage/%s";

}
