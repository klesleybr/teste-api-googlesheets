package com.example.googlesheetsapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class DashboardController {
	@GetMapping("/check") // Serve para indicar onde essa função será mostrada.
	// É só abrir o https://localhost:8080/check
	public String check() {
		return "Testando a API";
	}
}
