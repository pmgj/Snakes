import {Difficulty} from "./Difficulty.js";
export class Beginner extends Difficulty {
    constructor() {
        super(200);
    }
    changeDelay(foodConsumed) {
        if (this.delay > 70 && foodConsumed % 6 === 0) {
            this.delay -= 10;
        }
    }
}
