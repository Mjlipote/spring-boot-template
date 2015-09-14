/*
 *
 * @author Ming-Jheng Li
 *
 *
 *         Copyright 2015 Ming-Jheng Li
 *
 *         Licensed under the Apache License, Version 2.0 (the "License"); you
 *         may not use this file except in compliance with the License. You may
 *         obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *         Unless required by applicable law or agreed to in writing, software
 *         distributed under the License is distributed on an "AS IS" BASIS,
 *         WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *         implied. See the License for the specific language governing
 *         permissions and limitations under the License.
 *
 */
package tw.edu.ym.lab525.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import tw.edu.ym.lab525.entity.Book;
import tw.edu.ym.lab525.repository.BookRepository;

@Controller
public class SetupController {

  @Autowired
  BookRepository bookRepo;

  @PostConstruct
  void postProcessData() {

    Book book = new Book();
    book.setName("密碼學之旅與MATHEMATICA同行");
    book.setIsbn("957-21-5210-6");
    book.setAuthor("沈淵源");
    book.setPrice(400);
    bookRepo.save(book);
  }

}
