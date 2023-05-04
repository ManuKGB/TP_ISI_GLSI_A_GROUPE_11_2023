package com.ega.ega_api.controller;

import com.ega.ega_api.entity.Client;
import com.ega.ega_api.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    ClientService clientService;

    /*
    * RECUPERER TOUT LES CLIENTS
    * */
    @GetMapping("/all")
    public ResponseEntity<HashMap<Object, Object>> allClient(){

        return clientService.allClient();
    }
    /*
    recupere tout client supprmer
     */
    @GetMapping("/deleted/all")
    public ResponseEntity<HashMap<Object, Object>> allClientDeleted(){

        return clientService.allClientDeleted();
    }

    /*
    * ENREGISTRER UN CLIENT
    * */
    @PostMapping("/save")
    public ResponseEntity<HashMap<Object,Object>> createClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }
    /*
    * RECUPERER UN CLIENT PAR SON ID
    * */
    @GetMapping("/{id}")
    public ResponseEntity<HashMap<Object,Object>> getOneClient(@PathVariable UUID id){
        return clientService.getOneClient(id);
    }
    /*
    METTRE à LES INFOS PERSO D'UN CLIENT
     */

    @PutMapping("/update/{id}")
    public ResponseEntity<HashMap<Object,Object>> updateClient(@PathVariable UUID id ,@RequestBody Client clientBody){
        return clientService.updateClient(id,clientBody);
    }
    /*
        supprimer un client
     */

    @PutMapping("/delete/{id}")
    public  ResponseEntity<HashMap<Object,Object>> deleteClient(@PathVariable UUID id)
    {
        return clientService.deleteClient(id);
    }
    /*
        restaurer un client
     */
    @PutMapping("/backup/{id}")
    public ResponseEntity<HashMap<Object,Object>> backupClient(@PathVariable UUID id){
        return clientService.backupClient(id);
    }

}
