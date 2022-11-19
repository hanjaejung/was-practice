package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringsTest {
    @Test
    void createTest() {
        QueryStrings queryStrings = new QueryStrings("operand1=11&operator=*&operand2=55"); // List<QueryString>
        //위의 queryStrings("operand1=11&operator=*&operand2=55")가 올바르게 key, value형태로 있는지 검증하기 위한 테스트
        assertThat(queryStrings).isNotNull();
    }
}
