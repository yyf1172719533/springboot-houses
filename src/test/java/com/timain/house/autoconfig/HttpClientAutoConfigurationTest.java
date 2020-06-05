package com.timain.house.autoconfig;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.*;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/27 16:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpClientAutoConfigurationTest {

    @Autowired
    private HttpClient httpClient;

    @Test
    public void httpClient() throws Exception {
        String s = EntityUtils.toString(httpClient.execute(new HttpGet("http://www.baidu.com")).getEntity());
        System.out.println(s);
    }
}