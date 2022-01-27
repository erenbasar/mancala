package com.ebasar.mancala.controller;

import com.ebasar.mancala.exception.InvalidMoveException;
import com.ebasar.mancala.service.MancalaService;
import com.ebasar.mancala.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mancala")
public class MancalaController {

    @Autowired
    MancalaService mancalaService;

    @GetMapping(value = "/new")
    public @ResponseBody
    Game startNewGame() {
        return mancalaService.startNewGame();
    }


    @PostMapping(value = "/move")
    public @ResponseBody
    Game makeMove(String gameId, int pit) throws InvalidMoveException {
        return mancalaService.makeMove(gameId, pit);
    }

}