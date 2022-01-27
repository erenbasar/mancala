package com.ebasar.mancala.model;

import com.ebasar.mancala.exception.InvalidMoveException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MancalaModelTests {

    @Test
    public void testBasicMove() throws Exception{
        Game mockGame = new Game(1, 6);

        mockGame.makeMove(0);

        assert Arrays.equals(mockGame.getPlayerOne().getPits(), new int[]{0, 2, 1, 1, 1, 1});
        assert mockGame.getPlayerOne().getHomePit() == 0;
        assert !mockGame.getPlayerOne().isActive();

        assert Arrays.equals(mockGame.getPlayerTwo().getPits(), new int[]{1, 1, 1, 1, 1, 1});
        assert mockGame.getPlayerTwo().getHomePit() == 0;
        assert mockGame.getPlayerTwo().isActive();

    }

    @Test
    public void testMove() throws Exception{
        Game mockGame = new Game(2, 6);

        mockGame.makeMove(0);

        assert Arrays.equals(mockGame.getPlayerOne().getPits(), new int[]{0, 3, 3, 2, 2, 2});
        assert mockGame.getPlayerOne().getHomePit() == 0;
        assert !mockGame.getPlayerOne().isActive();

        assert Arrays.equals(mockGame.getPlayerTwo().getPits(), new int[]{2, 2, 2, 2, 2, 2});
        assert mockGame.getPlayerTwo().getHomePit() == 0;
        assert mockGame.getPlayerTwo().isActive();

    }

    @Test
    public void testPlayerTurn() throws Exception{
        Game mockGame = new Game(1, 6);
        mockGame.changePlayerTurn();
        assert mockGame.getPlayerTwo().isActive();
    }


    @Test
    public void testEndOnHomePit() throws Exception{
        Game mockGame = new Game(1, 6);

        mockGame.makeMove(5);

        assert Arrays.equals(mockGame.getPlayerOne().getPits(), new int[]{1, 1, 1, 1, 1, 0});
        assert mockGame.getPlayerOne().getHomePit() == 1;
        assert mockGame.getPlayerOne().isActive();

        assert Arrays.equals(mockGame.getPlayerTwo().getPits(), new int[]{1, 1, 1, 1, 1, 1});
        assert mockGame.getPlayerTwo().getHomePit() == 0;
        assert !mockGame.getPlayerTwo().isActive();
    }

    @Test
    public void testAroundOpponentPit() throws Exception{
        Game mockGame = new Game(1, 6);
        mockGame.getPlayerOne().setStoneNumberByPit(5, 9);

        mockGame.makeMove(5);

        // Player one validations
        assert Arrays.equals(mockGame.getPlayerOne().getPits(), new int[]{2, 2, 1, 1, 1, 0});
        assert mockGame.getPlayerOne().getHomePit() == 1;
        assert !mockGame.getPlayerOne().isActive();

        // Player two validations
        assert Arrays.equals(mockGame.getPlayerTwo().getPits(), new int[]{2, 2, 2, 2, 2, 2});
        assert mockGame.getPlayerTwo().getHomePit() == 0;
        assert mockGame.getPlayerTwo().isActive();
    }

    @Test
    public void testCapture() throws Exception {
        Game mockGame = new Game(1, 6);
        mockGame.getPlayerOne().setStoneNumberByPit(4, 0);
        mockGame.makeMove(3);


        assert Arrays.equals(mockGame.getPlayerOne().getPits(), new int[]{1, 1, 1, 0, 0, 1});
        assert mockGame.getPlayerOne().getHomePit() == 2;
        assert !mockGame.getPlayerOne().isActive();


        assert Arrays.equals(mockGame.getPlayerTwo().getPits(), new int[]{1, 0, 1, 1, 1, 1});
        assert mockGame.getPlayerTwo().getHomePit() == 0;
        assert mockGame.getPlayerTwo().isActive();

    }

    @Test
    public void testCollectRemainingStones() throws Exception{
        Game mockGame = new Game(1, 6);
        IntStream.range(0, 5).forEach(i -> mockGame.getPlayerOne().setStoneNumberByPit(i, 0));
        assert !mockGame.isGameOver();
        mockGame.makeMove(5);
        int pitCount = mockGame.getPlayerOne().getStoneNumberByPit(5);
        assert pitCount == 0;

        assert mockGame.isGameOver();
        mockGame.collectRemainingStones();

        assert Arrays.equals(mockGame.getPlayerOne().getPits(), new int[]{0, 0, 0, 0, 0, 0});
        assert Arrays.equals(mockGame.getPlayerTwo().getPits(), new int[]{0, 0, 0, 0, 0, 0});
        assert mockGame.getPlayerOne().getHomePit() == 1;
        assert mockGame.getPlayerTwo().getHomePit() == 6;
    }


    @Test(expected = InvalidMoveException.class)
    public void testInvalidMove() throws Exception{
        Game mockGame = new Game(1, 6);
        mockGame.getPlayerOne().setStoneNumberByPit(0, 0);

        mockGame.makeMove(0);
    }


}
