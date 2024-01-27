package sn.dev.systemparrainageapp.controllers;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.events.MouseEvent;
import sn.dev.systemparrainageapp.DBConnection;
import sn.dev.systemparrainageapp.entities.Role;
import sn.dev.systemparrainageapp.entities.Utilisateur;
import sn.dev.systemparrainageapp.repositories.role.IRole;
import sn.dev.systemparrainageapp.repositories.role.RoleImpl;
import sn.dev.systemparrainageapp.repositories.utilisateur.IUtilisateur;
import sn.dev.systemparrainageapp.repositories.utilisateur.UtilisateurImpl;
import sn.dev.systemparrainageapp.tools.Notification;
import sn.dev.systemparrainageapp.tools.Outils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;


import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private AnchorPane main_form;
    @FXML
    private AnchorPane ajoutCandidat_form;

    @FXML
    private TextField ajoutCandidat_login;

    @FXML
    private TextField ajoutCandidat_nom;

    @FXML
    private TextField ajoutCandidat_password;

    @FXML
    private TextField ajoutCandidat_prenom;

    @FXML
    private Button ajoutElecteurBtn;

    @FXML
    private AnchorPane ajoutElecteur_form;

    @FXML
    private TextField ajoutElecteur_login;

    @FXML
    private TextField ajoutElecteur_nom;

    @FXML
    private TextField ajoutElecteur_password;

    @FXML
    private TextField ajoutElecteur_prenom;

    @FXML
    private Button candidatBtn;

    @FXML
    private TableView<Utilisateur> candidatTableView;

    @FXML
    private TableColumn<Utilisateur, Integer> candidat_col_ID;

    @FXML
    private TableColumn<Utilisateur, Integer> candidat_col_etat;

    @FXML
    private TableColumn<Utilisateur, String> candidat_col_nom;

    @FXML
    private TableColumn<Utilisateur, String> candidat_col_prenom;

    @FXML
    private AnchorPane candidat_form;

    @FXML
    private Button dashBtn;

    @FXML
    private BarChart<?, ?> dash_chart;

    @FXML
    private AnchorPane dash_form;

    @FXML
    private Button electeurBtn;

    @FXML
    private TableView<Utilisateur> electeurTableView;

    @FXML
    private TableColumn<Utilisateur, Integer> electeur_col_ID;

    @FXML
    private TableColumn<Utilisateur, Integer> electeur_col_etat;

    @FXML
    private TableColumn<Utilisateur, String> electeur_col_login;
    private FontAwesomeIcon close;

    @FXML
    private TableColumn<Utilisateur, String> electeur_col_nom;

    @FXML
    private TableColumn<Utilisateur, String> electeur_col_prenom;

    @FXML
    private AnchorPane electeur_form;

    @FXML
    private Button homeBtn;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_totalCandidats;

    @FXML
    private Label home_totalElecteurs;

    @FXML
    private Button logout;

    private double x = 0.0;
    private double y = 0.0;
//

//    public void displayUsername() {
//        this.username.setText(getData.username);
//    }

    //    public void displayLogin() {
//        // Utilisez le texte du champ loginTfd
//        this.loginTfd.setText("VotreLogin"); // Remplacez "VotreLogin" par la valeur que vous souhaitez afficher
//    }
    public void logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message de Confirmation");
        alert.setHeaderText((String) null);
        alert.setContentText("Êtes-vous sûr de vouloir vous déconnecter?");
        Optional<ButtonType> option = alert.showAndWait();

        try {
            if (((ButtonType) option.get()).equals(ButtonType.OK)) {
                this.logout.getScene().getWindow().hide();
                Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/pages/login.fxml"));
//            Parent parent = FXMLLoader.load(getClass().getResource("/pages/login.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                root.setOnMousePressed((event) -> {
                    this.x = event.getSceneX();
                    this.y = event.getSceneY();
                });
                root.setOnMouseDragged((event) -> {
                    stage.setX(event.getScreenX() - this.x);
                    stage.setY(event.getScreenY() - this.y);
                    stage.setOpacity(0.8);
                });
                root.setOnMouseReleased((event) -> {
                    stage.setOpacity(1.0);
                });
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }
//    public void close() {
//        System.exit(0);
//    }

