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

package org.openo.vnfsdk.lctest.service.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.vnfsdk.lctest.common.util.RequestUtil;
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
@Path("/v1/vnfpackage")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VnfpackageRoa {

    private static final Logger LOGGER = LoggerFactory.getLogger(VnfpackageRoa.class);

    private VnfpackageService vnfpackageService;

    /**
     * <br>
     *
     * @param context
     * @return
     * @throws ServiceException
     * @since NFVO 0.5
     */
    @POST
    public JSONObject onboarding(@Context HttpServletRequest context) throws ServiceException {
        JSONObject object = RequestUtil.getJsonRequestBody(context);
        if(null == object) {
            LOGGER.error("function=onboarding; msg=context is null.");
            throw new ServiceException("org.openo.vnfsdk.lctest.service.rest.VnfpackageRoa.onboarding.null");
        }

        LOGGER.info("VnfpackageRoa::onboarding:{}", object.toString());
        return vnfpackageService.onboarding(object);
    }

    /**
     * <br>
     *
     * @param context
     * @param csarId
     * @return
     * @throws ServiceException
     * @since NFVO 0.5
     */
    @GET
    @Path("/{csarId}")
    public JSONObject queryVnfpackage(@Context HttpServletRequest context, @PathParam("csarId") String csarId)
            throws ServiceException {
        LOGGER.warn("function=queryVnfpackage, csarId={}", csarId);
        return vnfpackageService.queryVnfpackage(csarId);
    }

    public void setVnfpackageService(VnfpackageService vnfpackageService) {
        this.vnfpackageService = vnfpackageService;
    }
}
