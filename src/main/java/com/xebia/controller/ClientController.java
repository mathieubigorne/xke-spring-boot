package com.xebia.controller;

import com.xebia.domain.Client;
import com.xebia.service.ClientService;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ClientController {

    private final AtomicLong idGenerator = new AtomicLong();

    private final ClientService clientService;

    @Inject
    public ClientController(final ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(value = "/clients", method = GET)
    public ResponseEntity<List<Client>> allClients() {
        List<Client> clients = clientService.findAll();
        if(clients.isEmpty()){
            return new ResponseEntity<>(NO_CONTENT);
        }else{
            return new ResponseEntity<>(clients,OK);
        }
    }

    @RequestMapping(value="/client.html", method = RequestMethod.GET)
    public Model getListClients(Model model) {
      model.addAttribute("clients", clientService.findAll());
      return model;
    }

    @RequestMapping(value = "/client", method = GET)
    public Client client(@RequestParam(value = "name") String name) {
        return new Client(idGenerator.incrementAndGet(), name);
    }

    @RequestMapping(value = "/client", method = POST)
    @ResponseStatus(CREATED)
    public Client createClient(@RequestBody @Valid final Client client) {
        return clientService.save(client);
    }
}
