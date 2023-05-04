package com.ega.ega_api.controller;


import com.ega.ega_api.dto.Transaction;
import com.ega.ega_api.entity.Compte;
import com.ega.ega_api.services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashMap;

@RestController
@RequestMapping("/api/compte")
public class CompteController {
    @Autowired
    CompteService compteService;

    @GetMapping("/all")
    public ResponseEntity<HashMap<Object,Object>> allCompte(){
        return compteService.allCompte();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<Object,Object>> oneCompte(@PathVariable String id){
        return compteService.getOneCompte(id);
    }

    @GetMapping("/deleted/all")
    public ResponseEntity<HashMap<Object,Object>> allCompteDeleted(){
        return compteService.allCompteDeleted();
    }

    @PostMapping("/save")
    public ResponseEntity<HashMap<Object,Object>> saveCompte(@RequestBody Compte compte){
        return compteService.saveCompte(compte);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HashMap<Object,Object>> updateCompte( @PathVariable String id,@RequestBody Compte compte )
    {
        return compteService.updateCompte(id,compte);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<HashMap<Object,Object>> deleteCompte(@PathVariable String id){
        return compteService.deleteCompte(id);
    }

    @PutMapping("/backup/{id}")
    public ResponseEntity<HashMap<Object,Object>> backUpCompte(@PathVariable String id){
        return compteService.backupCompte(id);
    }


    @PutMapping("/{id}/depot/{montant}")
    public ResponseEntity<HashMap<Object,Object>> faireDepot(@PathVariable String id , @PathVariable Float montant)
    {
        return compteService.faireDepot(id,montant);
    }

    @PutMapping("/{id}/retrait/{montant}")
    public ResponseEntity<HashMap<Object,Object>> faireRetrait(@PathVariable String id , @PathVariable Float montant)
    {
        return compteService.faireRetrait(id,montant);
    }

    @PutMapping("/virement/{montant}")
    public ResponseEntity<HashMap<Object,Object>> faireVirement(@RequestBody Transaction transactionBody,
                                                                @PathVariable Float montant)
    {
        return compteService.faireVirement(transactionBody,montant);
    }







}
