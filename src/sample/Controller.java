package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class Controller {

    @FXML
    private TableView<Contact> tableView;

    @FXML
    private BorderPane mainBorderPane;

    public void initialize(){

        TableColumn firstNameCol= tableView.getColumns().get(0);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Contact,String>("firstName"));

        TableColumn lastNameCol= tableView.getColumns().get(1);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Contact,String>("lastName"));

        TableColumn phoneNumberCol= tableView.getColumns().get(2);
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<Contact,String>("phoneNumber"));

        TableColumn noteCol= tableView.getColumns().get(3);
        noteCol.setCellValueFactory(new PropertyValueFactory<Contact,String>("notes"));

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().add(0,new MenuItem("Add"));
        contextMenu.getItems().get(0).setOnAction(this::addItem);

        contextMenu.getItems().add(1,new MenuItem("Edit"));
        contextMenu.getItems().get(1).setOnAction(this::editContact);

        contextMenu.getItems().add(2,new MenuItem("Delete"));
        contextMenu.getItems().get(2).setOnAction(this::deleteContact);


        tableView.setItems(ContactData.getInstance().getContacts());

        tableView.setContextMenu(contextMenu);
    }

    @FXML
    public void addItem(ActionEvent actionEvent) {
        openContactDialog(null);

    }


    private void openContactDialog(Contact selectedContact){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("New contact item");
        dialog.setHeaderText("Create new item in contact book");

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.initOwner(mainBorderPane.getScene().getWindow());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("itemDialog.fxml"));

        try {
            Parent root = loader.load();
            dialog.getDialogPane().setContent(root);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ItemDialogController itemDialogController = loader.getController();
        itemDialogController.initializeContact(selectedContact);
        Optional<ButtonType> result = dialog.showAndWait();


        System.out.println("Result is present: "+result.isPresent() + "AND result eqals: " + result.get().equals(ButtonType.OK));
        if (result.isPresent() && result.get().equals(ButtonType.OK)){
            System.out.println("Ok button pressed");
            Contact newContact = itemDialogController.proceedResult();
            ContactData.getInstance().saveContacts();
            tableView.getSelectionModel().select(newContact);
        }

    }
    @FXML
    public void editContact(ActionEvent actionEvent){
        Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
        if (selectedContact!=null){
            openContactDialog(selectedContact);
            ContactData.getInstance().saveContacts();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No contact selected");
            alert.setHeaderText("Please select contact to delete");
            alert.showAndWait();
        }
    }

    @FXML
    public void deleteContact(ActionEvent actionEvent){
        Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
        if (selectedContact!=null){
            Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Do you really want to delete contact: "+ selectedContact.getFirstName());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get().equals(ButtonType.OK)){
                ContactData.getInstance().delete(selectedContact);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No contact selected");
            alert.setHeaderText("Please select contact to delete");
            alert.showAndWait();
            ContactData.getInstance().saveContacts();
        }

    }


    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
