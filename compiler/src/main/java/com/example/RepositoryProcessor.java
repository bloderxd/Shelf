package com.example;

import com.example.repository.environment.EnvironmentGenerator;
import com.example.repository.validator.LockerValidator;
import com.google.auto.service.AutoService;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Created by bloder on 20/01/17.
 */

@AutoService(Processor.class)
public class RepositoryProcessor extends AbstractProcessor {

    private Filer filer;
    private RepositoryGenerator generator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        generator = new RepositoryGenerator(new EnvironmentGenerator());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Locker.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Locker.class)) {
            if (LockerValidator.canCreateLocker(element)) {
                generator.setLocker(element);
                generator.createClasses(element);
                generator.setProdMethod(element, LockerValidator.getProd(element, roundEnvironment));
                generator.setFakeMethod(element, LockerValidator.getfake(element, roundEnvironment));
            }
        }
        generator.startGenerate(filer);
        return false;
    }
}
