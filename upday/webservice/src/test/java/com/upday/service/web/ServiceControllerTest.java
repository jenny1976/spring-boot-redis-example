package com.upday.service.web;

import com.upday.service.web.rs.domain.RsArticle;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * @author jschulz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class ServiceControllerTest {
    
    private MockMvc mvc;
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new ServiceController()).build();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCreateArticle() throws Exception {
        System.out.println("createArticle");
//        mvc.perform(MockMvcRequestBuilders.get("/article/update/1234").accept(MediaType.APPLICATION_XML))
//                .andExpect(status().isOk());
    }

    
}
