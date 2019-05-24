// Store frame for motion functions
var previousFrame = null;
var paused = true;
var a, b;
// Setup Leap loop with frame callback function
var controllerOptions = {enableGestures: true};
var actions = ["Hand Left/Right", "Hand Up/Down", "Hand Roll", "Pinky finger", "Ring finger", "Middle finger", "Index finger", "Thumb"];
var iteration = 0;

Leap.loop(controllerOptions, function(frame) {
    var dataActions = [];
    var dataValue = [];

    if (paused) {
        return; // Skip this update
    }

    // Display Hand object data
    if (frame.hands.length > 0) {
        for (var i = 0; i < frame.hands.length; i++) {
            var hand = frame.hands[i];
            var handId;

            if(hand.type === "right") {
                handId = hand.id;

                if(document.getElementById("flag" + String(actions.indexOf("Hand Left/Right"))).value === "1") {
                    document.getElementById("Hand Left/Right").innerText = hand.palmPosition[0];
                    dataActions[dataActions.length] = "Hand Left/Right";
                    dataValue[dataValue.length] = hand.palmPosition[0];
                }else{
                    document.getElementById("Hand Left/Right").innerText = "";
                }

                if(document.getElementById("flag" + String(actions.indexOf("Hand Up/Down"))).value === "1") {
                    document.getElementById("Hand Up/Down").innerText = hand.palmPosition[1];
                    dataActions[dataActions.length] = "Hand Up/Down";
                    dataValue[dataValue.length] = hand.palmPosition[1];
                }else{
                    document.getElementById("Hand Up/Down").innerText = "";
                }

                if(document.getElementById("flag" + String(actions.indexOf("Hand Roll"))).value === "1") {
                    document.getElementById("Hand Roll").innerText = hand.roll() * (180 / Math.PI);
                    dataActions[dataActions.length] = "Hand Roll";
                    dataValue[dataValue.length] = hand.roll() * (180 / Math.PI);
                }else{
                    document.getElementById("Hand Roll").innerText = "";
                }
            }
        }
    }

    // Display Pointable (finger and tool) object data
    if (frame.pointables.length > 0) {
        var fingerTypeMap = ["Thumb", "Index finger", "Middle finger", "Ring finger", "Pinky finger"];
        var boneTypeMap = ["Metacarpal", "Proximal phalanx", "Intermediate phalanx", "Distal phalanx"];
        for (var j = 0; j < frame.pointables.length; j++) {
            var pointable = frame.pointables[j];

            if (!pointable.tool) {
                if(pointable.handId === handId) {
                    pointable.bones.forEach(function (bone) {
                        if(fingerTypeMap[pointable.type] === "Thumb") {
                            if (boneTypeMap[bone.type] === "Intermediate phalanx") {
                                a = bone.direction();
                            }else {
                                if (boneTypeMap[bone.type] === "Proximal phalanx") {
                                    b = bone.direction();
                                }
                            }
                        }else{
                            if (boneTypeMap[bone.type] === "Metacarpal") {
                                a = bone.direction();
                            } else {
                                if (boneTypeMap[bone.type] === "Proximal phalanx") {
                                    b = bone.direction();
                                }
                            }
                        }
                    });
                    var crossProduct = Leap.vec3.create();
                    Leap.vec3.cross(crossProduct, a, b);
                    crossProduct = Math.asin(getMagnitude(crossProduct)) * (180 / Math.PI);
                    if(document.getElementById("flag" + String(actions.indexOf(fingerTypeMap[pointable.type]))).value === "1") {
                        document.getElementById(String(fingerTypeMap[pointable.type])).innerText = crossProduct;
                        dataActions[dataActions.length] = fingerTypeMap[pointable.type];
                        dataValue[dataValue.length] = crossProduct;
                    }else{
                        document.getElementById(String(fingerTypeMap[pointable.type])).innerText = "";
                    }
                }
            }
        }
    }

    if(iteration % 100 === 0) {
        if(dataActions.length !== 0 && dataValue.length !== 0) {
            $.ajax({
                type: "POST",
                url: "/action/sendData",
                data: {queryData : JSON.stringify({actions: dataActions, values: dataValue})},
                cache: false
            })
        }
        // console.log(iteration);
    }

    // Store frame for motion functions
    previousFrame = frame;

    if(dataActions.length !== 0 && dataValue.length !== 0) {
        iteration++;
    }
});

function getMagnitude(v1) {
    return Math.sqrt(v1[0] * v1[0] + v1[1] * v1[1] + v1[2] * v1[2]);
}

function vectorToString(vector, digits) {
    if (typeof digits === "undefined") {
        digits = 1;
    }
    return "(" + vector[0].toFixed(digits) + ", "
        + vector[1].toFixed(digits) + ", "
        + vector[2].toFixed(digits) + ")";
}

function togglePause(list) {
    paused = !paused;
    if (paused) {
        document.getElementById("pause").innerText = "Resume";
        document.getElementById("pause").style.backgroundColor = "lightpink";
    } else {
        document.getElementById("pause").innerText = "Pause";
        document.getElementById("pause").style.backgroundColor = "aquamarine";
    }
}