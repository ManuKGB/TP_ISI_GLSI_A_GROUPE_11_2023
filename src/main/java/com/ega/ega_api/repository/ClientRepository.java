package com.ega.ega_api.repository;

import com.ega.ega_api.entity.Client;
import com.ega.ega_api.entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClientRepository  extends JpaRepository<Client, UUID> {

}
