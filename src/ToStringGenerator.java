import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;

/**
 * Created by vinayaprasadn on 1/10/16.
 */
public class ToStringGenerator extends WriteCommandAction.Simple {
    private PsiClass classUnderCaret;

    public ToStringGenerator(PsiClass classUnderCaret) {
        super(classUnderCaret.getProject(), classUnderCaret.getContainingFile());
        this.classUnderCaret = classUnderCaret;
    }

    @Override
    protected void run() throws Throwable {
        //Get the element factory, where we can manufacture PsiMethods,fields etc.
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(classUnderCaret.getProject());

        //Get all fields from  class under the caret
        PsiField[] psiFields= classUnderCaret.getFields();

        //Create method from the text in the class under caret
        String methodString = getToStringMethod(psiFields);
        PsiMethod method = elementFactory.createMethodFromText(methodString, classUnderCaret);

        //Add this method to class under caret
        classUnderCaret.add(method);

    }


    private String getToStringMethod(PsiField[] psiFields) {
        return "@Override"+"\n"+"public String toString(){" +"return"+"\" Hello World\";"+"}";


    }

   /* private String getToStringMethod(PsiField[] psiFields) {
        String methodSignature= "@Override"+"\n"+"public String toString(){" +"\n";
        String methodBody="return";
        methodBody+="\"{\""+"+\"\\n\"+";
        for(PsiField psiField:psiFields){
            methodBody+="\""+psiField.getName()+"\""+"+\":\"+"+psiField.getName()+"+"+"\"\\n\"+";
        }
        String methodEnd = "\"}" + "\";\n" + "}";
        String method = methodSignature + methodBody+methodEnd;
        return method;

    }*/
}
