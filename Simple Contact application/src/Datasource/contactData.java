package Datasource;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Contacts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class contactData {
    private  static contactData instance = new contactData();
    private  static String filename = "contactlist.txt";

    private ObservableList<Contacts> contacts;

    public static contactData getInstance(){
        return instance;
    }

    public ObservableList<Contacts> getContacts() {
        return contacts;
    }



    private contactData(){

    }
    public  void  loadContacts() throws IOException{
        contacts = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try{
            while ((input=  br.readLine())!=null){
                String[] contactPieces = input.split("\t");

                String name = contactPieces[0];
                String no = contactPieces[1];
                String email = contactPieces[2];

                Contacts Recontacts = new Contacts(name,no,email);
                contacts.add(Recontacts);

            }
        }finally {
            if (br!=null){
                br.close();
            }
        }
    }
    public void storeContacts() throws  IOException{
        Path path = Paths.get(filename);
        BufferedWriter bw  = Files.newBufferedWriter(path);
        try {
            Iterator<Contacts> iter  = contacts.iterator();
            while (iter.hasNext()){
                Contacts con = iter.next();
                bw.write(String.format("%s\t%s\t%s",
                        con.getName(),
                        con.getNo(),
                        con.getEmail()  ));

                bw.newLine();
            }
        }finally {
            if(bw!=null){
                bw.close();
            }
        }
    }

    public void addToContact(Contacts contax) {
        contacts.add(contax);
    }

    public void deleteContact(Contacts item) {
        contacts.remove(item);
    }
    public void updateContact(Contacts item)
    {
        contacts.set(contacts.indexOf(item),item);
    }

}
