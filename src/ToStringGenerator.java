import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiField;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;

/**
 * Created by vinayaprasadn on 1/10/16.
 */
public class ToStringGenerator extends WriteCommandAction.Simple {


    private PsiClass aClass;

    public ToStringGenerator(PsiClass aClass) {
        super(aClass.getProject(), aClass.getContainingFile());
        this.aClass = aClass;
    }

    @Override
    protected void run() throws Throwable {
        PsiElementFactory factory = JavaPsiFacade.getElementFactory(aClass.getProject());
        PsiField[] psiFields= aClass.getFields();
        aClass.add(factory.createMethodFromText(generateToString(psiFields),aClass));
        JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(aClass.getProject());
        styleManager.optimizeImports(aClass.getContainingFile());
        styleManager.shortenClassReferences(aClass.getContainingFile());

    }



    private String generateToString(PsiField[] psiFields) {
        String methodSignature= "@Override"+"\n"+"public String toString(){" +"\n";;
        String methodBody="return";
        methodBody+="\"{\""+"+\"\\n\"+";
        for(PsiField psiField:psiFields){
            methodBody+="\""+psiField.getName()+"\""+"+\":\"+"+psiField.getName()+"+"+"\"\\n\"+";
        }
        String methodEnd = "\"}" + "\";\n" + "}";
        String method = methodSignature + methodBody+methodEnd;
        return method;

    }
}
