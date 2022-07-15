import Difficulty from "./Difficulty.js";

export default class Advanced extends Difficulty {
    constructor() {
        super(100);
    }
    changeDelay(foodConsumed) {
        if (this.delay > 30 && foodConsumed % 4 === 0) {
            this.delay -= 20;
        }
    }
}
