// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.codeInsight.externalAnnotation;

import com.intellij.codeInspection.dataFlow.Mutability;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierListOwner;
import com.siyeh.ig.psiutils.ClassUtils;
import org.jetbrains.annotations.NotNull;

public class UnmodifiableViewAnnotationProvider implements AnnotationProvider {

  @NotNull
  @Override
  public String getName(Project project) {
    return Mutability.UNMODIFIABLE_VIEW_ANNOTATION;
  }

  @Override
  public boolean isAvailable(PsiModifierListOwner owner) {
    return ApplicationManager.getApplication().isInternal() &&
           owner instanceof PsiMethod &&
           !ClassUtils.isImmutable(((PsiMethod)owner).getReturnType());
  }

  @Override
  public String @NotNull [] getAnnotationsToRemove(Project project) {
    return new String[]{Mutability.UNMODIFIABLE_ANNOTATION};
  }
}
