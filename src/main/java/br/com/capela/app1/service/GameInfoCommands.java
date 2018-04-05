package br.com.capela.app1.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class GameInfoCommands {

	@Autowired
	private GameInfoService gameInfoService;
	
    @ShellMethod("Consulta n melhores jogos para comprar")
    public void bests(@ShellOption(defaultValue="10") int n) throws IOException {
    	gameInfoService.consulta(n);
    }
}