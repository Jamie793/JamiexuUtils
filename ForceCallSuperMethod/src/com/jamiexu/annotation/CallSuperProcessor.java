package com.jamiexu.annotation;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.util.HashSet;
import java.util.Set;



public class CallSuperProcessor extends AbstractProcessor {

    public CallSuperProcessor() {
        super();
    }

    @Override
    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> strings = new HashSet<>();
        strings.add(ForceCallSuper.class.getCanonicalName());
        return strings;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
        return super.getCompletions(element, annotation, member, userText);
    }

    @Override
    protected synchronized boolean isInitialized() {
        return super.isInitialized();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(ForceCallSuper.class)) {
            if (!element.getModifiers().contains(Modifier.PUBLIC) || !element.getModifiers().contains(Modifier.STATIC)) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "please add public and static", element);
            }
        }
        return true;
    }


}
