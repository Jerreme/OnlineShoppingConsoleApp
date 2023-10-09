package routes;

import controllers.Navigator;
import controllers.RegistrationController;
import interfaces.Credential;
import interfaces.Route;
import views.RegistrationPageView;

public class RegistrationPage implements Route {
    @Override
    public void build() {
        final RegistrationPageView view = new RegistrationPageView();
        final RegistrationController controller = new RegistrationController(view);
        final Credential userForRegistration = controller.promptRegistration();

        if (userForRegistration == null) return;

        final Credential user = controller.submitRegistrationCredential(userForRegistration);
        if (user == null) {
            build();
        } else {
            Navigator.gotoLastRoute();
        }
    }
}
