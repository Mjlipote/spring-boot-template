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

import static com.google.common.collect.Lists.newArrayList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.wnameless.workbookaccessor.WorkbookWriter;

import net.sf.rubycollect4j.RubyFile;
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

  @RequestMapping(value = "/test", method = RequestMethod.GET)
  public void queryMethod(@RequestParam String id,
      @RequestParam Map<String, String> queryParameters,
      @RequestParam MultiValueMap<String, String> multiMap) {
    System.out.println("id=" + id);
    System.out.println(queryParameters);
    System.out.println(multiMap);
  }

  @RequestMapping(value = "/downloadExcel", method = RequestMethod.GET)
  public void downloadExcel(HttpServletResponse response,
      @RequestParam Map<String, String> queryParameters,
      @RequestParam MultiValueMap<String, String> multiMap) throws IOException {

    List<Map<String, String>> maps = newArrayList();
    maps.addAll(Arrays.asList(queryParameters));

    System.out.println(maps);

    WorkbookWriter writer = WorkbookWriter.openXLSX().setSheetName("test");
    for (String key : queryParameters.keySet()) {
      writer.addRow(key, queryParameters.get(key));
    }
    String fileName = "TestExcelFile-" + new Date() + ".xls";
    InputStream is = new FileInputStream(writer.save(fileName));
    IOUtils.copy(is, response.getOutputStream());
    response.setHeader("Content-disposition",
        "attachment; filename=" + fileName);
    response.flushBuffer();
    RubyFile.delete(fileName);
  }

}