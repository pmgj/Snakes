import {State} from "./State.js";
import {CellState} from "./CellState.js";
import {Direction} from "./Direction.js";
import {Cell} from "./Cell.js";
import {Beginner} from "../difficulty/Beginner.js";

function SnakeGame(w, h) {
    var board = Array(w).fill().map(() => Array(h).fill(CellState.EMPTY));
    var direction;
    var width = w;
    var height = h;
    var snake;
    var fruit;
    var state = State.START;
    var fruitConsumed;
    var diff = new Beginner();
    function getFruit() {
        return fruit;
    }
    function getSnake() {
        return snake;
    }
    function getDirection() {
        return direction;
    }
    function startGame() {
        createSnake();
        createFruit();
        state = State.GAME;
        direction = Direction.RIGHT;
        fruitConsumed = 0;
        showBoard();
    }
    function setDirection(d) {
        if ((d === Direction.LEFT && direction !== Direction.RIGHT)
                || (d === Direction.RIGHT && direction !== Direction.LEFT)
                || (d === Direction.UP && direction !== Direction.DOWN)
                || (d === Direction.DOWN && direction !== Direction.UP)) {
            direction = d;
        }
    }
    function updateBoard() {
        if (state === State.GAME) {
            move();
            checkFood();
            if (checkCollision()) {
                state = State.END;
            }
            showBoard();
        }
    }
    function getFruitConsumed() {
        return fruitConsumed;
    }
    function getState() {
        return state;
    }
    function getDiff() {
        return diff;
    }
    function setDiff(d) {
        diff = d;
    }
    function getBoard() {
        return board;
    }
    function createSnake() {
        snake = [new Cell(0, 0), new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4)];
    }
    function createFruit() {
        fruit = new Cell(Math.floor(Math.random() * width), Math.floor(Math.random() * height));
        if (snake.some(b => b.equals(fruit))) {
            createFruit();
        }
    }
    function showBoard() {
        // Board
        for (let i = 0; i < width; i++) {
            board[i].fill(CellState.EMPTY);
        }
        // Snake
        snake.forEach(c => {
            if (onBoard(c)) {
                board[c.getX()][c.getY()] = CellState.SNAKE;
            }
        });
        // Fruit
        board[fruit.getX()][fruit.getY()] = CellState.FRUIT;
    }
    function onBoard( {x, y}) {
        let inLimit = (value, limit) => value >= 0 && value < limit;
        return (inLimit(x, board.length) && inLimit(y, board[0].length));
    }
    function checkCollision() {
        let head = snake.slice(-1)[0];
        let body = snake.slice(0, snake.length - 1);
        return body.some(b => b.equals(head)) || !onBoard(head);
    }
    function checkFood() {
        let head = snake.slice(-1)[0];
        if (head.equals(fruit)) {
            fruitConsumed++;
            createFruit();
            diff.changeDelay(fruitConsumed);
        } else {
            snake.shift();
        }
    }
    function move() {
        let head = snake.slice(-1)[0];
        let row = head.getX(), col = head.getY();
        let map = {DOWN: new Cell(row + 1, col), UP: new Cell(row - 1, col), LEFT: new Cell(row, col - 1), RIGHT: new Cell(row, col + 1)};
        snake.push(map[direction]);
    }
    return {getFruit, getSnake, getDirection, startGame, setDirection, updateBoard, getFruitConsumed, getState, getDiff, setDiff, getBoard};
}
export {SnakeGame};