package com.ega.ega_api.entity;

import com.ega.ega_api.enums.TypeCompte;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

@Entity
@Table(name = "comptes")
public class Compte {
    @Id
    private String numeroCompte;

    @Enumerated(EnumType.STRING)
    private TypeCompte typeCompte;

    private LocalDateTime dateCreation;

    private BigDecimal solde;

    private boolean isDeleted;
//    @Transient
//    private  UUID clientId;

    @ManyToOne()
    @JoinColumn(name = "proprietaire_id")
    @JsonIgnoreProperties({"comptes"})
    private Client client;

    // constructeurs, getters, setters et autres méthodes


    public Compte() {
        int year = LocalDate.now().getYear();
        this.numeroCompte=generateAccountNumber();
        this.solde = new BigDecimal(0);
        this.dateCreation= LocalDateTime.now();
        this.isDeleted=false;
    }

    public Compte(TypeCompte typeCompte, LocalDateTime dateCreation, Client client) {
        this.typeCompte = typeCompte;
        this.dateCreation = dateCreation;
        this.client =client;
//        this.clientId=clientId;
    }

    public Compte(String numeroCompte, TypeCompte typeCompte, LocalDateTime dateCreation, BigDecimal solde, boolean isDeleted) {
        this.numeroCompte = numeroCompte;
        this.typeCompte = typeCompte;
        this.dateCreation = dateCreation;
        this.solde = solde;
        this.isDeleted = isDeleted;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public TypeCompte getTypeCompte() {
        return typeCompte;
    }

    public void setTypeCompte(TypeCompte typeCompte) {
        this.typeCompte = typeCompte;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isDeleted() {
        return isDeleted;
    }


    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
    public static String generateAccountNumber() {
        String characters = "AB0CD1EF2GH2IJ4KL5MN6OP7QR8ST9UVWXYZ";
        String accountNumber = "";
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            accountNumber += characters.charAt(random.nextInt(characters.length()));
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        accountNumber += "-" + dateFormat.format(date);
        return accountNumber;
    }

//    public UUID getClientId() {
//        return clientId;
//    }
//
//    public void setClientId(UUID clientId) {
//        this.clientId = clientId;
//    }
}
