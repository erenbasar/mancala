var gameId;

$(document).ready(function() {

    function newGame() {
        $.ajax({
            type: "GET",
            url: "mancala/new",
            success: function(data) {
                gameId = data.id
                createBoard(data)
            }
        });
    }

    function makeMove(x) {
        $.ajax({
            type: "POST",
            url: "mancala/move",
            data: {
                gameId: gameId,
                pit: x
            },
            success: function(data) {
                createBoard(data)
            }
        });
    }

    function createBoard(game) {

        for (i = 0; i < 6; i++) {
            $('.player-one-pit.' + i).html(game.playerOne.pits[i]);
            $('.player-two-pit.' + i).html(game.playerTwo.pits[i]);
        }

        // Set store counts
        $('.player-one.homePit1').html(game.playerOne.homePit);
        $('.player-two.homePit2').html(game.playerTwo.homePit);

        if (game.playerOne.active) {
            $('.current-player').html(game.playerOne.name);
            $('.player-one-pit').addClass('active-pit');

            $('.player-two-pit').removeClass('active-pit');
            clickUpdate();
        } else if (game.playerTwo.active) {
            $('.current-player').html(game.playerTwo.name);
            $('.player-two-pit').addClass('active-pit');

            $('.player-one-pit').removeClass('active-pit');
            clickUpdate();
        } else { // Game over


            $('.player-two-pit').unbind();
            $('.player-one-pit').unbind();


            var winner;
            if (game.playerOne.homePit > game.playerTwo.homePit) {
                winner = game.playerOne.name;
                $('.player-one-pit').addClass('active-pit');
                $('.player-two-pit').removeClass('active-pit');
            } else {
                winner = game.playerTwo.name;
                $('.player-two-pit').addClass('active-pit');
                $('.player-one-pit').removeClass('active-pit');
            }
            $('.status').html(winner + "wins !");
            alert(winner + " wins !");
        }
    }

    function clickUpdate() {
        // Update onclick
        $('.player-two-pit').unbind();
        $('.player-one-pit').unbind();
        $('.active-pit').on('click', function() {
            var classes = $(this).attr("class").split(" ");
            var pitNum = classes[classes.length - 2];
            makeMove(pitNum);

        });

    }

    $('.new-game').on('click', function() {
        newGame();
    });

    newGame();

})