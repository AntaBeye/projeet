package sn.dev.systemparrainageapp.entities;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Utilisateur {
    private int id;
    private String nom, prenom, login, password;
    private int actived;
    private Role profil;
    public String getEtatAsString() {
        return (actived == 1) ? "Activé" : "Désactivé";
    }
}
