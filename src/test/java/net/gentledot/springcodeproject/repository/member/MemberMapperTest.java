package net.gentledot.springcodeproject.repository.member;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberMapperTest {
    private static final Logger log = LoggerFactory.getLogger(MemberMapperTest.class);

    @Autowired
    private MemberMapper memberMapper;

    @Test
    void getTimeTest() {
        log.debug("시각 확인 : {}", memberMapper.getTime());
    }
}