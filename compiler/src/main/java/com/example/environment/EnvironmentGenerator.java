package com.example.environment;

import com.example.Generator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Created by bloder on 28/01/17.
 */

public class EnvironmentGenerator implements Generator {

    private Map<TypeSpec.Builder, Element> environmentGeneratedClass;

    public EnvironmentGenerator() {
        environmentGeneratedClass = new HashMap<>();
    }

    public void generateEnvironmentClass(Filer filer) {
        for (Map.Entry<TypeSpec.Builder, Element> builder : environmentGeneratedClass.entrySet()) {
            JavaFile javaFile = JavaFile.builder("bloder", builder.getKey().build()).build();
            generate(javaFile, filer);
        }
    }

    public void createEnvironmentClass(Element locker) {
        environmentGeneratedClass.put(TypeSpec.classBuilder(locker.getSimpleName().toString() + "Environment").addModifiers(Modifier.PUBLIC, Modifier.FINAL), locker);
    }

    public void generateEnvMethod(String methodName, Element env, Element element) {
        for (Map.Entry<TypeSpec.Builder, Element> builder : environmentGeneratedClass.entrySet()) {
            if (builder.getValue().equals(element)) {
                builder.getKey().addModifiers(Modifier.PUBLIC, Modifier.FINAL).addMethod(createMethod(methodName, env));
            }
        }
    }

    private MethodSpec createMethod(String methodName, Element element) {
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .returns(ClassName.get(element.getEnclosingElement().toString(), element.getSimpleName().toString()))
                .addStatement("return new $T()", ClassName.get(element.getEnclosingElement().toString(), element.getSimpleName().toString()))
                .build();
    }

    public String getEnvClass(Element element) {
        for (Map.Entry<TypeSpec.Builder, Element> builder : environmentGeneratedClass.entrySet()) {
            if (element.equals(builder.getValue())) return builder.getKey().build().name;
        }
        //return environmentGeneratedClass.build().name;
        return "error";
    }

    @Override
    public void generate(JavaFile javaFile, Filer filer) {
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
