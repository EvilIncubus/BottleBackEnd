package org.bottleProject.service;

import org.bottleProject.dao.BottleDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("integration_test")
public class BottleServiceTest {

    @Autowired
    BottleService bottleService;

    @MockBean
    BottleDao bottleDao;

    @Test
    public void shouldCreateBottle(){

    }

    @Test
    public void shouldGetBottleById(){

    }

    @Test
    public void shouldGetListOfBottleByCategory(){

    }
}
