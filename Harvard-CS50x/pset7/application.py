from cs50 import SQL
from flask import Flask, flash, redirect, render_template, request, session, url_for
from flask_session import Session
from passlib.apps import custom_app_context as pwd_context
from tempfile import mkdtemp
from passlib.context import CryptContext
from helpers import *
import helpers

# configure application
app = Flask(__name__)

# ensure responses aren't cached
if app.config["DEBUG"]:
    @app.after_request
    def after_request(response):
        response.headers["Cache-Control"] = "no-cache, no-store, must-revalidate"
        response.headers["Expires"] = 0
        response.headers["Pragma"] = "no-cache"
        return response

# custom filter
app.jinja_env.filters["usd"] = usd

# configure session to use filesystem (instead of signed cookies)
app.config["SESSION_FILE_DIR"] = mkdtemp()
app.config["SESSION_PERMANENT"] = False
app.config["SESSION_TYPE"] = "filesystem"
Session(app)

# configure CS50 Library to use SQLite database
db = SQL("sqlite:///finance.db")

@app.route("/")
@login_required
def index():
    
    id = session["user_id"]
    cash = db.execute("SELECT cash FROM users WHERE id= :id", id = id)
    cash = float(cash[0]['cash'])

    stocks = db.execute("SELECT symbol, name, price, SUM(shares) FROM sharess WHERE id = :id GROUP BY symbol HAVING SUM(shares) > 0",
                        id = id)

    for stock in stocks:
        #symbol = lookup(stock['symbol'])
        stock['total'] = stock['price'] * stock['SUM(shares)']
    
    total = 0
    for stock in stocks:
        total += stock['total']
        
    total += cash
    
    total = "${:,.2f}".format(total)
    cash = "${:,.2f}".format(cash)
    
    name = db.execute("SELECT username FROM users WHERE id=:id", id = id)
    name = name[0]['username']

    return render_template("index.html", cash = cash, stocks = stocks, total=total, name = name)

@app.route("/fx")
@login_required
def fx():
    return render_template("fx.html")
    
#@app.route("/redirects")
#@login_required
#def redirects():
    #id = session["user_id"]    
    #name = db.execute("SELECT username FROM users WHERE id=:id", id=id)
    #name = name[0]['username']
    #return render_template("redirects.html", name = name)

@app.route("/regs")
def regs():
    rows = db.execute("SELECT * from users")
    return render_template("registrants.html", registrants=rows)
        
        
        
@app.route("/unregister", methods=["GET", "POST"])
def unregister():
    if request.method == "GET":
        rows = db.execute("SELECT * from users")
        return render_template("unregister.html", registrants=rows)
    elif request.method == "POST":
        if request.form["id"]:
            id=request.form.get("id")
            db.execute("DELETE FROM users WHERE id=:id;", id=id)
        return redirect(url_for("regs"))
    
    
@app.route("/changed")
@login_required
def changed():
    return render_template("changed.html")

@app.route("/deposited")
@login_required
def deposited():
    return render_template("deposited.html")

        
@app.route("/buy", methods=["GET", "POST"])
@login_required
def buy():
    """Buy shares of stock."""
    # if user reached route via POST (as by submitting a form via POST)
    if request.method == "POST":
        # ensure username was submitted
        if not request.form.get("symbol"):
            return apology("must provide symbol")
        # ensure password was submitted
        elif not request.form.get("shares"):
            return apology("must provide shares")
        shares = int(request.form.get("shares"))
        stock = lookup(request.form.get("symbol"))
        if not stock:
            return apology("invalid symbol!")
            
        money = db.execute("SELECT cash FROM users WHERE id=:id;", id=session["user_id"])
        money = int(money[0]['cash'])    
        
        if (shares * stock['price']) > money:
            return apology("can't afford")
            
        else:
            db.execute("INSERT INTO sharess (id, shares, symbol, price, cash, name)\
            VALUES(:id, :shares, :symbol, :price, :cash, :name)",id=session["user_id"], shares=shares, symbol=stock["symbol"], price=stock["price"], cash = money, name=stock["name"])
        
            db.execute("INSERT INTO history (id, shares, symbol, price, name)\
            VALUES(:id, :shares, :symbol, :price, :name)",id=session["user_id"], shares=shares, symbol=stock["symbol"], price=stock["price"], name=stock["name"])
        
        db.execute("UPDATE users SET cash=:cash-:total_price WHERE id=:id",cash=money, total_price=stock['price']*shares, id=session["user_id"])
        # redirect user to home page
        flash('You were successfully bought the stock!')
        return redirect(url_for("index"))
   
    else:
        return render_template("buy.html")    

