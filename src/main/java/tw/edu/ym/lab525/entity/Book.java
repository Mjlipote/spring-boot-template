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
package tw.edu.ym.lab525.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

@Entity
public class Book extends AbstractPersistable<Long>implements Comparable<Book> {

  private static final long serialVersionUID = 1L;

  public Book() {}

  public Book(String name, String isbn, String author, Integer price) {
    this.name = name;
    this.isbn = isbn;
    this.author = author;
    this.price = price;

  }

  @Column(nullable = false)
  private String name;

  @Column(unique = true, nullable = false)
  private String isbn;

  @Column(nullable = false)
  private String author;

  @Column(nullable = false)
  private Integer price;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  @Override
  public int compareTo(final Book other) {
    return ComparisonChain.start().compare(name, other.name)
        .compare(isbn, other.isbn).compare(author, other.author)
        .compare(price, other.price).result();
  }

  @Override
  public boolean equals(final Object other) {
    if (!(other instanceof Book)) {
      return false;
    }
    Book castOther = (Book) other;
    return Objects.equal(name, castOther.name)
        && Objects.equal(isbn, castOther.isbn)
        && Objects.equal(author, castOther.author)
        && Objects.equal(price, castOther.price);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name, isbn, author, price);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("name", name).add("isbn", isbn)
        .add("author", author).add("price", price).toString();
  }

}
