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

import static com.google.common.collect.Sets.newHashSet;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import tw.edu.ym.lab525.chart.ColumnDescription;
import tw.edu.ym.lab525.chart.DataTable;
import tw.edu.ym.lab525.chart.Type;
import tw.edu.ym.lab525.chart.Value;
import tw.edu.ym.lab525.entity.Book;
import tw.edu.ym.lab525.repository.BookRepository;

public class ApiServiceImpl implements ApiService {

  @Autowired
  private BookRepository bookRepo;

  @Override
  public DataTable ChartDataTable() {
    DataTable dataTable = new DataTable();
    dataTable.addColumn(new ColumnDescription("price", "Price", Type.STRING));
    dataTable.addRow("0~500");
    dataTable.addRow("500~1000");
    dataTable.addRow("1000+");
    Set<String> authors = newHashSet();
    for (Book book : bookRepo.findAll()) {
      authors.add(book.getAuthor());
    }
    for (String author : authors) {
      dataTable.addColumn(new ColumnDescription("author", author, Type.NUMBER));
      dataTable.getCells(0).add(new Value<Integer>(
          bookRepo.findByAuthorAndPriceBetween(author, 0, 500).size()));
      dataTable.getCells(1).add(new Value<Integer>(
          bookRepo.findByAuthorAndPriceBetween(author, 500, 1000).size()));
      dataTable.getCells(2)
          .add(new Value<Integer>(bookRepo.findByAuthor(author).size()
              - bookRepo.findByAuthorAndPriceBetween(author, 0, 500).size()));
    }
    return dataTable;
  }

  @Override
  public DataTable ChartDataTableAll() {
    DataTable dataTable = new DataTable();
    dataTable.addColumn(new ColumnDescription("price", "Price", Type.STRING))
        .addColumn(new ColumnDescription("number", "Number", Type.NUMBER));
    dataTable.addRow("0~500", bookRepo.PriceBetween(0, 500).size());
    dataTable.addRow("500~1000", bookRepo.PriceBetween(500, 1000).size());
    dataTable.addRow("1000+",
        bookRepo.findAll().size() - bookRepo.PriceBetween(0, 1000).size());
    return dataTable;
  }

  @Override
  public DataTable PieChart() {
    return ChartDataTableAll();
  }

  @Override
  public DataTable AreaChart() {
    return ChartDataTable();
  }

  @Override
  public DataTable BarChart() {
    return ChartDataTable();
  }

  @Override
  public DataTable LineChart() {
    return ChartDataTable();
  }

}