@app.route("/history")
@login_required
def history():
    """Show history of transactions."""
    id = session["user_id"]

    stocks = db.execute("SELECT symbol, price, shares, name, datetime FROM sharess WHERE id = :id",id = id)

    return render_template("history.html", stocks = stocks)



@app.route("/login", methods=["GET", "POST"])
def login():
    """Log user in."""

    # forget any user_id
    session.clear()

    # if user reached route via POST (as by submitting a form via POST)
    if request.method == "POST":

        # ensure username was submitted
        if not request.form.get("username"):
            return apology("must provide username")

        # ensure password was submitted
        elif not request.form.get("password"):
            return apology("must provide password")

        # query database for username
        rows = db.execute("SELECT * FROM users WHERE username = :username", username=request.form.get("username"))

        # ensure username exists and password is correct
        if len(rows) != 1 or not pwd_context.verify(request.form.get("password"), rows[0]["hash"]):
            return apology("invalid username and/or password")

        # query database for username
        db.execute("SELECT * FROM users WHERE username = :username", username=request.form.get("username"))
        # remember which user has logged in
        session["user_id"] = rows[0]["id"]
        
        flash('Welcome back!')
        # redirect user to home page
        return redirect(url_for("index"))

    # else if user reached route via GET (as by clicking a link or via redirect)
    else:
        return render_template("login.html")

@app.route("/logout")
def logout():
    """Log user out."""

    # forget any user_id
    session.clear()

    # redirect user to login form
    return redirect(url_for("login"))

@app.route("/quote", methods=["GET", "POST"])
#@login_required
def quote():
    """Get stock quote."""
    # if user reached route via POST (as by submitting a form via POST)
    if request.method == "POST":
        # ensure username was submitted
        if not request.form.get("symbol"):
            return apology("empty symbol!")
        stock = lookup(request.form.get("symbol"))
        if not stock:
            return apology("invalid symbol!")
        #price = usd(stock.price)
        return render_template("quoted.html", stock=stock)
    else:            
        return render_template("quote.html")

@app.route("/register", methods=["GET", "POST"])
def register():
    # forget any user_id
    session.clear()
    # if user reached route via POST (as by submitting a form via POST)
    if request.method == "POST":
        # ensure username was submitted
        if not request.form.get("username"):
            return apology("must provide username")
        # ensure password was submitted
        elif not request.form.get("password"):
            return apology("must provide password")
        # ensure password was submitted        
        elif not request.form.get("password2"):
            return apology("must provide password")
        # ensure password was submitted 
        if request.form.get("password") != request.form.get("password2"):
            return apology("passwords should be the same!")        
        # query database for username
        rows = db.execute("SELECT * FROM users WHERE username = :username", username=request.form.get("username"))
        # ensure username exists and password is correct        
        if len(rows) == 1:
            return apology("username exists")
        # ensure doesn't exist
        if len(rows) != 1:
            #encrypt pass and save them to db
            encrypted=pwd_context.encrypt(request.form.get("password"))
            db.execute("INSERT INTO users (username, hash) VALUES(:username, :hash)",username=request.form.get("username"),hash=encrypted)
        # query database for username
        rows = db.execute("SELECT * FROM users WHERE username = :username", username=request.form.get("username"))
        # remember which user has logged in
        session["user_id"] = rows[0]["id"]
        # redirect user to home page
        return redirect(url_for("index"))
        # else if user reached route via GET (as by clicking a link or via redirect)
    else:
        return render_template("register.html")

