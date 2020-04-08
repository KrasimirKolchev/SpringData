package com.usersystem.customAnnotations.password;

import org.passay.*;
import org.passay.dictionary.WordListDictionary;
import org.passay.dictionary.WordLists;
import org.passay.dictionary.sort.ArraysSort;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

   private DictionaryRule dictionaryRule;

   @Override
   public void initialize(ValidPassword constraint) {
      try {
         String invalidPasswordList = this.getClass().getResource("/invalid-password-list.txt").getFile();
         dictionaryRule = new DictionaryRule(
                 new WordListDictionary(WordLists.createFromReader(
                         new FileReader[] { //Reader from the invalid-password-list.txt file
                                 new FileReader(invalidPasswordList)}, false, new ArraysSort()))
         );
      } catch (IOException e) {
         throw new RuntimeException("Cannot load word List", e);
      }
   }

   public boolean isValid(String password, ConstraintValidatorContext context) {
      PasswordValidator validator = new PasswordValidator(Arrays.asList(
              //Between 4 and 30 symbols long
              new LengthRule(4, 30),
              //at least one upper-case char
              new CharacterRule(EnglishCharacterData.UpperCase, 1),
              //at least one lower-case char
              new CharacterRule(EnglishCharacterData.LowerCase, 1),
              // ... digit char
              new CharacterRule(EnglishCharacterData.Digit, 1),
              // ... special Symbol
              new CharacterRule(EnglishCharacterData.Special, 1),
              // no spaces
              new WhitespaceRule(),

              dictionaryRule
      ));

      RuleResult ruleResult = validator.validate(new PasswordData(password));

      if (ruleResult.isValid()) {
         return true;
      }
      List<String> messages = validator.getMessages(ruleResult);
      String messageTemplate = messages.stream().collect(Collectors.joining(","));
      context.buildConstraintViolationWithTemplate(messageTemplate)
              .addConstraintViolation()
              .disableDefaultConstraintViolation();
      return false;
   }
}
