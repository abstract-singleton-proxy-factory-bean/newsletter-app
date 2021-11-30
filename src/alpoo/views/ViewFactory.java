package alpoo.views;

import jandl.wizard.WizardFactory;
import alpoo.database.Database;
import alpoo.database.models.UserManager;
import alpoo.http.HTTPRequestHandler;
import jandl.wizard.Data;
import jandl.wizard.WizardBase;

/**
 * Factory for the views
 */
public class ViewFactory {
    /**
     * Creates a new instance of the wizard
     * 
     * @param option Option to be selected
     * @return Wizard instance
     */
    public static WizardBase getWizard(ViewOptions option) {
        switch (option) {
            case INITIAL_VIEW:
                return getInitialView();
            case CREATE_USER_VIEW:
                return getCreateUserView();
            case FEEDBACK_VIEW:
                return getFeedbackView();
            case CONFIRM_EMAIL_VIEW:
                return getConfirmEmailView();
            case NOT_VALID_ANSWER_VIEW:
                return getNotValidAnswerView();
            case VALID_ANSWER_VIEW:
                return getValidAnswerView();
            default:
                return null;
        }
    }

    private static WizardBase getConfirmEmailView() {
        String[] fields = { "Digite \"Sim\" para se cadastrar na newsletter" };
        WizardBase wizard = WizardFactory.createField("Confirmar cadastro", fields, fields, fields);

        wizard.addPostProcessor(($wizard) -> {
            Data data = Data.instance();
            String wizardName = $wizard.getName();
            String answer = data.getAsString(wizardName + ".fieldPane0.Digite \"Sim\" para se cadastrar na newsletter");

            System.out.println("Resposta: " + answer);
            System.out.println("Nome: " + $wizard.getName());

            if (answer.equals("Sim")) {
                String name = data.getAsString("Wizard2.fieldPane0.Nome");
                String email = data.getAsString("Wizard2.fieldPane0.Email");

                try {
                    HTTPRequestHandler.makeNewsletterRequest(name, email);
                } catch (Exception e) {
                    System.out.println(e.toString());
                    System.out.println("Email não foi enviado");
                }

                return;
            }

            WizardBase notValidAnswerView = getNotValidAnswerView();
            WizardBase confirmEmailView = getConfirmEmailView();

            notValidAnswerView
                    .nextWizard(confirmEmailView)
                    .nextWizard(getValidAnswerView());

            $wizard.nextWizard(notValidAnswerView);
        });

        return wizard;
    }

    private static WizardBase getValidAnswerView() {
        WizardBase wizard = WizardFactory.createText("Sucesso", "!/resources/text/success-answer.txt");
        return wizard;
    }

    private static WizardBase getNotValidAnswerView() {
        WizardBase wizard = WizardFactory.createText("Resposta inválida", "!/resources/text/invalid-answer.txt");
        return wizard;
    }

    private static WizardBase getFeedbackView() {
        WizardBase feedbackView = WizardFactory.createText("Apenas mais alguns passos... :)",
                "!/resources/text/feedback.txt");
        return feedbackView;
    }

    private static WizardBase getCreateUserView() {
        String[] fields = { "Nome", "Email" };
        WizardBase wizard = WizardFactory.createField("Registre-se", fields, fields, fields);

        wizard.addPostProcessor(($wizard) -> {
            Data data = Data.instance();
            String name = data.getAsString("Wizard2.fieldPane0.Nome");
            String email = data.getAsString("Wizard2.fieldPane0.Email");

            Database database = Database.getInstance();
            UserManager userManager = new UserManager(database);
            userManager.createUser(name, email);
        });

        return wizard;
    }

    private static WizardBase getInitialView() {
        WizardBase wizard = WizardFactory.createBase("Newsletter de tecnologia");
        return wizard;
    }
}
