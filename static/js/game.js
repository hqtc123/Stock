/**
 * Created by Qing on 2016/4/24.
 */
var canvas = document.getElementById("canvas");
var cans = canvas.getContext("2d");
cans.strokeStyle = "grey";
cans.strokeRect(0, 0, 360, 500);
cans.moveTo(0, 250);
cans.arc(0, 250, 28, 0.5 * Math.PI, 90 + Math.PI);
cans.stroke();

var targetInterval, arrowInterval;

var started = false;
var targetUp = 0;

var target = {x: 356, y: 10, w: 2, h: 160};
var arrow = {x: 1, y: 250, len: 50};

drawArrow = function () {
    cans.fillStyle = 'red';
    cans.fillRect(arrow.x, arrow.y, arrow.len, 1);
};
clearArrow = function () {
    cans.clearRect(arrow.x, arrow.y - 1, arrow.len, 2);
};

drawTarget = function () {
    cans.fillStyle = 'green';
    cans.fillRect(target.x, target.y, target.w, target.h);
    for (var yi = target.y, xi = target.x, hi = target.h; yi < target.y + target.h / 2; yi += 8, xi -= 2, hi -= 16) {
        cans.fillRect(xi, yi, 2, hi);
    }
};
clearTarget = function () {
    cans.clearRect(target.x - 20, target.y, 22, target.h);
};
drawArrow();
drawTarget();

moveArrow = function () {
    clearArrow();
    arrow.x += 2;
    drawArrow();
};

moveTarget = function () {
    clearTarget();
    if (targetUp == 0) {
        target.y += 2;
        if (target.y + target.h > 490) {
            targetUp = 1;
        }
    } else {
        target.y -= 2;
        if (target.y < 10) {
            targetUp = 0;
        }
    }
    drawTarget();
};


function getScore() {
    if (arrow.x + arrow.len < target.x - 2 * 9) {
        return -1;
    }
    var yDiff = Math.abs(target.y + target.h / 2 - arrow.y);
    var nCircle = Math.floor(yDiff / 8);
    var tarX = target.x - 9 * 2 + 2 * nCircle;
    if (arrow.x + arrow.len < tarX) {
        return -1;
    }
    if (10 - nCircle < 0) {
        return 0;
    }
    return 10 - nCircle;
}

startGame = function () {
    clearArrow();
    clearTarget();

    started = true;
    target.y = 10;
    arrow.x = 1;
    drawArrow();
    drawTarget();
    targetUp = 0;
    targetInterval = setInterval(moveTarget, 50);
    var mainInterval = setInterval(function () {
        if (getScore() != -1) {
            alert("you get score " + getScore());
            clearInterval(arrowInterval);
            clearInterval(targetInterval);
            clearInterval(mainInterval);
            started = false;
        }
    }, 10);
};

shoot = function () {
    if (started) {
        arrowInterval = setInterval(moveArrow, 10);
    } else {
        alert("you should click start first");
    }
};
