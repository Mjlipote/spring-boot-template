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
package tw.edu.ym.lab525.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.edu.ym.lab525.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

  public Book findByName(String name);

  public Book findByIsbn(String isbn);

  public Book findByNameAndAuthorAndPrice(String name, String author,
      Integer price);

  public Set<Book> findByAuthor(String author);

  public Set<Book> findByPrice(String price);

  public List<Book> PriceBetween(Integer priceStart, Integer priceEnd);

  public List<Book> findByAuthorAndPriceBetween(String author,
      Integer priceStart, Integer priceEnd);

}
