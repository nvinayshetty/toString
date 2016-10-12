import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
``import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;

/**
 * Created by vinayaprasadn on 1/10/16.
 */
public class MouseClickAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        //get the class where the user action has began
        PsiClass classUnderCaret=getPsiClassUnderCaret(event);

        //Disable plugin from appearing if IDE is not ready
       event.getPresentation().setEnabled(classUnderCaret!=null);

        //add to string method to the class under caret
       new ToStringGenerator(classUnderCaret).execute();

    }


    @Override
    public void update(AnActionEvent event) {
        super.update(event);

        //get the class where the user action has began
        PsiClass classUnderCaret= getPsiClassUnderCaret(event);

        //Disable plugin from appearing if IDE is not ready
        event.getPresentation().setEnabled(classUnderCaret!=null);

    }


    private PsiClass getPsiClassUnderCaret(AnActionEvent e) {
        //Get the file on which user is working on
        PsiFile psiFile= e.getData(LangDataKeys.PSI_FILE);

        //Get the editor on which user initiated action
        Editor editor= e.getData(PlatformDataKeys.EDITOR);
        if(psiFile==null||editor==null){
            return null;
        }

        //get the offset where the user clicked
        int offset=editor.getCaretModel().getOffset();

        //Get the Psi token at the user clicked position
        PsiElement elementOffset=psiFile.findElementAt(offset);

        //Get the class where user action has started
        PsiClass psiClass= PsiTreeUtil.getParentOfType(elementOffset,PsiClass.class);
        return psiClass;
    }

}
