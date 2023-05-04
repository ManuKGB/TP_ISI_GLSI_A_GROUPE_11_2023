package com.ega.ega_api.entity;

import com.ega.ega_api.enums.Sexe;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull(message = "Le nom ne peut pas être null")
    private String nom;
    @NotNull
    private String prenom;
    @NotNull
    private LocalDate dateNaissance;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Sexe sexe;
    @NotNull
    private String adresse;
    @NotNull
    private String numeroTelephone;
    @NotNull
    private String courriel;

    private boolean isDeleted;

    private LocalDateTime createdAt;

    private String nationalite;

    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
    private List<Compte> comptes;

    public Client() {
        this.isDeleted=false;
        this.createdAt=LocalDateTime.now();

    }

    public Client(String nom, String prenom, LocalDate dateNaissance, Sexe sexe, String adresse, String numeroTelephone,
                  String courriel, String nationalite) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.adresse = adresse;
        this.numeroTelephone = numeroTelephone;
        this.courriel = courriel;
        this.nationalite = nationalite;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id){
        this.id=id;
    }



    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
