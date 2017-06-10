/**
 * Created by Bernard on 9-6-2017.
 */
public class Expr {

}

class Num extends Expr {
    float n;

    public Num(float n) {
        this.n = n;
    }
}

class Plus extends Expr {
    Expr e1;
    Expr e2;

    public Plus(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
}

class Sub extends Expr {
    Expr e1;
    Expr e2;

    public Sub(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
}

class Min extends Expr {
    Expr e;

    public Min(Expr e) {
        this.e = e;
    }
}

class Div extends Expr {
    Expr e1;
    Expr e2;

    public Div(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
}

class Mul extends Expr {
    Expr e1;
    Expr e2;

    public Mul(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
}

class Mod extends Expr {
    Expr e1;
    Expr e2;

    public Mod(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
}

class Pow extends Expr {
    Expr e1;
    Expr e2;

    public Pow(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
}

class Fac extends Expr {
    Expr e;

    public Fac(Expr e) {
        this.e = e;
    }
}

class Brack extends Expr {
    Expr e;

    public Brack(Expr e) {
        this.e = e;
    }
}
