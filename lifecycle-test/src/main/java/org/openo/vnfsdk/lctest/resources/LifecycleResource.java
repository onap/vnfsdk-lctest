/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
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
package org.openo.vnfsdk.lctest.resources;

import java.io.InputStream;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;





/**
 * csar package test service.
 * <p>
 * </p>
 * 
 * @author 71221
 */
@Path("/")
public class LifecycleResource {

	  @Path("/csars")
	  @POST
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response lctest(InputStream uploadedInputStream) {
		  //TODO code to be added for lctest , csar will be input in stream 
		    return Response.ok().build();
	  }
}
