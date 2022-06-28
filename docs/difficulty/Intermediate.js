import {Difficulty} from "./Difficulty.js";
export class Intermediate extends Difficulty {
    constructor() {
        super(150);
    }
    changeDelay(foodConsumed) {
        if (this.delay > 50 && foodConsumed % 5 === 0) {
            this.delay -= 15;
        }
    }
}
