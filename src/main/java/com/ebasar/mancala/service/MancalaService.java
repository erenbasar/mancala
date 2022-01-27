package com.ebasar.mancala.service;

import com.ebasar.mancala.exception.InvalidMoveException;
import com.ebasar.mancala.model.Game;
import com.ebasar.mancala.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MancalaService {

    @Autowired
    GameRepository gameRepository;

    @Value("${mancala.stonecount}")
    private int stoneCount;
    @Value("${mancala.boardsize}")
    private int boardSize;

    public Game startNewGame() {
        Game game = new Game(stoneCount, boardSize);
        gameRepository.save(game);

        return game;
    }

    public Game makeMove(String id, int x) throws InvalidMoveException {
        Game game = gameRepository.findById(id).orElse(null);
        game.makeMove(x);

        if(game.isGameOver()) game.collectRemainingStones();

        gameRepository.save(game);

        return game;
    }
}
