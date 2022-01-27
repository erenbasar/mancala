package com.ebasar.mancala.service;

import com.ebasar.mancala.service.MancalaService;
import com.ebasar.mancala.repository.GameRepository;
import com.ebasar.mancala.model.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MancalaServiceTests {
    @InjectMocks
    MancalaService mancalaService;

    @Mock
    GameRepository mockGameRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testMakeMove() throws Exception{
        Game mockGame = new Game(1, 6);
        when(mockGameRepository.findById("mock")).thenReturn(Optional.of(mockGame));

        Game newGameState = mancalaService.makeMove("mock", 4);

        assert Arrays.equals(newGameState.getPlayerOne().getPits(), new int[]{1, 1, 1, 1, 0, 2});
        assert newGameState.getPlayerOne().getHomePit() == 0;
        assert !newGameState.getPlayerOne().isActive();

        assert Arrays.equals(newGameState.getPlayerTwo().getPits(), new int[]{1, 1, 1, 1, 1, 1});
        assert newGameState.getPlayerTwo().getHomePit() == 0;
        assert newGameState.getPlayerTwo().isActive();

    }

    @Test
    public void testStartNewGame() throws Exception{
        Game mockGame = new Game(1, 6);
        when(mockGameRepository.findById("mock")).thenReturn(Optional.of(mockGame));

        Game newGame = mancalaService.startNewGame();

        testMakeMove();

    }
}