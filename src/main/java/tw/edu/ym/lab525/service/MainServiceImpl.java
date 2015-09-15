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
package tw.edu.ym.lab525.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import tw.edu.ym.lab525.entity.Book;
import tw.edu.ym.lab525.repository.BookRepository;

public class MainServiceImpl implements MainService {

  @Autowired
  private BookRepository bookRepo;

  @Override
  public List<Book> view() {
    return bookRepo.findAll();
  }

  @Override
  public List<Book> lookup(String isbn) {
    return isbn.equals("") ? bookRepo.findAll()
        : Arrays.asList(bookRepo.findByIsbn(isbn));
  }

  @Override
  public void create(String name, String isbn, String author, Integer price) {
    Book book = new Book();
    book.setName(name);
    book.setIsbn(isbn);
    book.setAuthor(author);
    book.setPrice(price);
    bookRepo.save(book);
  }

  @Override
  public Book read(String isbn) {
    return bookRepo.findByIsbn(isbn);
  }

  @Override
  public void update(String isbn, Integer price) {
    Book book = bookRepo.findByIsbn(isbn);
    book.setPrice(price);
    bookRepo.saveAndFlush(book);
  }

  @Override
  public void delete(String isbn) {
    bookRepo.delete(bookRepo.findByIsbn(isbn));
  }

}
