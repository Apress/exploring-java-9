let random = {
  [Symbol.iterator]() {
    return {
      next() {
        return { done: false, value: Math.random() }
      }
    }
  }
}

let result = [];
for (var n of random) {
  if (result.length >= 10)
    break;
  result.push(n);
}

result