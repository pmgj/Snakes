import SnakeGame from "./model/SnakeGame.js";
import State from "./model/State.js";
import Direction from "./model/Direction.js";
import difficulties from "./difficulty/Difficulties.js";

class GUI {
    constructor() {
        this.game = null;
        this.canvas = document.getElementById("gc");
        this.ctx = this.canvas.getContext("2d");
        this.UNIT_SIZE = 15;
        this.WIDTH = 35;
        this.HEIGHT = 25;
        this.SCREEN_WIDTH = this.WIDTH * this.UNIT_SIZE;
        this.SCREEN_HEIGHT = this.HEIGHT * this.UNIT_SIZE;
        this.canvas.width = this.SCREEN_WIDTH;
        this.canvas.height = this.SCREEN_HEIGHT;
        let main = document.querySelector("main");
        main.style.width = `${this.SCREEN_WIDTH}px`;
    }
    startGame() {
        this.game = new SnakeGame(this.HEIGHT, this.WIDTH);
        this.game.startGame();
        setTimeout(this.paintComponent.bind(this), this.game.getDiff().getDelay());
        this.setDifficulty();
    }
    resetCanvas() {
        this.ctx.clearRect(0, 0, this.SCREEN_WIDTH, this.SCREEN_HEIGHT);
        this.ctx.fillStyle = "black";
        this.ctx.fillRect(0, 0, this.SCREEN_WIDTH, this.SCREEN_HEIGHT);
    }
    paintComponent() {
        this.resetCanvas();
        this.game.updateBoard();
        let state = this.game.getState();
        if (state === State.GAME) {
            setTimeout(this.paintComponent.bind(this), this.game.getDiff().getDelay());
            // FOOD
            let food = this.game.getFruit();
            this.ctx.beginPath();
            this.ctx.arc(food.getY() * this.UNIT_SIZE + this.UNIT_SIZE / 2, food.getX() * this.UNIT_SIZE + this.UNIT_SIZE / 2, this.UNIT_SIZE / 2, 0, 2 * Math.PI);
            this.ctx.fillStyle = "magenta";
            this.ctx.fill();
            // SNAKE
            let snake = this.game.getSnake();
            for (let i = 0, SIZE = snake.length; i < SIZE; i++) {
                let x = snake[i].getX() * this.UNIT_SIZE, y = snake[i].getY() * this.UNIT_SIZE;
                if (i === SIZE - 1) {
                    // TONGUE
                    let direction = this.game.getDirection();
                    this.ctx.fillStyle = "red";
                    if (direction === Direction.LEFT || direction === Direction.RIGHT) {
                        this.ctx.fillRect(y, x + this.UNIT_SIZE / 2, this.UNIT_SIZE, this.UNIT_SIZE / 7);
                    } else {
                        this.ctx.fillRect(y + this.UNIT_SIZE / 2, x, this.UNIT_SIZE / 7, this.UNIT_SIZE);
                    }
                } else if (i === SIZE - 2) {
                    // HEAD
                    this.ctx.fillStyle = "yellow";
                    this.ctx.fillRect(y, x, this.UNIT_SIZE, this.UNIT_SIZE);
                } else {
                    // BODY
                    this.ctx.fillStyle = "green";
                    this.ctx.fillRect(y, x, this.UNIT_SIZE, this.UNIT_SIZE);
                }
            }
        } else if (state === State.END) {
            // GAME OVER
            this.ctx.fillStyle = "red";
            this.ctx.font = "60px MV Boli";
            this.ctx.fillText("GAME OVER!", this.SCREEN_WIDTH / 2 - 200, this.SCREEN_HEIGHT / 2);
        }
        // SCORE
        let message = document.getElementById("message");
        message.textContent = `Score: ${this.game.getFruitConsumed()} | Delay: ${this.game.getDiff().getDelay()}`;
    }
    keyPush(evt) {
        let map = { 'ArrowLeft': Direction.LEFT, 'ArrowRight': Direction.RIGHT, 'ArrowUp': Direction.UP, 'ArrowDown': Direction.DOWN };
        if (this.game) {
            this.game.setDirection(map[evt.key]);
        }
    }
    touchOccurred(evt) {
        let x = evt.touches[0].clientX;
        let y = evt.touches[0].clientY;
        let direction = y < 100 ? Direction.UP : y > 250 ? Direction.DOWN : x > 250 ? Direction.RIGHT : Direction.LEFT;
        if (this.game) {
            this.game.setDirection(direction);
        }
    }
    setDifficulty() {
        let select = document.querySelector("select");
        this.game.setDiff(difficulties[select.value]);
    }
    registerEvents() {
        let startButton = document.querySelector("input[type='button']");
        startButton.onclick = this.startGame.bind(this);
        let diff = document.querySelector("select");
        diff.onchange = this.setDifficulty;
        this.resetCanvas();
        document.addEventListener("keydown", this.keyPush.bind(this));
        this.canvas.addEventListener("touchstart", this.touchOccurred.bind(this));
    }
}
let gui = new GUI();
gui.registerEvents();