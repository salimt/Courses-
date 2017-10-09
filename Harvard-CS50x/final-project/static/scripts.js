var module = (function () {
    var theQuestions = [{
        question: "Question 1",
        choices: ["Answer 1", "Answer 2", "Answer 3", "Answer 4", "Answer 5"],
        answer: 2
    }, {
        question: "Question 2",
        choices: ["Answer 1", "Answer 2", "Answer 3", "Answer 4", "Answer 5"],
        answer: 4
    }, {
        question: "Question 3",
        choices: ["Answer 1", "Answer 2", "Answer 3", "Answer 4", "Answer 5"],
        answer: 1
    }, {
        question: "Question 4",
        choices: ["Answer 1", "Answer 2", "Answer 3", "Answer 4", "Answer 5"],
        answer: 1
    }, {
        question: "Question 5",
        choices: ["Answer 1", "Answer 2", "Answer 3", "Answer 4", "Answer 5"],
        answer: 3
    }],
        score = 0,
        title = document.getElementById("theQuestion"),
        back = document.getElementById("back"),
        next = document.getElementById("next"),
        radioButtons = document.getElementById("options"),
        options = document.getElementsByTagName("label"),
        radios = document.getElementsByTagName("input"),
        submitError = document.getElementById("submitError"),
        scoreBtn = document.getElementById("getScore"),
        scoreBox = document.getElementById("score"),
        currentQ = 0;

    var choices = [];

    function getRadioInfo() {
        var decision = null;
        for (var i = 0; i < radios.length; i += 1) {
            if (radios[i].checked) {
                decision = radios[i].value;
            }
        }
        return decision;
    }

    back.onclick = function () {
        var decision = getRadioInfo();
        if (decision != null) choices[currentQ] = decision;
        currentQ -= 1;
        if (currentQ === 0) {
            back.style.display = "none";
        }
        if (currentQ < theQuestions.length) {
            next.style.display = "block";
            scoreBtn.style.display = "none";
        }
        title.innerHTML = theQuestions[currentQ].question;
        var a = 0;
        for (var i = 0; i < options.length; i += 1) {
            options[i].innerHTML = '<input type="radio" name="question" value="' + a + '" />' + theQuestions[currentQ].choices[a];
            a += 1;
        }
        restorePreviousAnswer();
    };

    var restorePreviousAnswer = function () {
        /*
        for (var i = 0; i < choices.length; i += 1) {
            if (choices.indexOf(choices[i]) == currentQ) {
                var prev = choices[i];
            }
        }
        for (var x = 0; x < radios.length; x += 1) {
            if (radios[x].value == prev) {
                radios[x].checked = true;
            }
        }
*/
        if (choices[currentQ] != null) radios[choices[currentQ]].checked = true
    };


    next.onclick = function () {
        var decision = getRadioInfo();
        if (decision == null) {
            submitError.style.display = "block";
            return;
        } else {
            submitError.style.display = "none";
        }
        choices[currentQ] = decision;
        currentQ += 1;
        if (currentQ > 0) {
            back.style.display = "block";
        }
        if (currentQ == theQuestions.length - 1) {
            next.style.display = "none";
            scoreBtn.style.display = "block";
        }
        title.innerHTML = theQuestions[currentQ].question;
        var a = 0;
        for (var i = 0; i < options.length; i += 1) {
            options[i].innerHTML = '<input type="radio" name="question" value="' + a + '" />' + theQuestions[currentQ].choices[a];
            a += 1;
        }
        restorePreviousAnswer();

    };

    scoreBtn.onclick = function () {
        hideAll();
        scoreBox.innerHTML = "You scored " + score + " out of " + theQuestions.length;
    };

})();