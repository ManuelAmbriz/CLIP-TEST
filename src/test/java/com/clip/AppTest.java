package com.clip;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.clip.ClipApplication;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClipApplication.class)
public class AppTest {

    @Test
    public void testNothing() {
        assertThat(true).isNotNull();
    }
}
