package org.quickstart.kitchensink.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.quickstart.kitchensink.domain.Member;
import org.quickstart.kitchensink.repository.MemberRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class ValidationUtilTest {

    @Mock
    private Validator validator;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    ConstraintViolation<Member> constraintViolation;

    @InjectMocks
    private ValidationUtil validationUtil = new ValidationUtil();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateMemberTest() {
        Set<ConstraintViolation<Member>> violations = new HashSet<>();
        Optional<Member> memberOptional = Optional.empty();
        Mockito.doReturn(violations).when(validator).validate(Mockito.any());
        Mockito.when(memberRepository.findByEmail(Mockito.any())).thenReturn(memberOptional);
        validationUtil.validateMember(new Member());
        Mockito.verify(validator, Mockito.times(1)).validate(Mockito.any());
        Mockito.verify(memberRepository, Mockito.times(1)).findByEmail(Mockito.any());
    }

    @Test
    void validateMemberConstraintViolationTest() {
        Set<ConstraintViolation<Member>> violations = new HashSet<>();
        violations.add(constraintViolation);
        Mockito.doReturn(violations).when(validator).validate(Mockito.any());
        Assertions.assertThrows(ConstraintViolationException.class, () -> validationUtil.validateMember(new Member()));
    }

    @Test
    void validateMemberValidationExceptionTest() {
        Set<ConstraintViolation<Member>> violations = new HashSet<>();
        Mockito.doReturn(violations).when(validator).validate(Mockito.any());
        Optional<Member> memberOptional = Optional.of(new Member());
        Mockito.when(memberRepository.findByEmail(Mockito.any())).thenReturn(memberOptional);
        Assertions.assertThrows(ValidationException.class, () -> validationUtil.validateMember(new Member()));
    }

}
