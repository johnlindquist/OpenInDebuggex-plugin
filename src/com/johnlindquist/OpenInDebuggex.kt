package com.johnlindquist

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile
import com.intellij.codeInsight.intention.impl.QuickEditAction
import org.intellij.lang.regexp.RegExpLanguage
import com.intellij.codeInsight.editorActions.SelectWordUtil
import com.intellij.psi.util.PsiUtil
import com.intellij.psi.util.PsiUtilBase
import java.net.URLEncoder

public class OpenInDebuggex: QuickEditAction() {
    public override fun getText(): String {
        return "OpenInDebuggex"
    }

    public override fun getFamilyName(): String {
        return getText()
    }

    public override fun isAvailable(project: Project, editor: Editor?, file: PsiFile?): Boolean {
        val pair = getRangePair(file, editor)

        if(pair != null && pair.first != null){
            val language = pair.first?.getLanguage()
            val baseLanguage = language?.getBaseLanguage()

            return language == RegExpLanguage.INSTANCE || baseLanguage == RegExpLanguage.INSTANCE
        }

        return false
    }

    public override fun invoke(project: Project, editor: Editor?, file: PsiFile?) {
        val psiElement = PsiUtilBase.getElementAtCaret(editor!!)

        val string = psiElement?.getText()
        val encode = URLEncoder.encode(string!!)
        val url = "http://debuggex.com/?re=" + encode
        BrowserUtil.launchBrowser(url)
    }
}