//    public void displayUsername() {
//        this.login.setText(getData.login);
//    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == homeBtn) {
            home_form.setVisible(true);
            candidat_form.setVisible(false);
            electeur_form.setVisible(false);
            dash_form.setVisible(false);

            homeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            candidatBtn.setStyle("-fx-background-color:transparent");
            electeurBtn.setStyle("-fx-background-color:transparent");
            dashBtn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == candidatBtn) {
            home_form.setVisible(false);
            candidat_form.setVisible(true);
            electeur_form.setVisible(false);
            dash_form.setVisible(false);

            candidatBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            homeBtn.setStyle("-fx-background-color:transparent");
            electeurBtn.setStyle("-fx-background-color:transparent");
            dashBtn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == electeurBtn) {
            home_form.setVisible(false);
            candidat_form.setVisible(false);
            electeur_form.setVisible(true);
            dash_form.setVisible(false);

            electeurBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            homeBtn.setStyle("-fx-background-color:transparent");
            candidatBtn.setStyle("-fx-background-color:transparent");
            dashBtn.setStyle("-fx-background-color:transparent");

//            addEmployeeGendernList();
//            addEmployeePositionList();
//            addEmployeeSearch();

        } else if (event.getSource() == dashBtn) {
            home_form.setVisible(false);
            candidat_form.setVisible(false);
            electeur_form.setVisible(false);
            dash_form.setVisible(true);

            dashBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            homeBtn.setStyle("-fx-background-color:transparent");
            electeurBtn.setStyle("-fx-background-color:transparent");
            candidatBtn.setStyle("-fx-background-color:transparent");


        }

    }

    DBConnection db = new DBConnection();

    public ObservableList<Utilisateur> getUtilisateur() {
        ObservableList<Utilisateur> utilisateurs = FXCollections.observableArrayList();
        String sql = "SELECT * FROM user " +
                "WHERE profil IN (SELECT id FROM role WHERE name = 'ROLE_ELECTEUR') " +
                "ORDER BY nom ASC";
        try {
            db.initPrepar(sql);
            ResultSet rs = db.executeSelect();
            while (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setLogin(rs.getString("login"));
                user.setActived(rs.getInt("actived"));
                utilisateurs.add(user);
            }
            db.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return utilisateurs;
    }


    public void LoadTable() {
        ObservableList<Utilisateur> liste = getUtilisateur();
        electeurTableView.setItems(liste);
        electeur_col_ID.setCellValueFactory(new PropertyValueFactory<Utilisateur, Integer>("id"));
        electeur_col_nom.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("nom"));
        electeur_col_prenom.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("prenom"));
        electeur_col_login.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("login"));
        electeur_col_etat.setCellValueFactory(new PropertyValueFactory<>("actived"));
        electeur_col_etat.setCellFactory(column -> new TableCell<Utilisateur, Integer>() {
            @Override
            protected void updateItem(Integer actived, boolean empty) {
                super.updateItem(actived, empty);
                if (empty || actived == null) {
                    setText(null);
                } else {
                    setText(actived == 1 ? "Activé" : "Désactivé");
                }
            }
        });
    }

    public ObservableList<Utilisateur> getUtilisateurCandidat() {
        ObservableList<Utilisateur> utilisateurs = FXCollections.observableArrayList();
        String sql = "SELECT * FROM user " +
                "WHERE profil IN (SELECT id FROM role WHERE name = 'ROLE_CANDIDAT') " +
                "ORDER BY nom ASC";
        try {
            db.initPrepar(sql);
            ResultSet rs = db.executeSelect();
            while (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setLogin(rs.getString("login"));
                user.setActived(rs.getInt("actived"));
                utilisateurs.add(user);
            }
            db.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return utilisateurs;
    }


    public void LoadTableCandidat() {
        ObservableList<Utilisateur> listee = getUtilisateurCandidat();
        candidatTableView.setItems(listee);
        candidat_col_ID.setCellValueFactory(new PropertyValueFactory<Utilisateur, Integer>("id"));
        candidat_col_nom.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("nom"));
        candidat_col_prenom.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("prenom"));
        candidat_col_etat.setCellValueFactory(new PropertyValueFactory<>("actived"));
        candidat_col_etat.setCellFactory(column -> new TableCell<Utilisateur, Integer>() {
            @Override
            protected void updateItem(Integer actived, boolean empty) {
                super.updateItem(actived, empty);
                if (empty || actived == null) {
                    setText(null);
                } else {
                    setText(actived == 1 ? "Activé" : "Désactivé");
                }
            }
        });
    }
    @FXML
    void save(ActionEvent event) {
        String sql = "INSERT INTO user (id, nom, prenom, login, password, actived, profil) VALUES (null, ?, ?, ?, ?, ?, ?)";

        try {
            db.initPrepar(sql);
            db.getPstm().setString(1, ajoutElecteur_nom.getText());
            db.getPstm().setString(2, ajoutElecteur_prenom.getText());
            db.getPstm().setString(3, ajoutElecteur_login.getText());
            db.getPstm().setString(4, ajoutElecteur_password.getText());
            // Définir actived à 1 (par défaut)
            db.getPstm().setInt(5, 1);
            db.getPstm().setInt(6, 3);
            int ok = db.executeMaj();
            db.closeConnection();
            LoadTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getElecteurRoleId() throws SQLException {
        String roleName = "ROLE_ELECTEUR";
        String sql = "SELECT id FROM role WHERE name = ?";
        db.initPrepar(sql);
        db.getPstm().setString(1, roleName);

        // Exécutez la requête SELECT et récupérez le résultat
        ResultSet rs = db.executeSelect();

        if (rs.next()) {
            return rs.getInt("id");
        } else {
            throw new SQLException("Le rôle n'a pas été trouvé : " + roleName);
        }
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoadTable();
    }}

