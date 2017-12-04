class Shape {
    get area() {
        return 0;
    }
}

class Circle extends Shape {
    constructor(radius) {
        super();
        this.radius = radius;
    }

    get area() {
        return Math.PI * this.radius * this.radius;
    }
}

let circle = new Circle(10);
console.log(circle.area);
circle.area