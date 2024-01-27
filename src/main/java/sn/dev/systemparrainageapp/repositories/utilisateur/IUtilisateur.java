package sn.dev.systemparrainageapp.repositories.utilisateur;

import sn.dev.systemparrainageapp.entities.Utilisateur;

public interface IUtilisateur {
    public Utilisateur seConnecter(String login, String password);
}
