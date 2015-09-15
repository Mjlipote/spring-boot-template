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

import tw.edu.ym.lab525.repository.BookRepository;
import tw.edu.ym.lab525.service.MainService;

@RequestMapping("/books")
@Controller
public class MainController {

  @Autowired
  BookRepository bookRepo;

  @Autowired
  MainService mainService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String view(ModelMap map) {
    map.addAttribute("books", mainService.view());
    return "books";
  }

  @RequestMapping(value = "/lookup", method = RequestMethod.GET)
  public String lookup(ModelMap map,
      @RequestParam(value = "isbn") String isbn) {
    if (bookRepo.findByIsbn(isbn) == null) {
      map.addAttribute("errorMessage", "找不到這本書");
      return "books";
    } else {
      map.addAttribute("books", mainService.lookup(isbn));
      return "books";
    }
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
      mainService.create(name, isbn, author, price);
      return "redirect:/books";
    }
  }

  @RequestMapping(value = "/{isbn}", method = RequestMethod.GET)
  public String read(ModelMap map, @PathVariable("isbn") String isbn) {

    map.addAttribute("books", mainService.read(isbn));

    return "book-info";
  }

  @RequestMapping(value = "/{isbn}", method = RequestMethod.PUT)
  public String update(ModelMap map, @PathVariable("isbn") String isbn,
      @RequestParam(value = "price") Integer price) {
    mainService.update(isbn, price);
    return "redirect:/books";
  }

  @RequestMapping(value = "/{isbn}", method = RequestMethod.DELETE)
  public String delete(ModelMap map, @PathVariable("isbn") String isbn) {
    mainService.delete(isbn);
    return "redirect:/books";
  }

  @RequestMapping("/hello")
  @ResponseBody
  public String hello() {
    return "Hello!!!";
  }

}
