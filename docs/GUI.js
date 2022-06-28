import {SnakeGame} from "./model/SnakeGame.js";
import {State} from "./model/State.js";
import {Direction} from "./model/Direction.js";
import {Beginner} from "./difficulty/Beginner.js";
import {Intermediate} from "./difficulty/Intermediate.js";
import {Advanced} from "./difficulty/Advanced.js";

function GUI() {
    let SCREEN_WIDTH, SCREEN_HEIGHT;
    let UNIT_SIZE = 15;
    let game, ctx;
    function startGame() {
        game = new SnakeGame(SCREEN_HEIGHT / UNIT_SIZE, SCREEN_WIDTH / UNIT_SIZE);
        game.startGame();
        setTimeout(paintComponent, game.getDiff().getDelay());
        setDifficulty();
    }
    function resetCanvas() {
        let canv = document.getElementById("gc");
        ctx = canv.getContext("2d");
        SCREEN_WIDTH = parseInt(canv.width);
        SCREEN_HEIGHT = parseInt(canv.height);
        ctx.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        ctx.fillStyle = "black";
        ctx.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }
    function paintComponent() {
        resetCanvas();
        game.updateBoard();
        let state = game.getState();
        if (state === State.GAME) {
            setTimeout(paintComponent, game.getDiff().getDelay());
            // FOOD
            let food = game.getFruit();
            ctx.beginPath();
            ctx.arc(food.getY() * UNIT_SIZE + UNIT_SIZE / 2, food.getX() * UNIT_SIZE + UNIT_SIZE / 2, UNIT_SIZE / 2, 0, 2 * Math.PI);
            ctx.fillStyle = "magenta";
            ctx.fill();
            // SNAKE
            let snake = game.getSnake();
            for (let i = 0, SIZE = snake.length; i < SIZE; i++) {
                let x = snake[i].getX() * UNIT_SIZE, y = snake[i].getY() * UNIT_SIZE;
                if (i === SIZE - 1) {
                    // TONGUE
                    let direction = game.getDirection();
                    ctx.fillStyle = "red";
                    if (direction === Direction.LEFT || direction === Direction.RIGHT) {
                        ctx.fillRect(y, x + UNIT_SIZE / 2, UNIT_SIZE, UNIT_SIZE / 7);
                    } else {
                        ctx.fillRect(y + UNIT_SIZE / 2, x, UNIT_SIZE / 7, UNIT_SIZE);
                    }
                } else if (i === SIZE - 2) {
                    // HEAD
                    ctx.fillStyle = "yellow";
                    ctx.fillRect(y, x, UNIT_SIZE, UNIT_SIZE);
                } else {
                    // BODY
                    ctx.fillStyle = "green";
                    ctx.fillRect(y, x, UNIT_SIZE, UNIT_SIZE);
                }
            }
        } else if (state === State.END) {
            // GAME OVER
            ctx.fillStyle = "red";
            ctx.font = "60px MV Boli";
            ctx.fillText("GAME OVER!", SCREEN_WIDTH / 2 - 200, SCREEN_HEIGHT / 2);
        }
        // SCORE
        let message = document.getElementById("message");
        message.textContent = `Score: ${game.getFruitConsumed()} | Delay: ${game.getDiff().getDelay()}`;
    }
    function keyPush(evt) {
        let map = {'ArrowLeft': Direction.LEFT, 'ArrowRight': Direction.RIGHT, 'ArrowUp': Direction.UP, 'ArrowDown': Direction.DOWN};
        if (game) {
            game.setDirection(map[evt.key]);
        }
    }
    function setDifficulty() {
        let select = document.querySelector("select");
        let diff = {"Beginner": new Beginner(), "Intermediate": new Intermediate(), "Advanced": new Advanced()};
        game.setDiff(diff[select.value]);
    }
    function registerEvents() {
        let startButton = document.querySelector("input[type='button']");
        startButton.onclick = startGame;
        let diff = document.querySelector("select");
        diff.onchange = setDifficulty;
        resetCanvas();
        document.addEventListener("keydown", keyPush);
    }
    return {registerEvents};
}
let gui = new GUI();
gui.registerEvents();
