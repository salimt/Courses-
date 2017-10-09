from flask import Flask, redirect, render_template, request, url_for

import helpers
from analyzer import Analyzer
import csv
import os
import sys
import helpers
from analyzer import Analyzer
from termcolor import colored

app = Flask(__name__)

@app.route("/")
def index():
    return render_template("index.html")

@app.route("/search")
def search():

    # validate screen_name
    screen_name = request.args.get("screen_name", "")
    if not screen_name:
        return redirect(url_for("index"))

    # get screen_name's tweets
    tweets = helpers.get_user_timeline(screen_name, 50)

    # TODO
    positive, negative, neutral = 0.0, 0.0, 0.0
    
        # absolute paths to lists
    positives = os.path.join(sys.path[0], "positive-words.txt")
    negatives = os.path.join(sys.path[0], "negative-words.txt")

    # instantiate analyzer
    analyzer = Analyzer(positives, negatives)
    final_Score = 0
    # analyze word
    for text in tweets:
        score = analyzer.analyze(text)
        if score > 0.0:
            final_Score += 1
            positive += 1
        elif score < 0.0:
            final_Score -= 1
            negative += 1
        else:
            neutral += 1
    print(final_Score)
    
    
    file = open("tweet.csv", "a", newline='')
    writer = csv.writer(file)
    writer.writerow((request.args.get("screen_name", ""), final_Score,positive,negative,neutral, tweets))
    file.close()

    # generate chart
    chart = helpers.chart(positive, negative, neutral)

    # render results
    return render_template("search.html", chart=chart, screen_name=screen_name)
