import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiMethodUtil;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Created by vinayaprasadn on 1/10/16.
 */
public class MouseClickAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiClass psiClass=getPsiClassFromContext(e);
        new ToStringGenerator(psiClass).execute();

    }





    private PsiClass getPsiClassFromContext(AnActionEvent e) {
        PsiFile psiFile= e.getData(LangDataKeys.PSI_FILE);
        Editor editor= e.getData(PlatformDataKeys.EDITOR);
        if(psiFile==null||editor==null){
            e.getPresentation().setEnabled(false);
            return null;
        }
        int offset=editor.getCaretModel().getOffset();
        PsiElement elementAt=psiFile.findElementAt(offset);
        PsiClass psiClass= PsiTreeUtil.getParentOfType(elementAt,PsiClass.class);
        return psiClass;
    }

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        PsiClass psiClass=  getPsiClassFromContext(e);
    }
}
