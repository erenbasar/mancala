package com.ebasar.mancala.model;

import com.ebasar.mancala.exception.InvalidMoveException;
import lombok.Getter;
import org.springframework.context.annotation.PropertySource;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.stream.IntStream;

@Getter
@Entity
public class Game {

    @Id
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    private Player playerOne;
    @OneToOne(cascade = CascadeType.ALL)
    private Player playerTwo;

    private int boardSize;

    protected Game() {

    }

    public Game(int stoneCount, int boardSize) {
        id = String.valueOf(Math.random());
        this.boardSize = boardSize;
        playerOne = new Player("PlayerOne", stoneCount, boardSize, true);
        playerTwo = new Player("PlayerTwo", stoneCount, boardSize, false);
    }
    public void changePlayerTurn() {
        playerOne.setActive(!playerOne.isActive());
        playerTwo.setActive(!playerTwo.isActive());
    }

    public boolean isGameOver() {
        return (IntStream.of(playerOne.getPits()).sum() == 0 || IntStream.of(playerTwo.getPits()).sum() == 0);
    }

    public void collectRemainingStones() {

        playerOne.addStonesToHomePit(IntStream.of(playerOne.getPits()).sum());
        playerTwo.addStonesToHomePit(IntStream.of(playerTwo.getPits()).sum());

        for (int i = 0; i < playerOne.getPits().length; i++) {
            playerOne.setStoneNumberByPit(i, 0);
            playerTwo.setStoneNumberByPit(i, 0);
        }
        playerOne.setActive(false);
        playerTwo.setActive(false);
    }

    public void makeMove(int clickedPit) throws InvalidMoveException {

        Player activePlayer = playerOne.isActive() ? playerOne : playerTwo;
        Player passivePlayer = playerOne.isActive() ? playerTwo : playerOne;

        if (isGameOver()) {
            throw new InvalidMoveException(String.format("Game is over, cant move"));
        }

        int clickedPitStones = activePlayer.getStoneNumberByPit(clickedPit);

        if (clickedPitStones == 0) {
            throw new InvalidMoveException(String.format("You cant select an empty pit"));
        }

        activePlayer.setStoneNumberByPit(clickedPit, 0);

        while (clickedPitStones > 0) {

            for (int i = clickedPit + 1; i < boardSize; i++) {

                if (clickedPitStones == 1) {

                    if (activePlayer.getStoneNumberByPit(i) == 0) {
                        activePlayer.addStonesToHomePit(1);
                        activePlayer.addStonesToHomePit(passivePlayer.getStoneNumberByPit(boardSize - 1 - i));
                        passivePlayer.setStoneNumberByPit(boardSize - 1 - i, 0);
                    } else {
                        activePlayer.addStoneToPit(i);
                    }
                    clickedPitStones--;
                    changePlayerTurn();
                    return;
                } else if (clickedPitStones > 1) {
                    activePlayer.addStoneToPit(i);
                    clickedPitStones--;
                } else {
                    changePlayerTurn();
                    return;
                }
            }

            if (clickedPitStones == 1) {
                activePlayer.addStonesToHomePit(1);
                clickedPitStones--;
                return;
            } else if (clickedPitStones > 1) {
                activePlayer.addStonesToHomePit(1);
                clickedPitStones--;
            }

            for (int i = 0; i < boardSize; i++) {
                if (clickedPitStones > 0) {
                    passivePlayer.addStoneToPit(i);
                    clickedPitStones--;
                } else {
                    changePlayerTurn();
                    return;
                }
            }

            if (clickedPitStones > 0) {
                clickedPit = -1;
            }
        }
        changePlayerTurn();
    }
}