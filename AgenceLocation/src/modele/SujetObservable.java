package app.modele;

import java.util.ArrayList;
import java.util.List;

public class SujetObservable {
    private List<Observateur> observateurs = new ArrayList<>();

    public void ajouterObservateur(Observateur o) {
        observateurs.add(o);
    }

    public void notifierTous(String message) {
        for (Observateur o : observateurs) {
            o.notifier(message);
        }
    }
}
