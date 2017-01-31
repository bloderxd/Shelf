package com.example;

import com.example.environment.EnvironmentGenerator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Created by bloder on 21/01/17.
 */

public class RepositoryGenerator implements Generator {

    private List<Element> lockers;
    private String rootPackage = "bloder";
    private EnvironmentGenerator environmentGenerator;
    private TypeSpec.Builder repoClass;

    public RepositoryGenerator(EnvironmentGenerator environmentGenerator) {
        this.lockers = new ArrayList<>();
        this.environmentGenerator = environmentGenerator;
    }

    public void setLocker(Element locker) {
        this.lockers.add(locker);
    }

    public void startGenerate(Filer filer) {
        environmentGenerator.generateEnvironmentClass(filer);
        for (Element locker : lockers) repoClass.addMethod(generateEnvMethod(locker));
        JavaFile javaFile = JavaFile.builder(rootPackage, repoClass.build()).build();
        generate(javaFile, filer);
    }

    public void setProdMethod(Element element, Element prod) {
        if (prod != null) environmentGenerator.generateEnvMethod("inProdEnvironment", prod, element);
    }

    public void setFakeMethod(Element element, Element fake) {
        if (fake != null) environmentGenerator.generateEnvMethod("inFakeEnvironment", fake, element);
    }

    public void createClasses(Element element) {
        environmentGenerator.createEnvironmentClass(element);
        repoClass = TypeSpec.classBuilder("Shelf").addModifiers(Modifier.PUBLIC, Modifier.FINAL);
    }

    @Override public void generate(JavaFile javaFile, Filer filer) {
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MethodSpec generateEnvMethod(Element locker) {
        return MethodSpec.methodBuilder("for" + locker.getSimpleName().toString())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .returns(ClassName.get(rootPackage, environmentGenerator.getEnvClass(locker)))
                .addStatement("return new $T()", ClassName.get(rootPackage, environmentGenerator.getEnvClass(locker)))
                .build();
    }
}
