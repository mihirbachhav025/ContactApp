package sample;

import Datasource.contactData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DialogController {
  @FXML
  private TextField Dname;
  @FXML
  private  TextField Dphoneno;
  @FXML
  private  TextField Demail;



    public Contacts  processResults(){
      String name= Dname.getText();
      String phoneno= Dphoneno.getText();
      String email = Demail.getText();
      Contacts ncontacts = new Contacts(name,phoneno,email);
          contactData.getInstance().addToContact(ncontacts);


      return ncontacts;
  }

  public TextField getDname() {
    return Dname;
  }
}
