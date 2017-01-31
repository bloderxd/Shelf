package com.example;

import com.squareup.javapoet.JavaFile;

import javax.annotation.processing.Filer;

/**
 * Created by bloder on 21/01/17.
 */

public interface Generator {

    void generate(JavaFile javaFile, Filer filer);
}
