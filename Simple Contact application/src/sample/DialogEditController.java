package sample;

import Datasource.contactData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DialogEditController {
    @FXML
    private TextField Ename;
    @FXML
    private TextField Ephoneno;
    @FXML
    private TextField Eemail;

    public Contacts  processUpdate(){
        String name = Ename.getText().trim();
        String phoneno = Ephoneno.getText().trim();
        String email = Eemail.getText().trim();
        Contacts ncontacts = new Contacts(name,phoneno,email);
        contactData.getInstance().addToContact(ncontacts);


        return ncontacts;
    }

    public void setInfo(Contacts OldContact){
        Ename.setText(OldContact.getName());
        Ephoneno.setText(OldContact.getNo());
        Eemail.setText(OldContact.getEmail());
    }

    public TextField getEname() {
        return Ename;
    }

    public TextField getEphoneno() {
        return Ephoneno;
    }

    public TextField getEemail() {
        return Eemail;
    }
}
