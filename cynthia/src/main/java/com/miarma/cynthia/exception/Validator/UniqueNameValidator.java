package com.miarma.cynthia.exception.Validator;

import com.miarma.cynthia.exception.annotations.UniqueName;
import com.miarma.cynthia.users.repos.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueNameValidator implements ConstraintValidator<UniqueName, String> {
    @Autowired
    private UserEntityRepository repository;

    @Override
    public void initialize(UniqueName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String route, ConstraintValidatorContext constraintValidatorContext) {
        if(repository == null){
            return true;
        }
        return StringUtils.hasText(route) && !repository.existsByFullName(route);
    }
}