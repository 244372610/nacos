/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.nacos.cmdb.controllers;

import com.alibaba.nacos.cmdb.core.SwitchAndOptions;
import com.alibaba.nacos.cmdb.memory.CmdbProvider;
import com.alibaba.nacos.cmdb.utils.UtilsAndCommons;
import com.alibaba.nacos.core.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:zpf.073@gmail.com">nkorange</a>
 */
@RestController
@RequestMapping(UtilsAndCommons.NACOS_CMDB_CONTEXT + "/ops")
public class OperationController {

    @Autowired
    private SwitchAndOptions switches;

    @Autowired
    private CmdbProvider cmdbProvider;

    @RequestMapping(value = "/updateSwitch", method = RequestMethod.POST)
    public String updateSwitch(HttpServletRequest request) throws Exception {

        String entry = WebUtils.required(request, "entry");
        String value = WebUtils.required(request, "value");

        switch (entry) {
            case "dumpTaskInterval":
                switches.setDumpTaskInterval(Integer.parseInt(value));
                break;
            case "eventTaskInterval":
                switches.setEventTaskInterval(Integer.parseInt(value));
                break;
            case "loadDataAtStart":
                switches.setLoadDataAtStart(Boolean.parseBoolean(value));
                break;
            case "labelTaskInterval":
                switches.setLabelTaskInterval(Integer.parseInt(value));
            default:
                break;
        }
        return "ok";
    }

    @RequestMapping(value = "/queryLabel", method = RequestMethod.GET)
    public String queryLabel(HttpServletRequest request) throws Exception {
        String entry = WebUtils.required(request, "entry");
        String label = WebUtils.required(request, "label");
        return cmdbProvider.queryLabel(entry, "ip", label);
    }
}