@app.route("/sell", methods=["GET", "POST"])
@login_required
def sell():
    """Sell shares of stock."""
    # if user reached route via POST (as by submitting a form via POST)
    id = session["user_id"]
    if request.method == "POST":
        # ensure username was submitted
        if not request.form.get("symbol"):
            return apology("must provide symbol")
        # ensure password was submitted
        elif not request.form.get("shares"):
            return apology("must provide shares")
        sharesx = int(request.form.get("shares"))
        stock = lookup(request.form.get("symbol"))
        if not stock:
            return apology("invalid symbol!")
            
        # is the stock in the portfolio?
        stocks_held = db.execute("SELECT SUM(shares) FROM sharess WHERE id=:id AND symbol=:symbol;",id=id, symbol=stock['symbol'])
        if not stocks_held[0]['SUM(shares)'] :
            return apology("you don't own this stock")

        money = db.execute("SELECT cash FROM users WHERE id=:id;", id=id)
        money = int(money[0]['cash'])

        # is shares less or = to the stocks held?
        if sharesx > stocks_held[0]['SUM(shares)']:
            return apology("you don't own that many stocks")
     
        else:
            db.execute("INSERT INTO sharess (id, shares, symbol, price, cash, name)\
            VALUES(:id, :shares, :symbol, :price, :cash, :name)",id=session["user_id"], shares=-sharesx, symbol=stock["symbol"], price=stock["price"], cash = money+stock["price"]*sharesx, name=stock["name"])

            # update cash
            db.execute("UPDATE users SET cash = cash + :total_price WHERE id = :id;", total_price=sharesx*stock['price'], id=id)
            
            flash('You were successfully sold the stock!')
            return redirect(url_for("index"))            

    else:
        return render_template("sell.html") 
        
    
@app.route("/change", methods=["GET", "POST"])
@login_required
def change():
    #return apology("TODO")
    id = session["user_id"]
    # if user reached route via POST (as by submitting a form via POST)
    if request.method == "POST":
        
        # ensure password was submitted
        if not request.form.get("password"):
            return apology("must provide the old password")
            
        # query database for username
        rows = db.execute("SELECT * FROM users WHERE id = :id", id=id)

        # ensure username exists and password is correct
        if len(rows) != 1 or not pwd_context.verify(request.form.get('password'), rows[0]['hash']):
            return apology("old password invalid")
            
        # ensure password2 was submitted        
        elif not request.form.get("password2"):
            return apology("must provide the new password")
    
        encrypted2=pwd_context.encrypt(request.form.get("password2"))
        
        db.execute("UPDATE users SET hash=:hashes WHERE id=:id",id=id, hashes=encrypted2)
         
        return redirect(url_for('logout'))            
    
        # else if user reached route via GET (as by clicking a link or via redirect)
    else:
        return render_template("change.html")
        
@app.route("/uchange", methods=["GET", "POST"])
@login_required
def uchange():
    #return apology("TODO")
    id = session["user_id"]
    if request.method == "POST":
        
        # ensure password was submitted
        if not request.form.get("name"):
            return apology("must provide the old name")
            
        # query database for username
        rows = db.execute("SELECT * FROM users WHERE id = :id", id=id)

        # ensure username exists and password is correct
        if len(rows) != 1 or not request.form.get('name') == rows[0]['username']:
            return apology("old username invalid")
            
        # ensure password2 was submitted        
        elif not request.form.get("name2"):
            return apology("must provide the new username")    
        
        # query database for username
        rowss = db.execute("SELECT * FROM users WHERE username = :username", username=request.form.get("name2"))
        # ensure username exists and password is correct        
        if len(rowss) == 1:
            return apology("username already taken")
            
        else:
            db.execute("UPDATE users SET username=:username WHERE id=:id",id=id, username=request.form.get('name2'))
            
            return redirect(url_for('logout'))               
    
        # else if user reached route via GET (as by clicking a link or via redirect)
    else:
        return render_template("uchange.html")
        
@app.route("/deposit", methods=["GET", "POST"])
@login_required
def deposit():
    #return apology("TODO")
    id = session["user_id"]
    # if user reached route via POST (as by submitting a form via POST)
    if request.method == "POST":
        
        # ensure password was submitted
        if not request.form.get("amount"):
            return apology("must provide amount")
            
        # query database for username
        rows = db.execute("SELECT * FROM users WHERE id = :id", id=id)
        rows = rows[0]['cash']
        amount = request.form.get("amount")

        db.execute("UPDATE users SET cash=:casha+:cashes WHERE id=:id",id=id, cashes=amount, casha=rows)
        
        flash('Deposited!')
        return redirect(url_for('index'))            
    
        # else if user reached route via GET (as by clicking a link or via redirect)
    else:
        return render_template("deposit.html")
