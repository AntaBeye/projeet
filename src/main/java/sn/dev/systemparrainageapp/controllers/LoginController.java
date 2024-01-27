package sn.dev.systemparrainageapp.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sn.dev.systemparrainageapp.entities.Utilisateur;
import sn.dev.systemparrainageapp.repositories.utilisateur.IUtilisateur;
import sn.dev.systemparrainageapp.repositories.utilisateur.UtilisateurImpl;
import sn.dev.systemparrainageapp.tools.Notification;
import sn.dev.systemparrainageapp.tools.Outils;

import static java.lang.System.*;

public class LoginController {

    @FXML
    private TextField loginTfd;
    @FXML
    private Button close;
    @FXML
    private PasswordField passwordTfd;

    private IUtilisateur userDao = new UtilisateurImpl();

    @FXML
    void login(ActionEvent event) {
        String login = loginTfd.getText();
        String password = passwordTfd.getText();
        if(login.trim().isEmpty() || password.trim().isEmpty()){
            Notification.NotifError("Erreur","Tous les champs sont obligatoires !");
        }else{
            Utilisateur user = userDao.seConnecter(login, password);
            if(user != null){
                try{
                    Notification.NotifSuccess("Succès","Connexion réussie !");
                    switch (user.getProfil().getName()){
                        case "ROLE_ADMIN":
                            Outils.load(event, "Bienvenue", "/pages/admin.fxml");
                            break;
                        case "ROLE_CANDIDAT":
                            Outils.load(event, "Bienvenue", "/pages/candidat.fxml");
                            break;
                        case "ROLE_ELECTEUR":
                            Outils.load(event, "Bienvenue", "/pages/electeur.fxml");
                            break;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else
                Notification.NotifError("Erreur","Login et/ou password incorrects !");
        }
    }

    public void close() {
        exit(0);
    }

}
