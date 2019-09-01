% a strange graph numbering problem
var 1..8: a;
var 1..8: b;
var 1..8: c;
var 1..8: d;
var 1..8: e;
var 1..8: f;
var 1..8: g;
var 1..8: h;

% Each number is used exactly once
constraint a != b;
constraint a != c;
constraint a != d;
constraint a != e;
constraint a != f;
constraint a != g;
constraint a != h;
constraint b != c;
constraint b != d;
constraint b != e;
constraint b != f;
constraint b != g;
constraint b != h;
constraint c != d;
constraint c != e;
constraint c != f;
constraint c != g;
constraint c != h;
constraint d != e;
constraint d != f;
constraint d != g;
constraint d != h;
constraint e != f;
constraint e != g;
constraint e != h;
constraint f != g;
constraint f != h;
constraint g != h;

% Adjacent vertices differ by at most two
constraint abs(a - b) >= 2;
constraint abs(a - c) >= 2;
constraint abs(a - d) >= 2;
constraint abs(b - c) >= 2;
constraint abs(b - e) >= 2;
constraint abs(b - f) >= 2;
constraint abs(c - d) >= 2;
constraint abs(c - e) >= 2;
constraint abs(c - f) >= 2;
constraint abs(c - g) >= 2;
constraint abs(d - f) >= 2;
constraint abs(d - g) >= 2;
constraint abs(e - f) >= 2;
constraint abs(e - h) >= 2;
constraint abs(f - g) >= 2;
constraint abs(f - h) >= 2;
constraint abs(g - h) >= 2;

solve satisfy;

output ["  \(b)-\(e)\n",
        " /|*|\\\n",
        "\(a)-\(c)-\(f)-\(h)\n",
        " \\|*|/\n",
        "  \(d)-\(g)\n"] ++
       ["\na = \(a); b = \(b); c = \(c); d = \(d);",
        " e = \(e); f = \(f); g = \(g); h = \(h);\n"] 

