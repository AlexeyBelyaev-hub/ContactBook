package sample;

import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ItemDialogController {

    private Contact contact;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextArea notes;

    public Contact proceedResult(){

        String newFirstName = firstName.getText().trim();
        String newLastName = lastName.getText().trim();
        String newPhoneNumber = phoneNumber.getText().trim();
        String newNote = notes.getText().trim();

        if (contact == null){
            Contact newContact = new Contact(newFirstName, newLastName, newPhoneNumber, newNote);
            ContactData.getInstance().addContact(newContact);
            return newContact;
        }else{
            contact.setFirstName(newFirstName);
            contact.setLastName(newLastName);
            contact.setPhoneNumber(newPhoneNumber);
            contact.setNotes(newNote);
            return contact;
        }

    }

    public void initializeContact(Contact contactToLoad){
        if (contactToLoad != null){
           firstName.setText( contactToLoad.getFirstName());
           lastName.setText(contactToLoad.getLastName());
           phoneNumber.setText(contactToLoad.getPhoneNumber());
           notes.setText(contactToLoad.getNotes());
            contact=contactToLoad;
        }else{
            contact=null;
        }

    }


}
