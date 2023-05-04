package com.ega.ega_api.services;

import com.ega.ega_api.entity.Client;
import com.ega.ega_api.entity.Compte;
import com.ega.ega_api.repository.ClientRepository;
import com.ega.ega_api.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CompteRepository compteRepository;

    public HashMap<Object,Object> reponseModel(int statusCode, Object result){
        HashMap<Object,Object> map = new HashMap<>();
        map.put("statusCode",statusCode);
        if (statusCode==201){
            map.put("message","Client creer avec succes");
            map.put("ok",true);
        } else if (statusCode==200) {
            map.put("message","Tout les clients sont recuperer");
            map.put("ok",true);
        } else if (statusCode==404) {
            map.put("message","Ce client n'existe pas!");
            map.put("ok",false);
        } else if (statusCode==204) {
            map.put("message","Client supprimer avec success!");
            map.put("ok",true);
        } else if (statusCode==205) {
            map.put("message","Client restaurer avec success!");
            map.put("ok",true);
        }
        map.put("response",result);
        return map;
    }
    public List<Compte> listCompte(UUID id){
       return compteRepository.findAll()
                .stream()
                .filter(compte -> !compte.isDeleted())
               .filter(compte -> compte.getClient().getId() == id)
               .map(
                       compte -> new Compte(
                                    compte.getNumeroCompte(),
                                   compte.getTypeCompte(),
                                   compte.getDateCreation(),
                                   compte.getSolde(),
                                   compte.isDeleted()
                       )
               ).toList()
               ;
    }

    /*
    * ICI COMMENCE LES SERVICES PROPREMENT DIT
    * */
    public ResponseEntity<HashMap<Object,Object>> allClient(){
        var allClients = clientRepository.findAll()
                .stream()
                .filter(client -> !client.isDeleted())
                .peek(client -> client.setComptes(listCompte(client.getId())))
                .collect(Collectors.toList());
        var response = this.reponseModel(HttpStatus.OK.value(), allClients);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /*
     * ENREGISTRER UN CLIENT
     * */
    public ResponseEntity<HashMap<Object,Object>> saveClient(Client client){
        var response = this.reponseModel(HttpStatus.CREATED.value(),client);
        clientRepository.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    /*
     * Rechercher un client par son id
     * */
    public ResponseEntity<HashMap<Object,Object>> getOneClient( UUID id){
        var oneClient= clientRepository.findById(id);
        if (oneClient.isEmpty() || oneClient.get().isDeleted()) {
            var response = this.reponseModel(HttpStatus.NOT_FOUND.value(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        oneClient.get().setComptes( listCompte(id) );
        var response = this.reponseModel(HttpStatus.OK.value(), oneClient);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    /*
    UPADTE UN CLIENT
     */
    public ResponseEntity<HashMap<Object,Object>> updateClient(UUID id , Client clientBody){
        var clientToUpdate=clientRepository.findById(id);

        if ( clientToUpdate.isEmpty() || clientToUpdate.get().isDeleted()){
            var response = this.reponseModel(HttpStatus.NOT_FOUND.value(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        clientToUpdate.get().setNom(clientBody.getNom());
        clientToUpdate.get().setPrenom(clientBody.getPrenom());
        clientToUpdate.get().setCourriel(clientBody.getCourriel());
        clientToUpdate.get().setAdresse(clientBody.getAdresse());
        clientToUpdate.get().setDateNaissance(clientBody.getDateNaissance());
        clientToUpdate.get().setNumeroTelephone(clientBody.getNumeroTelephone());
        clientToUpdate.get().setNationalite(clientBody.getNationalite());
        clientToUpdate.get().setSexe(clientBody.getSexe());
        clientRepository.save(clientToUpdate.get());
        clientToUpdate.get().setComptes(listCompte(clientToUpdate.get().getId()));
        var response = this.reponseModel(HttpStatus.OK.value(), clientToUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(response);


    }
    /*
        SUPPRESSION LOGIQUE
     */
    public ResponseEntity<HashMap<Object,Object>> deleteClient(UUID id){
        var clientToDelete = clientRepository.findById(id);
        if (clientToDelete.isEmpty() || clientToDelete.get().isDeleted()){
            var response = this.reponseModel(HttpStatus.NOT_FOUND.value(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        clientToDelete.get().setDeleted(true);
        clientRepository.save(clientToDelete.get());
        var comptesToDelete = clientToDelete.get().getComptes();
        for (var compte : comptesToDelete) {
            compte.setDeleted(true);
            compteRepository.save(compte);
        }
        clientToDelete.get().setComptes(listCompte(clientToDelete.get().getId()));

        var response = this.reponseModel(204, clientToDelete.get());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    /*
        AFFICHER TOUT LES CLIENTS SUPPRMER
     */

    public ResponseEntity<HashMap<Object,Object>> allClientDeleted(){
        var allClientsDeleted = clientRepository.findAll()
                .stream()
                .filter(Client::isDeleted)
                .peek(client -> client.setComptes(listCompte(client.getId())))
                .collect(Collectors.toList())
                ;
        var response = this.reponseModel(HttpStatus.OK.value(), allClientsDeleted);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /*
        RESTAURER UN CLIENT
     */
    public ResponseEntity<HashMap<Object,Object>> backupClient(UUID id){
        var clientToBackUp = clientRepository.findById(id);
        if (clientToBackUp.isEmpty() ){
            var response = this.reponseModel(HttpStatus.NOT_FOUND.value(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        clientToBackUp.get().setDeleted(false);
        clientRepository.save(clientToBackUp.get());
        var comptesToDelete = clientToBackUp.get().getComptes();
        for (var compte : comptesToDelete) {
            compte.setDeleted(false);
            compteRepository.save(compte);
        }
        clientToBackUp.get().setComptes(listCompte(clientToBackUp.get().getId()));
        var response = this.reponseModel(205, clientToBackUp.get());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }




}
