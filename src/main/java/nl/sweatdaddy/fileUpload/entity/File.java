package nl.sweatdaddy.fileUpload.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;


// Let op: het bestand staat hier niet opgeslagen, maar alleen de referentie naar de file in de uploads map
@Entity
public class File {

    @Id
    @GeneratedValue
    private Long id;

    private String fileName;

//    niet nodig
//    @Lob
//    private byte[] docFile;

    public File(String fileName) {
        this.fileName = fileName;
    }

    public File() {

    }

    public String getFileName() {
        return fileName;
    }
}
