package com.example.validator;

import com.example.repository.Fake;
import com.example.repository.Prod;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by bloder on 20/01/17.
 */

public class LockerValidator {

    public static boolean canCreateLocker(Element element) {
        return element.getKind().equals(ElementKind.INTERFACE) && element.getModifiers().contains(Modifier.PUBLIC);
    }

    public static Element getProd(Element element, RoundEnvironment roundEnvironment) {
        for (Element prod : roundEnvironment.getElementsAnnotatedWith(Prod.class)) {
            if (((TypeElement) prod).getInterfaces().contains(element.asType())) {
                return prod;
            }
        }
        return null;
    }

    public static Element getfake(Element element, RoundEnvironment roundEnvironment) {
        for (Element fake : roundEnvironment.getElementsAnnotatedWith(Fake.class)) {
            if (((TypeElement) fake).getInterfaces().contains(element.asType())) {
                return fake;
            }
        }
        return null;
    }
}
