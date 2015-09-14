/*
 *
 * @author Ming-Jheng Li
 *
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.edu.ym.lab525.entity.Book;
import tw.edu.ym.lab525.repository.BookRepository;

@RequestMapping("/books")
@Controller
public class MainController {

  @Autowired
  BookRepository bookRepo;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String view(ModelMap map) {
    map.addAttribute("books", bookRepo.findAll());

    return "books";
  }

  @RequestMapping(value = "/lookup", method = RequestMethod.GET)
  public String lookup(ModelMap map,
      @RequestParam(value = "isbn") String isbn) {
    map.addAttribute("books",
        isbn.equals("") ? bookRepo.findAll() : bookRepo.findByIsbn(isbn));

    return "books";
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public String create(ModelMap map, @RequestParam(value = "name") String name,
      @RequestParam(value = "isbn") String isbn,
      @RequestParam(value = "author") String author,
      @RequestParam(value = "price") Integer price) {

    if (name.equals("") || isbn.equals("") || author.equals("")
        || price == null) {
      map.addAttribute("errorMessage", "請勿輸入空值");
      return "books";
    } else if (bookRepo.findByIsbn(isbn) != null) {
      map.addAttribute("errorMessage", "ISBN 重複");
      return "books";
    } else {
      Book book = new Book();
      book.setName(name);
      book.setIsbn(isbn);
      book.setAuthor(author);
      book.setPrice(price);
      bookRepo.save(book);

      return "redirect:/books";
    }
  }

  @RequestMapping(value = "/{isbn}", method = RequestMethod.GET)
  public String update(ModelMap map, @PathVariable("isbn") String isbn) {

    map.addAttribute("books", bookRepo.findByIsbn(isbn));

    return "book-info";
  }

  @RequestMapping(value = "/{isbn}", method = RequestMethod.PUT)
  public String edit(ModelMap map, @PathVariable("isbn") String isbn,
      @RequestParam(value = "price") Integer price) {

    Book book = bookRepo.findByIsbn(isbn);
    book.setPrice(price);
    bookRepo.saveAndFlush(book);

    return "redirect:/books";
  }

  @RequestMapping("/hello")
  @ResponseBody
  public String hello() {
    return "Hello!!!";
  }

}
