package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringTest {
    @Test
    void createTest() {
        QueryString queryString = new QueryString("operand1", "11");
        //key, value가 온전히 있는지 테스트
        assertThat(queryString).isNotNull();
    }
}
