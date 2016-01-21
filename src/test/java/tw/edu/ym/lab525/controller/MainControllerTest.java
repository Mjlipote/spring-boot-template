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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tw.edu.ym.lab525.ApplicationTest;
import tw.edu.ym.lab525.repository.BookRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationTest.class)
@WebIntegrationTest
public class MainControllerTest {

  RestTemplate restTemplate = new TestRestTemplate();
  ObjectMapper mapper = new ObjectMapper();

  @Autowired
  BookRepository bookRepo;

  @Test
  public void testHelloPage() {
    ResponseEntity<String> res = restTemplate
        .getForEntity("http://localhost:8080/books/hello", String.class);
    assertEquals("Hello!!!", res.getBody());
  }

  @Test
  public void testView() {
    ResponseEntity<String> res =
        restTemplate.getForEntity("http://localhost:8080/books", String.class);
    Document doc = Jsoup.parse(res.getBody());
    assertEquals("密碼學之旅與MATHEMATICA同行",
        doc.getElementsByTag("td").get(8).text());
    assertEquals("957-21-5210-6", doc.getElementsByTag("td").get(9).text());
    assertEquals("沈淵源", doc.getElementsByTag("td").get(10).text());
    assertEquals("400", doc.getElementsByTag("td").get(11).text());
  }

  @Test
  public void testRead() {
    ResponseEntity<String> res = restTemplate.getForEntity(
        "http://localhost:8080/books/957-21-5210-6", String.class);
    Document doc = Jsoup.parse(res.getBody());
    assertEquals("密碼學之旅與MATHEMATICA同行",
        doc.getElementsByTag("input").get(0).val());
    assertEquals("957-21-5210-6", doc.getElementsByTag("input").get(1).val());
    assertEquals("沈淵源", doc.getElementsByTag("input").get(2).val());
    assertEquals("400", doc.getElementsByTag("input").get(3).val());

  }

  @Test
  public void testDelete() throws JsonProcessingException, URISyntaxException {

    restTemplate.exchange(new URI("http://localhost:8080/books/957-21-5210-6"),
        HttpMethod.DELETE, null, String.class);

    assertTrue(bookRepo.findByIsbn("957-21-5210-6") == null);
  }

  @Test
  public void testCreate() throws URISyntaxException, IOException {

    MultiValueMap<String, Object> map =
        new LinkedMultiValueMap<String, Object>();

    map.add("name", "密碼學概論");
    map.add("isbn", "957-21-5210-8");
    map.add("author", "李明政");
    map.add("price", 450);

    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
    requestHeaders.add("Content-Type",
        "application/x-www-form-urlencoded; charset=UTF-8");

    HttpEntity<MultiValueMap<String, Object>> requestEntity =
        new HttpEntity<MultiValueMap<String, Object>>(map, requestHeaders);
    restTemplate.getMessageConverters().add(0,
        new StringHttpMessageConverter(Charset.forName("UTF-8")));
    restTemplate.exchange(new URI("http://localhost:8080/books"),
        HttpMethod.POST, requestEntity, String.class);

    assertTrue(bookRepo.findByIsbn("957-21-5210-8") != null);

  }

}
