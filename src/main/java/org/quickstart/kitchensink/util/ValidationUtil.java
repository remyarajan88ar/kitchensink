package org.quickstart.kitchensink.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.quickstart.kitchensink.domain.Member;
import org.quickstart.kitchensink.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
public class ValidationUtil {

    @Autowired
    private Validator validator;

    @Autowired
    private MemberRepository memberRepository;


    public void validateMember(Member member) {
        Set<ConstraintViolation<Member>> violations = validator.validate(member);
        if (!CollectionUtils.isEmpty(violations)) {
            throw new ConstraintViolationException(violations);
        }

        if (emailAlreadyExists(member.getEmail())) {
            throw new ValidationException("Unique email violation");
        }
    }

    private boolean emailAlreadyExists(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.isPresent();
    }
}
