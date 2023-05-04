package com.ega.ega_api.services;


import com.ega.ega_api.entity.Client;
import com.ega.ega_api.entity.Compte;
import com.ega.ega_api.repository.ClientRepository;
import com.ega.ega_api.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;


@Service
public class CompteService {
    @Autowired
    CompteRepository compteRepository;
    @Autowired
    ClientRepository clientRepository;

    public HashMap<Object, Object> reponseModel(int statusCode, Object result) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("statusCode", statusCode);
        if (statusCode == 201) {
            map.put("message", "Compte creer avec succes");
            map.put("ok", true);
        } else if (statusCode == 200) {
            map.put("message", "Compte  recuperer");
            map.put("ok", true);
        } else if (statusCode == 404) {
            map.put("message", "Ce compte n'existe pas!");
            map.put("ok", false);
        } else if (statusCode == 204) {
            map.put("message", "Compte supprimer avec success!");
            map.put("ok", true);
        } else if (statusCode == 205) {
            map.put("message", "Compte restaurer avec success!");
            map.put("ok", true);
        } else if (statusCode == 400) {
            map.put("message", "Mauvaise requete ! verifier le montant");
            map.put("ok", false);
        } else if (statusCode == 407) {
            map.put("message", "Erreur de transaction: Solde insuffisant!");
            map.put("ok", false);
        }
        map.put("response", result);
        return map;
    }


    public ResponseEntity<HashMap<Object, Object>> allCompte() {
        var allCompte= compteRepository.findAll()
                .stream()
                .filter(compte -> !compte.isDeleted());
        var response = this.reponseModel(HttpStatus.OK.value(), allCompte);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<HashMap<Object, Object>> saveCompte(Compte compte) {
        var response = this.reponseModel(HttpStatus.CREATED.value(), compte);
        Client proprietaire = clientRepository.findById(compte.getClient().getId()).get();
        compte.setClient(proprietaire);
        compteRepository.save(compte);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<HashMap<Object, Object>> getOneCompte(String id) {
        var oneCompte = compteRepository.findById(id);
        if (oneCompte.isEmpty() || oneCompte.get().isDeleted()) {
            var response = this.reponseModel(HttpStatus.NOT_FOUND.value(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        var response = this.reponseModel(HttpStatus.OK.value(), oneCompte);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<HashMap<Object, Object>> updateCompte(String id, Compte compteBody) {
        var compteToUpdate = compteRepository.findById(id);
        if (compteToUpdate.isEmpty() || compteToUpdate.get().isDeleted()) {
            var response = this.reponseModel(HttpStatus.NOT_FOUND.value(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        compteToUpdate.get().setTypeCompte(compteBody.getTypeCompte());


        compteRepository.save(compteToUpdate.get());
        var response = this.reponseModel(HttpStatus.OK.value(), compteToUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<HashMap<Object, Object>> deleteCompte(String id) {
        var compteToDelete = compteRepository.findById(id);
        if (compteToDelete.isEmpty() || compteToDelete.get().isDeleted()) {
            var response = this.reponseModel(HttpStatus.NOT_FOUND.value(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        compteToDelete.get().setDeleted(true);
        compteRepository.save(compteToDelete.get());
        var response = this.reponseModel(204, compteToDelete.get());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<HashMap<Object, Object>> allCompteDeleted() {
        var allCompteDeleted = compteRepository.findAll()
                .stream()
                .filter(Compte::isDeleted)
                .toList();
        var response = this.reponseModel(HttpStatus.OK.value(), allCompteDeleted);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<HashMap<Object, Object>> backupCompte(String id) {
        var compteToBackUp = compteRepository.findById(id);
        if (compteToBackUp.isEmpty()) {
            var response = this.reponseModel(HttpStatus.NOT_FOUND.value(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        compteToBackUp.get().setDeleted(false);
        compteRepository.save(compteToBackUp.get());
        var response = this.reponseModel(205, compteToBackUp.get());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}