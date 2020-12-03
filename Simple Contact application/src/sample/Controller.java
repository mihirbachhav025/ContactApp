package sample;

import Datasource.contactData;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


public class Controller {

    @FXML
    private ListView contactlistview;
    @FXML
    private Label outName;
    @FXML
    private Label Outnumber;
    @FXML
    private  Label outEmail;
    @FXML
    private GridPane mainGridPane;
    @FXML
    private ContextMenu listContextMenu;
    @FXML
    private  TextField searchText;
    @FXML
    private  ToggleButton searchB;

    private FilteredList<Contacts> contactsFilteredList;


    private List<Contacts> Contact = new ArrayList<>();
    public  void  initialize(){
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
       MenuItem editMenuItem = new MenuItem("Update");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Contacts contac = (Contacts) contactlistview.getSelectionModel().getSelectedItem();
                deleteItem(contac);
            }
        });
        editMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Contacts contact = (Contacts) contactlistview.getSelectionModel().getSelectedItem();
                showEditContactDialog(contact);
            }
        });

        listContextMenu.getItems().addAll(deleteMenuItem);
        listContextMenu.getItems().addAll(editMenuItem);
        contactlistview.setContextMenu(listContextMenu);


        contactlistview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if(t1!=null){
                    Contacts contacts = (Contacts) contactlistview.getSelectionModel().getSelectedItem();
                    outEmail.setText(contacts.getEmail());
                    outName.setText(contacts.getName());
                    Outnumber.setText(contacts.getNo());
            }
            }
        });

        contactsFilteredList  = new FilteredList<Contacts>(contactData.getInstance().getContacts(),
                new Predicate<Contacts>(){

                    @Override
                    public boolean test(Contacts contacts) {
                        return true;
                    }
                });
        SortedList<Contacts> sortedList = new SortedList<Contacts>(contactsFilteredList, new Comparator<Contacts>() {
            @Override
            public int compare(Contacts o1, Contacts o2) {

                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

     //   contactlistview.setItems(contactData.getInstance().getContacts());
        Label label =new Label("No contact");
        label.setFont(new Font("Cambria",30));
        contactlistview.setPlaceholder(label);
        contactlistview.setItems(sortedList);
        contactlistview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        contactlistview.getSelectionModel().selectFirst();
    }
    @FXML
    public void showEditContactDialog(Contacts contact){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainGridPane.getScene().getWindow());
        FXMLLoader fxmlLoader2 = new FXMLLoader();
        fxmlLoader2.setLocation(getClass().getResource("contactEditDialog.fxml"));
        try{

            dialog.getDialogPane().setContent(fxmlLoader2.load() );


        }catch (IOException e){
            System.out.println("couldny load");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        DialogEditController controllerx = fxmlLoader2.getController();
        controllerx.setInfo(contact);
        Optional<ButtonType> result = dialog.showAndWait();


        if(result.isPresent() && result.get() == ButtonType.APPLY){
            if(controllerx.getEname().getText().isEmpty()) {
//                contactData.getInstance().deleteContact(contact);
//                Contacts econtact = controllerx.processUpdate();
//                contactlistview.getSelectionModel().select(econtact);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Name field should not be empty");
                alert.showAndWait();
            }
            else {
                contactData.getInstance().deleteContact(contact);
                Contacts econtact = controllerx.processUpdate();
                contactlistview.getSelectionModel().select(econtact);
//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setContentText("Name field should not be empty");
//                alert.showAndWait();
            }
            System.out.println("Pressed Apply");
        }else{
            System.out.println("cancel Presssed");
        }
    }




    public  void  showNewContactDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainGridPane.getScene().getWindow());
        dialog.setTitle("Add New Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactaddDialog.fxml"));
        try{

            dialog.getDialogPane().setContent(fxmlLoader.load() );

        }catch (IOException e){
            System.out.println("couldny load");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        DialogController controller = fxmlLoader.getController();
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            if(controller.getDname().getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Name field should not be empty");
                    alert.showAndWait();

            }
            else {
                Contacts ncontact = controller.processResults();
                contactlistview.getSelectionModel().select(ncontact);
                System.out.println("Pressed ok");
            }
        }else{
            System.out.println("cancel Presssed");
        }

    }
    @FXML
    public  void handleClickListView(){
        Contacts contact = (Contacts) contactlistview.getSelectionModel().getSelectedItem();
       // System.out.println(contact);
        outEmail.setText(contact.getEmail());
        outName.setText(contact.getName());
        Outnumber.setText(contact.getNo());
    }
    public void deleteItem(Contacts item){
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Delete Contact");
      alert.setHeaderText("Delete Contact : "+"\n" +"Name: "+item.getName() +"\n" +"Phone no: "+ item.getNo()+ "\n"+"E-mail id: "+ item.getEmail());
      alert.setContentText("Are you sure?");
      Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            contactData.getInstance().deleteContact(item);
        }
    }



    public void handleSearchButton(ActionEvent actionEvent) {
        if(searchB.isSelected()){
            contactsFilteredList.setPredicate(new Predicate<Contacts>() {
                @Override
                public boolean test(Contacts contacts) {

                    return ((contacts.getName().toLowerCase()).contains(searchText.getText().toLowerCase()) ||contacts.getNo().toLowerCase().contains(searchText.getText().toLowerCase()) || contacts.getEmail().toLowerCase().contains(searchText.getText().toLowerCase()) );

                }
            });


        }else{
            contactsFilteredList.setPredicate(new Predicate<Contacts>() {
                @Override
                public boolean test(Contacts contacts) {
                    return true;
                }
            });
        }
    }
}
