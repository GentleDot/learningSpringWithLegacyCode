package net.gentledot.springcodeproject.repository.member;

import net.gentledot.springcodeproject.model.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MemberMapperTest {
    private static final Logger log = LoggerFactory.getLogger(MemberMapperTest.class);

    @Autowired
    private MemberMapper memberMapper;

    @Test
    @DisplayName("DB에서 현재 시각 조회")
    void getTimeTest() {
        log.debug("시각 확인 : {}", memberMapper.getTime());
    }

    @Test
    @DisplayName("Member 객체를 DB에 저장")
    @Transactional
    @Rollback
    void insertMemberTest() {
        Member testMember = new Member.Builder("user01")
                .userpw("user01")
                .username("USER01")
                .email("user01@test.com")
                .build();

        int result = memberMapper.insertMember(testMember);

        assertEquals(result, 1);
    }

    @Test
    @DisplayName("대상 userid를 가지고 있는 Member 객체를 조회")
    void findByUserIdTest() {
        Member user00 = memberMapper.findByUserId("user00");

        log.debug("조회 유저 : {}", user00);
    }

    @Test
    @DisplayName("대상 userid를 가지고 있는 Member 객체를 조회")
    void findByUserIdAndUserPwTest() {
        Member user00 = memberMapper.findByUserIdAndUserPw("user00", "user00");

        log.debug("조회 유저 : {}", user00);
    }
}