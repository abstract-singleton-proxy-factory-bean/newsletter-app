package alpoo;

import javax.swing.SwingUtilities;

import alpoo.views.ViewFactory;
import alpoo.views.ViewOptions;
import jandl.wizard.WizardBase;

public class App {
    public static void main(String[] args) {
        WizardBase initialViewWizard = ViewFactory.getWizard(ViewOptions.INITIAL_VIEW);
        WizardBase createUserViewWizard = ViewFactory.getWizard(ViewOptions.CREATE_USER_VIEW);
        WizardBase feedbackViewWizard = ViewFactory.getWizard(ViewOptions.FEEDBACK_VIEW);
        WizardBase confirmEmailViewWizard = ViewFactory.getWizard(ViewOptions.CONFIRM_EMAIL_VIEW);
        WizardBase validAnswerViewWizard = ViewFactory.getWizard(ViewOptions.VALID_ANSWER_VIEW);

        initialViewWizard
                .nextWizard(createUserViewWizard)
                .nextWizard(feedbackViewWizard)
                .nextWizard(confirmEmailViewWizard)
                .nextWizard(validAnswerViewWizard);

        SwingUtilities.invokeLater(() -> initialViewWizard.setVisible(true));
    }
}
