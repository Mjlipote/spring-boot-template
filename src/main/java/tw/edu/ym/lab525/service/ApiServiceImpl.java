/*
 *
 * Copyright 2016 Ming-Jheng Li
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
package tw.edu.ym.lab525.service;

import org.springframework.beans.factory.annotation.Autowired;

import tw.edu.ym.lab525.chart.ColumnDescription;
import tw.edu.ym.lab525.chart.DataTable;
import tw.edu.ym.lab525.chart.Type;
import tw.edu.ym.lab525.repository.BookRepository;

public class ApiServiceImpl implements ApiService {

  @Autowired
  private BookRepository bookRepo;

  @Override
  public DataTable lineChartAllBetween() {

    DataTable dataTable = new DataTable();
    dataTable.addColumn(new ColumnDescription("price", "Price", Type.STRING))
        .addColumn(new ColumnDescription("number", "Number", Type.NUMBER));

    dataTable.addRow("0~500", bookRepo.PriceBetween(0, 500).size());
    dataTable.addRow("500~1000", bookRepo.PriceBetween(500, 1000).size());
    dataTable.addRow("1000+",
        bookRepo.findAll().size() - bookRepo.PriceBetween(0, 1000).size());

    return dataTable;
  }

}
