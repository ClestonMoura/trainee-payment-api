package br.com.flexpag.traineepaymentapi.controller;

import br.com.flexpag.traineepaymentapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService service;



}
