import State from "./State.js";
import CellState from "./CellState.js";
import Direction from "./Direction.js";
import Cell from "./Cell.js";
import Beginner from "../difficulty/Beginner.js";

export default class SnakeGame {
    constructor(w, h) {
        this.board = Array(w).fill().map(() => Array(h).fill(CellState.EMPTY));
        this.direction = Direction.RIGHT;
        this.width = w;
        this.height = h;
        this.snake;
        this.fruit;
        this.state = State.START;
        this.fruitConsumed;
        this.diff = new Beginner();
    }
    getFruit() {
        return this.fruit;
    }
    getSnake() {
        return this.snake;
    }
    getDirection() {
        return this.direction;
    }
    startGame() {
        this.createSnake();
        this.createFruit();
        this.state = State.GAME;
        this.direction = Direction.RIGHT;
        this.fruitConsumed = 0;
        this.showBoard();
    }
    setDirection(d) {
        if ((d === Direction.LEFT && this.direction !== Direction.RIGHT)
            || (d === Direction.RIGHT && this.direction !== Direction.LEFT)
            || (d === Direction.UP && this.direction !== Direction.DOWN)
            || (d === Direction.DOWN && this.direction !== Direction.UP)) {
            this.direction = d;
        }
    }
    updateBoard() {
        if (this.state === State.GAME) {
            this.move();
            this.checkFood();
            if (this.checkCollision()) {
                this.state = State.END;
            }
            this.showBoard();
        }
    }
    getFruitConsumed() {
        return this.fruitConsumed;
    }
    getState() {
        return this.state;
    }
    getDiff() {
        return this.diff;
    }
    setDiff(d) {
        this.diff = d;
    }
    getBoard() {
        return this.board;
    }
    createSnake() {
        this.snake = [new Cell(0, 0), new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4)];
    }
    createFruit() {
        this.fruit = new Cell(Math.floor(Math.random() * this.width), Math.floor(Math.random() * this.height));
        if (this.snake.some(b => b.equals(this.fruit))) {
            this.createFruit();
        }
    }
    showBoard() {
        // Board
        for (let i = 0; i < this.width; i++) {
            this.board[i].fill(CellState.EMPTY);
        }
        // Snake
        this.snake.forEach(c => {
            if (this.onBoard(c)) {
                this.board[c.getX()][c.getY()] = CellState.SNAKE;
            }
        });
        // Fruit
        this.board[this.fruit.getX()][this.fruit.getY()] = CellState.FRUIT;
    }
    onBoard({ x, y }) {
        let inLimit = (value, limit) => value >= 0 && value < limit;
        return (inLimit(x, this.board.length) && inLimit(y, this.board[0].length));
    }
    checkCollision() {
        let head = this.snake.slice(-1)[0];
        let body = this.snake.slice(0, this.snake.length - 1);
        return body.some(b => b.equals(head)) || !this.onBoard(head);
    }
    checkFood() {
        let head = this.snake.slice(-1)[0];
        if (head.equals(this.fruit)) {
            this.fruitConsumed++;
            this.createFruit();
            this.diff.changeDelay(this.fruitConsumed);
        } else {
            this.snake.shift();
        }
    }
    move() {
        let head = this.snake.slice(-1)[0];
        let row = head.getX(), col = head.getY();
        let map = { DOWN: new Cell(row + 1, col), UP: new Cell(row - 1, col), LEFT: new Cell(row, col - 1), RIGHT: new Cell(row, col + 1) };
        this.snake.push(map[this.direction]);
    }
}