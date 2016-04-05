/*
 *
 * Copyright 2015 Ming-Jheng Li
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package tw.edu.ym.lab525.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.edu.ym.lab525.chart.DataTable;
import tw.edu.ym.lab525.service.ApiService;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class ApiController {

  @Autowired
  ApiService apiService;

  @RequestMapping(value = "/area-chart", method = GET)
  DataTable AreaChart() {
    return apiService.AreaChart();
  }

  @RequestMapping(value = "/pie-chart", method = GET)
  DataTable PieChart() {
    return apiService.PieChart();
  }

  @RequestMapping(value = "/bar-chart", method = GET)
  DataTable BarChart() {
    return apiService.BarChart();
  }

  @RequestMapping(value = "/line-chart", method = GET)
  DataTable lineChart() {
    return apiService.LineChart();
  }

}